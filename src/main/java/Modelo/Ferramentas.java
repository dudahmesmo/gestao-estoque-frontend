package Modelo;

public class Ferramentas {

    // Os atributos 
    private int id_ferramenta;
    private String nome_ferramenta;
    private String marca_ferramenta;
    private double preco;

    // Construtor vazio
    public Ferramentas() {
    }

    // Construtor antigo
    public Ferramentas(String nome, String marca, double custo) {
        this.nome_ferramenta = nome;
        this.marca_ferramenta = marca;
        this.preco = custo;
    }

    // Métodos Getters e Setters 

    // Getter para o atributo id
    public int getId_ferramenta() {
        return id_ferramenta;
    }

    // Setter para o atributo id
    public void setId_ferramenta(int id_ferramenta) {
        this.id_ferramenta = id_ferramenta;
    }

    // Getter para o atributo nome
    public String getNome_ferramenta() {
        return nome_ferramenta;
    }

    // Setter para o atributo nome
    public void setNome_ferramenta(String nome_ferramenta) {
        this.nome_ferramenta = nome_ferramenta;
    }

    // Getter para o atributo marca
    public String getMarca_ferramenta() {
        return marca_ferramenta;
    }

    // Setter para o atributo marca
    public void setMarca_ferramenta(String marca_ferramenta) {
        this.marca_ferramenta = marca_ferramenta;
    }

    // Getter para o atributo preco
    public double getPreco() {
        return preco;
    }

    // Setter para o atributo preco
    public void setPreco(double preco) {
        this.preco = preco;
    }
}
