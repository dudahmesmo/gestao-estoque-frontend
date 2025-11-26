package Controle;

import Modelo.Ferramentas;
import http.ApiClient; 
import java.util.List;
import javax.swing.JOptionPane;

public class FerramentasControle {

    private final ApiClient apiClient;

    public FerramentasControle() {
        this.apiClient = new ApiClient();
    }

    /**
     * Adicionar nova ferramenta com categoria
     */
    public boolean adicionarFerramenta(String nome, String marca, double preco, 
                                     int Quantidade_estoque, int Quantidade_minima, 
                                     int Quantidade_maxima, String categoria) {
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
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    public boolean adicionarFerramenta(String nome, String marca, double preco) {
        return adicionarFerramenta(nome, marca, preco, 0, 1, 100, "Geral");
    }

    /**
     * Obter categorias do back-end
     */
    public java.util.List<String> obterCategorias() {
        try {
            // Chamada para o API Client obter categorias
            return apiClient.obterCategorias();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar categorias: " + e.getMessage());
            // Retorna categorias padrão em caso de erro
            return java.util.Arrays.asList("Elétrica", "Manual", "Hidráulica", "Pneumática", "Geral");
        }
    }

    /**
     * Listar todas as ferramentas (GET)
     */
    public List<Ferramentas> listarFerramentas() {
        try {
            return apiClient.listarFerramentas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar. Verifique o servidor.");
            return null;
        }
    }

    /**
     * Deletar uma ferramenta pelo ID (DELETE)
     */
    public boolean deletarFerramenta(int id) {
        try {
            apiClient.excluirFerramenta(id);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
            return false;
        }
    }

    /**
     * Atualizar uma ferramenta 
     */
    public boolean atualizarFerramenta(Ferramentas ferramenta) {
        try {
            
            apiClient.atualizarFerramenta(ferramenta); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verificar estoque baixo
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
            JOptionPane.showMessageDialog(null, "Erro ao verificar estoque baixo.");
        }
        return null;
    }
    
    /**
     * Obter ferramentas por categoria
     */
    public List<Ferramentas> getFerramentasPorCategoria(String categoria) {
        try {
            List<Ferramentas> todas = apiClient.listarFerramentas();
            if (todas != null && categoria != null && !categoria.equals("Todas")) {
                return todas.stream()
                    .filter(f -> f.getCategoria().equals(categoria))
                    .toList();
            }
            return todas;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar por categoria.");
            return null;
        }
    }

    /**
     * Obter ferramentas por status
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
            JOptionPane.showMessageDialog(null, "Erro ao filtrar por status.");
            return null;
        }
    }
}