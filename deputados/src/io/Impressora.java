package io;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import eleicao.Candidato;
import eleicao.Partido;

public class Impressora {

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
            System.out.println(partido.getNumero() + " - " + partido.getNome() + " - " + partido.getSigla() + " - " + partido.getQtdVotosTotal());
        }
    }

    
    public void imprimeRelatorio1(List<Candidato> candidatos, int flag) {
        int vagas = 0;
        for (Candidato cand : candidatos) {
            if ((cand.getCdSitTotTurno() == 2 || cand.getCdSitTotTurno() == 3) && cand.getCdCargo() == flag) {
                vagas++;
            }
        }
        System.out.println("Número de vagas: " + vagas);
    }


    public void imprimeRelatorio2(List<Candidato> candidatos, int flag) {
        System.out.println("Deputados estaduais eleitos:");
        int i = 1;
        
        for (Candidato c : candidatos) {
            
            if (c.getCdCargo() != flag || i>30 || (c.getCdSitTotTurno() != 2 && c.getCdSitTotTurno() != 3) ) continue;

            this.imprimeCandidato(c, i);

            i++;
        }
    }

    public void imprimeRelatorio3(List<Candidato> candidatos, int flag) {
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        int i=1;
        for (Candidato c : candidatos) {
            
            if (c.getCdCargo() != flag || i>30) continue;

            this.imprimeCandidato(c, -1);

            i++;
        }
    }

    public void imprimeRelatorio4(List<Candidato> candidatos, int flag) {
        System.out.printf("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.printf("\n(com sua posição no ranking de mais votados)\n");
        
        for (Candidato c : candidatos) {
            
            if (c.getCdCargo() != flag || c.getCdSitTotTurno() == 2 || c.getCdSitTotTurno() == 3) continue;

            if (c.getPosRankingVotos() > 30) break;

            this.imprimeCandidato(c, -1);
        }

    }

    public void imprimeRelatorio5(List<Candidato> candidatos, int flag) {
        System.out.printf("Eleitos, que se beneficiaram do sistema proporcional:\n");
        System.out.printf("(com sua posição no ranking de mais votados)\n");

        for (Candidato c : candidatos) {
            
            if (
                c.getCdCargo() == flag &&
                (c.getCdSitTotTurno()==2 || c.getCdSitTotTurno()==3) &&
                c.getPosRankingVotos()>30
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
            System.out.printf("%d - %s - %d, ", 
                i,
                p.getSigla(),
                p.getNumero()
            );
            System.out.print(nf.format(p.getQtdVotosTotal()) + " votos ");
            System.out.print("(" + nf.format(p.getQtdVotosNominais()) + " nominais e ");
            System.out.print(nf.format(p.getQtdVotosLegenda()) + " de legenda), "); 
            System.out.printf("%d candidatos eleitos\n", p.getQtdCandidatosEleitos());
            i++;
        }
    }

    public void imprimeRelatorio7(List<Partido> partidos, int flag) {
        System.out.printf("Sem especificação para o relatório 7\n");
    }

    // TODO: ordenar
    public void imprimeRelatorio8(List<Partido> partidos, int flag) {
        System.out.printf("Primeiro e último colocados de cada partido:\n");
        int i = 1;

        Locale localeBR = new Locale("pt","BR");
        NumberFormat nf = NumberFormat.getNumberInstance(localeBR);

        for (Partido p : partidos) {
            System.out.printf("%d - %s - %d, ", 
                i,
                p.getSigla(),
                p.getNumero()
            );

            Candidato candidatoMaisVotado = p.getCandidatoMaisVotado(flag);
            System.out.print(candidatoMaisVotado.getNome() + " (" + candidatoMaisVotado.getNrCandidato());
            System.out.print(", " + nf.format(candidatoMaisVotado.getQtVotos()) + " votos) / ");

            Candidato candidatoMenosVotado = p.getCandidatoMenosVotado(flag);
            System.out.print(candidatoMenosVotado.getNome() + " (" + candidatoMenosVotado.getNrCandidato());
            System.out.println(", " + nf.format(candidatoMenosVotado.getQtVotos()) + " votos)");

            i++;
        }
    }

    public void imprimeRelatorio9() {
        System.out.printf("Relatório 9\n");
    }

    public void imprimeRelatorio10() {
        System.out.printf("Relatório 10\n");
    }

    public void imprimeRelatorio11() {
        System.out.printf("Relatório 11\n");
    }
}
