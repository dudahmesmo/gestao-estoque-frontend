package Controle;

import java.util.List;

import javax.swing.JOptionPane;

import Modelo.Ferramentas;
import http.ApiClient;

public class FerramentasControle {

    private final ApiClient apiClient;

    public FerramentasControle() {
        this.apiClient = new ApiClient();
    }

    public List<Ferramentas> listarFerramentas() {
        try {
            return apiClient.listarFerramentas();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Recebe Preço e envia Quantidade e Disponível (adaptando ao backend)
    public boolean adicionarFerramenta(String nome, String marca, double preco) {
        try {
            Ferramentas nova = new Ferramentas();
            nova.setNome(nome);
            nova.setMarca(marca);
            nova.setPreco(preco); 
            
            
            nova.setQuantidade(1); 
            nova.setDisponivel(true); 

            apiClient.cadastrarFerramenta(nova);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar ferramenta: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarFerramenta(int id) {
        try {
            apiClient.excluirFerramenta(id);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar ferramenta: " + e.getMessage());
            return false;
        }
    }
}
