package Modelo;

import java.text.DecimalFormat; // Importação adicionada para formatar o preço no toString()

public class Ferramentas {

    // Os atributos 
    private int id_ferramenta;
    private String nome_ferramenta;
    private String marca_ferramenta;
    private double preco;
    
    // NOVO ATRIBUTO: Essencial para controlar empréstimos e devoluções
    private boolean disponivel = true; 

    // Construtor vazio
    public Ferramentas() {
    }

    // Construtor principal (inclui o ID para uso na manipulação de dados)
    public Ferramentas(int id, String nome, String marca, double custo, boolean disponivel) {
        this.id_ferramenta = id;
        this.nome_ferramenta = nome;
        this.marca_ferramenta = marca;
        this.preco = custo;
        this.disponivel = disponivel;
    }
    
    // Construtor para NOVO REGISTRO (sem ID e assumindo Disponível)
    public Ferramentas(String nome, String marca, double custo) {
        this.nome_ferramenta = nome;
        this.marca_ferramenta = marca;
        this.preco = custo;
        this.disponivel = true; // Nova ferramenta é sempre disponível
    }

    // Métodos Getters e Setters 

    /**
     * CORREÇÃO CRÍTICA 1: O método getId() é o que estava faltando no seu código. 
     * Ele chama o seu método existente getId_ferramenta().
     */
    public Long getId() { 
        // Retorna Long para compatibilidade com o código de empréstimo/devolução
        return (long) id_ferramenta;
    }

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
    
    // Getter para o atributo disponivel (isDisponivel)
    /**
     * CORREÇÃO CRÍTICA 2: Este método era necessário para o filtro na tela de empréstimo.
     */
    public boolean isDisponivel() {
        return disponivel;
    }

    // Setter para o atributo disponivel
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    /**
     * CORREÇÃO CRÍTICA 3: O método toString() é usado para exibir no ComboBox
     * e para encontrar a ferramenta pelo nome completo no registroEmprestimo.java.
     */
    @Override
    public String toString() {
        // Formata o preço para duas casas decimais
        DecimalFormat df = new DecimalFormat("#.00");
        String status = this.disponivel ? "Disponível" : "Emprestada";
        
        // Retorna a string completa que aparece no ComboBox: ID - Nome (Marca) - R$ Preço - Status
        return this.id_ferramenta + " - " + this.nome_ferramenta + " (" + this.marca_ferramenta + ") - R$ " + df.format(this.preco) + " - " + status;
    }
}
