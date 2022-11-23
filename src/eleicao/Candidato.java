package eleicao;

import java.util.Calendar;
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
        int cdSitTotTurno,
        int cdGenero,
        boolean apenasVotosDeLegenda
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
        this.cdGenero = cdGenero;
        this.apenasVotosDeLegenda = apenasVotosDeLegenda;
    }

    Partido partioCandidato;
    int posRankingVotos;

    /* arquivo dos candidatos */
    int nrFederacaoPartidoCandidato = 0;    // -1: candidato em partido isolado    
    boolean apenasVotosDeLegenda;           // Caso esteja "Válido (legenda)" no campo NM_TIPO_DESTINACAO_VOTOS
    int cdDetalheSituacaoCand = 0;          // processar apenas os candidatos com 2 ou 16 (candidatura deferida)
    int nrPartidoCandidato = 0;             // Número do partido
    
    String sgPartidoCandidato;              // Sigla do partido do candidato
    String nmUrnaCandidato;                 // Nome do candidato na urna
    int cdSitTotTurno = 0;                  // Situação do candidato (2 ou 3 - eleito)
    int nrCandidato = 0;                    // Número do candidato
    Date dtNascimento;                      // Data de nascimento do candidato
    int cdCargo = 0;                        // Código do cargo (7 - dep estadual, 6 - dep federal)
    int cdGenero;                           // Genero do candidato (2 - masculino, 4 - feminino)
    
    
    /* arquivo da votação */
    int nrVotavel = 0; 
    // o número do candidato no caso de voto nominal ou o número do partido se for voto na legenda
    // 95, 96, 97, 98 representam casos de votos em branco, nulos ou anulados, e devem ser ignorados
    int qtVotos = 0;
    
    public int getNrPartidoCandidato() {
        return nrPartidoCandidato;
    }
    
    public boolean getApenasVotosDeLegenda() {
        return apenasVotosDeLegenda;
    }

    public void setApenasVotosDeLegenda(boolean apenasVotosDeLegenda) {
        this.apenasVotosDeLegenda = apenasVotosDeLegenda;
    }
    
    public int getCdGenero() {
        return this.cdGenero;
    }

    public Partido getPartidoCandidato() {
        return this.partioCandidato;
    }

    public void setNrVotavel(int nrVotavel) {
        this.nrVotavel = nrVotavel;
    }
    
    public int getNrVotavel() {
        return this.nrVotavel;
    }

    public int getNrCandidato() {
        return this.nrCandidato;
    }

    public int getPosRankingVotos() {
        return this.posRankingVotos;
    }

    public void setPosRankingVotos(int posRankingVotos) {
        this.posRankingVotos = posRankingVotos;
    }

    public void setPartidoCandidato(Partido partioCandidato) {
        this.partioCandidato = partioCandidato;
    }
    
    public int getNrFederacaoPartidoCandidato() {
        return this.nrFederacaoPartidoCandidato;
    }

    public Date getDtNascimento() {
        return this.dtNascimento;
    }
    
    public String getSgPartidoCandidato() {
        return this.sgPartidoCandidato;
    }

    public int getCdSitTotTurno() {
        return this.cdSitTotTurno;
    }
    
    public int getQtVotos() {
        return this.qtVotos;
    }

    public void setQtVotos(int qtVotos) {
        this.qtVotos = qtVotos;
    }

    public String getNmUrnaCandidato() {
        return this.nmUrnaCandidato;
    }

    public int getCdCargo() {
        return this.cdCargo;
    }

    public int calculaIdade(Date dtEleicao) {
        Calendar calendarioDataEleicao = Calendar.getInstance();
        calendarioDataEleicao.setTime(dtEleicao);
        Calendar calendarioDataNascimento = Calendar.getInstance();
        calendarioDataNascimento.setTime(this.dtNascimento);

        int anos = calendarioDataEleicao.get(Calendar.YEAR) - calendarioDataNascimento.get(Calendar.YEAR);

        if (calendarioDataEleicao.get(Calendar.MONTH) < calendarioDataNascimento.get(Calendar.MONTH)) {
            anos--;
        } else {
            if (calendarioDataEleicao.get(Calendar.MONTH) == calendarioDataNascimento.get(Calendar.MONTH)
                    && calendarioDataEleicao.get(Calendar.DAY_OF_MONTH) < calendarioDataNascimento.get(Calendar.DAY_OF_MONTH)) {
                anos--;
            }
        }

        return anos;
    }
}