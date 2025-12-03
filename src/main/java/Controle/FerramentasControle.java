package Controle;

import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

import Modelo.Ferramentas;
import http.ApiClient;

public class FerramentasControle {

    private final ApiClient apiClient;

    public FerramentasControle() {
        this.apiClient = new ApiClient();
    }

    /**
     * Adiciona nova ferramenta com categoria.
     */
    public boolean adicionarFerramenta(String nome, String marca,
            double preco,
            int Quantidade_estoque, int Quantidade_minima, int Quantidade_maxima, String categoria) {
        try {
            Ferramentas novaFerramenta = new Ferramentas();
            novaFerramenta.setNome(nome);
            novaFerramenta.setMarca(marca);
            novaFerramenta.setPreco(preco);
            novaFerramenta.setQuantidade_estoque(Quantidade_estoque);
            novaFerramenta.setQuantidade_minima(Quantidade_minima);
            novaFerramenta.setQuantidade_maxima(Quantidade_maxima);
            novaFerramenta.setCategoria(categoria);
            novaFerramenta.setDisponivel(Quantidade_estoque > 0);

            apiClient.cadastrarFerramenta(novaFerramenta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    public boolean adicionarFerramenta(String nome, String marca,
            double preco) {
        return adicionarFerramenta(nome, marca, preco, 0, 1, 100,
                "Geral");
    }

    /**
     * Obter categorias do back-end.
     */
    public java.util.List<String> obterCategorias() {
        try {
            return apiClient.obterCategorias();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar"
                    + " categorias: " + e.getMessage());
            // Retorna categorias padrão em caso de erro
            return Arrays.asList("Elétrica", "Manual",
                    "Hidraulica", "Pneumática", "Geral");
        }
    }

    /**
     * Listar todas as ferramentas (GET).
     */
    public List<Ferramentas> listarFerramentas() {
        try {
            return apiClient.listarFerramentas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar: "
                    + "Verifique a conexão com o servidor e o modelo de dados.");
            return null;
        }
    }

    /**
     * Deletar uma ferramenta pelo ID (DELETE).
     */
    public boolean deletarFerramenta(int id) {
        try {
            apiClient.excluirFerramenta(id);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Atualizar uma ferramenta.
     */
    public boolean atualizarFerramenta(Ferramentas ferramenta) {
        try {
            apiClient.atualizarFerramenta(ferramenta);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Verificar estoque baixo.
     */
    public List<Ferramentas> getFerramentasComEstoqueBaixo() {
        try {
            List<Ferramentas> todas = apiClient.listarFerramentas();

            if (todas != null) {
                return todas.stream()
                        .filter(Ferramentas::isEstoqueBaixo)
                        .toList();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar"
                    + " estoque baixo.");
        }
        return null;
    }

    /**
     * Obter ferramentas por categoria.
     */
    public List<Ferramentas> getFerramentasPorCategoria(String categoria) {
        try {
            List<Ferramentas> todas = apiClient.listarFerramentas();

            if (todas != null && categoria != null && !categoria.equals("Todas")) {

                return todas.stream()
                        .filter(f -> f.getCategoria() != null && f.getCategoria().equals(categoria))
                        .toList();
            }

            return todas;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar por"
                    + " categoria.");
            return null;
        }
    }

    /**
     * Obter ferramentas por status.
     */
    public List<Ferramentas> getFerramentasPorStatus(String status) {
        try {
            List<Ferramentas> todas = apiClient.listarFerramentas();

            if (todas != null && status != null && !status.equals("Todos")) {

                return todas.stream()
                        .filter(f -> f.getStatusEstoque().contains(status.toUpperCase()))
                        .toList();
            }

            return todas;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar por"
                    + " status.");
            return null;
        }
    }

    // Relatório de Produtos por Categoria
    /**
     * Busca o relatório da quantidade de produtos distintos por categoria.
     */
    public List<Object[]> getQuantidadeProdutosPorCategoria() {
        try {
            return apiClient.getQuantidadeProdutosPorCategoria();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar Relatório de Produtos por Categoria: " + e.getMessage());
            return null;
        }
    }
}