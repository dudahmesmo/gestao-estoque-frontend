package Modelo;

import java.text.DecimalFormat; 

public class Ferramentas {

    private Long id;
    private String nome;
    private String marca;
    private double preco;
    private Boolean disponivel = true; 
    private double custoAquisicao;
    private int Quantidade_estoque;      // Quantidade atual em estoque
    private int Quantidade_minima;       // Quantidade mínima necessária
    private int Quantidade_maxima;       // Quantidade máxima permitida

    public Ferramentas() {}

    public Ferramentas(String nome, String marca, double preco, int Quantidade_estoque, int Quantidade_minima, int Quantidade_maxima) {
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.Quantidade_estoque = Quantidade_estoque;
        this.Quantidade_minima = Quantidade_minima;
        this.Quantidade_maxima = Quantidade_maxima;
        this.disponivel = Quantidade_estoque > 0;
    }

    // MÉTODOS GETTERS E SETTERS
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
    
    public boolean isDisponivel() { 
        return disponivel != null && disponivel && Quantidade_estoque > 0; 
    }

    public double getCustoAquisicao() { return custoAquisicao; }
    public void setCustoAquisicao(double custoAquisicao) { this.custoAquisicao = custoAquisicao; }
    
    // MÉTODOS PARA CONTROLE DE ESTOQUE
    public int getQuantidade_estoque() { return Quantidade_estoque; }
    public void setQuantidade_estoque(int Quantidade_estoque) { 
        this.Quantidade_estoque = Quantidade_estoque; 
        // Atualiza automaticamente o status de disponibilidade
        if (this.Quantidade_estoque <= 0) {
            this.disponivel = false;
        } else {
            this.disponivel = true;
        }
    }
    
    public int getQuantidade_minima() { return Quantidade_minima; }
    public void setQuantidade_minima(int Quantidade_minima) { 
        this.Quantidade_minima = Quantidade_minima; 
    }
    
    public int getQuantidade_maxima() { return Quantidade_maxima; }
    public void setQuantidade_maxima(int Quantidade_maxima) { 
        this.Quantidade_maxima = Quantidade_maxima; 
    }
    
    // MÉTODO PARA VERIFICAR SE ESTÁ ABAIXO DO MÍNIMO
    public boolean isEstoqueBaixo() {
        return Quantidade_estoque <= Quantidade_minima;
    }
    
    // MÉTODO PARA VERIFICAR SE ESTÁ ACIMA DO MÁXIMO
    public boolean isEstoqueExcedido() {
        return Quantidade_estoque > Quantidade_maxima;
    }
    
    // MÉTODO PARA OBTER STATUS COMPLETO DO ESTOQUE
    public String getStatusEstoque() {
        if (Quantidade_estoque <= 0) {
            return "FORA DE ESTOQUE";
        } else if (isEstoqueBaixo()) {
            return "ESTOQUE BAIXO (" + Quantidade_estoque + " unidades)";
        } else if (isEstoqueExcedido()) {
            return "ESTOQUE EXCEDIDO (" + Quantidade_estoque + " unidades)";
        } else {
            return Quantidade_estoque + " unidades (OK)";
        }
    }
    
    // MÉTODO PARA OBTER RESUMO DO ESTOQUE
    public String getResumoEstoque() {
        return "Atual: " + Quantidade_estoque + " | Mín: " + Quantidade_minima + " | Máx: " + Quantidade_maxima;
    }
    
    // Getters longos para compatibilidade
    public int getId_ferramenta() { return id != null ? id.intValue() : 0; }
    public String getNome_ferramenta() { return nome; }
    public String getMarca_ferramenta() { return marca; }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00");
        String status = this.getStatusEstoque();
        return this.id + " - " + this.nome + " (" + this.marca + ") - R$ " + df.format(this.preco) + " - " + status;
    }
}