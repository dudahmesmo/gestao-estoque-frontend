package http; 

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Modelo.Amigos;
import Modelo.Emprestimos;
import Modelo.Ferramentas; 

/**
 * Esta classe faz todas as chamadas HTTP
 * para a API REST do back-end.
 */
public class ApiClient {

    // Endereço onde o back-end irá rodar
    private static final String BASE_URL = "http://localhost:8080";

    private final HttpClient client;
    private final Gson gson;

    public ApiClient() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }
    
    // ========================================================================
    // MÉTODOS DE AMIGOS
    // ========================================================================
    
    public void cadastrarAmigo(Amigos amigo) throws Exception {
        String jsonBody = gson.toJson(amigo);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/amigos")) 
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json") 
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception("Falha ao cadastrar amigo. Código: " + response.statusCode());
        }
    }

    public List<Amigos> listarAmigos() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/amigos")) 
                .GET()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao listar amigos. Código: " + response.statusCode());
        }
        
        Type tipoListaAmigos = new TypeToken<List<Amigos>>() {}.getType();
        return gson.fromJson(response.body(), tipoListaAmigos);
    }

    public void excluirAmigo(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/amigos/" + id)) 
                .DELETE()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) { 
            throw new Exception("Falha ao excluir amigo. Código: " + response.statusCode());
        }
    }

    // ========================================================================
    // MÉTODOS DE FERRAMENTAS
    // ========================================================================
    
    // CORRIGIDO: Este método agora usa a lógica HTTP real e não tem o print com erro.
    public void cadastrarFerramenta(Ferramentas ferramenta) throws Exception {
        String jsonBody = gson.toJson(ferramenta);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception("Falha ao cadastrar ferramenta. Código: " + response.statusCode());
        }
    }
    
    public List<Ferramentas> listarFerramentas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas"))
                .GET()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao listar ferramentas. Código: " + response.statusCode());
        }
        
        Type tipoListaFerramentas = new TypeToken<List<Ferramentas>>() {}.getType();
        return gson.fromJson(response.body(), tipoListaFerramentas);
    }
    
    public void excluirFerramenta(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas/" + id))
                .DELETE()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new Exception("Falha ao excluir ferramenta. Código: " + response.statusCode());
        }
    }

    // ========================================================================
    // MÉTODOS PENDENTES (EMPRÉSTIMOS)
    // ========================================================================
    
    public void registrarEmprestimo(Emprestimos emprestimo) {
         System.out.println("TAREFA 13 (Pendente): Chamar API POST /api/emprestimos");
        // TODO: Implementar a chamada HTTP POST real aqui
    }
    
    public void registrarDevolucao(int idEmprestimo) {
         System.out.println("TAREFA 13 (Pendente): Chamar API PUT /api/emprestimos/devolucao/" + idEmprestimo);
        // TODO: Implementar a chamada HTTP PUT real aqui
    }
}