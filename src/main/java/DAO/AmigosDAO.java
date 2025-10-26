package DAO;

import Modelo.Amigos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelo acesso aos dados dos amigos no banco de dados.
 */
public class AmigosDAO {
    private Connection conexao;

    /**
     * Construtor que inicializa a conexão com o banco de dados.
     * @param conexao Conexão com o banco de dados.
     */
    public AmigosDAO(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Método para adicionar um novo amigo no banco de dados.
     * @param nomeAmigo Nome do amigo.
     * @param telefoneAmigo Telefone do amigo.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public void adicionarAmigo(String nomeAmigo, String telefoneAmigo) throws SQLException {
        String sql = "INSERT INTO amigos (nome_usuario, telefone_usuario) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeAmigo);
            stmt.setString(2, telefoneAmigo);
            stmt.executeUpdate();
        }
    }

    /**
     * Método para listar todos os amigos do banco de dados.
     * @return Lista de objetos Amigos contendo os dados dos amigos.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public List<Amigos> listarAmigos() throws SQLException {
        List<Amigos> amigos = new ArrayList<>();
        String sql = "SELECT * FROM amigos";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Amigos amigo = new Amigos(
                    rs.getInt("id_amigo"),
                    rs.getString("nome_usuario"),
                    rs.getString("telefone_usuario")
                );
                amigos.add(amigo);
            }
        }
        return amigos;
    }

    /**
     * Método para atualizar os dados de um amigo no banco de dados.
     * @param amigo Objeto Amigos contendo os dados atualizados do amigo.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public void atualizarAmigo(Amigos amigo) throws SQLException {
        String sql = "UPDATE amigos SET nome_usuario = ?, telefone_usuario = ? WHERE id_amigo = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, amigo.getNome());
            stmt.setString(2, amigo.getTelefone());
            stmt.setInt(3, amigo.getIdUsuario());
            stmt.executeUpdate();
        }
    }

    /**
     * Método para deletar um amigo do banco de dados.
     * @param idUsuario ID do amigo a ser deletado.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public void deletarAmigo(int idUsuario) throws SQLException {
        // Verificar empréstimos pendentes
        String sqlVerificarEmprestimos = "SELECT * FROM emprestimos WHERE id_amigo = ? AND status_emprestimo != 'Devolvido'";
        try (PreparedStatement stmtEmprestimos = conexao.prepareStatement(sqlVerificarEmprestimos)) {
            stmtEmprestimos.setInt(1, idUsuario);
            try (ResultSet rsEmprestimos = stmtEmprestimos.executeQuery()) {
                if (rsEmprestimos.next()) {
                    throw new SQLException("O amigo possui empréstimos pendentes e não pode ser deletado.");
                }
            }
        }

        // Excluir o amigo
        String sqlExcluirAmigo = "DELETE FROM amigos WHERE id_amigo = ?";
        try (PreparedStatement stmtExcluir = conexao.prepareStatement(sqlExcluirAmigo)) {
            stmtExcluir.setInt(1, idUsuario);
            stmtExcluir.executeUpdate();
        }
    }

    /**
     * Método para obter o ID do usuário com base no nome do usuário.
     * @param nomeUsuario Nome do usuário.
     * @return ID do usuário, ou -1 se não for encontrado.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public int obterIdUsuario(String nomeUsuario) throws SQLException {
        String sql = "SELECT id_amigo FROM amigos WHERE nome_usuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_amigo");
                }
            }
        }
        return -1; // Retorna -1 se o usuário não for encontrado
    }

    /**
     * Método para obter o telefone do usuário com base no nome do amigo.
     * @param nomeAmigo Nome do amigo.
     * @return Telefone do usuário, ou null se não for encontrado.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public String obterTelefoneUsuario(String nomeAmigo) throws SQLException {
        String telefoneUsuario = null;
        String sql = "SELECT telefone_usuario FROM amigos WHERE nome_usuario = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nomeAmigo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                telefoneUsuario = rs.getString("telefone_usuario");
            }
        }
        return telefoneUsuario;
    }
}
