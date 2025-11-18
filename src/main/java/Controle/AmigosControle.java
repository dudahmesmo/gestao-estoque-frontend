package Controle;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Modelo.Amigos;

public class AmigosControle {

    private final HttpClient client;
    private final Gson gson;
    private final String API_URL = "http://localhost:8080/amigos"; 

    public AmigosControle() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    // 1. MÉTODO LISTAR (GET)
    
    public List<Amigos> listarAmigos() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Type listaTipo = new TypeToken<ArrayList<Amigos>>() {}.getType();
                return gson.fromJson(response.body(), listaTipo);
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao buscar dados: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2. MÉTODO DELETAR (DELETE) - Retorna boolean
    
    public boolean deletarAmigo(int id) {
        try {
            String urlDeletar = API_URL + "/" + id; 
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlDeletar))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 204) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao deletar. Status: " + response.statusCode());
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro de conexão ao deletar: " + e.getMessage());
            return false;
        }
    }

    // 3. MÉTODO ADICIONAR (POST)

    public boolean adicionarAmigo(String nome, String telefone) {
        try {
            // 1. Cria o objeto Amigo
            Amigos novoAmigo = new Amigos();
            novoAmigo.setNome(nome);
            novoAmigo.setTelefone(telefone);

            // 2. Converte para JSON
            String jsonBody = gson.toJson(novoAmigo);

            // 3. Monta a requisição POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // 4. Envia
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 5. Verifica sucesso (201 Created ou 200 OK)
            if (response.statusCode() == 201 || response.statusCode() == 200) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar. Status: " + response.statusCode());
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro de conexão ao cadastrar: " + e.getMessage());
            return false;
        }
    }
}