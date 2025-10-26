package DAO;

import Modelo.Ferramentas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelo acesso aos dados das ferramentas no banco de dados.
 */
public class FerramentasDAO {
    private Connection conexao;

    /**
     * Construtor que inicializa a conexão com o banco de dados.
     * @param conexao Conexão com o banco de dados.
     */
    public FerramentasDAO(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Método para adicionar uma nova ferramenta no banco de dados.
     * @param ferramenta Ferramenta a ser adicionada.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public void adicionarFerramenta(Ferramentas ferramenta) throws SQLException {
        String sql = "INSERT INTO ferramentas (id_ferramenta, nome_ferramenta, marca_ferramenta, preco) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, ferramenta.getId());
            stmt.setString(2, ferramenta.getNome());
            stmt.setString(3, ferramenta.getMarca());
            stmt.setDouble(4, ferramenta.getCusto());
            stmt.executeUpdate();
        }
    }

    /**
     * Método para listar todas as ferramentas do banco de dados.
     * @return Lista de ferramentas.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public List<Ferramentas> listarFerramentas() throws SQLException {
        List<Ferramentas> ferramentas = new ArrayList<>();
        String sql = "SELECT * FROM ferramentas";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ferramentas ferramenta = new Ferramentas(
                    rs.getString("nome_ferramenta"),
                    rs.getString("marca_ferramenta"),
                    rs.getDouble("preco")
                );
                ferramentas.add(ferramenta);
            }
        }
        return ferramentas;
    }

    /**
     * Método para atualizar os dados de uma ferramenta no banco de dados.
     * @param ferramenta Ferramenta a ser atualizada.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public void atualizarFerramenta(Ferramentas ferramenta) throws SQLException {
        String sql = "UPDATE ferramentas SET nome_ferramenta = ?, marca_ferramenta = ?, preco = ? WHERE id_ferramenta = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, ferramenta.getNome());
            stmt.setString(2, ferramenta.getMarca());
            stmt.setDouble(3, ferramenta.getCusto());
            stmt.setInt(4, ferramenta.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Método para deletar uma ferramenta do banco de dados.
     * Verifica se há empréstimos pendentes antes de deletar.
     * @param id ID da ferramenta a ser deletada.
     * @throws SQLException Se ocorrer um erro de SQL ou se a ferramenta possuir empréstimos pendentes.
     */
    public void deletarFerramenta(int id) throws SQLException {
        // Verificar empréstimos pendentes
        String sqlVerificarEmprestimos = "SELECT * FROM emprestimos WHERE id_ferramenta = ? AND status_emprestimo != 'Devolvido'";
        try (PreparedStatement stmtEmprestimos = conexao.prepareStatement(sqlVerificarEmprestimos)) {
            stmtEmprestimos.setInt(1, id);
            try (ResultSet rsEmprestimos = stmtEmprestimos.executeQuery()) {
                if (rsEmprestimos.next()) {
                    throw new SQLException("A ferramenta possui empréstimos pendentes e não pode ser deletada.");
                }
            }
        }

        // Excluir a ferramenta
        String sqlExcluirFerramenta = "DELETE FROM ferramentas WHERE id_ferramenta = ?";
        try (PreparedStatement stmtExcluir = conexao.prepareStatement(sqlExcluirFerramenta)) {
            stmtExcluir.setInt(1, id);
            stmtExcluir.executeUpdate();
        }
    }

    /**
     * Método para obter o ID de uma ferramenta pelo nome.
     * @param nomeFerramenta Nome da ferramenta.
     * @return ID da ferramenta ou -1 se não for encontrada.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public int getFerramentaId(String nomeFerramenta) throws SQLException {
        int idFerramenta = -1; // Valor padrão caso não encontre o ID

        String sql = "SELECT id_ferramenta FROM ferramentas WHERE nome_ferramenta = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeFerramenta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idFerramenta = rs.getInt("id_ferramenta");
                }
            }
        }

        return idFerramenta;
    }
}
