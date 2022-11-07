import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import eleicao.Candidato;

public class App {
    public static void main(String[] args) throws Exception {
        // if (args.length < 4) {
        //     System.out.println("Use: java -jar deputados.jar <opção_de_cargo> <caminho_arquivo_candidatos> <caminho_arquivo_votacao> <data>");
        //     System.exit(1);
        // }

        String caminho_arquivo_candidatos = "./in/consulta_cand_2022_ES.csv";
        String caminho_arquivo_votacao = "./in/votacao_secao_2022_ES.csv";
        String linha = "";
        String csvDivisor = ";";
        
        // Vamos controlar essa flag com a entrada futuramente
        // ela serve pra distinguir --estadual (6) e --federal (7)
        int flag=7;

        List<Candidato> candidatos = new LinkedList<>();

        String colunaDeInteresse = "NR_FEDERACAO";
        /* 
         * NM_URNA: coluna 19 (index 18)
         * CD_SIT_TOT_TURNO: coluna 57 (index 56)
         * SG_PARTIDO: coluna 29 (index 28)
         * CD_CARGO: coluna 14 (index 13)
        */

        int i = 0, j = 0;
        int index = 0;

        /* Lendo o arquivo dos candidatos */
        //TODO: mover isso para uma função separada para organizar melhor o codigo?
        try (
                InputStream is = new FileInputStream(caminho_arquivo_candidatos);
                InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
                BufferedReader br = new BufferedReader(isr);
            ) {

            while ((linha = br.readLine()) != null) {

                String[] infoCandidato = linha.split(csvDivisor);

                /* algorítimo pra tirar as aspas */
                for (j = 0; j < infoCandidato.length; j++) {
                    StringBuilder sb = new StringBuilder();
                    char[] tab = infoCandidato[j].toCharArray();
                    for (char current : tab) {
                        if (current != '"') sb.append(current);
                    }
                    String novaString = sb.toString();
                    infoCandidato[j] = novaString;
                }

                /* descobrir o index da coluna "NM_CANDDIDATO" */
                if (i == 0) {
                    while (!infoCandidato[i].equals(colunaDeInteresse)) {
                        i++;
                    }
                    index = i;
                    // System.out.println("index: " + index);
                    continue;
                }
                
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date dtNascimento = formatter.parse(infoCandidato[42]);

                Candidato novoCandidato = new Candidato(
                    infoCandidato[17], 
                    infoCandidato[18],
                    Integer.parseInt(infoCandidato[56]), 
                    infoCandidato[28],
                    Integer.parseInt(infoCandidato[13]),
                    Integer.parseInt(infoCandidato[30]),
                    dtNascimento
                );
                candidatos.add(novoCandidato);

                i++;
            }

        } catch (IOException ex) {
            System.out.println("Problemas com a cópia: " + ex);
        }



        i = 0;
        colunaDeInteresse = "NM_VOTAVEL";
        /*
         * QT_VOTOS: coluna 22 (index 21)
         * NM_VOTAVEL: coluna 21 (index 20)
         */

        /* Lendo o arquivo da votação (partido?) */
        try (
                InputStream is = new FileInputStream(caminho_arquivo_votacao);
                InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
                BufferedReader br = new BufferedReader(isr);
            ) {

            while ((linha = br.readLine()) != null) {

                String[] infoVotacao = linha.split(csvDivisor);

                /* algorítimo pra tirar as aspas */
                for (j = 0; j < infoVotacao.length; j++) {
                    StringBuilder sb = new StringBuilder();
                    char[] tab = infoVotacao[j].toCharArray();
                    for (char current : tab) {
                        if (current != '"') sb.append(current);
                    }
                    String novaString = sb.toString();
                    infoVotacao[j] = novaString;
                }

                /* descobrir o index da coluna "NM_CANDDIDATO" */
                if (i == 0) {
                    while (!infoVotacao[i].equals(colunaDeInteresse)) {
                        i++;
                    }
                    index = i;
                    // System.out.println("index: " + index);
                    continue;
                }


                candidatos.forEach(candidato -> {
                    if (candidato.getNome().equals(infoVotacao[20])) {
                        candidato.setQtVotos(candidato.getQtVotos() + Integer.parseInt(infoVotacao[21]));
                    }
                });
            }

        } catch (IOException ex) {
            System.out.println("Problemas com a cópia: " + ex);
        }

        // Colocando os candidatos em ordem
        Collections.sort(candidatos, (c1, c2) -> {
            if (c1.getQtVotos() == c2.getQtVotos()) {
                //caso tenham o mesmo numero de votos, o mais velho ganha
                return c2.getDtNascimento().compareTo(c1.getDtNascimento());
            } else {
                return c2.getQtVotos() - c1.getQtVotos();
            }
        });


        /* Processando os dados */

        /* Debug */
        // imprimeCandidatos(candidatos);

        /* Relatório 1 */
        imprimeNumeroDeVagas(candidatos, flag);
        System.out.println();
        
        /* Relatório 2 */
        imprimeCandidatosEleitos(candidatos, flag);
        System.out.println();

        /* Relatório 3 */
        imprimeCandidatosMaisVotados(candidatos, flag);
        System.out.println();
    }
    
    public static void imprimeCandidatos(List<Candidato> candidatos) {
        int i = 1;
        for (Candidato cand : candidatos) {
            System.out.println(i + " - " + cand.getNmUrnaCandidato() + " - " + cand.getCdSitTotTurno());
            i++;
        }
    }
    
    
    private static void imprimeNumeroDeVagas(List<Candidato> candidatos, int flag) {
        int vagas = 0;
        for (Candidato cand : candidatos) {
            if ((cand.getCdSitTotTurno() == 2 || cand.getCdSitTotTurno() == 3) && cand.getCdCargo() == flag) {
                vagas++;
            }
        }
        System.out.println("Número de vagas: " + vagas);
    }


    private static void imprimeCandidatosEleitos(List<Candidato> candidatos, int flag) {
        System.out.println("Deputados estaduais eleitos:");
        int i = 1;
        
        for (Candidato c : candidatos) {
            
            if (c.getCdCargo() != flag || i==31 || (c.getCdSitTotTurno() != 2 && c.getCdSitTotTurno() != 3) ) continue;

            String ehFederacao="";
            if (c.getNrFederacaoPartidoCandidato() != -1) ehFederacao = "*";

            System.out.printf(
                "%d - %s%s (%s, %,d votos)\n", 
                i,
                ehFederacao,
                c.getNmUrnaCandidato(),
                c.getSgPartidoCandidato(),
                c.getQtVotos()
            );

            i++;
        }
    }


    private static void imprimeCandidatosMaisVotados(List<Candidato> candidatos, int flag){
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        int i=1;
        for (Candidato c : candidatos) {
            
            if (c.getCdCargo() != flag || i==31) continue;

            String ehFederacao="";
            if (c.getNrFederacaoPartidoCandidato() != -1) ehFederacao = "*";

            System.out.printf(
                "%d - %s%s (%s, %,d votos)\n", 
                i,
                ehFederacao,
                c.getNmUrnaCandidato(),
                c.getSgPartidoCandidato(),
                c.getQtVotos()
            );

            i++;
        }
    }

}