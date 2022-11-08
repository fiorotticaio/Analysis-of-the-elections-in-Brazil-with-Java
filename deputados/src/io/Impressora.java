package io;

import java.util.List;

import eleicao.Candidato;
import eleicao.Partido;

public class Impressora {

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
            
            if (c.getCdCargo() != flag || i==31 || (c.getCdSitTotTurno() != 2 && c.getCdSitTotTurno() != 3) ) continue;

            String ehFederacao="";
            if (c.getNrFederacaoPartidoCandidato() != -1) ehFederacao = "*";

            System.out.printf(
                "%d - %s%s (%s, %,d votos)\n", 
                i,
                ehFederacao,
                c.getNmUrnaCandidato(),
                c.getSgPartidoCandidato(),
                c.getQtVotos()
            );

            i++;
        }
    }

    public void imprimeRelatorio3(List<Candidato> candidatos, int flag) {
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        int i=1;
        for (Candidato c : candidatos) {
            
            if (c.getCdCargo() != flag || i==31) continue;

            String ehFederacao="";
            if (c.getNrFederacaoPartidoCandidato() != -1) ehFederacao = "*";

            System.out.printf(
                "%d - %s%s (%s, %,d votos)\n", 
                i,
                ehFederacao,
                c.getNmUrnaCandidato(),
                c.getSgPartidoCandidato(),
                c.getQtVotos()
            );

            i++;
        }
    }

    public void imprimeRelatorio4() {
        System.out.printf("Relatório 4\n");
    }

    public void imprimeRelatorio5() {
        System.out.printf("Relatório 5\n");
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
