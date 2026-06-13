package api;

import com.google.gson.Gson;
import dto.ComentarioDTO;
import dto.VotoDTO;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import service.VotacaoService;

import java.io.IOException;

@WebServlet("/api/votacao/*")
public class VotacaoApiController
        extends HttpServlet {

    private VotacaoService service =
            new VotacaoService();

    private Gson gson =
            new Gson();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        String path =
                req.getPathInfo();

        if ("/voto".equals(path)) {

            VotoDTO voto =
                    gson.fromJson(
                            req.getReader(),
                            VotoDTO.class);

            service.processarVoto(
                    voto.getIdeiaId(),
                    voto.getUsuarioId());

            resp.getWriter().write(
                    "{\"mensagem\":\"Voto registrado\"}");
        }

        if ("/comentario".equals(path)) {

            ComentarioDTO comentario =
                    gson.fromJson(
                            req.getReader(),
                            ComentarioDTO.class);

            service.processarComentario(
                    comentario.getIdeiaId(),
                    comentario.getTexto(),
                    comentario.getUsuarioId());

            resp.getWriter().write(
                    "{\"mensagem\":\"Comentário registrado\"}");
        }
    }
}