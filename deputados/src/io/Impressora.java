package io;

import java.util.Date;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import eleicao.Candidato;
import eleicao.Partido;

public class Impressora {

    public int numeroDeVagas = 0;

    public int getnumeroDeVagas() {
        return this.numeroDeVagas;
    }

    public void setNumeroDeVagas(int numeroDeVagas) {
        this.numeroDeVagas = numeroDeVagas;
    }

    public void ordenaPartidosPorMaiorVotoCandidato(List<Partido> partidos, int flag) {
        Collections.sort(partidos, (p1, p2) -> {
            return p2.getMaiorQtdDeVotosDeUmCandidato() - p1.getMaiorQtdDeVotosDeUmCandidato();
        });
    }
        

    public void ordenaPartidos(List<Partido> partidos, int flag) {
       // Colocando os partidos em ordem decrescente de votos
        Collections.sort(partidos, (p1, p2) -> {
            if (p1.getQtdVotosTotal() == p2.getQtdVotosTotal()) {
                // caso tenham o mesmo numero de votos, o com menor número partidário ganha
                return p1.getNumero() - p2.getNumero();
            } else {
                return p2.getQtdVotosTotal() - p1.getQtdVotosTotal();
            }
        });     
    }

    public void ordenaCandidatos(List<Candidato> candidatos, int flag){
        // Colocando os candidatos em ordem
        Collections.sort(candidatos, (c1, c2) -> {
            if (c1.getQtVotos() == c2.getQtVotos()) {
                //caso tenham o mesmo numero de votos, o mais velho ganha
                return c2.getDtNascimento().compareTo(c1.getDtNascimento());
            } else {
                return c2.getQtVotos() - c1.getQtVotos();
            }
        });

        // Inserindo rankings na lista de candidatos
        int i = 1;
        for (Candidato c : candidatos){
            if (c.getCdCargo() != flag) continue;
            c.setPosRankingVotos(i);
            i++;
        }
    }

    public void imprimeCandidato(Candidato c, int i){
        // O argumento "i" faz referencia a qual indice será colocado antes do nome do candidato.
        //   -> Para i=-1 será colocado a posição do candidato no ranking de votos
        //   -> Para qualquer outro valor será usado o i como indice
        String ehFederacao="";
        if (c.getNrFederacaoPartidoCandidato() != -1) ehFederacao = "*";

        Locale localeBR = new Locale("pt","BR");
        NumberFormat nf = NumberFormat.getNumberInstance(localeBR);

        System.out.printf(
            "%d - %s%s (%s, ", 
            i==-1?c.getPosRankingVotos():i,
            ehFederacao,
            c.getNmUrnaCandidato(),
            c.getSgPartidoCandidato()
        );
        System.out.println(nf.format(c.getQtVotos()) + " votos)");
    }

    /* Debug */
    public void imprimeCandidatos(List<Candidato> candidatos) {
        int i = 1;
        for (Candidato cand : candidatos) {
            this.imprimeCandidato(cand, i);
            i++;
        }
    }

    /* Debug */
    public void imprimePartidos(List<Partido> partidos) {
        for (Partido partido : partidos) {
            System.out.println(partido.getNumero() + " - " + partido.getSigla() + " - " + partido.getQtdVotosTotal());
        }
    }

    
    public void imprimeRelatorio1(List<Candidato> candidatos, int flag) {
        int vagas = 0;
        for (Candidato cand : candidatos) {
            if ((cand.getCdSitTotTurno() == 2 || cand.getCdSitTotTurno() == 3) && cand.getCdCargo() == flag) {
                vagas++;
            }
        }
        this.setNumeroDeVagas(vagas);
        System.out.println("Número de vagas: " + vagas);
    }


    public void imprimeRelatorio2(List<Candidato> candidatos, int flag) {
        System.out.printf("Deputados %s eleitos:\n", flag==7?"estaduais":"federais");
        int i = 1;
        
        for (Candidato c : candidatos) {
            
            if (c.getCdCargo() != flag || i > this.numeroDeVagas || (c.getCdSitTotTurno() != 2 && c.getCdSitTotTurno() != 3) ) continue;

            this.imprimeCandidato(c, i);

            i++;
        }
    }

    public void imprimeRelatorio3(List<Candidato> candidatos, int flag) {
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        int i=1;
        for (Candidato c : candidatos) {
            
            if (c.getCdCargo() != flag || i > this.numeroDeVagas) continue;

            this.imprimeCandidato(c, -1);

            i++;
        }
    }

