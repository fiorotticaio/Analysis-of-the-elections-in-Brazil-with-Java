package eleicao;

import java.util.Date;

public class Candidato {

    public Candidato(
        int cdCargo, 
        int cdDetalheSituacaoCand,
        int nrCandidato,
        String nmUrnaCandidato,
        int nrPartidoCandidato,
        String sgPartidoCandidato,
        int nrFederacaoPartidoCandidato,
        Date dtNascimento,
        int cdSitTotTurno
    ) {
        this.cdCargo = cdCargo;
        this.cdDetalheSituacaoCand = cdDetalheSituacaoCand;
        this.nrCandidato = nrCandidato;
        this.nmUrnaCandidato = nmUrnaCandidato;
        this.nrPartidoCandidato = nrPartidoCandidato;
        this.sgPartidoCandidato = sgPartidoCandidato;
        this.nrFederacaoPartidoCandidato = nrFederacaoPartidoCandidato;
        this.dtNascimento = dtNascimento;
        this.cdSitTotTurno = cdSitTotTurno;
    }

    Partido partioCandidato;
    int posRankingVotos;

    /* arquivo dos candidatos */
    int cdCargo = 0;                        // Código do cargo (7 - dep estadual, 6 - dep federal)
    int cdDetalheSituacaoCand = 0;          // processar apenas os candidatos com 2 ou 16 (candidatura deferida)
    int nrCandidato = 0;                    // Número do candidato
    String nmUrnaCandidato;                 // Nome do candidato na urna
    int nrPartidoCandidato = 0;             // Número do partido
    String sgPartidoCandidato;              // Sigla do partido do candidato
    int nrFederacaoPartidoCandidato = 0;    // -1: candidato em partido isolado    
    Date dtNascimento;                      // Data de nascimento do candidato
    int cdSitTotTurno = 0;                  // Situação do candidato (2 ou 3 - eleito)
    int cdGenero = 0;                       // Genero do candidato (2 - masculino, 4 - feminino)
    
    /* arquivo da votação */
    int nrVotavel = 0; 
    // o número do candidato no caso de voto nominal ou o número do partido se for voto na legenda
    // 95, 96, 97, 98 representam casos de votos em branco, nulos ou anulados, e devem ser ignorados
    int qtVotos = 0;
    

    public void setNrVotavel(int nrVotavel) {
        this.nrVotavel = nrVotavel;
    }
    
    public int getNrVotavel() {
        return nrVotavel;
    }

    public int getNrCandidato() {
        return nrCandidato;
    }

    public int getPosRankingVotos() {
        return posRankingVotos;
    }

    public void setPosRankingVotos(int posRankingVotos) {
        this.posRankingVotos = posRankingVotos;
    }

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

    public int getCdCargo() {
        return cdCargo;
    }
}