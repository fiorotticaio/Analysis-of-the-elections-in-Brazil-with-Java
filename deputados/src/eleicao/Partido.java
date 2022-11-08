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


    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public int getQtdVotosTotal() {
        return qtdVotosTotal;
    }

    public void adicionaCandidato(Candidato candidato) {
        this.candidatos.add(candidato);
    }
}