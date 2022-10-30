package eleicao;

import java.util.Date;

public class Candidato {

    public Candidato(String nome) {
        this.nome = nome;
    }

    String nome;

    /* arquivo dos candidatos */
    int cdCargo = 0;
    int nrCandidato = 0;
    String nmUrnaCandidato;
    int nrPartidoCandidato = 0;
    String sgPartidoCandidato;
    int nrFederacaoPartidoCandidato = 0; // -1: candidato em partido isolado
    Date dtNascimento; // TODO: inicializar aqui com new?
    int cdSitTotTurno = 0; // 2 ou 3 representa candidato eleito
    int cdGenero = 0; // 2 para masculino, 4 para feminino

    /* arquivo da votação */
    int nrVotavel = 0; 
    // o número do candidato no caso de voto nominal ou o número do partido se for voto na legenda
    // 95, 96, 97, 98 representam casos de votos em branco, nulos ou anulados, e devem ser ignorados
    int qtVotos = 0; // no candidato ou no partido
    
    
    public String getNome() {
        return this.nome;
    }
}