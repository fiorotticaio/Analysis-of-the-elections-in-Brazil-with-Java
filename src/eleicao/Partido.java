package eleicao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Partido {
    
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
    }

    /**
     * Calcula a quantidade candidatos eleitos,
     * a quantidade de votos totais e o maior número de votos de um candidato
     * em um partido
     * @param flag
     */
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

    /**
     * Descobre o candidato mais votado de um partido
     * @param candidatos
     * @param flag
     * @return Candidato mais votado do partido
     */
    public Candidato getCandidatoMaisVotado(List<Candidato> candidatos, int flag) {
        Candidato maisVotado = null;
        for (Candidato candidato : candidatos) {

            if (candidato.getCdCargo() != flag) continue;

            if (maisVotado == null) {
                maisVotado = candidato;
            } else if (candidato.getQtVotos() > maisVotado.getQtVotos()) {
                maisVotado = candidato;
            }
        }
        return maisVotado;
    }

    /**
     * Descobre o candidato menos votado de um partido
     * @param candidatos
     * @param flag
     * @return Candidato menos votado do partido
     */
    public Candidato getCandidatoMenosVotado(List<Candidato> candidatos, int flag) {
        Candidato menosVotado = null;
        for (Candidato candidato : candidatos) {

            if (candidato.getCdCargo() != flag) continue;

            if (menosVotado == null) {
                menosVotado = candidato;
            } else if (candidato.getQtVotos() < menosVotado.getQtVotos()) {
                menosVotado = candidato;
            }
        }
        return menosVotado;
    }

    /* Debug */
    public void imprimeCandidatos() {
        for (Candidato candidato : this.getCandidatos()) {
            System.out.println(candidato.getNrVotavel() + " - " + candidato.getNmUrnaCandidato() + " - " + candidato.getQtVotos());
        }
    }
}