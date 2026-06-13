package api;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet; // Usando a sua VotacaoService estruturada!
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Ideia;
import service.IdeiaService;
import service.VotacaoService;

@WebServlet("/api/ideias")
public class IdeiaApiController extends HttpServlet {

    private IdeiaService service = new IdeiaService();
    private VotacaoService votacaoService = new VotacaoService(); // Adicionado para trazer votos/comentários
    private Gson gson = new Gson();

    // 1. ROTA GET (ADICIONADA): Para buscar e listar todas as ideias da faculdade
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        try {
            // Puxa a listagem completa que estruturamos no seu Service
            List<Ideia> lista = votacaoService.listarIdeiasCompletas();
            
            // Transforma a lista de Java para o texto JSON automaticamente usando o GSON
            String json = gson.toJson(lista);
            
            resp.setStatus(200);
            resp.getWriter().write(json);
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"erro\":\"Erro ao listar ideias: " + e.getMessage() + "\"}");
        }
    }

    // 2. ROTA POST (Já existia): Para criar uma ideia nova
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        try {
            Ideia ideia = gson.fromJson(req.getReader(), Ideia.class);
            boolean sucesso = service.criarIdeia(ideia);

            if (sucesso) {
                resp.setStatus(201);
                resp.getWriter().write("{\"mensagem\":\"Ideia criada\"}");
            } else {
                resp.setStatus(400);
                resp.getWriter().write("{\"erro\":\"Dados inválidos\"}");
            }
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"erro\":\"Erro ao processar requisição\"}");
        }
    }
}