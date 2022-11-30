import java.util.Date;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import eleicao.Candidato;
import eleicao.Partido;
import io.Impressora;
import io.Leitor;

public class App {
    public static void main(String[] args) {
        
        /*======== Recebendo dados da entrada padrão =========*/
        if (args.length < 4) {
            System.out.println("Use: java -jar deputados.jar <opção_de_cargo> <caminho_arquivo_candidatos> <caminho_arquivo_votacao> <data>");
            System.exit(1);
        } 

        int flag;

        if (args[0].compareTo("--estadual")==0) flag = 7;
        else if (args[0].compareTo("--federal")==0) flag = 6;
        else flag = 0;

        String caminhoArquivoCandidatos = args[1];
        String caminhoArquivoVotacao = args[2];
        String dataDaEleicao = args[3];

        if (flag!=6 && flag!=7) {
            System.out.println("Código de deputado não reconhecido");
            System.exit(1);
        }

        /*=========== Criando variáveis importantes (maps e tipo Date) ===========*/
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dtEleicao = new Date();

        try {
            dtEleicao = formatter.parse(dataDaEleicao);
        } catch (ParseException ex) {
            System.out.println("Erro no formato de data fornecida: " + ex);
            System.exit(1);
        }
        
        Map<Integer, Candidato> candidatos = new HashMap<>(); // <NR_CANDIDATO, CANDIDATO>
        Map<Integer, Partido> partidos = new HashMap<>(); // <NR_PARTIDO, PARTIDO>
        

        /*======= Leitura dos dados ===========*/
        Leitor leitor = new Leitor();
        leitor.leArquivoCandidatos(caminhoArquivoCandidatos, candidatos, partidos, flag);
        leitor.adicionaCandidatosPartidos(candidatos, partidos);
        leitor.leArquivoVotacao(caminhoArquivoVotacao, candidatos, partidos, flag);        
        
        
        /*======== Processando os dados =========*/
        for (Partido p : partidos.values()) p.calculaQuantidadeDeVotos(flag);
        
        Impressora impressora = new Impressora(); 
        List<Candidato> candidatosOrdenados = impressora.ordenaCandidatos(candidatos, flag);
        List<Partido> partidosOrdenados = impressora.ordenaPartidos(partidos, flag);
        

        /*======== Imprimindo relatórios ========*/

        /* Relatório 1 */
        impressora.imprimeRelatorio1(candidatosOrdenados, flag);
        System.out.printf("\n");
        
        /* Relatório 2 */
        impressora.imprimeRelatorio2(candidatosOrdenados, flag);
        System.out.printf("\n");

        /* Relatório 3 */
        impressora.imprimeRelatorio3(candidatosOrdenados, flag);
        System.out.printf("\n");

        /* Relatório 4 */
        impressora.imprimeRelatorio4(candidatosOrdenados, flag);
        System.out.printf("\n");

        /* Relatório 5 */
        impressora.imprimeRelatorio5(candidatosOrdenados, flag);
        System.out.printf("\n");

        /* Relatório 6 */
        impressora.imprimeRelatorio6(partidosOrdenados, flag);
        System.out.printf("\n");

        /* Relatório 8 */
        List<Partido> partidosOrdenadosMaiorCand = impressora.ordenaPartidosPorMaiorVotoCandidato(partidosOrdenados, flag);
        impressora.imprimeRelatorio8(partidosOrdenadosMaiorCand, flag);
        System.out.printf("\n");

        /* Relatório 9 */
        impressora.imprimeRelatorio9(candidatosOrdenados, flag, dtEleicao);
        System.out.printf("\n");

        /* Relatório 10 */
        impressora.imprimeRelatorio10(candidatosOrdenados, flag);
        System.out.printf("\n");

        /* Relatório 11 */
        impressora.imprimeRelatorio11(partidosOrdenadosMaiorCand, flag);
        System.out.printf("\n");
    }
}