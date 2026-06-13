package api;

import com.google.gson.Gson;
import dto.LoginDTO;
import model.Usuario;
import security.JwtUtil;
import service.UsuarioService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/auth/login")
public class AuthApiController
        extends HttpServlet {

    private UsuarioService service =
            new UsuarioService();

    private Gson gson =
            new Gson();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        LoginDTO login =
                gson.fromJson(
                        req.getReader(),
                        LoginDTO.class);

        Usuario usuario =
                service.autenticar(
                        login.getEmail(),
                        login.getSenha());

        if (usuario == null) {

            resp.setStatus(401);

            resp.getWriter().write(
                    "{\"erro\":\"Login inválido\"}");

            return;
        }

        String token =
                JwtUtil.gerarToken(usuario);

        resp.setContentType(
                "application/json");

        resp.getWriter().write(
                gson.toJson(
                        Map.of(
                                "token", token,
                                "usuario",
                                usuario.getNome()
                        )
                )
        );
    }
}