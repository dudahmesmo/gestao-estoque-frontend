package Modelo;

import java.text.DecimalFormat;

public class Ferramentas {

    private Long id;
    private String nome;
    private String marca;
    private double preco;
    private Boolean disponivel = true;
    private double custoAquisicao;
    private int quantidadeEstoque;
    private int quantidadeMinima;
    private int quantidadeMaxima;
    private Categoria categoria; // AGORA É UM OBJETO CATEGORIA

    public Ferramentas() {}

    // Construtor sem categoria (para compatibilidade)
    public Ferramentas(String nome, String marca, double preco, int quantidadeEstoque,
                       int quantidadeMinima, int quantidadeMaxima) {
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.quantidadeMinima = quantidadeMinima;
        this.quantidadeMaxima = quantidadeMaxima;
        this.disponivel = quantidadeEstoque > 0;
    }

    // Construtor com categoria como String (para compatibilidade)
    public Ferramentas(String nome, String marca, double preco, int quantidadeEstoque,
                       int quantidadeMinima, int quantidadeMaxima, String nomeCategoria) {
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.quantidadeMinima = quantidadeMinima;
        this.quantidadeMaxima = quantidadeMaxima;
        this.categoria = new Categoria(null, nomeCategoria); // Cria categoria com ID null
        this.disponivel = quantidadeEstoque > 0;
    }

    // Construtor com objeto Categoria (RECOMENDADO)
    public Ferramentas(String nome, String marca, double preco, int quantidadeEstoque,
                       int quantidadeMinima, int quantidadeMaxima, Categoria categoria) {
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.quantidadeMinima = quantidadeMinima;
        this.quantidadeMaxima = quantidadeMaxima;
        this.categoria = categoria;
        this.disponivel = quantidadeEstoque > 0;
    }

    // Construtor completo com ID
    public Ferramentas(Long id, String nome, String marca, double preco, int quantidadeEstoque,
                       int quantidadeMinima, int quantidadeMaxima, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.quantidadeMinima = quantidadeMinima;
        this.quantidadeMaxima = quantidadeMaxima;
        this.categoria = categoria;
        this.disponivel = quantidadeEstoque > 0;
    }

