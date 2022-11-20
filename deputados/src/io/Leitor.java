package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import eleicao.Candidato;
import eleicao.Partido;

public class Leitor {
    
    public void leArquivoCandidatos(
            String caminhoArquivo,
            List<Candidato> candidatos,
            List<Partido> partidos,
            int flag)
    throws Exception {

        int i = 0, j = 0;
        String linha = "";
        String csvDivisor = ";";

        try (
                InputStream is = new FileInputStream(caminhoArquivo);
                InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
                BufferedReader br = new BufferedReader(isr);
            ) {

            while ((linha = br.readLine()) != null) {

                String[] infoCandidato = linha.split(csvDivisor);

                // pulando a primeira linha dos títulos das colunas
                if (i == 0) {
                    i++;
                    continue;
                }

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

                /* adiciona o partido à lista de partidos (mesmo se o voto for invalido ou nulo) */
                if (!partidoJaExiste(partidos, infoCandidato[28])) {
                    Partido novoPartido = new Partido(
                        Integer.parseInt(infoCandidato[27]),
                        infoCandidato[28],
                        infoCandidato[29]
                    );
                    partidos.add(novoPartido);   
                }
                
                /* checagem da candidatura definida ou se é voto de legenda direto */
                if (!(Integer.parseInt(infoCandidato[68])==2 || Integer.parseInt(infoCandidato[68]) == 16) && (infoCandidato[67] != "Válido (legenda)")) continue;
                
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date dtNascimento = formatter.parse(infoCandidato[42]);
                
                Candidato novoCandidato = new Candidato(
                    Integer.parseInt(infoCandidato[13]),
                    Integer.parseInt(infoCandidato[68]),
                    Integer.parseInt(infoCandidato[16]),
                    infoCandidato[18].strip(),
                    Integer.parseInt(infoCandidato[27]),
                    infoCandidato[28].strip(),
                    Integer.parseInt(infoCandidato[30]),
                    dtNascimento,
                    Integer.parseInt(infoCandidato[56]),
                    Integer.parseInt(infoCandidato[45]),
                    infoCandidato[67] == "Válido (legenda)"
                );
                
                candidatos.add(novoCandidato);

                i++;
            }

        } catch (IOException ex) {
            System.out.println("Problemas com a cópia: " + ex);
        } catch (ParseException ex) {
            System.out.println("Problemas ao converter o texto em objeto Date: " + ex);
        }
    }



    public void leArquivoVotacao(
        String caminhoArquivo,
        List<Candidato> candidatos,
        List<Partido> partidos,
        int flag
    ) throws Exception {

        int i = 0, j = 0;
        String linha = "";
        String csvDivisor = ";";

        try (
                InputStream is = new FileInputStream(caminhoArquivo);
                InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
                BufferedReader br = new BufferedReader(isr);
            ) {


            while ((linha = br.readLine()) != null) {

                // pulando a primeira linha dos títulos das colunas
                if (i == 0) {
                    i++;
                    continue;
                }    

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

                /* checando se são votos válidos */
                if (Integer.parseInt(infoVotacao[19]) == 95 ||
                    Integer.parseInt(infoVotacao[19]) == 96 ||
                    Integer.parseInt(infoVotacao[19]) == 97 ||
                    Integer.parseInt(infoVotacao[19]) == 98 ||
                    Integer.parseInt(infoVotacao[17]) != flag) continue;


                // buscando votos nominais analisando o código dos candidatos
                int existeCandidato = 0;
                for (Candidato candidato : candidatos) {
                    if (candidato.getNrCandidato() == Integer.parseInt(infoVotacao[19]) && candidato.getCdCargo() == flag) { 
                        
                        //caso o candidato tenha especificado NM_TIPO_DESTINACAO_VOTOS como "Valido (legenda)"
                        //automaticamente ele é de legenda, então sai do loop
                        if (candidato.getApenasVotosDeLegenda()) break;
                        
                        candidato.setNrVotavel(Integer.parseInt(infoVotacao[19]));

                        candidato.setQtVotos(candidato.getQtVotos() + Integer.parseInt(infoVotacao[21]));

                        candidato.getPartidoCandidato().setQtdVotosNominais(
                            candidato.getPartidoCandidato().getQtdVotosNominais() + Integer.parseInt(infoVotacao[21])
                        );

                        existeCandidato = 1;
                    }
                }

                // buscando votos de legenda analisando o código dos partidos
                if (existeCandidato == 0) {
                    for (Partido partido : partidos) {
                        if (partido.getNumero() == Integer.parseInt(infoVotacao[19])) { 
                            partido.setQtdVotosLegenda(
                                partido.getQtdVotosLegenda() + Integer.parseInt(infoVotacao[21])
                            );
                        }
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println("Problemas com a cópia: " + ex);
        }
    }



    /*========== Funções auxiliares =========*/

    public boolean partidoJaExiste(List<Partido> partidos, String sigla) {
        for (Partido partido : partidos) {
            if (partido.getSigla().equals(sigla)) return true;
        }
        return false;
    }
    
    public Partido getPartido(List<Partido> partidos, String sigla) {
        for (Partido partido : partidos) {
            if (partido.getSigla().equals(sigla)) return partido;
        }
        return null;
    }

    public void adicionaCandidatosPartidos(List<Candidato> candidatos, List<Partido> partidos) {
        for (Partido partido : partidos) {
            for (Candidato candidato : candidatos) {
                if (candidato.getSgPartidoCandidato().equals(partido.getSigla())) {
                    partido.adicionaCandidato(candidato);
                    candidato.setPartidoCandidato(partido);
                }
            }
        }
    }
}



