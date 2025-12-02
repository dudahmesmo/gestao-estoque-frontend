package http;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Modelo.Amigos;
import Modelo.Categoria;
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
    /**
     * Obter categorias como objetos Categoria (ATUALIZADO)
     */
    public List<Categoria> obterCategorias() throws Exception {
        try {
            System.out.println("Tentando acessar: " + BASE_URL + "/api/categorias");
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/api/categorias"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println("Resposta: " + response.body());

            if (response.statusCode() == 200) {
                Type tipoListaCategorias = new TypeToken<List<Categoria>>() {}.getType();
                List<Categoria> categorias = gson.fromJson(response.body(), tipoListaCategorias);
                System.out.println("Categorias obtidas: " + categorias.size());
                return categorias;
            } else if (response.statusCode() == 404) {
                // Tenta endpoint alternativo
                return obterCategoriasFallback();
            } else {
                throw new Exception("Falha ao obter categorias. Código: "
                        + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Erro em obterCategorias: " + e.getMessage());
            // Retorna categorias mock para desenvolvimento
            return obterCategoriasMock();
        }
    }
    
    /**
     * Método fallback para endpoint alternativo
     */
    private List<Categoria> obterCategoriasFallback() throws Exception {
        try {
            System.out.println("Tentando endpoint alternativo: /categorias/nomes");
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/categorias/nomes"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Type tipoListaStrings = new TypeToken<List<String>>() {}.getType();
                List<String> nomes = gson.fromJson(response.body(), tipoListaStrings);
                
                // Converte strings para objetos Categoria
                List<Categoria> categorias = new ArrayList<>();
                long id = 1;
                for (String nome : nomes) {
                    categorias.add(new Categoria(id++, nome));
                }
                return categorias;
            }
            throw new Exception("Endpoint alternativo também falhou");
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Método mock para desenvolvimento quando backend não está disponível
     */
    private List<Categoria> obterCategoriasMock() {
        System.out.println("Usando categorias mock para desenvolvimento");
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria(1L, "Elétrica"));
        categorias.add(new Categoria(2L, "Manual"));
        categorias.add(new Categoria(3L, "Hidráulica"));
        categorias.add(new Categoria(4L, "Pneumática"));
        categorias.add(new Categoria(5L, "Geral"));
        return categorias;
    }

    // Obter apenas nomes das categorias (para compatibilidade)
    public List<String> obterNomesCategorias() throws Exception {
        List<Categoria> categorias = obterCategorias();
        List<String> nomes = new ArrayList<>();
        for (Categoria cat : categorias) {
            nomes.add(cat.getNome());
        }
        return nomes;
    }

    // CADASTRAR CATEGORIA
    public void cadastrarCategoria(String nome, String descricao)
            throws Exception {
        Categoria categoria = new Categoria(null, nome);
        String jsonBody = gson.toJson(categoria);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/categorias"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception("Falha ao cadastrar categoria. Código: "
                    + response.statusCode());
        }
    }

    // LISTAR TODAS AS CATEGORIAS 
    public List<Categoria> listarCategoriasCompletas() throws Exception {
        return obterCategorias(); // Já retorna objetos Categoria
    }

    // EXCLUIR CATEGORIA
    public void excluirCategoria(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/categorias/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new Exception("Falha ao excluir categoria. Código: "
                    + response.statusCode());
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
            throw new Exception("Falha ao cadastrar amigo. Código: "
                    + response.statusCode());
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

        Type tipoListaAmigos = new TypeToken<List<Amigos>>() {}.getType();
        return gson.fromJson(response.body(), tipoListaAmigos);
    }

    public void excluirAmigo(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/amigos/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new Exception("Falha ao excluir amigo. Código: "
                    + response.statusCode());
        }
    }

    // MÉTODOS DE FERRAMENTAS
    public void cadastrarFerramenta(Ferramentas ferramenta) throws Exception {
        // Prepara o objeto para JSON incluindo a categoria
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("nome", ferramenta.getNome());
        requestBody.put("marca", ferramenta.getMarca());
        requestBody.put("preco", ferramenta.getPreco());
        requestBody.put("quantidadeEstoque", ferramenta.getQuantidadeEstoque());
        requestBody.put("quantidadeMinima", ferramenta.getQuantidadeMinima());
        requestBody.put("quantidadeMaxima", ferramenta.getQuantidadeMaxima());
        requestBody.put("disponivel", ferramenta.isDisponivel());
        
        // Adiciona a categoria se existir
        if (ferramenta.getCategoria() != null) {
            Map<String, Object> categoriaMap = new HashMap<>();
            categoriaMap.put("id", ferramenta.getCategoria().getId());
            categoriaMap.put("nome", ferramenta.getCategoria().getNome());
            requestBody.put("categoria", categoriaMap);
        }
        
        String jsonBody = gson.toJson(requestBody);
        System.out.println("JSON enviado para cadastrar ferramenta: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        
        System.out.println("Status resposta cadastro: " + response.statusCode());
        System.out.println("Resposta cadastro: " + response.body());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new Exception("Falha ao cadastrar ferramenta. Código: "
                    + response.statusCode() + " - " + response.body());
        }
    }

    public List<Ferramentas> listarFerramentas() throws Exception {
        try {
            System.out.println("Acessando: " + BASE_URL + "/ferramentas");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/ferramentas"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println("Resposta: " + response.body());

            if (response.statusCode() == 200) {
                Type tipoListaFerramentas = new TypeToken<List<Ferramentas>>() {}.getType();
                List<Ferramentas> ferramentas = gson.fromJson(response.body(), tipoListaFerramentas);
                System.out.println("Ferramentas desserializadas: " + (ferramentas != null ? ferramentas.size() : 0));
                return ferramentas;
            } else {
                throw new Exception("Erro ao listar ferramentas. Código: " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("Erro em listarFerramentas: " + e.getMessage());
            throw e;
        }
    }

    public void excluirFerramenta(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new Exception("Falha ao excluir ferramenta. Código: "
                    + response.statusCode());
        }
    }

    public void atualizarFerramenta(Ferramentas ferramenta) throws Exception {
        if (ferramenta.getId() == null) {
            throw new IllegalArgumentException("ID da ferramenta é obrigatório para atualização.");
        }

        // Prepara o objeto para JSON
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", ferramenta.getId());
        requestBody.put("nome", ferramenta.getNome());
        requestBody.put("marca", ferramenta.getMarca());
        requestBody.put("preco", ferramenta.getPreco());
        requestBody.put("quantidadeEstoque", ferramenta.getQuantidadeEstoque());
        requestBody.put("quantidadeMinima", ferramenta.getQuantidadeMinima());
        requestBody.put("quantidadeMaxima", ferramenta.getQuantidadeMaxima());
        requestBody.put("disponivel", ferramenta.isDisponivel());
        
        // Adiciona a categoria se existir
        if (ferramenta.getCategoria() != null) {
            Map<String, Object> categoriaMap = new HashMap<>();
            categoriaMap.put("id", ferramenta.getCategoria().getId());
            categoriaMap.put("nome", ferramenta.getCategoria().getNome());
            requestBody.put("categoria", categoriaMap);
        }
        
        String jsonBody = gson.toJson(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas/" + ferramenta.getId()))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            String errorMessage = response.body() != null && !response.body().isEmpty()
                    ? response.body() : "Nenhuma mensagem de erro do servidor.";
            throw new Exception("Falha ao atualizar ferramenta. Código: "
                    + response.statusCode() + ". Detalhes: " + errorMessage);
        }
    }

    // MÉTODOS DE RELATÓRIO DE FERRAMENTAS
    public Map<String, Object> getCustoTotalEstoque() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas/relatorios/custo-total"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao obter o relatório de custos. Código: " + response.statusCode());
        }

        Type tipoMapa = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(response.body(), tipoMapa);
    }

    // Relatório de Quantidade por Categoria 
    public List<Object[]> getQuantidadeProdutosPorCategoria() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ferramentas/relatorios/quantidade-por-categoria"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao obter o relatório de quantidade por categoria. Código: " + response.statusCode());
        }

        Type tipoListaArray = new TypeToken<List<Object[]>>() {}.getType();
        return gson.fromJson(response.body(), tipoListaArray);
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
            String errorMessage = response.body() != null && !response.body().isEmpty()
                    ? response.body() : "Nenhuma mensagem de erro do servidor.";
            throw new Exception("Falha ao registrar empréstimo. Código: "
                    + response.statusCode() + ". Detalhes: " + errorMessage);
        }
    }

    public void registrarDevolucao(int idFerramenta) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/emprestimos/devolver/" + idFerramenta))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new Exception("Falha ao registrar devolução. Código: " + response.statusCode());
        }
    }

    // Lista Empréstimos Ativos
    public List<Modelo.Emprestimos> listarEmprestimosAtivos() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/emprestimos/ativos"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao listar empréstimos ativos. Código: " + response.statusCode());
        }

        Type tipoListaEmprestimos = new TypeToken<List<Modelo.Emprestimos>>() {}.getType();
        return new Gson().fromJson(response.body(), tipoListaEmprestimos);
    }

    // Lista Empréstimos Históricos
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

        Type tipoListaEmprestimos = new TypeToken<List<Emprestimos>>() {}.getType();
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

        Type tipoListaAmigos = new TypeToken<List<Amigos>>() {}.getType();
        return gson.fromJson(response.body(), tipoListaAmigos);
    }

    // Relatório de Ferramentas Mais Emprestadas
    public List<Object[]> getFerramentasMaisEmprestadas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/emprestimos/relatorios/mais-emprestadas"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao obter o relatório de mais emprestadas. Código: " + response.statusCode());
        }

        Type tipoListaArray = new TypeToken<List<Object[]>>() {}.getType();
        return gson.fromJson(response.body(), tipoListaArray);
    }

    // Relatório de Ferramentas Mais Devolvidas
    public List<Object[]> getFerramentasMaisDevolvidas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/emprestimos/relatorios/mais-devolvidas"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Falha ao obter o relatório de mais devolvidas. Código: " + response.statusCode());
        }

        Type tipoListaArray = new TypeToken<List<Object[]>>() {}.getType();
        return gson.fromJson(response.body(), tipoListaArray);
    }
    
    /**
     * Método de teste para verificar conexão com o backend
     */
    public boolean testarConexao() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/api/categorias"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            
            return response.statusCode() == 200;
        } catch (Exception e) {
            System.err.println("Falha no teste de conexão: " + e.getMessage());
            return false;
        }
    }
}