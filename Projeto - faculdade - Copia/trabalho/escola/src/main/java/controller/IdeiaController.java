package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Ideia;
import model.Usuario;
import service.IdeiaService;

@WebServlet("/IdeiaController")
public class IdeiaController extends HttpServlet {
    private IdeiaService ideiaService = new IdeiaService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");

        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        int idDoUsuario = (usuarioLogado != null) ? usuarioLogado.getId() : 4; 

        try {
            Ideia novaIdeia = new Ideia();
            novaIdeia.setTitulo(titulo);
            novaIdeia.setDescricao(descricao);
            novaIdeia.setUsuario_id(idDoUsuario); 

            ideiaService.criarIdeia(novaIdeia);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/VotacaoController");
    }
}