    public void imprimeRelatorio4(List<Candidato> candidatos, int flag) {
        System.out.printf("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.printf("\n(com sua posição no ranking de mais votados)\n");
        
        for (Candidato c : candidatos) {
            
            if (c.getCdCargo() != flag || c.getCdSitTotTurno() == 2 || c.getCdSitTotTurno() == 3) continue;

            if (c.getPosRankingVotos() > this.numeroDeVagas) break;

            this.imprimeCandidato(c, -1);
        }

    }

    public void imprimeRelatorio5(List<Candidato> candidatos, int flag) {
        System.out.printf("Eleitos, que se beneficiaram do sistema proporcional:\n");
        System.out.printf("(com sua posição no ranking de mais votados)\n");

        for (Candidato c : candidatos) {
            
            if (
                c.getCdCargo() == flag &&
                (c.getCdSitTotTurno() == 2 || c.getCdSitTotTurno() == 3) &&
                c.getPosRankingVotos() > this.numeroDeVagas
            ) this.imprimeCandidato(c, -1);
            
            else continue;
        }      
    }

    public void imprimeRelatorio6(List<Partido> partidos, int flag) {
        System.out.printf("Votação dos partidos e número de candidatos eleitos:\n");
        int i = 1;

        Locale localeBR = new Locale("pt","BR");
        NumberFormat nf = NumberFormat.getNumberInstance(localeBR);

        for (Partido p : partidos) {
            System.out.printf("%d - %s - %d, %s %s (%s %s e %s de legenda), %d %s\n", 
                i,
                p.getSigla(),
                p.getNumero(),

                nf.format(p.getQtdVotosTotal()),
                p.getQtdVotosTotal()>1?"votos":"voto",  //tratamento de plural ou singular

                nf.format(p.getQtdVotosNominais()),
                p.getQtdVotosNominais()>1?"nominais":"nominal", //tratamento de plural ou singular

                nf.format(p.getQtdVotosLegenda()),

                p.getQtdCandidatosEleitos(),
                p.getQtdCandidatosEleitos()>1?"candidatos eleitos":"candidato eleito"
            );

            i++;
        }
    }


    public void imprimeRelatorio8(List<Partido> partidos, int flag) {
        System.out.printf("Primeiro e último colocados de cada partido:\n");
        int i = 1;

        Locale localeBR = new Locale("pt","BR");
        NumberFormat nf = NumberFormat.getNumberInstance(localeBR);

        for (Partido p : partidos) {

            /* Partidos que não possuírem candidatos com um número positivo de votos válidos devem ser ignorados */
            if (p.getQtdVotosNominais() == 0) continue;

            this.ordenaCandidatos(p.getCandidatos(), flag);
            Candidato candidatoMaisVotado = p.getCandidatoMaisVotado(flag);
            Candidato candidatoMenosVotado = p.getCandidatoMenosVotado(flag);

            System.out.printf("%d - %s - %d, %s (%d, %s %s) / %s (%d, %s %s)\n",
                i,
                p.getSigla(),
                p.getNumero(),

                candidatoMaisVotado.getNmUrnaCandidato(),
                candidatoMaisVotado.getNrCandidato(),
                nf.format(candidatoMaisVotado.getQtVotos()),
                candidatoMaisVotado.getQtVotos()>1?"votos":"voto", //tratamento de plural ou singular

                candidatoMenosVotado.getNmUrnaCandidato(),
                candidatoMenosVotado.getNrCandidato(),
                nf.format(candidatoMenosVotado.getQtVotos()),
                candidatoMenosVotado.getQtVotos()>1?"votos":"voto" //tratamento de plural ou singular
            );

            i++;
        }
    }


    public void imprimeRelatorio9(List<Candidato> candidatos, int flag, Date dtEleicao) {
        System.out.printf("Eleitos, por faixa etária (na data da eleição):\n");

        int qtdEleitosMenor30 = 0, qtdEleitosMaior30Menor40 = 0, qtdEleitosMaior40Menor50 = 0;
        int qtdEleitosMaior50Menor60 = 0, qtdEleitosMaior60 = 0, qtdEleitosTotal = 0; 

        for (Candidato c : candidatos) {
            if (
                c.getCdCargo() == flag &&
                (c.getCdSitTotTurno() == 2 || c.getCdSitTotTurno() == 3)
            ) {
                if (c.calculaIdade(dtEleicao) < 30) qtdEleitosMenor30++;
                else if (c.calculaIdade(dtEleicao) < 40) qtdEleitosMaior30Menor40++;
                else if (c.calculaIdade(dtEleicao) < 50) qtdEleitosMaior40Menor50++;
                else if (c.calculaIdade(dtEleicao) < 60) qtdEleitosMaior50Menor60++;
                else qtdEleitosMaior60++;
            }
        }

        qtdEleitosTotal = qtdEleitosMenor30 + qtdEleitosMaior30Menor40 + qtdEleitosMaior40Menor50 + qtdEleitosMaior50Menor60 + qtdEleitosMaior60;

        System.out.printf("Idade < 30: %d (%.2f%%)\n",
            qtdEleitosMenor30,
            (qtdEleitosMenor30 * 100.0) / qtdEleitosTotal);

        System.out.printf("30 <= Idade < 40: %d (%.2f%%)\n",
            qtdEleitosMaior30Menor40, 
            (qtdEleitosMaior30Menor40 * 100.0) / qtdEleitosTotal);

        System.out.printf("40 <= Idade < 50: %d (%.2f%%)\n",
            qtdEleitosMaior40Menor50,
            (qtdEleitosMaior40Menor50 * 100.0) / qtdEleitosTotal);

        System.out.printf("50 <= Idade < 60: %d (%.2f%%)\n", 
            qtdEleitosMaior50Menor60,
            (qtdEleitosMaior50Menor60 * 100.0) / qtdEleitosTotal);
        
        System.out.printf("60 <= Idade : %d (%.2f%%)\n", 
            qtdEleitosMaior60,
            (qtdEleitosMaior60 * 100.0) / qtdEleitosTotal);
    }

    public void imprimeRelatorio10(List<Candidato> candidatos, int flag) {
        System.out.printf("Eleitos, por gênero:\n");

        int qtdEleitosFeminino = 0, qtdEleitosMasculino = 0, qtdEleitosTotal = 0;

        for (Candidato c : candidatos) {
            if (
                c.getCdCargo() == flag &&
                (c.getCdSitTotTurno() == 2 || c.getCdSitTotTurno() == 3)
            ) {
                if (c.getCdGenero() == 4) qtdEleitosFeminino++;
                else qtdEleitosMasculino++;
            }
        }

        qtdEleitosTotal = qtdEleitosFeminino + qtdEleitosMasculino;

        System.out.printf("Feminino: %d (%.2f%%)\n", qtdEleitosFeminino, (qtdEleitosFeminino * 100.0) / qtdEleitosTotal);
        System.out.printf("Masculino: %d (%.2f%%)\n", qtdEleitosMasculino, (qtdEleitosMasculino * 100.0) / qtdEleitosTotal);
    }

    public void imprimeRelatorio11(List<Partido> partidos, int flag) {

        int qtdVotosNominaisDeTodosOsPartidos = 0, qtdDeVotosDeLegendaDeTodoOsPartidos = 0;
        int qtdVotosTotaisDeTodosOsPartidos = 0;

        for (Partido p : partidos) {
            qtdVotosNominaisDeTodosOsPartidos += p.getQtdVotosNominais();
            qtdDeVotosDeLegendaDeTodoOsPartidos += p.getQtdVotosLegenda();
            qtdVotosTotaisDeTodosOsPartidos += p.getQtdVotosTotal();
        }

        Locale localeBR = new Locale("pt","BR");
        NumberFormat nf = NumberFormat.getNumberInstance(localeBR);

        System.out.printf("Total de votos válidos: %s\n",
             nf.format(qtdVotosTotaisDeTodosOsPartidos));

        System.out.printf("Total de votos nominais: %s (%.2f%%)\n",
            nf.format(qtdVotosNominaisDeTodosOsPartidos),
            (qtdVotosNominaisDeTodosOsPartidos * 100.0) / qtdVotosTotaisDeTodosOsPartidos);

        System.out.printf("Total de votos de legenda: %s (%.2f%%)\n",
            nf.format(qtdDeVotosDeLegendaDeTodoOsPartidos),
            (qtdDeVotosDeLegendaDeTodoOsPartidos * 100.0) / qtdVotosTotaisDeTodosOsPartidos);
    }
}
