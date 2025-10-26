package Controle;

import DAO.EmprestimosDAO;
import Modelo.Emprestimos;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmprestimosControle {
    private EmprestimosDAO emprestimoDAO;

    // Construtor da classe que recebe um objeto EmprestimosDAO como parâmetro
    public EmprestimosControle(EmprestimosDAO emprestimoDAO) {
        this.emprestimoDAO = emprestimoDAO;
    }

    // Método para criar um novo empréstimo no banco de dados
    public void criarEmprestimo(Connection conexao, Emprestimos emprestimo, String idUsuarioStr) throws SQLException {
        // Convertendo a String para int
        int idUsuario = Integer.parseInt(idUsuarioStr);
        // Chamando o método correspondente no DAO passando a conexão e o ID do usuário
        emprestimoDAO.criarEmprestimo(conexao, emprestimo, idUsuario);
    }

    // Método para ler um empréstimo do banco de dados com base no seu ID
    public Emprestimos lerEmprestimoPorId(Connection conexao, int id) throws SQLException {
        return emprestimoDAO.lerEmprestimoPorId(conexao, id);
    }

    // Método para atualizar os dados de um empréstimo no banco de dados
    public void atualizarEmprestimo(Connection conexao, Emprestimos emprestimo) throws SQLException {
        emprestimoDAO.atualizarEmprestimo(conexao, emprestimo);
    }

    // Método para excluir um empréstimo do banco de dados com base no seu ID
    public void excluirEmprestimo(Connection conexao, int id) throws SQLException {
        emprestimoDAO.excluirEmprestimo(conexao, id);
    }

    // Método para listar todos os empréstimos cadastrados no banco de dados
    public List<Emprestimos> listarEmprestimos(Connection conexao) throws SQLException {
        return emprestimoDAO.listarEmprestimos(conexao);
    }
}
