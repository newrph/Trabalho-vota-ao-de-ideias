package service;

import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticar(String email, String senha) {
        if (email == null || senha == null || email.trim().isEmpty()) {
            return null;
        }
        return usuarioDAO.login(email, senha);
    }

    public boolean cadastrar(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getEmail() == null || usuario.getSenha() == null) {
            return false;
        }
        usuarioDAO.cadastrar(usuario);
        return true;
    }
}