    // GETTERS E SETTERS
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
        return disponivel != null && disponivel && quantidadeEstoque > 0; 
    }

    public double getCustoAquisicao() { return custoAquisicao; }
    public void setCustoAquisicao(double custoAquisicao) { this.custoAquisicao = custoAquisicao; }
    
    // GETTERS E SETTERS PARA CATEGORIA (AGORA É OBJETO)
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    
    // MÉTODOS AUXILIARES PARA CATEGORIA (para compatibilidade)
    public String getNomeCategoria() {
        return categoria != null ? categoria.getNome() : null;
    }
    
    public Long getIdCategoria() {
        return categoria != null ? categoria.getId() : null;
    }
    
    // MÉTODO DE COMPATIBILIDADE (mantido para evitar quebras)
    public void setCategoria(String nomeCategoria) {
        this.categoria = new Categoria(null, nomeCategoria);
    }
    
    // GETTERS E SETTERS PARA QUANTIDADES (com nomes padronizados em camelCase)
    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { 
        this.quantidadeEstoque = quantidadeEstoque; 
        // Atualiza automaticamente o status de disponibilidade
        if (this.quantidadeEstoque <= 0) {
            this.disponivel = false;
        } else {
            this.disponivel = true;
        }
    }
    
    public int getQuantidadeMinima() { return quantidadeMinima; }
    public void setQuantidadeMinima(int quantidadeMinima) { 
        this.quantidadeMinima = quantidadeMinima; 
    }
    
    public int getQuantidadeMaxima() { return quantidadeMaxima; }
    public void setQuantidadeMaxima(int quantidadeMaxima) { 
        this.quantidadeMaxima = quantidadeMaxima; 
    }
    
    // MÉTODOS DE COMPATIBILIDADE (mantendo os nomes antigos)
    public int getQuantidade_estoque() { return quantidadeEstoque; }
    public void setQuantidade_estoque(int quantidadeEstoque) { 
        this.setQuantidadeEstoque(quantidadeEstoque);
    }
    
    public int getQuantidade_minima() { return quantidadeMinima; }
    public void setQuantidade_minima(int quantidadeMinima) { 
        this.setQuantidadeMinima(quantidadeMinima);
    }
    
    public int getQuantidade_maxima() { return quantidadeMaxima; }
    public void setQuantidade_maxima(int quantidadeMaxima) { 
        this.setQuantidadeMaxima(quantidadeMaxima);
    }
    
    // MÉTODO PARA VERIFICAR SE ESTÁ ABAIXO DO MÍNIMO
    public boolean isEstoqueBaixo() {
        return quantidadeEstoque <= quantidadeMinima;
    }
    
    // MÉTODO PARA VERIFICAR SE ESTÁ ACIMA DO MÁXIMO
    public boolean isEstoqueExcedido() {
        return quantidadeEstoque > quantidadeMaxima;
    }
    
    // MÉTODO PARA OBTER STATUS COMPLETO DO ESTOQUE
    public String getStatusEstoque() {
        if (quantidadeEstoque <= 0) {
            return "FORA DE ESTOQUE";
        } else if (isEstoqueBaixo()) {
            return "ESTOQUE BAIXO (" + quantidadeEstoque + " unidades)";
        } else if (isEstoqueExcedido()) {
            return "ESTOQUE EXCEDIDO (" + quantidadeEstoque + " unidades)";
        } else {
            return quantidadeEstoque + " unidades (OK)";
        }
    }
    
    // Método para obter resumo de estoque
    public String getResumoEstoque() {
        return "Atual: " + quantidadeEstoque + " | Mín: " + quantidadeMinima + " | Máx: " + quantidadeMaxima;
    }
    
    // Getters longos para compatibilidade
    public int getId_ferramenta() { return id != null ? id.intValue() : 0; }
    public String getNome_ferramenta() { return nome; }
    public String getMarca_ferramenta() { return marca; }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00");
        String status = this.getStatusEstoque();
        
        String cat = this.categoria != null ? this.categoria.getNome() : "Sem Categoria";
        return this.id + " - " + this.nome + " (" + this.marca + ") - " + cat + " - R$ " + df.format(this.preco) + " - " + status;
    }
    
    // Método para obter status simplificado (exibição rápida)
    public String getStatusSimples() {
        if (quantidadeEstoque <= 0) {
            return "❌ FORA DE ESTOQUE";
        } else if (isEstoqueBaixo()) {
            return "⚠️ ESTOQUE BAIXO";
        } else {
            return "✅ EM ESTOQUE";
        }
    }

    // Método para obter status disponível
    public String getQuantidadeDisponivel() {
        if (quantidadeEstoque <= 0) {
            return "Indisponível";
        } else {
            return quantidadeEstoque + " unidades";
        }
    }
    
    // MÉTODOS PARA JSON (serialização)
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":").append(id != null ? id : "null").append(",");
        json.append("\"nome\":\"").append(nome != null ? nome : "").append("\",");
        json.append("\"marca\":\"").append(marca != null ? marca : "").append("\",");
        json.append("\"preco\":").append(preco).append(",");
        json.append("\"quantidadeEstoque\":").append(quantidadeEstoque).append(",");
        json.append("\"quantidadeMinima\":").append(quantidadeMinima).append(",");
        json.append("\"quantidadeMaxima\":").append(quantidadeMaxima).append(",");
        if (categoria != null) {
            json.append("\"categoria\":").append(categoria.toJson()).append(",");
        } else {
            json.append("\"categoria\":null,");
        }
        json.append("\"disponivel\":").append(disponivel);
        json.append("}");
        return json.toString();
    }
    
    // Versão alternativa do toJson que não depende do método toJson() da Categoria
    public String toJsonCompleto() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":").append(id != null ? id : "null").append(",");
        json.append("\"nome\":\"").append(nome != null ? nome : "").append("\",");
        json.append("\"marca\":\"").append(marca != null ? marca : "").append("\",");
        json.append("\"preco\":").append(preco).append(",");
        json.append("\"quantidadeEstoque\":").append(quantidadeEstoque).append(",");
        json.append("\"quantidadeMinima\":").append(quantidadeMinima).append(",");
        json.append("\"quantidadeMaxima\":").append(quantidadeMaxima).append(",");
        if (categoria != null) {
            json.append("\"categoria\": {");
            json.append("\"id\":").append(categoria.getId() != null ? categoria.getId() : "null").append(",");
            json.append("\"nome\":\"").append(categoria.getNome() != null ? categoria.getNome() : "").append("\"");
            json.append("},");
        } else {
            json.append("\"categoria\":null,");
        }
        json.append("\"disponivel\":").append(disponivel);
        json.append("}");
        return json.toString();
    }
    
    // Método para verificar igualdade (baseado no ID)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ferramentas that = (Ferramentas) obj;
        return id != null && id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}