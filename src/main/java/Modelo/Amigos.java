package Modelo;

public class Amigos {
    private int idUsuario;
    private String nome;
    private String telefone;

    public Amigos(int idUsuario, String nome, String telefone) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.telefone = telefone;
    }

    // Getters e setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
