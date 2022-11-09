package eleicao;

import java.util.LinkedList;
import java.util.List;

public class Partido {
    
    public Partido(int numero, String sigla, String nome) {
        this.candidatos = new LinkedList<Candidato>();
        this.numero = numero;
        this.sigla = sigla;
        this.nome = nome;
    }
    
    List<Candidato> candidatos;
    int numero = 0;
    String nome;
    String sigla;
    int nrFederacaoPartido = 0;
    int qtdVotosTotal = 0;
    int qtdVotosNominais = 0;
    int qtdVotosLegenda = 0;
    int qtdCandidatosEleitos = 0;



    public List<Candidato> getCandidatos() {
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
        this.candidatos.add(candidato);
    }

    public void calculaQuantidadeDeVotos(int flag) {
        for (Candidato candidato : this.candidatos) {
            if ((candidato.getCdSitTotTurno() == 2 | candidato.getCdSitTotTurno() == 3) && candidato.getCdCargo() == flag) {
                this.qtdCandidatosEleitos++;
                
                if (candidato.getNrFederacaoPartidoCandidato() == -1) {
                    this.qtdVotosLegenda += candidato.getQtVotos();
                } else {
                    this.qtdVotosNominais += candidato.getQtVotos();
                }
            }

        }
        this.qtdVotosTotal = this.qtdVotosLegenda + this.qtdVotosNominais;
    }

    public Candidato getCandidatoMaisVotado(int flag) {
        Candidato maisVotado = null;
        for (Candidato candidato : this.candidatos) {

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
        for (Candidato candidato : this.candidatos) {

            if (candidato.getCdCargo() != flag) continue;

            if (menosVotado == null) {
                menosVotado = candidato;
            } else if (candidato.getQtVotos() < menosVotado.getQtVotos()) {
                menosVotado = candidato;
            }
        }
        return menosVotado;
    }
}