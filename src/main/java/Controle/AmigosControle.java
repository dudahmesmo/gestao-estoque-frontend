package Controle;

import java.util.List;

import javax.swing.JOptionPane;

import Modelo.Amigos;
import http.ApiClient; 

public class AmigosControle {
    
    private ApiClient apiClient; 

    public AmigosControle() {
        // Inicializa o ApiClient
        this.apiClient = new ApiClient(); 
        System.out.println("Controle de Amigos iniciado (pronto para ApiClient)");
    }

    // Método para adicionar um novo amigo
    public void adicionarAmigo(String nomeAmigo, String telefoneAmigo) {
        // 1. Cria o objeto Amigo 
        Amigos novoAmigo = new Amigos();
        novoAmigo.setNome_usuario(nomeAmigo);
        novoAmigo.setTelefone_usuario(telefoneAmigo);
        
        // 2. Chama o ApiClient para fazer a "ligação" (HTTP POST)
        try {
            apiClient.cadastrarAmigo(novoAmigo);
            // Se o 'cadastrarAmigo' não lançar uma exceção, deu certo.
            JOptionPane.showMessageDialog(null, "Amigo cadastrado com sucesso!");
        } catch (Exception e) {
            // Se der erro, é mostrado aqui
            System.err.println("Erro no AmigosControle ao cadastrar: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar amigo: " + e.getMessage(), "Erro de API", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para listar todos os amigos
    public List<Amigos> listarAmigos() {
        try {
            // 1. Chama o ApiClient para buscar os dados (HTTP GET)
            return apiClient.listarAmigos();
        } catch (Exception e) {
            // Se der erro, mostra o pop-up e retorna nulo
            System.err.println("Erro no AmigosControle ao listar: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao listar amigos: " + e.getMessage(), "Erro de API", JOptionPane.ERROR_MESSAGE);
            return null; // Retorna nulo em caso de erro
        }
    }

    // Método para atualizar os dados de um amigo
    public void atualizarAmigo(Amigos amigo) {
        System.out.println("Lógica de atualizarAmigo será implementada com ApiClient.");
        // pendente até a criação do endpoint PUT
    }

    // Método para deletar um amigo
    public void deletarAmigo(int idUsuario) {
        try {
            // 1. Chama o ApiClient para deletar (HTTP DELETE)
            apiClient.excluirAmigo(idUsuario);
            JOptionPane.showMessageDialog(null, "Amigo excluído com sucesso.");
        } catch (Exception e) {
            // Se der erro, mostra o pop-up
            System.err.println("Erro no AmigosControle ao deletar: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao excluir amigo: " + e.getMessage(), "Erro de API", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obter o ID de um usuário
    public int obterIdUsuario(String nomeUsuario) {
        System.out.println("Lógica de obterIdUsuario agora está obsoleta (ID vem do listarAmigos).");
        return 0; // Retorna '0' por enquanto.
    }
}
