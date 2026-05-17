package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.IdeiaDAO;
import model.Ideia;
import model.Usuario;

@WebServlet("/IdeiaController")
public class IdeiaController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        System.out.println("=========================================");
        System.out.println("[RASTREIO 1] O Controller foi ACIONADO pelo formulario!");
        
        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");
        
        System.out.println("[RASTREIO 2] Dados recebidos: Titulo = " + titulo + " | Descricao = " + descricao);
        System.out.println("=========================================");

        HttpSession session = request.getSession();
        
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        int idDoUsuario;
        
        if (usuarioLogado != null) {
            idDoUsuario = usuarioLogado.getId(); 
            System.out.println("[RASTREIO] Usuario logado encontrado na sessao! ID: " + idDoUsuario);
        } else {
            idDoUsuario = 4; 
            System.out.println("[RASTREIO] Nenhum usuario na sessao. Usando ID 4 de seguranca.");
        }

        try {
            Ideia novaIdeia = new Ideia();
            novaIdeia.setTitulo(titulo);
            novaIdeia.setDescricao(descricao);
            novaIdeia.setUsuario_id(idDoUsuario); 

            System.out.println("[RASTREIO 3] Chamando o IdeiaDAO agora...");
            
            IdeiaDAO dao = new IdeiaDAO();
            dao.cadastrar(novaIdeia);

            System.out.println("[RASTREIO 4] Passou pelo DAO com sucesso!");

        } catch (Exception e) {
            System.out.println("[RASTREIO ERRO] Deu ruim no try do Controller: " + e.getMessage());
            e.printStackTrace();
        }

        // REDIRECIONAMENTO CORRIGIDO:
        // Usa o getContextPath() para garantir que va para /escola/VotacaoController
        response.sendRedirect(request.getContextPath() + "/VotacaoController");
    }
}