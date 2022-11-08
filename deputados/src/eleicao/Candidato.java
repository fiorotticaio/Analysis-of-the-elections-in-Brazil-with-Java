package eleicao;

import java.util.Date;

public class Candidato {

    public Candidato(
        String nome, 
        String nmUrnaCandidato,
        int cdSitTotTurno,
        String sgPartidoCandidato,
        int cdCargo, 
        int nrFederacaoPartidoCandidato,
        Date dtNascimento
    ) {
        this.nome = nome;
        this.nmUrnaCandidato = nmUrnaCandidato;
        this.cdSitTotTurno = cdSitTotTurno;
        this.sgPartidoCandidato = sgPartidoCandidato;
        this.cdCargo = cdCargo;
        this.nrFederacaoPartidoCandidato = nrFederacaoPartidoCandidato;
        this.dtNascimento = dtNascimento;
    }

    String nome;
    Partido partioCandidato;

    /* arquivo dos candidatos */
    int cdCargo = 0;
    int nrCandidato = 0;
    String nmUrnaCandidato;
    int nrPartidoCandidato = 0;
    String sgPartidoCandidato;
    int nrFederacaoPartidoCandidato = 0; // -1: candidato em partido isolado    
    Date dtNascimento; 
    int cdSitTotTurno = 0; // 2 ou 3 representa candidato eleito
    int cdGenero = 0; // 2 para masculino, 4 para feminino
    
    /* arquivo da votação */
    int nrVotavel = 0; 
    // o número do candidato no caso de voto nominal ou o número do partido se for voto na legenda
    // 95, 96, 97, 98 representam casos de votos em branco, nulos ou anulados, e devem ser ignorados
    int qtVotos = 0; // no candidato ou no partido

    
    public void setPartioCandidato(Partido partioCandidato) {
        this.partioCandidato = partioCandidato;
    }

    public Partido getPartioCandidato() {
        return partioCandidato;
    }
    
    public int getNrFederacaoPartidoCandidato() {
        return nrFederacaoPartidoCandidato;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }
    
    public String getSgPartidoCandidato() {
        return sgPartidoCandidato;
    }

    public int getCdSitTotTurno() {
        return this.cdSitTotTurno;
    }
    
    public int getQtVotos() {
        return qtVotos;
    }

    public void setQtVotos(int qtVotos) {
        this.qtVotos = qtVotos;
    }

    public String getNmUrnaCandidato() {
        return this.nmUrnaCandidato;
    }

    public String getNome() {
        return nome;
    }

    public int getCdCargo() {
        return cdCargo;
    }
}