package Controle;

// import DAO.EmprestimosDAO;
import Modelo.Emprestimos;
// import java.sql.Connection;
// import java.sql.SQLException;
import java.util.List;

public class EmprestimosControle {
    //private EmprestimosDAO emprestimoDAO;

    // Construtor da classe que recebe um objeto EmprestimosDAO como parâmetro
    //public EmprestimosControle(EmprestimosDAO emprestimoDAO) {
        //this.emprestimoDAO = emprestimoDAO;
    //}

    // Construtor novo e vazio para o código não quebrar
    public EmprestimosControle() {
        System.out.println("Controle de Empréstimos iniciado (pronto para ApiClient)");
    }

    // Método para criar um novo empréstimo no banco de dados
    // Os paramêtros 'Connection' e 'trows' foram removidos.
    public void criarEmprestimo(Emprestimos emprestimo, String idUsuarioStr) {
        // Convertendo a String para int
        // int idUsuario = Integer.parseInt(idUsuarioStr);
        // Chamando o método correspondente no DAO passando a conexão e o ID do usuário
        // emprestimoDAO.criarEmprestimo(conexao, emprestimo, idUsuario);
        System.out.println("Lógica de criarEmprestimo será implementada com APiClient");
    }

    // Método para ler um empréstimo
    public Emprestimos lerEmprestimoPorId(int id) /* throws SQLException */ {
        //return emprestimoDAO.lerEmprestimoPorId(conexao, id);
        System.out.println("Lógica de lerEmprestimoPorId será implementada com ApiClient");
        return null; // Retorna 'null' provisoriamente, só para o código compilar
    }

    //Método para atualizar os dados de um empréstimo 
    public void atualizarEmprestimo(Emprestimos emprestimo) /* throws SQLException */ {
        //emprestimoDAO.atualizarEmprestimo(conexao, emprestimo);
        System.out.println("Lógica de atualizarEmprestimo será implementada com ApiClient");
    }

    // Método para excluir um empréstimo
    public void excluirEmprestimo(int id) /* throws SQLException */ {
        //emprestimoDAO.excluirEmprestimo(conexao, id);
        System.out.println("Lógica de excluirEmprestimo será implementada com ApiClient");
    }

    // Método para listar todos os empréstimos cadastrados 
    public List<Emprestimos> listarEmprestimos() /* throws SQLException */ {
        // return emprestimoDAO.listarEmprestimos(conexao); 
        System.out.println("Lógica de listarEmprestimos será implementada com ApiClient");
        return null; // Retorna null provisoriamente
    }
}
