import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        // if (args.length < 4) {
        //     System.out.println("Use: java -jar deputados.jar <opção_de_cargo> <caminho_arquivo_candidatos> <caminho_arquivo_votacao> <data>");
        //     System.exit(1);
        // }

        String arquivoCSV = "./teste.csv";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ";";

        try {
            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((linha = br.readLine()) != null) {

                String[] pessoa = linha.split(csvDivisor);

                /* protótipo de algorítimo pra tirar as aspas */
                StringBuilder sb1 = new StringBuilder();
                char[] tab = pessoa[0].toCharArray();
                for (char current : tab) {
                    if (current != '"') sb1.append(current);
                }
                sb1.toString();

                StringBuilder sb2 = new StringBuilder();
                char[] tab2 = pessoa[0].toCharArray();
                for (char current : tab2) {
                    if (current != '"') sb2.append(current);
                }
                sb2.toString();

                System.out.println("[nome= " + sb1 + " , cidade=" + sb2 + "]");
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
    }
}