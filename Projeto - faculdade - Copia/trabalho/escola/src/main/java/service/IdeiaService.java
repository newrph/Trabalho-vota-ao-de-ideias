package service;

import dao.IdeiaDAO;
import model.Ideia;

public class IdeiaService {
    private IdeiaDAO ideiaDAO = new IdeiaDAO();

    public boolean criarIdeia(Ideia ideia) {
        // Regra de negócio: impede títulos ou descrições vazias
        if (ideia.getTitulo() == null || ideia.getTitulo().trim().isEmpty() ||
            ideia.getDescricao() == null || ideia.getDescricao().trim().isEmpty()) {
            return false;
        }
        ideiaDAO.cadastrar(ideia);
        return true;
    }
}