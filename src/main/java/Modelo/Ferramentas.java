package Modelo;

public class Ferramentas {
    // Atributos da classe Ferramentas
    private int id;
    private String nome;
    private String marca;
    private double custo;

    // Construtor da classe que recebe nome, marca e custo como parâmetros
    public Ferramentas(String nome, String marca, double custo) {
        this.nome = nome;
        this.marca = marca;
        this.custo = custo;
    }

    // Métodos getters e setters para acessar e modificar os atributos da classe

    // Getter para o atributo id
    public int getId() {
        return id;
    }

    // Setter para o atributo id
    public void setId(int id) {
        this.id = id;
    }

    // Getter para o atributo nome
    public String getNome() {
        return nome;
    }

    // Setter para o atributo nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter para o atributo marca
    public String getMarca() {
        return marca;
    }

    // Setter para o atributo marca
    public void setMarca(String marca) {
        this.marca = marca;
    }

    // Getter para o atributo custo
    public double getCusto() {
        return custo;
    }

    // Setter para o atributo custo
    public void setCusto(double custo) {
        this.custo = custo;
    }
}
