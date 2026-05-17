package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Ideia;
import model.Usuario; // Importa o seu modelo de Usuario

@WebServlet("/VotacaoController")
public class VotacaoController extends HttpServlet {

    private final String URL = "jdbc:mysql://localhost:3306/sistema_votacao?useTimezone=true&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Ideia> listaIdeias = new ArrayList<>();
        Map<Integer, List<String>> mapaComentarios = new HashMap<>();
        Map<Integer, Integer> mapaVotos = new HashMap<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String sqlIdeias = "SELECT id, titulo, descricao, usuario_id FROM ideias";
            PreparedStatement stmtIdeias = conn.prepareStatement(sqlIdeias);
            ResultSet rsIdeias = stmtIdeias.executeQuery();

            while (rsIdeias.next()) {
                int idIdeia = rsIdeias.getInt("id");
                
                Ideia ideia = new Ideia();
                ideia.setId(idIdeia);
                ideia.setTitulo(rsIdeias.getString("titulo"));
                ideia.setDescricao(rsIdeias.getString("descricao"));
                ideia.setUsuario_id(rsIdeias.getInt("usuario_id"));
                listaIdeias.add(ideia);

                String sqlContarVotos = "SELECT COUNT(*) AS total FROM votos WHERE ideia_id = ?";
                PreparedStatement stmtVotos = conn.prepareStatement(sqlContarVotos);
                stmtVotos.setInt(1, idIdeia);
                ResultSet rsVotos = stmtVotos.executeQuery();
                int totalVotos = 0;
                if (rsVotos.next()) {
                    totalVotos = rsVotos.getInt("total");
                }
                rsVotos.close();
                stmtVotos.close();
                mapaVotos.put(idIdeia, totalVotos);

                List<String> listaTextos = new ArrayList<>();
                String sqlComentarios = "SELECT texto FROM comentarios WHERE ideia_id = ?";
                PreparedStatement stmtComentarios = conn.prepareStatement(sqlComentarios);
                stmtComentarios.setInt(1, idIdeia);
                ResultSet rsComentarios = stmtComentarios.executeQuery();
                
                while (rsComentarios.next()) {
                    listaTextos.add(rsComentarios.getString("texto"));
                }
                
                rsComentarios.close();
                stmtComentarios.close();
                mapaComentarios.put(idIdeia, listaTextos);
            }

            rsIdeias.close();
            stmtIdeias.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("listaIdeias", listaIdeias);
        request.setAttribute("mapaComentarios", mapaComentarios);
        request.setAttribute("mapaVotos", mapaVotos);
        request.getRequestDispatcher("votos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("ideia_id");
        String textoComentario = request.getParameter("texto_comentario");
        String acaoVoto = request.getParameter("acao_voto");

        if (idStr != null) {
            int idIdeia = Integer.parseInt(idStr);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

                // 1. PEGA O OBJETO USUARIO DA SESSAO EXATAMENTE COMO NO LOGINCONTROLLER
                HttpSession sessao = request.getSession(false);
                Integer usuarioLogadoId = null;

                if (sessao != null && sessao.getAttribute("usuario") != null) {
                    Usuario usu = (Usuario) sessao.getAttribute("usuario");
                    usuarioLogadoId = usu.getId(); // Pega o ID real do usuario logado
                }

                // Plano B de seguranca caso nao ache ninguem na sessao
                if (usuarioLogadoId == null) {
                    String sqlBuscaDono = "SELECT usuario_id FROM ideias WHERE id = ?";
                    PreparedStatement stmtDono = conn.prepareStatement(sqlBuscaDono);
                    stmtDono.setInt(1, idIdeia);
                    ResultSet rsDono = stmtDono.executeQuery();
                    if (rsDono.next()) {
                        usuarioLogadoId = rsDono.getInt("usuario_id");
                    }
                    rsDono.close();
                    stmtDono.close();
                }

                // CASO 1: O usuario clicou no botao de Votar
                if (acaoVoto != null && acaoVoto.equals("votar")) {
                    
                    // Verifica se ESSE usuario especifico ja votou NESSA ideia
                    String sqlVerificaVoto = "SELECT COUNT(*) AS ja_votou FROM votos WHERE usuario_id = ? AND ideia_id = ?";
                    PreparedStatement stmtCheck = conn.prepareStatement(sqlVerificaVoto);
                    stmtCheck.setInt(1, usuarioLogadoId);
                    stmtCheck.setInt(2, idIdeia);
                    ResultSet rsCheck = stmtCheck.executeQuery();
                    
                    int jaVotou = 0;
                    if (rsCheck.next()) {
                        jaVotou = rsCheck.getInt("ja_votou");
                    }
                    rsCheck.close();
                    stmtCheck.close();
                    
                    // Se ele nao votou, grava o voto perfeitamente para este usuario
                    if (jaVotou == 0) {
                        String sqlNovoVoto = "INSERT INTO votos (usuario_id, ideia_id) VALUES (?, ?)";
                        PreparedStatement stmtNovoVoto = conn.prepareStatement(sqlNovoVoto);
                        stmtNovoVoto.setInt(1, usuarioLogadoId);
                        stmtNovoVoto.setInt(2, idIdeia);
                        stmtNovoVoto.executeUpdate();
                        stmtNovoVoto.close();
                    }
                }

                // CASO 2: O usuario enviou um comentario
                if (textoComentario != null && !textoComentario.trim().isEmpty()) {
                    String sqlNovoComentario = "INSERT INTO comentarios (ideia_id, texto, usuario_id) VALUES (?, ?, ?)";
                    PreparedStatement stmtNovoComent = conn.prepareStatement(sqlNovoComentario);
                    stmtNovoComent.setInt(1, idIdeia);
                    stmtNovoComent.setString(2, textoComentario);
                    stmtNovoComent.setInt(3, usuarioLogadoId); // Salva com o autor correto
                    stmtNovoComent.executeUpdate();
                    stmtNovoComent.close();
                }
                
                conn.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/VotacaoController");
    }
}