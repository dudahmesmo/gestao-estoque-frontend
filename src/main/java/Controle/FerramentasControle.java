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
     * MÉTODO PARA ADICIONAR uma nova ferramenta (POST).
     */
    public boolean adicionarFerramenta(String nome, String marca, double preco) {
        try {
            // Cria o objeto modelo com setters limpos
            Ferramentas novaFerramenta = new Ferramentas();
            novaFerramenta.setNome(nome);
            novaFerramenta.setMarca(marca);
            novaFerramenta.setPreco(preco);
            
            novaFerramenta.setDisponivel(true); 

            apiClient.cadastrarFerramenta(novaFerramenta);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    /**
     * MÉTODO PARA LISTAR todas as ferramentas (GET).
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
     * MÉTODO PARA DELETAR uma ferramenta pelo ID (DELETE).
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
     * Lógica para atualizar uma ferramenta. (Ainda pendente)
     */
    public boolean atualizarFerramenta(Ferramentas ferramenta) {
        System.out.println("Lógica atualizarFerramenta pendente.");
        return false;
    }
}