package Visao;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Controle.AmigosControle;
import Modelo.Amigos; 
import java.util.List; 
import java.util.Collections;
import java.util.Comparator;
import Visao.registroEmprestimo; // Import necessário

public class gerenciarAmigo extends javax.swing.JFrame {

    private AmigosControle amigosControle; 
    private final registroEmprestimo telaPrincipal; 

    // Construtor principal (para ser usado pelo registroEmprestimo)
    public gerenciarAmigo(registroEmprestimo telaPrincipal) {
        initComponents();
        this.telaPrincipal = telaPrincipal;
        
        this.amigosControle = new AmigosControle();

        atualizarTabela();
        this.setLocationRelativeTo(null);
        List<Amigos> listaDeAmigos = this.amigosControle.listarAmigos();

        // Implementação da Ordenação Alfabética
        if (listaDeAmigos != null) {
            Collections.sort(listaDeAmigos, Comparator.comparing(Amigos::getNome)); 
        }
    }
    
    public gerenciarAmigo() {
        this(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        button1 = new java.awt.Button();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        buttonExcluirAmigo = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelaAmigos = new javax.swing.JTable();
        autualizarBD = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        button1.setLabel("button1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento de Amigos");
        setResizable(false);

        buttonExcluirAmigo.setText("Excluir Amigo");
        buttonExcluirAmigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExcluirAmigoActionPerformed(evt);
            }
        });

        TabelaAmigos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "Nome", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaAmigos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TabelaAmigos.setShowGrid(true);
        jScrollPane3.setViewportView(TabelaAmigos);

        autualizarBD.setText("Atualizar banco de dados");
        autualizarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autualizarBDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonExcluirAmigo)
                .addGap(258, 258, 258))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(autualizarBD)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(autualizarBD)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(buttonExcluirAmigo)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonExcluirAmigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExcluirAmigoActionPerformed
        // Obtém o índice da linha selecionada na tabela
        int rowIndex = TabelaAmigos.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um amigo para excluir.");
            return;
        }
        
        // Confirmação antes de excluir
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Obtém o ID do amigo da linha selecionada
        int idAmigo = (int) TabelaAmigos.getValueAt(rowIndex, 0);

        // Chama o Controle para deletar
        boolean sucesso = amigosControle.deletarAmigo(idAmigo); 
            
        if (sucesso) {
            atualizarTabela(); // Atualiza a tabela visualmente
            JOptionPane.showMessageDialog(this, "Amigo excluído com sucesso!");
            
            // Atualiza a lista na tela principal
            if (telaPrincipal != null) { 
                telaPrincipal.updateComboAmigos(); // Chama o método de atualização do combo
            }

        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir amigo.");
        }
    }//GEN-LAST:event_buttonExcluirAmigoActionPerformed

    private void autualizarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autualizarBDActionPerformed
        atualizarTabela();
    }//GEN-LAST:event_autualizarBDActionPerformed
    
    private void atualizarTabela() {
        // 1. Pega o modelo da tabela
        DefaultTableModel model = (DefaultTableModel) TabelaAmigos.getModel();
        
        // 2. Limpa a tabela visualmente
        model.setRowCount(0);
            
        // 3. Chama o Controle para buscar os dados da API
        List<Amigos> listaDeAmigos = this.amigosControle.listarAmigos();

        // 4. Implementação da Ordenação Alfabética
        if (listaDeAmigos != null) {
            // Ordena a lista de Amigos por Nome
            listaDeAmigos.sort(Comparator.comparing(Amigos::getNome));
        }
        
        // 5. Preenche a tabela com os resultados da API
        if (listaDeAmigos != null) {
            for (Amigos amigo : listaDeAmigos) {
                model.addRow(new Object[]{
                    amigo.getId(),
                    amigo.getNome(),
                    amigo.getTelefone()
                });
            }
        }
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(gerenciarAmigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gerenciarAmigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gerenciarAmigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gerenciarAmigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gerenciarAmigo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelaAmigos;
    private javax.swing.JButton autualizarBD;
    private java.awt.Button button1;
    private javax.swing.JButton buttonExcluirAmigo;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}