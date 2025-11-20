package http; 

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date; 

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Modelo.Amigos;
import Modelo.Emprestimos;
import Modelo.Ferramentas; 

/**
 * Esta classe faz todas as chamadas HTTP
 * para a API REST do back-end.
 */
public class ApiClient { // ⬅️ A CHAVE DA CLASSE COMEÇA AQUI

    // Endereço onde o back-end irá rodar
    private static final String BASE_URL = "http://localhost:8080"; 

    private final HttpClient client;
    private final Gson gson;

    public ApiClient() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }
    
    // MÉTODOS DE AMIGOS
    
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

    // MÉTODOS DE FERRAMENTAS
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

    // MÉTODOS DE EMPRÉSTIMO
    
    public void registrarEmprestimo(Emprestimos emprestimo) throws Exception {
        
        Map<String, Long> amigoMap = new HashMap<>();
        amigoMap.put("id", (long) emprestimo.getIdAmigo()); 

        Map<String, Long> ferramentaMap = new HashMap<>();
        ferramentaMap.put("id", (long) emprestimo.getIdFerramenta());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataEmprestimoStr = sdf.format(emprestimo.getDataEmprestimo());
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amigo", amigoMap); 
        requestBody.put("ferramenta", ferramentaMap);
        requestBody.put("dataEmprestimo", dataEmprestimoStr);
        requestBody.put("ativo", true);
        
        String jsonBody = gson.toJson(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/emprestimos"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            String errorMessage = response.body() != null && !response.body().isEmpty() 
                                    ? response.body() : "Nenhuma mensagem de erro do servidor.";
            throw new Exception("Falha ao registrar empréstimo. Código: " + response.statusCode() + ". Detalhes: " + errorMessage);
        }
    }
    
    public void registrarDevolucao(int idFerramenta) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas/devolver/" + idFerramenta)) 
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new Exception("Falha ao registrar devolução. Código: " + response.statusCode());
        }
    }
}
// ⬆️ A CHAVE DE FECHAMENTO FINAL DA CLASSE ESTÁ AQUI