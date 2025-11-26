package Modelo;

// import java.time.LocalDateTime;

public class Amigos {

    private Long id;
    private String nome;
    private String telefone;
    private String email;
    // private LocalDateTime dataCadastro; 
    private Boolean oDevedor; 

    public Amigos() {
    }

    // GETTERS E SETTERS

    public Long getId_amigo() {
        return id;
    }
    public void setId_amigo(Long id) {
        this.id = id;
    }

    public String getNome_usuario() {
        return nome;
    }
    public void setNome_usuario(String nome) {
        this.nome = nome;
    }

    public String getTelefone_usuario() {
        return telefone;
    }
    public void setTelefone_usuario(String telefone) {
        this.telefone = telefone;
    }

    // GETTERS E SETTERS

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getoDevedor() { return oDevedor; }
    public void setoDevedor(Boolean oDevedor) { this.oDevedor = oDevedor; }
}