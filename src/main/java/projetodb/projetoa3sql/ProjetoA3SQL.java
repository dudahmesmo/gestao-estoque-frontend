package projetodb.projetoa3sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;

import Visao.registroEmprestimo;

public class ProjetoA3SQL {
    // Declaração da conexão como variável estática para ser acessada em todo o programa
    private static Connection conexao = null;

    public static void main(String[] args) {
        try {
            // Estabelece a conexão com o banco de dados
            conexao = Conexao.conectar();
            System.out.println("Conexão bem-sucedida ao banco de dados");

            // Cria e exibe o JFrame
            registroEmprestimo objetotela = new registroEmprestimo();

            // Adiciona um listener para fechar a conexão quando o JFrame for fechado
            objetotela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            objetotela.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    if (conexao != null) {
                        try {
                            // Fecha a conexão com o banco de dados
                            conexao.close();
                            System.out.println("Conexão fechada");
                        } catch (SQLException ex) {
                            System.out.println("Erro ao fechar a conexão: " + ex.getMessage());
                        }
                    }
                }
            });

            objetotela.setVisible(true); // Torna o JFrame visível

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
        }
    }
}
