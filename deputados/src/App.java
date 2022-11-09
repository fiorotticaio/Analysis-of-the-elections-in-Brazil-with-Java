import java.util.Collections;
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
        
        List<Candidato> candidatos = new LinkedList<>();
        List<Partido> partidos = new LinkedList<>();
        

        /*======= Leitura dos dados ===========*/
        Leitor leitor = new Leitor();
        leitor.leArquivoCandidatos(caminhoArquivoCandidatos, candidatos, partidos);
        leitor.leArquivoVotacao(caminhoArquivoVotacao, candidatos);
        
        leitor.adicionaCandidatosPartidos(candidatos, partidos);


        /*======== Processando os dados =========*/


        for (Partido p : partidos) {
            p.calculaQuantidadeDeVotos(flag);
        }

        
        Impressora impressora = new Impressora(); 
        

        // TODO: 
        //  perguntar para o prof se as funções de fora do App.java devem printar
        //  ou se devem retornar valores para serem printados aqui
        
        impressora.ordenaCandidatos(candidatos, flag);
        impressora.ordenaPartidos(partidos, flag);

        /* Debug */
        // impressora.imprimeCandidatos(candidatos);
        // System.out.println();
        // impressora.imprimePartidos(partidos);
        System.out.println();

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
        // FIXME: mano, tem alguma coisa nessa buceta q ta errado, ou o pdf ta errado
        impressora.imprimeRelatorio6(partidos, flag);
        System.out.printf("\n");

        /* Relatório 7 */
        // TODO: não tem exemplo de relatório 7 no pdf
        impressora.imprimeRelatorio7(partidos, flag);
        System.out.printf("\n");

        /* Relatório 8 */
        impressora.imprimeRelatorio8(partidos, flag);
        System.out.printf("\n");
    }
}