package Controle;

import java.util.List;
import javax.swing.JOptionPane;
import Modelo.Emprestimos;
import http.ApiClient;

public class EmprestimosControle {
    
    private final ApiClient apiClient;
    
    public EmprestimosControle() {
        this.apiClient = new ApiClient();
    }

    /**
     * Registra um novo empréstimo (POST para a API).
     * O Backend deve mudar o status da Ferramenta para indisponível.
     */
    public boolean registrarEmprestimo(Emprestimos novoEmprestimo) {
        try {
            // Chama o método HTTP real no ApiClient
            apiClient.registrarEmprestimo(novoEmprestimo); 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // A mensagem de erro da API é passada para a View
            JOptionPane.showMessageDialog(null, "Erro ao registrar empréstimo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registra a devolução de uma ferramenta (PUT para a API).
     * O Backend deve mudar o status da Ferramenta de volta para disponível.
     */
    public boolean registrarDevolucao(int idFerramenta) {
        try {
            // Chama o método HTTP real no ApiClient
            apiClient.registrarDevolucao(idFerramenta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao registrar devolução: " + e.getMessage());
            return false;
        }
    }
    
    // Método de listar Empréstimos Ativos (para relatórios)
    public List<Emprestimos> listarEmprestimosAtivos() {
        // Implementação de listagem pendente
        return null; 
    }
}