package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.Ideia;

public class IdeiaDAO {

    public void cadastrar(Ideia ideia) {
        // ATENÇÃO: Vamos tentar rodar o INSERT básico.
        // Se no seu banco a coluna for 'id_usuario', mude onde está 'usuario_id' abaixo!
        String sql = "INSERT INTO ideias (titulo, descricao, usuario_id) VALUES (?, ?, ?)";

        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, ideia.getTitulo());
            stmt.setString(2, ideia.getDescricao());
            
            // Se o ID que vem da sessão for 0 ou nulo, forçamos o ID 1 para o banco não rejeitar
            int idParaGravar = (ideia.getUsuario_id() > 0) ? ideia.getUsuario_id() : 1;
            stmt.setInt(3, idParaGravar);

            stmt.executeUpdate(); // Força a gravação no MySQL

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
}