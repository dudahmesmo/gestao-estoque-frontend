package Controle;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import Modelo.Ferramentas;
import Modelo.Categoria;
import http.ApiClient;

public class FerramentasControle {

    private final ApiClient apiClient;
    private List<Categoria> cacheCategorias; // Cache para categorias
    private boolean usandoMockCategorias = false; // Flag para saber se está usando mock

    public FerramentasControle() {
        this.apiClient = new ApiClient();
        this.cacheCategorias = null; // Inicialmente nulo
    }

    /**
     * Adiciona nova ferramenta com categoria.
     */
    public boolean adicionarFerramenta(String nome, String marca,
            double preco,
            int quantidadeEstoque, int quantidadeMinima, 
            int quantidadeMaxima, Categoria categoria) {
        try {
            // Validação básica
            if (nome == null || nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome da ferramenta é obrigatório!");
                return false;
            }
            
            if (categoria == null) {
                JOptionPane.showMessageDialog(null, "Selecione uma categoria!");
                return false;
            }
            
            Ferramentas novaFerramenta = new Ferramentas();
            novaFerramenta.setNome(nome);
            novaFerramenta.setMarca(marca);
            novaFerramenta.setPreco(preco);
            novaFerramenta.setQuantidade_estoque(quantidadeEstoque);
            novaFerramenta.setQuantidade_minima(quantidadeMinima);
            novaFerramenta.setQuantidade_maxima(quantidadeMaxima);
            novaFerramenta.setCategoria(categoria);
            novaFerramenta.setDisponivel(quantidadeEstoque > 0);

            apiClient.cadastrarFerramenta(novaFerramenta);
            return true;
        } catch (Exception e) {
            exibirErro("Falha ao cadastrar ferramenta", e);
            return false;
        }
    }
    
    /**
     * Método alternativo que recebe nome da categoria como String
     * (para compatibilidade com código existente)
     */
    public boolean adicionarFerramenta(String nome, String marca,
            double preco,
            int quantidadeEstoque, int quantidadeMinima, 
            int quantidadeMaxima, String nomeCategoria) {
        try {
            // Busca a categoria pelo nome
            Categoria categoria = buscarCategoriaPorNome(nomeCategoria);
            if (categoria == null) {
                // Se não encontrar, cria uma nova categoria temporária
                categoria = new Categoria(null, nomeCategoria);
            }
            
            return adicionarFerramenta(nome, marca, preco, 
                quantidadeEstoque, quantidadeMinima, quantidadeMaxima, categoria);
        } catch (Exception e) {
            exibirErro("Falha ao cadastrar ferramenta", e);
            return false;
        }
    }

    // Sobrecarga mantida para compatibilidade
    public boolean adicionarFerramenta(String nome, String marca, double preco) {
        Categoria categoriaGeral = buscarCategoriaPorNome("Geral");
        if (categoriaGeral == null) {
            categoriaGeral = new Categoria(0L, "Geral");
        }
        return adicionarFerramenta(nome, marca, preco, 0, 1, 100, categoriaGeral);
    }

