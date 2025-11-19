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
     * @return true se o registro foi bem-sucedido, false caso contrário.
     */
    public boolean registrarEmprestimo(Emprestimos novoEmprestimo) {
        try {
            apiClient.registrarEmprestimo(novoEmprestimo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao registrar empréstimo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registra a devolução de uma ferramenta (PUT para a API).
     * @return true se o registro foi bem-sucedido, false caso contrário.
     */
    public boolean registrarDevolucao(int idFerramenta) {
        try {
            apiClient.registrarDevolucao(idFerramenta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao registrar devolução: " + e.getMessage());
            return false;
        }
    }
    
    // Método de listar Empréstimos Ativos (para relatórios, se necessário)
    public List<Emprestimos> listarEmprestimosAtivos() {
        
         return null; 
    }
}
