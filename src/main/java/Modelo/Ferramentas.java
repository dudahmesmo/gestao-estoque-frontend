package Modelo;

import java.text.DecimalFormat; 

public class Ferramentas {

    private Long id;
    private String nome;
    private String marca;
    private double preco;
    private Boolean disponivel = true; 
    private double custoAquisicao; 

    public Ferramentas() {}

    public Ferramentas(String nome, String marca, double preco) {
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.disponivel = true;
    }

    // MÉTODOS COMPLETOS PARA COMPILAÇÃO
    public Long getId() { return id; } 
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    
    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }
    public boolean isDisponivel() { return disponivel != null && disponivel; }

    public double getCustoAquisicao() { return custoAquisicao; }
    public void setCustoAquisicao(double custoAquisicao) { this.custoAquisicao = custoAquisicao; }
    
    // Getters longos 
    public int getId_ferramenta() { return id != null ? id.intValue() : 0; }
    public String getNome_ferramenta() { return nome; }
    public String getMarca_ferramenta() { return marca; }


    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00");
        String status = this.isDisponivel() ? "Disponível" : "Emprestada";
        return this.id + " - " + this.nome + " (" + this.marca + ") - R$ " + df.format(this.preco) + " - " + status;
    }
}