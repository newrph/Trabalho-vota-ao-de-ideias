package api;

import com.google.gson.Gson;
import model.Ideia;
import service.IdeiaService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/api/ideias")
public class IdeiaApiController
        extends HttpServlet {

    private IdeiaService service =
            new IdeiaService();

    private Gson gson =
            new Gson();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        Ideia ideia =
                gson.fromJson(
                        req.getReader(),
                        Ideia.class);

        boolean sucesso =
                service.criarIdeia(ideia);

        if (sucesso) {

            resp.setStatus(201);

            resp.getWriter().write(
                    "{\"mensagem\":\"Ideia criada\"}");

        } else {

            resp.setStatus(400);

            resp.getWriter().write(
                    "{\"erro\":\"Dados inválidos\"}");
        }
    }
}