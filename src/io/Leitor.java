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
import java.util.Map;

import eleicao.Candidato;
import eleicao.Partido;

public class Leitor {
    
    /**
     * Lê o arquivo de candidatos
     * <li> Preenchendo os campos iniciais do mapa de candidatos e partidos
     * @param caminhoArquivo
     * @param candidatos
     * @param partidos
     * @param flag
     */
    public void leArquivoCandidatos(
            String caminhoArquivo,
            Map<Integer, Candidato> candidatos,
            Map<Integer, Partido> partidos,
            int flag) {

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
                if (!partidoJaExiste(partidos, Integer.parseInt(infoCandidato[27]))) {
                    Partido novoPartido = new Partido(
                        Integer.parseInt(infoCandidato[27]),
                        infoCandidato[28],
                        infoCandidato[29]
                    );
                    partidos.put(novoPartido.getNumero(), novoPartido);
                }
                
                /* checagem da candidatura definida ou se é voto de legenda direto */
                boolean ehLegenda = (infoCandidato[67]).compareTo("Válido (legenda)")==0?true:false;
                boolean ehValido = (Integer.parseInt(infoCandidato[68])==2 || Integer.parseInt(infoCandidato[68])==16);
                boolean ehDoEscopo = Integer.parseInt(infoCandidato[13])==flag;
                
                if ((!ehLegenda && !ehValido) || (!ehDoEscopo)) continue;
                
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date dtNascimento = formatter.parse(infoCandidato[42]);

                /* criando um novo candidato */
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
                    ehLegenda
                );
                
                /* inserindo o candidato no mapa */
                candidatos.put(Integer.parseInt(infoCandidato[16]), novoCandidato);

                i++;
            }

            br.close();

        } catch (IOException ex) {
            System.out.println("Problemas com a cópia: " + ex);
            System.exit(1);
        } catch (ParseException ex) {
            System.out.println("Problemas na conversão de arquivos: " + ex);
            System.exit(1);
        }
    }

    /**
     * Lê o arquivo de votos
     * <li> Preenche os campos de votos do mapa de candidatos e partidos
     * @param caminhoArquivo
     * @param candidatos
     * @param partidos
     * @param flag
     */
    public void leArquivoVotacao(
        String caminhoArquivo,
        Map<Integer, Candidato> candidatos,
        Map<Integer, Partido> partidos,
        int flag)  {

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
                
                int existeCandidato = 0;

                /* buscando votos nominais analisando o código dos candidatos */
                Candidato cand = candidatos.get(Integer.parseInt(infoVotacao[19]));
                if (cand != null && cand.getCdCargo() == flag) {
                    if (cand.getApenasVotosDeLegenda()) {
                        Partido partido = cand.getPartidoCandidato();
                        partido.setQtdVotosLegenda(partido.getQtdVotosLegenda() + Integer.parseInt(infoVotacao[21]));
                        
                        partido.getCandidatosMap().remove(cand.getNrCandidato(), cand);

                    } else {
                        cand.setNrVotavel(Integer.parseInt(infoVotacao[19]));
                        cand.setQtVotos(cand.getQtVotos() + Integer.parseInt(infoVotacao[21]));
                        Partido partido = cand.getPartidoCandidato();
                        partido.setQtdVotosNominais(partido.getQtdVotosNominais() + Integer.parseInt(infoVotacao[21]));
    
                        existeCandidato = 1;
                    }
                }

                /* buscando o código dos partidos para contabilizar os votos de legenda */
                if (existeCandidato == 0) {
                    Partido part = partidos.get(Integer.parseInt(infoVotacao[19]));
                    if (part != null) {
                        part.setQtdVotosLegenda(part.getQtdVotosLegenda() + Integer.parseInt(infoVotacao[21]));
                    }
                }
            }

            br.close();

        } catch (IOException ex) {
            System.out.println("Problemas com a cópia: " + ex);
            System.exit(1);
        }
    }


    /*========== Funções auxiliares =========*/

    /**
     * Confere se um partido já existe no mapa de partidos
     * @param partidos
     * @param numeroPartido
     * @return true, caso o partido já exista no mapa de partidos, false caso contrário 
     */
    private boolean partidoJaExiste(Map<Integer, Partido> partidos, int numeroPartido) {
        if (partidos.containsKey(numeroPartido)) return true;
        else return false;
    }
    
    /**
     * Adiciona um partido a seu candidato e os candidatos de um partido a ele
     * @param candidatos
     * @param partidos
     */
    public void adicionaCandidatosPartidos(Map<Integer, Candidato> candidatos, Map<Integer, Partido> partidos) {
        for (Candidato candidato : candidatos.values()) {
            Partido partido = partidos.get(candidato.getNrPartidoCandidato());
            partido.adicionaCandidato(candidato);
            candidato.setPartidoCandidato(partido);
        }
    }
}