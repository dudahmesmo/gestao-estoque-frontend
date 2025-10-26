package DAO;

import Modelo.Emprestimos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável pelo acesso aos dados dos empréstimos no banco de dados.
 */
public class EmprestimosDAO {

    private static final Logger LOGGER = Logger.getLogger(EmprestimosDAO.class.getName());

    /**
     * Método para criar um novo empréstimo no banco de dados.
     * @param conexao Conexão com o banco de dados.
     * @param emprestimo Objeto Emprestimos contendo os dados do empréstimo.
     * @param idAmigo ID do amigo associado ao empréstimo.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public void criarEmprestimo(Connection conexao, Emprestimos emprestimo, int idAmigo) throws SQLException {
        String sql = "INSERT INTO Emprestimos (id_emprestimo, id_ferramenta, nome_ferramenta, data_emprestimo, data_devolucao_esperada, id_amigo, nome_usuario, telefone_usuario, status_emprestimo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, emprestimo.getIdEmprestimo());
            pstmt.setInt(2, emprestimo.getIdFerramenta());
            pstmt.setString(3, emprestimo.getNomeFerramenta());
            pstmt.setDate(4, new java.sql.Date(emprestimo.getDataEmprestimo().getTime()));
            pstmt.setDate(5, new java.sql.Date(emprestimo.getDataDevolucaoEsperada().getTime()));
            pstmt.setInt(6, idAmigo); // Agora usamos apenas o ID do amigo
            pstmt.setString(7, emprestimo.getNomeUsuario());
            pstmt.setString(8, emprestimo.getTelefoneUsuario());
            pstmt.setString(9, emprestimo.getStatusEmprestimo());
            pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Empréstimo criado com sucesso.");
        }
    }

    /**
     * Método para ler um empréstimo do banco de dados pelo ID.
     * @param conexao Conexão com o banco de dados.
     * @param id ID do empréstimo.
     * @return Objeto Emprestimos contendo os dados do empréstimo.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public Emprestimos lerEmprestimoPorId(Connection conexao, int id) throws SQLException {
        String sql = "SELECT * FROM Emprestimos WHERE id_emprestimo = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Emprestimos(
                    rs.getInt("id_emprestimo"),
                    rs.getInt("id_ferramenta"),
                    rs.getString("nome_ferramenta"),
                    rs.getDate("data_emprestimo"),
                    rs.getDate("data_devolucao_esperada"),
                    rs.getInt("id_amigo"), // Mantido como getInt para armazenar como int
                    rs.getString("nome_usuario"),
                    rs.getString("telefone_usuario"),
                    rs.getString("status_emprestimo")
                );
            }
            return null;
        }
    }

    /**
     * Método para atualizar os dados de um empréstimo no banco de dados.
     * @param conexao Conexão com o banco de dados.
     * @param emprestimo Objeto Emprestimos contendo os dados atualizados do empréstimo.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public void atualizarEmprestimo(Connection conexao, Emprestimos emprestimo) throws SQLException {
        String sql = "UPDATE Emprestimos SET id_ferramenta = ?, nome_ferramenta = ?, data_emprestimo = ?, data_devolucao_esperada = ?, nome_usuario = ?, telefone_usuario = ?, status_emprestimo = ? WHERE id_emprestimo = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, emprestimo.getIdFerramenta());
            pstmt.setString(2, emprestimo.getNomeFerramenta());
            pstmt.setDate(3, new java.sql.Date(emprestimo.getDataEmprestimo().getTime()));
            pstmt.setDate(4, new java.sql.Date(emprestimo.getDataDevolucaoEsperada().getTime()));
            pstmt.setString(5, emprestimo.getNomeUsuario());
            pstmt.setString(6, emprestimo.getTelefoneUsuario());
            pstmt.setString(7, emprestimo.getStatusEmprestimo());
            pstmt.setInt(8, emprestimo.getIdEmprestimo());
            pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Empréstimo atualizado com sucesso.");
        }
    }

    /**
     * Método para excluir um empréstimo do banco de dados.
     * @param conexao Conexão com o banco de dados.
     * @param id ID do empréstimo a ser excluído.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public void excluirEmprestimo(Connection conexao, int id) throws SQLException {
        String sql = "DELETE FROM Emprestimos WHERE id_emprestimo = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Empréstimo excluído com sucesso.");
        }
    }

    /**
     * Método para listar todos os empréstimos do banco de dados.
     * @param conexao Conexão com o banco de dados.
     * @return Lista de objetos Emprestimos contendo os dados dos empréstimos.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public List<Emprestimos> listarEmprestimos(Connection conexao) throws SQLException {
        List<Emprestimos> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM Emprestimos";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Emprestimos emprestimo = new Emprestimos(
                    rs.getInt("id_emprestimo"),
                    rs.getInt("id_ferramenta"),
                    rs.getString("nome_ferramenta"),
                    rs.getDate("data_emprestimo"),
                    rs.getDate("data_devolucao_esperada"),
                    rs.getInt("id_amigo"),
                    rs.getString("nome_usuario"),
                    rs.getString("telefone_usuario"),
                    rs.getString("status_emprestimo")
                );
                emprestimos.add(emprestimo);
            }
        }
        return emprestimos;
    }
}
