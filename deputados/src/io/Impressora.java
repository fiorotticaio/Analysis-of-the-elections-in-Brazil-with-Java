package io;

import java.util.Collections;
import java.util.List;

import eleicao.Candidato;
import eleicao.Partido;

public class Impressora {

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
        int i=1;
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

        //TODO: adicionar filtro para o separador de milhar ser sempre o ponto
        System.out.printf(
            "%d - %s%s (%s, %,d votos)\n", 
            i==-1?c.getPosRankingVotos():i,
            ehFederacao,
            c.getNmUrnaCandidato(),
            c.getSgPartidoCandidato(),
            c.getQtVotos()
        );
    }

    /* Debug */
    public void imprimeCandidatos(List<Candidato> candidatos) {
        int i = 1;
        for (Candidato cand : candidatos) {
            System.out.println(i + " - " + cand.getNmUrnaCandidato() + " - " + cand.getCdSitTotTurno());
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

    public void imprimeRelatorio6() {
        System.out.printf("Relatório 6\n");
    }

    public void imprimeRelatorio7() {
        System.out.printf("Relatório 7\n");
    }

    public void imprimeRelatorio8() {
        System.out.printf("Relatório 8\n");
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
