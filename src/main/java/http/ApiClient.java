package http; 

import Modelo.Amigos;
import Modelo.Emprestimos;
import Modelo.Ferramentas;
import java.util.List;
import com.google.gson.Gson; 
import java.net.http.HttpClient; 
import java.net.http.HttpRequest; 
import java.net.http.HttpResponse; 
import java.net.URI;
import java.lang.reflect.Type; 
import com.google.gson.reflect.TypeToken; 


/**
 * Esta classe faz todas as chamadas HTTP
 * para a API REST do back-end.
 */
public class ApiClient {

    // Endereço onde o back-end irá rodar
    private static final String BASE_URL = "http://localhost:8080/api";

    // Ferramentas para fazer a ligação e traduzir o JSON
    private final HttpClient client;
    private final Gson gson;

    public ApiClient() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }
    
    public void cadastrarAmigo(Amigos amigo) throws Exception {
        // 1. Converte o objeto Amigo.java para uma string JSON

        String jsonBody = gson.toJson(amigo);

        // 2. Cria a requisição para o endpoint
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/amigos")) // Endpoint: POST /api/amigos
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json") // Avisa a API que estamos a enviar JSON
                .build();

        // 3. Envia a requisição e espera uma resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        // 4. Se a resposta não for 200 (OK) ou 201 (Criado), lança um erro
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception("Falha ao cadastrar amigo. Código: " + response.statusCode());
        }
    }

    // Conecta Gerenciamento 
    public List<Amigos> listarAmigos() throws Exception {
        // 1. Cria a "ligação" para o endpoint 
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/amigos")) // Endpoint: GET /api/amigos
                .GET()
                .build();
        
        // 2. Envia a requisição e recebe a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao listar amigos. Código: " + response.statusCode());
        }
        
        // 3. Define o "tipo" da resposta (uma Lista de Amigos)
        Type tipoListaAmigos = new TypeToken<List<Amigos>>() {}.getType();
        
        // 4. Usa o Gson para "traduzir" o texto JSON de volta para a Lista de Amigos
        List<Amigos> amigos = gson.fromJson(response.body(), tipoListaAmigos);
        
        return amigos;
    }

    // Conecta Gerenciamento 
    public void excluirAmigo(int id) throws Exception {
        // 1. Cria a requisição para o endpoint
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/amigos/" + id)) // Endpoint: DELETE /api/amigos/{id}
                .DELETE()
                .build();
        
        // 2. Envia a requisição
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) { // 204 = No Content (Sucesso também)
            throw new Exception("Falha ao excluir amigo. Código: " + response.statusCode());
        }
    }

    // --- MÉTODOS PENDENTES (FERRAMENTAS, EMPRÉSTIMOS, ETC.) ---
    
    
    public void cadastrarFerramenta(Ferramentas ferramenta) {
        System.out.println("TAREFA 11 (Pendente): Chamar API POST /api/ferramentas para: " + ferramenta.getNome_ferramenta());
        // TODO: Implementar a chamada HTTP POST real aqui (quando o backend estiver pronto)
    }
    
    public List<Ferramentas> listarFerramentas() {
        System.out.println("TAREFA 12 (Pendente): Chamar API GET /api/ferramentas...");
        // TODO: Implementar a chamada HTTP GET real aqui (quando o backend estiver pronto)
        return null; // Retorno temporário
    }
    
    public void excluirFerramenta(int id) {
        System.out.println("TAREFA 12 (Pendente): Chamar API DELETE /api/ferramentas/" + id);
        // TODO: Implementar a chamada HTTP DELETE real aqui (quando o backend estiver pronto)
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