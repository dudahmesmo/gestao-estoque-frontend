package Visao;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Controle.EmprestimosControle;

public class relatorioDevedores extends javax.swing.JFrame {

    private EmprestimosControle emprestimosControle = new EmprestimosControle();

    public relatorioDevedores() {
        initComponents();
        atualizarBanco();
    }

    // GUI (initComponents)
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        autualizarBD = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatório de Devedores");
        setResizable(false);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID Amigo", "Nome", "Telefone"
                }
        ));

        jTable2.setShowGrid(true);
        jScrollPane4.setViewportView(jTable2);
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
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane4,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(205, 205, 205)
                                .addComponent(autualizarBD)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane4,
                                        javax.swing.GroupLayout.PREFERRED_SIZE, 246,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        27, Short.MAX_VALUE)
                                .addComponent(autualizarBD)
                                .addGap(25, 25, 25))
        );
        pack();
        setLocationRelativeTo(null); // Centralizar a janela
    }

    private void atualizarBanco() {

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        try {

            List<Modelo.Amigos> devedores = new Controle.EmprestimosControle().listarDevedores();

            if (devedores != null && !devedores.isEmpty()) {
                for (Modelo.Amigos amigo : devedores) {
                    // Preenche a tabela com ID, Nome e Telefone do Amigo
                    Object[] rowData = {
                        amigo.getId_amigo(),
                        amigo.getNome(),
                        amigo.getTelefone()
                    };
                    model.addRow(rowData);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum devedor encontrado ou erro na API.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar lista de devedores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void autualizarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event autualizarBDActionPerformed
        atualizarBanco(); // 
    }//GEN-LAST:event autualizarBDActionPerformed

    private javax.swing.JButton autualizarBD;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
