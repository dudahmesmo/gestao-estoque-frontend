package Controle;

import java.util.List;

import javax.swing.JOptionPane;

import Modelo.Amigos;
import Modelo.Emprestimos;
import http.ApiClient;

public class EmprestimosControle {

    private final ApiClient apiClient;

    public EmprestimosControle() {
        this.apiClient = new ApiClient();
    }

    // Registra um novo empréstimo 
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

    // Registra a devolução de uma ferramenta
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

    public List<Emprestimos> listarEmprestimosAtivos() {
        try {
            return apiClient.listarEmprestimosAtivos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar empréstimos ativos: " + e.getMessage());
            return null;
        }
    }

    /*
    // Lista Empréstimos Históricos (para Relatório)
    public List<Emprestimos> listarHistoricoEmprestimos() {
        try {
            return apiClient.listarHistoricoEmprestimos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar histórico de empréstimos: " + e.getMessage());
            return null; 
        }
    }
     */
    public List<Amigos> listarDevedores() {
        try {

            return apiClient.listarDevedores();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar devedores: " + e.getMessage());
            return null;
        }
    }

    // Lista Empréstimos Históricos
    public List<Modelo.Emprestimos> listarHistoricoEmprestimos() {
        try {
            return apiClient.listarHistoricoEmprestimos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar"
                    + " histórico de empréstimos: " + e.getMessage());
            return null;
        }
    }
}
