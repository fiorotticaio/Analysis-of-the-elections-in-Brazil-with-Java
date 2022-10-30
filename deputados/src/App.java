import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import eleicao.Candidato;

public class App {

    public static void imprimeCandidatos(List<Candidato> candidatos) {
        int i = 1;
        for (Candidato cand : candidatos) {
            System.out.println(i + " - " + cand.getNome());
            i++;
        }
    }


    public static void main(String[] args) throws Exception {
        // if (args.length < 4) {
        //     System.out.println("Use: java -jar deputados.jar <opção_de_cargo> <caminho_arquivo_candidatos> <caminho_arquivo_votacao> <data>");
        //     System.exit(1);
        // }

        String arquivoCSV = "./consulta_cand_2022_ES.csv";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ";";

        List<Candidato> candidatos = new LinkedList<>();

        String colunaDeInteresse = "NM_CANDIDATO";

        int i = 0, j = 0;
        int index = 0;

        try {
            br = new BufferedReader(new FileReader(arquivoCSV));
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
                    continue;
                }

                Candidato novoCandidato = new Candidato(infoCandidato[index]);
                candidatos.add(novoCandidato);

                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        imprimeCandidatos(candidatos);
    }
}