package controller;

import dao.UsuarioDAO;
import model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        UsuarioDAO dao = new UsuarioDAO();

        Usuario usuario = dao.login(email, senha);

        if (usuario != null) {

            HttpSession session =
                    request.getSession();

            session.setAttribute("usuario", usuario);

            response.sendRedirect("home.jsp");

        } else {

            response.getWriter()
                    .println("Email ou senha inválidos");

        }
    }
}