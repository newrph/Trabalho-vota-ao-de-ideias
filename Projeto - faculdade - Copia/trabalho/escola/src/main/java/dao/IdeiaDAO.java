package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Ideia;

public class IdeiaDAO {

    // Seu método cadastrar original intacto (apenas usando o padrão do seu projeto)
    public void cadastrar(Ideia ideia) {
        String sql = "INSERT INTO ideias (titulo, descricao, usuario_id) VALUES (?, ?, ?)";

        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, ideia.getTitulo());
            stmt.setString(2, ideia.getDescricao());
            
            int idParaGravar = (ideia.getUsuario_id() > 0) ? ideia.getUsuario_id() : 1;
            stmt.setInt(3, idParaGravar);

            stmt.executeUpdate();

            System.out.println("=========================================");
            System.out.println("[SUCESSO] O MySQL aceitou a ideia de titulo: " + ideia.getTitulo());
            System.out.println("=========================================");

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("=========================================");
            System.out.println("[ERRO NO MYSQL] O banco de dados recusou o cadastro!");
            System.out.println("Mensagem real do erro: " + e.getMessage());
            System.out.println("=========================================");
            e.printStackTrace();
        }
    }

    // --- NOVOS MÉTODOS PARA O VOTACAOSERVICE FUNCIONAR ---

    // 1. Método para listar tudo acoplado com votos e comentários
    public List<Ideia> listarTodasComVotosEComentarios() {
        List<Ideia> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, descricao, usuario_id FROM ideias";
        
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Ideia ideia = new Ideia();
                int idIdeia = rs.getInt("id");
                ideia.setId(idIdeia);
                ideia.setTitulo(rs.getString("titulo"));
                ideia.setDescricao(rs.getString("descricao"));
                ideia.setUsuario_id(rs.getInt("usuario_id"));
                
                // Métodos auxiliares usando a mesma conexão ativa
                ideia.setTotalVotos(contarVotosAux(conn, idIdeia));
                ideia.setComentarios(listarComentariosAux(conn, idIdeia));
                
                lista.add(ideia);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println("[ERRO DAO] Erro ao listar ideias completas: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    private int contarVotosAux(Connection conn, int idIdeia) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM votos WHERE ideia_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idIdeia);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("total") : 0;
            }
        }
    }

    private List<String> listarComentariosAux(Connection conn, int idIdeia) throws SQLException {
        List<String> comentarios = new ArrayList<>();
        String sql = "SELECT texto FROM comentarios WHERE ideia_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idIdeia);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    comentarios.add(rs.getString("texto"));
                }
            }
        }
        return comentarios;
    }

    // 2. Método que verifica se o usuário já votou
    public boolean usuarioJaVotou(int idUsuario, int idIdeia) {
        String sql = "SELECT COUNT(*) AS ja_votou FROM votos WHERE usuario_id = ? AND ideia_id = ?";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idIdeia);
            ResultSet rs = stmt.executeQuery();
            
            boolean jaVotou = false;
            if (rs.next()) {
                jaVotou = rs.getInt("ja_votou") > 0;
            }
            
            rs.close();
            stmt.close();
            conn.close();
            return jaVotou;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Método para gravar o voto (Limpo, sem caracteres ocultos)
    public void gravarVoto(int idUsuario, int idIdeia) {
        String sql = "INSERT INTO votos (usuario_id, ideia_id) VALUES (?, ?)";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idIdeia);
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. Método para gravar o comentário
    public void gravarComentario(int idIdeia, String texto, int idUsuario) {
        String sql = "INSERT INTO comentarios (ideia_id, texto, usuario_id) VALUES (?, ?, ?)";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idIdeia);
            stmt.setString(2, texto);
            stmt.setInt(3, idUsuario);
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. Método para buscar dono da ideia de segurança
    public int buscarDonoDaIdeia(int idIdeia) {
        String sql = "SELECT usuario_id FROM ideias WHERE id = ?";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idIdeia);
            ResultSet rs = stmt.executeQuery();
            
            int donoId = 0;
            if (rs.next()) {
                donoId = rs.getInt("usuario_id");
            }
            
            rs.close();
            stmt.close();
            conn.close();
            return donoId;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}