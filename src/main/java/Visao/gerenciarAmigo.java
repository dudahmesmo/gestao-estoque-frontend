package Visao;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Controle.AmigosControle;
import Modelo.Amigos; 
import java.util.List; 

public class gerenciarAmigo extends javax.swing.JFrame {

    private AmigosControle amigosControle; 

    public gerenciarAmigo() {
        initComponents(); // Inicializa os componentes da interface gráfica
        
        //Inicializa o controle com o novo construtor vazio (que já inicializa o ApiClient)
        this.amigosControle = new AmigosControle();

        atualizarTabela(); // Atualiza a tabela de amigos
    }

    /* O método conectarBanco() foi removido */

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
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
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
            // Exibe uma mensagem se nenhuma linha estiver selecionada
            JOptionPane.showMessageDialog(this, "Selecione um amigo para excluir.");
            return;
        }
        // Obtém o ID do amigo da linha selecionada
        int idAmigo = (int) TabelaAmigos.getValueAt(rowIndex, 0);

        // 1. Chama o Controle (que chama o ApiClient e trata os erros/JOptionPanes)
        amigosControle.deletarAmigo(idAmigo); 
            
        // 2. Atualiza a tabela para mostrar que o amigo sumiu
        atualizarTabela();
    }//GEN-LAST:event_buttonExcluirAmigoActionPerformed

    private void autualizarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autualizarBDActionPerformed
        // Atualiza a tabela de amigos
         atualizarTabela();
    }//GEN-LAST:event_autualizarBDActionPerformed
    
    private void atualizarTabela() {
        
        
        // 1. Limpa a tabela 
        DefaultTableModel model = (DefaultTableModel) TabelaAmigos.getModel();
        model.setRowCount(0);
            
        // 2. Chama o Controle para buscar os dados da API
        // O 'amigosControle' já trata o erro (mostra JOptionPane) e retorna null se falhar
        List<Amigos> listaDeAmigos = this.amigosControle.listarAmigos();

        // 3. Preenche a tabela com os resultados da API
        if (listaDeAmigos != null) { // Verifica se a API não deu erro
            for (Amigos amigo : listaDeAmigos) {
                // Adiciona cada amigo como uma nova linha na tabela
                model.addRow(new Object[]{
                    amigo.getId_amigo(), 
                    amigo.getNome_usuario(), 
                    amigo.getTelefone_usuario()
                });
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

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