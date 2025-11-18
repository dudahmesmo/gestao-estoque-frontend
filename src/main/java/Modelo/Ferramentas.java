package Modelo;

public class Ferramentas {

    private int id;
    private String nome;
    private String marca;
    private double preco;
    private int quantidade;
    private boolean disponivel;
    private double custoAquisicao; 

    public Ferramentas() {
    }

    public Ferramentas(int id, String nome, String marca, double preco, int quantidade, boolean disponivel, double custoAquisicao) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
        this.custoAquisicao = custoAquisicao;
    }

    // -- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public double getCustoAquisicao() {
        return custoAquisicao;
    }

    public void setCustoAquisicao(double custoAquisicao) {
        this.custoAquisicao = custoAquisicao;
    }

    @Override
    public String toString() {
        return nome;
    }
}