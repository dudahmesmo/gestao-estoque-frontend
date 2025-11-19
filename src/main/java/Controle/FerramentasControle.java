package Controle;

import java.util.List;

import javax.swing.JOptionPane; // Importa a classe que faz as chamadas HTTP

import Modelo.Ferramentas;
import http.ApiClient;

public class FerramentasControle {

    private final ApiClient apiClient;

    public FerramentasControle() {
        this.apiClient = new ApiClient();
    }

    // Método para adicionar uma ferramenta (usado pela tela cadastrarFerramentas)
    public boolean adicionarFerramenta(Ferramentas ferramenta) {
        try {
            // Chamada real à API
            apiClient.cadastrarFerramenta(ferramenta);
            return true;
        } catch (Exception e) {
            // Trata o erro (exibe mensagem) e retorna false
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    // Método para listar todas as ferramentas (usado pela tela gerenciarFerramentas)
    public List<Ferramentas> listarFerramentas() {
        try {
            // Chamada real à API
            return apiClient.listarFerramentas();
        } catch (Exception e) {
            // Trata o erro e retorna null para não travar a tabela
            e.printStackTrace();
            return null;
        }
    }

    // Método para atualizar os dados de uma ferramenta
    public boolean atualizarFerramenta(Ferramentas ferramenta) {
        System.out.println("Lógica atualizarFerramenta pendente.");
        return false;
    }

    /**
     * Método para deletar uma ferramenta.
     * Retorna boolean (true/false) para a View.
     */
    public boolean deletarFerramenta(int id) {
        try {
            // Chamada real à API
            apiClient.excluirFerramenta(id);
            return true;
        } catch (Exception e) {
            // Trata o erro e retorna false
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
            return false;
        }
    }
}