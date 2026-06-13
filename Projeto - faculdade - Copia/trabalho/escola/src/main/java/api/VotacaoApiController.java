package api;

import com.google.gson.Gson;
import dto.ComentarioDTO;
import dto.VotoDTO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.VotacaoService;
import java.io.IOException;

@WebServlet("/api/votacao/*")
public class VotacaoApiController extends HttpServlet {

    private VotacaoService service = new VotacaoService();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        String path = req.getPathInfo();

        if ("/voto".equals(path)) {
            VotoDTO voto = gson.fromJson(req.getReader(), VotoDTO.class);
            
            // Chama a sua regra de negócio que já verifica se já votou antes de gravar!
            service.processarVoto(voto.getIdeiaId(), voto.getUsuarioId());

            resp.getWriter().write("{\"mensagem\":\"Voto processado\"}");
            return;
        }

        if ("/comentario".equals(path)) {
            ComentarioDTO comentario = gson.fromJson(req.getReader(), ComentarioDTO.class);
            
            service.processarComentario(comentario.getIdeiaId(), comentario.getTexto(), comentario.getUsuarioId());

            resp.getWriter().write("{\"mensagem\":\"Comentário registrado\"}");
            return;
        }

        resp.setStatus(404);
        resp.getWriter().write("{\"erro\":\"Rota não encontrada\"}");
    }
}