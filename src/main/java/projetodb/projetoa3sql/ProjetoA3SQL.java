package projetodb.projetoa3sql;

import javax.swing.JFrame;

import Visao.registroEmprestimo;

public class ProjetoA3SQL {
   
    public static void main(String[] args) {
        
        
        System.out.println("Iniciando aplicação (Modo API Client)..."); 
        registroEmprestimo objetotela = new registroEmprestimo();
        objetotela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        objetotela.setVisible(true); 
    }
}