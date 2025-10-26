package Modelo;

import java.util.Date;

public class Emprestimos {
    // Atributos da classe Emprestimos
    private int idEmprestimo;
    private int idFerramenta;
    private String nomeFerramenta;
    private Date dataEmprestimo;
    private Date dataDevolucaoEsperada;
    private int idAmigo;
    private String nomeUsuario;
    private String telefoneUsuario;
    private String statusEmprestimo;

    // Construtor da classe que recebe todos os parâmetros necessários para criar um objeto Emprestimos
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

    // Métodos getters para acessar os atributos da classe

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public int getIdFerramenta() {
        return idFerramenta;
    }

    public String getNomeFerramenta() {
        return nomeFerramenta;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public Date getDataDevolucaoEsperada() {
        return dataDevolucaoEsperada;
    }

    public int getIdAmigo() {
        return idAmigo;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public String getStatusEmprestimo() {
        return statusEmprestimo;
    }
}
