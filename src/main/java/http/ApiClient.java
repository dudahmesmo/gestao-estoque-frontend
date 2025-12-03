package http;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Modelo.Amigos;
import Modelo.Emprestimos;
import Modelo.Ferramentas;

/**
 * Esta classe faz todas as chamadas HTTP para a API REST do back-end.
 */
public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080";
    private final HttpClient client;
    private final Gson gson;

    public ApiClient() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    // MÉTODOS DE CATEGORIA
    public List<String> obterCategorias() throws Exception {
    try {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/categorias"))
            .GET()
            .header("Accept", "application/json")
            .build();

        HttpResponse<String> response = client.send(request, 
            HttpResponse.BodyHandlers.ofString());

        System.out.println("Status Code: " + response.statusCode());
        
        if (response.statusCode() == 200) {
            Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
            List<Map<String, Object>> categorias = gson.fromJson(response.body(), listType);
            
            List<String> nomes = new ArrayList<>();
            for (Map<String, Object> cat : categorias) {
                nomes.add((String) cat.get("nome"));
            }
            return nomes;
        } else {
            throw new Exception("Falha ao obter categorias. Código: " + 
                response.statusCode());
        }
    } catch (Exception e) {
        System.err.println("Erro ao buscar categorias: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
}
    
    public void cadastrarCategoria(String nome, String descricao) throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nome", nome);
        requestBody.put("descricao", descricao != null ? descricao : "");

        String jsonBody = gson.toJson(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/categorias"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception("Falha ao cadastrar categoria. Código: " + response.statusCode());
        }
    }
    
    public List<Map<String, Object>> listarCategoriasCompletas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/categorias"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao listar categorias. Código: " + response.statusCode());
        }

        Type tipolistaCategorias = new TypeToken<List<Map<String, Object>>>() {
        }.getType();

        return gson.fromJson(response.body(), tipolistaCategorias);
    }
    
    public void excluirCategoria(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/categorias/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new Exception("Falha ao excluir categoria. Código: " + response.statusCode());
        }
    }

    // MÉTODOS DE AMIGOS
    public void cadastrarAmigo(Amigos amigo) throws Exception {
        String jsonBody = gson.toJson(amigo);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/amigos/"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception("Falha ao cadastrar amigo. Código: " + response.statusCode());
        }
    }

    public List<Amigos> listarAmigos() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/amigos/"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao listar amigos. Código: "
                    + response.statusCode());
        }

        Type tipolistaAmigos = new TypeToken<List<Amigos>>() {
        }.getType();

        return gson.fromJson(response.body(), tipolistaAmigos);
    }

    public void excluirAmigo(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/amigos/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new Exception("Falha ao excluir amigo. Código: " + response.statusCode());
        }
    }

    // MÉTODOS DE FERRAMENTAS
    public void cadastrarFerramenta(Ferramentas ferramenta) throws Exception {
        
        Map<String, Object> requestBody = new HashMap<>();
        
        requestBody.put("nome", ferramenta.getNome());
        requestBody.put("marca", ferramenta.getMarca());
        requestBody.put("preco", ferramenta.getPreco());
        requestBody.put("quantidadeEstoque", ferramenta.getQuantidade_estoque());
        requestBody.put("quantidadeMinimaEstoque", ferramenta.getQuantidade_minima());
        requestBody.put("quantidadeMaximaEstoque", ferramenta.getQuantidade_maxima());
        requestBody.put("disponivel", ferramenta.isDisponivel());
        
        // Mapeamento da Categoria
        String categoriaNome = ferramenta.getCategoria();
        if (categoriaNome != null && !categoriaNome.isEmpty()) {
            Map<String, String> categoriaMap = new HashMap<>();
            categoriaMap.put("nome", categoriaNome); 
            requestBody.put("categoria", categoriaMap);
        } else {
            requestBody.put("categoria", null);
        }

        String jsonBody = gson.toJson(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            String errorMessage = response.body() != null
                    && !response.body().isEmpty()
                    ? response.body() : "Nenhuma mensagem de erro do servidor.";

            throw new Exception("Falha ao cadastrar ferramenta. Código: "
                    + response.statusCode() + ". Detalhes: " + errorMessage);
        }
    }

    public List<Ferramentas> listarFerramentas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao listar ferramentas. Código: " + response.statusCode());
        }

        Type tipolistaFerramentas = new TypeToken<List<Ferramentas>>() {
        }.getType();

        return gson.fromJson(response.body(), tipolistaFerramentas);
    }

    public void excluirFerramenta(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new Exception("Falha ao excluir ferramenta. Código: " + response.statusCode());
        }
    }

    public void atualizarFerramenta(Ferramentas ferramenta) throws Exception {
        if (ferramenta.getId() == null) {
            throw new IllegalArgumentException("ID da ferramenta é"
                    + " obrigatório para atualização.");
        }

        Map<String, Object> requestBody = new HashMap<>();
        
        requestBody.put("id", ferramenta.getId()); 
        requestBody.put("nome", ferramenta.getNome());
        requestBody.put("marca", ferramenta.getMarca());
        requestBody.put("preco", ferramenta.getPreco());
        requestBody.put("quantidadeEstoque", ferramenta.getQuantidade_estoque());
        requestBody.put("quantidadeMinimaEstoque", ferramenta.getQuantidade_minima());
        requestBody.put("quantidadeMaximaEstoque", ferramenta.getQuantidade_maxima());
        requestBody.put("disponivel", ferramenta.isDisponivel());
        
        // Mapeamento da Categoria 
        String categoriaNome = ferramenta.getCategoria();
        if (categoriaNome != null && !categoriaNome.isEmpty()) {
            Map<String, String> categoriaMap = new HashMap<>();
            categoriaMap.put("nome", categoriaNome); 
            requestBody.put("categoria", categoriaMap);
        } else {
            requestBody.put("categoria", null);
        }

        String jsonBody = gson.toJson(requestBody);

        // URL: BASE_URL + /ferramentas/(id)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas/"
                        + ferramenta.getId()))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            String errorMessage = response.body() != null
                    && !response.body().isEmpty()
                    ? response.body() : "Nenhuma mensagem de erro do servidor.";

            throw new Exception("Falha ao atualizar ferramenta. Código: "
                    + response.statusCode() + ". Detalhes: " + errorMessage);
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

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            String errorMessage = response.body() != null
                    && !response.body().isEmpty()
                    ? response.body() : "Nenhuma mensagem de erro do servidor.";

            throw new Exception("Falha ao registrar empréstimo. Código: "
                    + response.statusCode() + ". Detalhes: " + errorMessage);
        }
    }

    public void registrarDevolucao(int idFerramenta) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/emprestimos/devolver/" 
                        + idFerramenta))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new Exception("Falha ao registrar devolução. Código: "
                    + response.statusCode());
        }
    }
    
    // Métodos de Listagem 
    public List<Modelo.Emprestimos> listarEmprestimosAtivos() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/emprestimos/ativos"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao listar empréstimos ativos. Código: "
                    + response.statusCode());
        }

        Type tipoListaEmprestimos = new TypeToken<List<Modelo.Emprestimos>>() {
        }.getType();

        return new Gson().fromJson(response.body(), tipoListaEmprestimos);
    }

    public List<Emprestimos> listarHistoricoEmprestimos() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/emprestimos/historico"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao listar histórico de empréstimos. Código: " + response.statusCode());
        }

        Type tipoListaEmprestimos = new TypeToken<List<Emprestimos>>() {
        }.getType();
        return gson.fromJson(response.body(), tipoListaEmprestimos);
    }
    
    // Lista Devedores (Relatório)
    public List<Amigos> listarDevedores() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/emprestimos/relatorios/devedores-em-atraso"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao obter o relatório de devedores. Código: " + response.statusCode());
        }

        Type tipoListaAmigos = new TypeToken<List<Amigos>>() {
        }.getType();
        return gson.fromJson(response.body(), tipoListaAmigos);
    }
    
    // Métodos de Relatório de Ferramentas
    public Map<String, Object> getCustoTotalEstoque() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas/relatorios/custo-total"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao obter o relatório de custos. Código: " + response.statusCode());
        }

        Type tipoMapa = new TypeToken<Map<String, Object>>() {
        }.getType();
        return gson.fromJson(response.body(), tipoMapa);
    }
    
    public List<Object[]> getFerramentasMaisEmprestadas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/emprestimos/relatorios/mais-emprestadas"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao obter o relatório de mais emprestadas. Código: " + response.statusCode());
        }

        Type tipoListaArray = new TypeToken<List<Object[]>>() {
        }.getType();
        return gson.fromJson(response.body(), tipoListaArray);
    }

    public List<Object[]> getFerramentasMaisDevolvidas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/emprestimos/relatorios/mais-devolvidas"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao obter o relatório de mais devolvidas. Código: " + response.statusCode());
        }

        Type tipoListaArray = new TypeToken<List<Object[]>>() {
        }.getType();
        return gson.fromJson(response.body(), tipoListaArray);
    }
    
    public List<Object[]> getQuantidadeProdutosPorCategoria() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas/relatorios/quantidade-por-categoria"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao obter o relatório de quantidade por categoria. Código: " + response.statusCode());
        }

        Type tipoListaArray = new TypeToken<List<Object[]>>() {
        }.getType();
        return gson.fromJson(response.body(), tipoListaArray);
    }
}