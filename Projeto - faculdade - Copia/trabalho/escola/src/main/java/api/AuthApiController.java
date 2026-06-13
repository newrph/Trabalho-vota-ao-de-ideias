package api;

import java.io.IOException;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.LoginDTO;
import model.Usuario;
import security.JwtUtil;
import service.UsuarioService;

@WebServlet("/api/auth/login")
public class AuthApiController extends HttpServlet {

    private UsuarioService service = new UsuarioService();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        
        try {
            LoginDTO login = gson.fromJson(req.getReader(), LoginDTO.class);
            Usuario usuario = service.autenticar(login.getEmail(), login.getSenha());

            if (usuario == null) {
                resp.setStatus(401);
                resp.setContentType("application/json;charset=UTF-8");
                resp.getWriter().write("{\"erro\":\"Login inválido\"}");
                return;
            }

            String token = JwtUtil.gerarToken(usuario);

            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(gson.toJson(Map.of(
                    "token", token,
                    "usuario", usuario.getNome()
            )));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}