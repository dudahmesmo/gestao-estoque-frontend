package Controle;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import Modelo.Ferramentas;
import Modelo.Categoria;

public class FerramentasControle {

    private final http.ApiClient apiClient;
    private final CategoriaControle categoriaControle; // Use CategoriaControle

    public FerramentasControle() {
        this.apiClient = new http.ApiClient();
        this.categoriaControle = new CategoriaControle(); // Inicializa o controle de categorias
    }

    /**
     * Obter categorias usando CategoriaControle
     */
    public List<Categoria> obterCategorias() {
        return categoriaControle.obterTodasCategorias();
    }
    
    /**
     * Método auxiliar para obter apenas os nomes das categorias
     */
    public List<String> obterNomesCategorias() {
        return categoriaControle.obterNomesCategorias();
    }
    
    /**
     * Buscar categoria por nome
     */
    public Categoria buscarCategoriaPorNome(String nome) {
        return categoriaControle.buscarCategoriaPorNome(nome);
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
     */
    public boolean adicionarFerramenta(String nome, String marca,
            double preco,
            int quantidadeEstoque, int quantidadeMinima, 
            int quantidadeMaxima, String nomeCategoria) {
        try {
            // Busca a categoria pelo nome
            Categoria categoria = categoriaControle.buscarCategoriaPorNome(nomeCategoria);
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
        Categoria categoriaGeral = categoriaControle.buscarCategoriaPorNome("Geral");
        if (categoriaGeral == null) {
            categoriaGeral = new Categoria(0L, "Geral");
        }
        return adicionarFerramenta(nome, marca, preco, 0, 1, 100, categoriaGeral);
    }
    
    /**
     * Testa a conexão com o backend
     */
    public boolean testarConexao() {
        return categoriaControle.testarConexao();
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
            return new ArrayList<>();
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
            List<Ferramentas> todas = listarFerramentas();

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
        
        System.err.println("ERRO: " + titulo);
        System.err.println("Mensagem: " + mensagem);
        e.printStackTrace();
        
        JOptionPane.showMessageDialog(null,
            titulo + ":\n" + mensagem,
            "Erro",
            JOptionPane.ERROR_MESSAGE);
    }
}