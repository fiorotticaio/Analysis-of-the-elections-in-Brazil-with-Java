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
        // ela serve pra distinguir --estadual (6) e --federal (7)
        int flag = 7;
        
        List<Candidato> candidatos = new LinkedList<>();
        List<Partido> partidos = new LinkedList<>();
        

        /*======= Leitura dos dados ===========*/
        Leitor leitor = new Leitor();
        leitor.leArquivoCandidatos(caminhoArquivoCandidatos, candidatos, partidos);
        leitor.leArquivoVotacao(caminhoArquivoVotacao, candidatos);


        // Colocando os candidatos em ordem
        Collections.sort(candidatos, (c1, c2) -> {
            if (c1.getQtVotos() == c2.getQtVotos()) {
                //caso tenham o mesmo numero de votos, o mais velho ganha
                return c2.getDtNascimento().compareTo(c1.getDtNascimento());
            } else {
                return c2.getQtVotos() - c1.getQtVotos();
            }
        });


        /*======== Processando os dados =========*/
        Impressora impressora = new Impressora(); 

        /* Debug */
        // impressora.imprimeCandidatos(candidatos);
        // System.out.println();
        impressora.imprimePartidos(partidos);
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
    }
}