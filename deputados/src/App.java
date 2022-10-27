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
                System.out.println("[nome= " + pessoa[pessoa.length-2] 
                                    + " , cidade=" + pessoa[pessoa.length-1] + "]");

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
