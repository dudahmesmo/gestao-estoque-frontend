package http; 

import Modelo.Amigos;
import Modelo.Ferramentas;
import Modelo.Emprestimos;

import java.util.List;
// import com.google.gson.Gson; 
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.net.URI;

/**
 * Esta classe faz todas as chamadas HTTP
 * para a API REST do back-end.
 */
public class ApiClient {

    // Endereço onde o back-end irá rodar
    private static final String BASE_URL = "http://localhost:8080/api";


    // --- MÉTODOS PARA TAREFAS FUTURAS ---

    // Tarefa 11: Conectar Cadastro
    public void cadastrarAmigo(Amigos amigo) {
        System.out.println("TAREFA 11 (Pendente): Chamar API POST /api/amigos para: " + amigo.getNome_usuario());
        // TODO: Implementar a chamada HTTP POST real aqui
    }

    public void cadastrarFerramenta(Ferramentas ferramenta) {
        System.out.println("TAREFA 11 (Pendente): Chamar API POST /api/ferramentas para: " + ferramenta.getNome_ferramenta());
        // TODO: Implementar a chamada HTTP POST real aqui
    }

    // Tarefa 12: Conectar Gerenciamento
    public List<Amigos> listarAmigos() {
        System.out.println("TAREFA 12 (Pendente): Chamar API GET /api/amigos...");
        // TODO: Implementar a chamada HTTP GET real aqui
        return null; // Retorno temporário
    }

    public void excluirAmigo(int id) {
        System.out.println("TAREFA 12 (Pendente): Chamar API DELETE /api/amigos/" + id);
        // TODO: Implementar a chamada HTTP DELETE real aqui
    }
    
    public List<Ferramentas> listarFerramentas() {
        System.out.println("TAREFA 12 (Pendente): Chamar API GET /api/ferramentas...");
        // TODO: Implementar a chamada HTTP GET real aqui
        return null; // Retorno temporário
    }
    
    public void excluirFerramenta(int id) {
        System.out.println("TAREFA 12 (Pendente): Chamar API DELETE /api/ferramentas/" + id);
        // TODO: Implementar a chamada HTTP DELETE real aqui
    }

    // Tarefa 13: Conectar Empréstimo
    public void registrarEmprestimo(Emprestimos emprestimo) {
         System.out.println("TAREFA 13 (Pendente): Chamar API POST /api/emprestimos");
        // TODO: Implementar a chamada HTTP POST real aqui
    }
    
    public void registrarDevolucao(int idEmprestimo) {
         System.out.println("TAREFA 13 (Pendente): Chamar API PUT /api/emprestimos/devolucao/" + idEmprestimo);
        // TODO: Implementar a chamada HTTP PUT real aqui
    }
    
    // Tarefa 15: Conectar Relatórios
    // Adicionar os métodos de relatório 
}