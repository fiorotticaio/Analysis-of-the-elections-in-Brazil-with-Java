import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import eleicao.Candidato;
import eleicao.Partido;
import io.Impressora;
import io.Leitor;

public class App {
    public static void main(String[] args) throws Exception {
        // if (args.length < 4) {
        //     System.out.println("Use: java -jar deputados.jar <opção_de_cargo> <caminho_arquivo_candidatos> <caminho_arquivo_votacao> <data>");
        //     System.exit(1);
        // }

        for (String str : args) {
            System.out.println(str);
        }

        String caminhoArquivoCandidatos = "src/in/consulta_cand_2022_ES.csv";
        String caminhoArquivoVotacao = "src/in/votacao_secao_2022_ES.csv";

        // Vamos controlar essa flag com a entrada futuramente
        // ela serve pra distinguir --estadual (7) e --federal (6)
        int flag = 7;

        String dataDaEleicao = "02/10/2022";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dtEleicao = formatter.parse(dataDaEleicao);
        
        List<Candidato> candidatos = new LinkedList<>();
        List<Partido> partidos = new LinkedList<>();
        

        /*======= Leitura dos dados ===========*/
        Leitor leitor = new Leitor();
        leitor.leArquivoCandidatos(caminhoArquivoCandidatos, candidatos, partidos, flag);
        leitor.adicionaCandidatosPartidos(candidatos, partidos);
        leitor.leArquivoVotacao(caminhoArquivoVotacao, candidatos, partidos, flag);        

        
        
        /*======== Processando os dados =========*/
        // int count = 1;
        for (Partido p : partidos) {
            // System.out.print(count + " - ");
            p.calculaQuantidadeDeVotos(flag);
            // System.out.println(p.getNome());
            // p.imprimeCandidatos();
            // System.out.println();
            // count++;
        }
        
        Impressora impressora = new Impressora(); 
        
        impressora.ordenaCandidatos(candidatos, flag);
        impressora.ordenaPartidos(partidos, flag);

        /* Debug */
        // impressora.imprimeCandidatos(candidatos);
        // System.out.println();
        // impressora.imprimePartidos(partidos);
        // System.out.println();

        /* Relatório 1 */
        impressora.imprimeRelatorio1(candidatos, flag);
        System.out.printf("\n");
        
        /* Relatório 2 */
        impressora.imprimeRelatorio2(candidatos, flag);
        System.out.printf("\n");

        /* Relatório 3 */
        impressora.imprimeRelatorio3(candidatos, flag);
        System.out.printf("\n");

        /* Relatório 4 */
        impressora.imprimeRelatorio4(candidatos, flag);
        System.out.printf("\n");

        /* Relatório 5 */
        impressora.imprimeRelatorio5(candidatos, flag);
        System.out.printf("\n");

        /* Relatório 6 */
        impressora.imprimeRelatorio6(partidos, flag);
        System.out.printf("\n");

        /* Relatório 8 */
        impressora.ordenaPartidosPorMaiorVotoCandidato(partidos, flag);
        impressora.imprimeRelatorio8(partidos, flag);
        System.out.printf("\n");

        /* Relatório 9 */
        impressora.imprimeRelatorio9(candidatos, flag, dtEleicao);
        System.out.printf("\n");

        /* Relatório 10 */
        impressora.imprimeRelatorio10(candidatos, flag);
        System.out.printf("\n");

        /* Relatório 11 */
        impressora.imprimeRelatorio11(partidos, flag);
        System.out.printf("\n");
    }
}