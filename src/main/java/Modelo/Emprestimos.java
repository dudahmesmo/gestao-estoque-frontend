package Modelo;

import java.util.Date;

public class Emprestimos {
    private int idEmprestimo;
    private int idFerramenta;
    private String nomeFerramenta;
    private Date dataEmprestimo;
    private Date dataDevolucaoEsperada;
    private int idAmigo;
    private String nomeUsuario;
    private String telefoneUsuario;
    private String statusEmprestimo;
    private String amigoNome;
    private String ferramentaNome;
    
    private String marcaFerramenta;
    private double preco;

    public Emprestimos() {
    }

    public Emprestimos(int idEmprestimo, int idFerramenta, String nomeFerramenta, Date dataEmprestimo, Date dataDevolucaoEsperada, int idAmigo, String nomeUsuario, String telefoneUsuario, String statusEmprestimo) {
        this.idEmprestimo = idEmprestimo;
        this.idFerramenta = idFerramenta;
        this.nomeFerramenta = nomeFerramenta;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoEsperada = dataDevolucaoEsperada;
        this.idAmigo = idAmigo;
        this.nomeUsuario = nomeUsuario;
        this.telefoneUsuario = telefoneUsuario;
        this.statusEmprestimo = statusEmprestimo;
    }

    // Getters para acessar os atributos da classe
    public int getIdEmprestimo() {
        return idEmprestimo;
    }
    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public int getIdFerramenta() {
        return idFerramenta;
    }
    public void setIdFerramenta(int idFerramenta) {
        this.idFerramenta = idFerramenta;
    }

    public String getNomeFerramenta() {
        return nomeFerramenta;
    }
    public void setNomeFerramenta(String nomeFerramenta) {
        this.nomeFerramenta = nomeFerramenta;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }
    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucaoEsperada() {
        return dataDevolucaoEsperada;
    }
    public void setDataDevolucaoEsperada(Date dataDevolucaoEsperada) {
        this.dataDevolucaoEsperada = dataDevolucaoEsperada;
    }

    public int getIdAmigo() {
        return idAmigo;
    }
    public void setIdAmigo(int idAmigo) {
        this.idAmigo = idAmigo;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }
    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public String getStatusEmprestimo() {
        return statusEmprestimo;
    }
    public void setStatusEmprestimo(String statusEmprestimo) {
        this.statusEmprestimo = statusEmprestimo;
    }

    public String getAmigoNome() {
        return amigoNome;
    }
    public void setAmigoNome(String amigoNome) {
        this.amigoNome = amigoNome;
    }

    public String getFerramentaNome() {
        return ferramentaNome;
    }
    public void setFerramentaNome(String ferramentaNome) {
        this.ferramentaNome = ferramentaNome;
    }
    
    public String getMarcaFerramenta() {
        return marcaFerramenta;
    }
    public void setMarcaFerramenta(String marcaFerramenta) {
        this.marcaFerramenta = marcaFerramenta;
    }
    
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
}