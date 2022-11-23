package eleicao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Partido implements Comparable<Partido> {
    
    public Partido(int numero, String sigla, String nome) {
        this.candidatos = new HashMap<>();
        this.numero = numero;
        this.sigla = sigla;
        this.nome = nome;
    }
    
    String nome;
    String sigla;
    int numero = 0;
    
    Map<Integer, Candidato> candidatos;
    
    int qtdVotosTotal = 0;
    int qtdVotosLegenda = 0;
    int qtdVotosNominais = 0;
    int nrFederacaoPartido = 0;
    int qtdCandidatosEleitos = 0;
    int maiorQtdDeVotosDeUmCandidato = 0;

    public int getMaiorQtdDeVotosDeUmCandidato() {
        return this.maiorQtdDeVotosDeUmCandidato;
    }

    public void setMaiorQtdDeVotosDeUmCandidato(int maiorQtdDeVotosDeUmCandidato) {
        this.maiorQtdDeVotosDeUmCandidato = maiorQtdDeVotosDeUmCandidato;
    }

    public void setQtdVotosNominais(int qtdVotosNominais) {
        this.qtdVotosNominais = qtdVotosNominais;
    }

    public List<Candidato> getCandidatos() {
        return new LinkedList<>(this.candidatos.values());
        // return this.candidatos;
    }

    public Map<Integer, Candidato> getCandidatosMap() {
        return this.candidatos;
    }

    public int getQtdVotosNominais() {
        return this.qtdVotosNominais;
    }

    public int getQtdVotosLegenda() {
        return this.qtdVotosLegenda;
    }

    public void setQtdVotosLegenda(int qtdVotosLegenda) {
        this.qtdVotosLegenda = qtdVotosLegenda;
    }

    public int getQtdCandidatosEleitos() {
        return this.qtdCandidatosEleitos;
    }

    public int getNumero() {
        return this.numero;
    }

    public String getNome() {
        return this.nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public int getQtdVotosTotal() {
        return this.qtdVotosTotal;
    }

    public void adicionaCandidato(Candidato candidato) {
        this.candidatos.put(candidato.getNrCandidato(), candidato);
        // this.candidatos.add(candidato);
    }

    @Override
    public int compareTo(Partido o) {
        if (this.qtdVotosTotal > o.qtdVotosTotal) {
            return -1;
        } else if (this.qtdVotosTotal < o.qtdVotosTotal) {
            return 1;
        } else {
            // critério de desempate: número
            if (this.numero < o.numero) {
                return -1;
            } else if (this.numero > o.numero) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public void calculaQuantidadeDeVotos(int flag) {

        int maiorQtdDeVotosDeUmCandidato = -1;

        for (Candidato candidato : this.getCandidatos()) {
            if ((candidato.getCdSitTotTurno() == 2 | 
                candidato.getCdSitTotTurno() == 3) && 
                candidato.getCdCargo() == flag) 
            {
                this.qtdCandidatosEleitos++;
            }

            /* já preparando pra ordenar pro relatório 8 */
            if (candidato.getCdCargo() == flag) {
                if (candidato.getQtVotos() > maiorQtdDeVotosDeUmCandidato) {
                    maiorQtdDeVotosDeUmCandidato = candidato.getQtVotos();
                }
            }
        }

        this.setMaiorQtdDeVotosDeUmCandidato(maiorQtdDeVotosDeUmCandidato);
        this.qtdVotosTotal = this.qtdVotosLegenda + this.qtdVotosNominais;
    }

    public Candidato getCandidatoMaisVotado(int flag) {
        Candidato maisVotado = null;
        for (Candidato candidato : this.getCandidatos()) {

            if (candidato.getCdCargo() != flag) continue;

            if (maisVotado == null) {
                maisVotado = candidato;
            } else if (candidato.getQtVotos() > maisVotado.getQtVotos()) {
                maisVotado = candidato;
            }
        }
        return maisVotado;
    }

    public Candidato getCandidatoMenosVotado(int flag) {
        Candidato menosVotado = null;
        for (Candidato candidato : this.getCandidatos()) {

            if (candidato.getCdCargo() != flag) continue;

            if (menosVotado == null) {
                menosVotado = candidato;
            } else if (candidato.getQtVotos() < menosVotado.getQtVotos()) {
                menosVotado = candidato;
            }
        }
        return menosVotado;
    }

    public void imprimeCandidatos() {
        for (Candidato candidato : this.getCandidatos()) {
            System.out.println(candidato.getNrVotavel() + " - " + candidato.getNmUrnaCandidato() + " - " + candidato.getQtVotos());
        }
    }
}