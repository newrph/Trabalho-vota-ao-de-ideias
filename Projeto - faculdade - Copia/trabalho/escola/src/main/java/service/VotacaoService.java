package service;

import dao.IdeiaDAO; // Vamos centralizar as buscas de votos/comentários de ideias no IdeiaDAO
import model.Ideia;
import java.util.List;

public class VotacaoService {
    private IdeiaDAO ideiaDAO = new IdeiaDAO();

    public List<Ideia> listarIdeiasCompletas() {
        return ideiaDAO.listarTodasComVotosEComentarios();
    }

    public void processarVoto(int idIdeia, int idUsuario) {
        // Regra de negócio: Só vota se ainda não tiver votado
        if (!ideiaDAO.usuarioJaVotou(idUsuario, idIdeia)) {
            ideiaDAO.gravarVoto(idUsuario, idIdeia);
        }
    }

    public void processarComentario(int idIdeia, String texto, int idUsuario) {
        if (texto != null && !texto.trim().isEmpty()) {
            ideiaDAO.gravarComentario(idIdeia, texto, idUsuario);
        }
    }
    
    public int buscarDonoDaIdeia(int idIdeia) {
        return ideiaDAO.buscarDonoDaIdeia(idIdeia);
    }
}