    /**
     * Obter categorias do back-end com cache.
     */
    public List<Categoria> obterCategorias() {
        // Se já temos cache, retorna do cache
        if (cacheCategorias != null && !cacheCategorias.isEmpty()) {
            return cacheCategorias;
        }
        
        try {
            System.out.println("Obtendo categorias do backend...");
            List<Categoria> categorias = apiClient.obterCategorias();
            
            if (categorias != null && !categorias.isEmpty()) {
                cacheCategorias = categorias;
                usandoMockCategorias = false;
                System.out.println("Categorias obtidas com sucesso: " + categorias.size());
                return categorias;
            } else {
                throw new Exception("Nenhuma categoria retornada pelo backend");
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter categorias: " + e.getMessage());
            
            // Se estiver em ambiente de desenvolvimento, não mostra erro ao usuário
            // apenas usa as categorias mockadas silenciosamente
            if (!usandoMockCategorias) {
                // Mostra aviso apenas na primeira vez
                int resposta = JOptionPane.showOptionDialog(null,
                    "Não foi possível carregar categorias do servidor.\n" +
                    "Deseja usar categorias padrão para continuar trabalhando?\n\n" +
                    "Erro: " + e.getMessage(),
                    "Aviso - Conexão com Servidor",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new String[]{"Usar Categorias Padrão", "Tentar Novamente"},
                    "Usar Categorias Padrão");
                
                if (resposta == JOptionPane.NO_OPTION) {
                    // Tenta novamente
                    cacheCategorias = null;
                    return obterCategorias();
                }
            }
            
            // Usa categorias padrão
            cacheCategorias = criarCategoriasPadrao();
            usandoMockCategorias = true;
            return cacheCategorias;
        }
    }
    
    /**
     * Limpa o cache de categorias (útil após adicionar nova categoria)
     */
    public void limparCacheCategorias() {
        cacheCategorias = null;
    }
    
    /**
     * Busca categoria por nome
     */
    private Categoria buscarCategoriaPorNome(String nome) {
        if (nome == null) return null;
        
        List<Categoria> categorias = obterCategorias();
        if (categorias != null) {
            for (Categoria cat : categorias) {
                if (cat.getNome() != null && cat.getNome().equalsIgnoreCase(nome)) {
                    return cat;
                }
            }
        }
        return null;
    }
    
    /**
     * Busca categoria por ID
     */
    public Categoria buscarCategoriaPorId(Long id) {
        if (id == null) return null;
        
        List<Categoria> categorias = obterCategorias();
        if (categorias != null) {
            for (Categoria cat : categorias) {
                if (cat.getId() != null && cat.getId().equals(id)) {
                    return cat;
                }
            }
        }
        return null;
    }
    
    /**
     * Cria lista padrão de categorias para fallback
     */
    private List<Categoria> criarCategoriasPadrao() {
        return Arrays.asList(
            new Categoria(1L, "Elétrica"),
            new Categoria(2L, "Manual"),
            new Categoria(3L, "Hidráulica"),
            new Categoria(4L, "Pneumática"),
            new Categoria(5L, "Geral")
        );
    }
    
    /**
     * Método auxiliar para obter apenas os nomes das categorias
     */
    public List<String> obterNomesCategorias() {
        List<Categoria> categorias = obterCategorias();
        List<String> nomes = new ArrayList<>();
        
        if (categorias != null) {
            for (Categoria cat : categorias) {
                if (cat.getNome() != null) {
                    nomes.add(cat.getNome());
                }
            }
        }
        
        // Garante que sempre retorne pelo menos as categorias padrão
        if (nomes.isEmpty()) {
            nomes.add("Elétrica");
            nomes.add("Manual");
            nomes.add("Hidráulica");
            nomes.add("Pneumática");
            nomes.add("Geral");
        }
        
        return nomes;
    }

    /**
     * Testa a conexão com o backend
     */
    public boolean testarConexao() {
        try {
            // Limpa cache para forçar nova requisição
            cacheCategorias = null;
            List<Categoria> categorias = obterCategorias();
            return categorias != null && !categorias.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Verifica se está usando dados mockados
     */
    public boolean isUsandoMockCategorias() {
        return usandoMockCategorias;
    }

    /**
     * Listar todas as ferramentas.
     */
    public List<Ferramentas> listarFerramentas() {
        try {
            List<Ferramentas> ferramentas = apiClient.listarFerramentas();
            if (ferramentas == null) {
                throw new Exception("Nenhuma ferramenta retornada");
            }
            return ferramentas;
        } catch (Exception e) {
            exibirErro("Erro ao listar ferramentas", e);
            return new ArrayList<>(); // Retorna lista vazia em vez de null
        }
    }

    /**
     * Deletar uma ferramenta pelo ID.
     */
    public boolean deletarFerramenta(int id) {
        try {
            // Confirmação antes de deletar
            int confirmacao = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir a ferramenta ID " + id + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacao != JOptionPane.YES_OPTION) {
                return false;
            }
            
            apiClient.excluirFerramenta(id);
            return true;
        } catch (Exception e) {
            exibirErro("Erro ao excluir ferramenta", e);
            return false;
        }
    }

    /**
     * Atualizar uma ferramenta.
     */
    public boolean atualizarFerramenta(Ferramentas ferramenta) {
        try {
            // Validações
            if (ferramenta == null) {
                JOptionPane.showMessageDialog(null, "Ferramenta inválida!");
                return false;
            }
            
            if (ferramenta.getId() == null) {
                JOptionPane.showMessageDialog(null, "ID da ferramenta é obrigatório para atualização!");
                return false;
            }
            
            apiClient.atualizarFerramenta(ferramenta);
            return true;
        } catch (Exception e) {
            exibirErro("Erro ao atualizar ferramenta", e);
            return false;
        }
    }

    /**
     * Verificar estoque baixo.
     */
    public List<Ferramentas> getFerramentasComEstoqueBaixo() {
        try {
            List<Ferramentas> todas = listarFerramentas(); // Usa nosso método que já trata erro

            if (todas != null && !todas.isEmpty()) {
                return todas.stream()
                        .filter(Ferramentas::isEstoqueBaixo)
                        .toList();
            }
            return new ArrayList<>();
        } catch (Exception e) {
            exibirErro("Erro ao verificar estoque baixo", e);
            return new ArrayList<>();
        }
    }

    /**
     * Obter ferramentas por categoria (por nome).
     */
    public List<Ferramentas> getFerramentasPorCategoria(String nomeCategoria) {
        try {
            List<Ferramentas> todas = listarFerramentas();

            if (todas != null && !todas.isEmpty() && nomeCategoria != null
                    && !nomeCategoria.equals("Todas") && !nomeCategoria.equals("Todos")) {

                return todas.stream()
                        .filter(f -> f.getCategoria() != null
                        && f.getCategoria().getNome() != null
                        && f.getCategoria().getNome().equalsIgnoreCase(nomeCategoria))
                        .toList();
            }

            return todas != null ? todas : new ArrayList<>();

        } catch (Exception e) {
            exibirErro("Erro ao filtrar por categoria", e);
            return new ArrayList<>();
        }
    }
    
    /**
     * Obter ferramentas por categoria (usando objeto Categoria)
     */
    public List<Ferramentas> getFerramentasPorCategoria(Categoria categoria) {
        if (categoria == null || categoria.getId() == null) {
            return listarFerramentas();
        }
        
        try {
            List<Ferramentas> todas = listarFerramentas();

            if (todas != null && !todas.isEmpty()) {
                return todas.stream()
                        .filter(f -> f.getCategoria() != null
                        && f.getCategoria().getId() != null
                        && f.getCategoria().getId().equals(categoria.getId()))
                        .toList();
            }

            return new ArrayList<>();

        } catch (Exception e) {
            exibirErro("Erro ao filtrar por categoria", e);
            return new ArrayList<>();
        }
    }

    /**
     * Obter ferramentas por status.
     */
    public List<Ferramentas> getFerramentasPorStatus(String status) {
        try {
            List<Ferramentas> todas = listarFerramentas();

            if (todas != null && !todas.isEmpty() && status != null
                    && !status.equals("Todos") && !status.equals("Todas")) {

                return todas.stream()
                        .filter(f -> f.getStatusEstoque() != null
                        && f.getStatusEstoque().toUpperCase().contains(status.toUpperCase()))
                        .toList();
            }

            return todas != null ? todas : new ArrayList<>();

        } catch (Exception e) {
            exibirErro("Erro ao filtrar por status", e);
            return new ArrayList<>();
        }
    }

    /**
     * Relatório de Produtos por Categoria
     */
    public List<Object[]> getQuantidadeProdutosPorCategoria() {
        try {
            return apiClient.getQuantidadeProdutosPorCategoria();
        } catch (Exception e) {
            exibirErro("Erro ao buscar Relatório de Produtos por Categoria", e);
            return new ArrayList<>();
        }
    }
    
    /**
     * Método auxiliar para exibir erros de forma padronizada
     */
    private void exibirErro(String titulo, Exception e) {
        String mensagem = e.getMessage();
        if (mensagem == null || mensagem.isEmpty()) {
            mensagem = "Erro desconhecido. Verifique a conexão com o servidor.";
        }
        
        // Log no console para debug
        System.err.println("ERRO: " + titulo);
        System.err.println("Mensagem: " + mensagem);
        e.printStackTrace();
        
        // Exibe para o usuário (apenas se não for erro de conexão esperado)
        if (!mensagem.contains("mock") && !mensagem.contains("fallback")) {
            JOptionPane.showMessageDialog(null,
                titulo + ":\n" + mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Método para diagnóstico - testa todos os endpoints
     */
    public void diagnosticoCompleto() {
        StringBuilder diagnostico = new StringBuilder();
        diagnostico.append("=== DIAGNÓSTICO DO SISTEMA ===\n\n");
        
        try {
            // Testa conexão básica
            diagnostico.append("1. Testando conexão com backend...\n");
            boolean conectado = apiClient.testarConexao();
            diagnostico.append("   Status: ").append(conectado ? "CONECTADO ✓" : "DESCONECTADO ✗").append("\n");
            
            // Testa categorias
            diagnostico.append("\n2. Testando endpoint de categorias...\n");
            try {
                List<Categoria> categorias = apiClient.obterCategorias();
                diagnostico.append("   Status: OK ✓\n");
                diagnostico.append("   Categorias encontradas: ").append(categorias != null ? categorias.size() : 0).append("\n");
                if (categorias != null && !categorias.isEmpty()) {
                    diagnostico.append("   Exemplo: ").append(categorias.get(0).getNome()).append("\n");
                }
            } catch (Exception e) {
                diagnostico.append("   Status: FALHOU ✗\n");
                diagnostico.append("   Erro: ").append(e.getMessage()).append("\n");
            }
            
            // Testa ferramentas
            diagnostico.append("\n3. Testando endpoint de ferramentas...\n");
            try {
                List<Ferramentas> ferramentas = apiClient.listarFerramentas();
                diagnostico.append("   Status: OK ✓\n");
                diagnostico.append("   Ferramentas encontradas: ").append(ferramentas != null ? ferramentas.size() : 0).append("\n");
            } catch (Exception e) {
                diagnostico.append("   Status: FALHOU ✗\n");
                diagnostico.append("   Erro: ").append(e.getMessage()).append("\n");
            }
            
            diagnostico.append("\n=== FIM DO DIAGNÓSTICO ===\n");
            
        } catch (Exception e) {
            diagnostico.append("Erro durante diagnóstico: ").append(e.getMessage());
        }
        
        // Exibe o diagnóstico
        JOptionPane.showMessageDialog(null,
            diagnostico.toString(),
            "Diagnóstico do Sistema",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
