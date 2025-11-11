package Modelo;

public class Amigos {

    private int id_amigo;
    private String nome_usuario;
    private String telefone_usuario;

    public Amigos() {
    }

    public Amigos(int id_amigo, String nome_usuario, String telefone_usuario) {
        this.id_amigo = id_amigo;
        this.nome_usuario = nome_usuario;
        this.telefone_usuario = telefone_usuario;
    }

    // Métodos Getters e Setters 
    
    public int getId_amigo() {
        return id_amigo;
    }

    public void setId_amigo(int id_amigo) {
        this.id_amigo = id_amigo;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getTelefone_usuario() {
        return telefone_usuario;
    }

    public void setTelefone_usuario(String telefone_usuario) {
        this.telefone_usuario = telefone_usuario;
    }
}