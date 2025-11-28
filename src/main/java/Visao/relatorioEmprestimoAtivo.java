package Visao;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.util.List;
import Modelo.Emprestimos;
import Controle.EmprestimosControle;
import java.text.SimpleDateFormat;

public class relatorioEmprestimoAtivo extends javax.swing.JFrame {

    private final EmprestimosControle emprestimosControle = new EmprestimosControle();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public relatorioEmprestimoAtivo() {
        initComponents();
        atualizarBanco();
    }

    // GUI (initComponents)
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPanel = new javax.swing.JScrollPane();
        tableEmprestimosAtivos = new javax.swing.JTable();
        autualizarBD = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatório Empréstimos Ativos");
        setResizable(false);

        tableEmprestimosAtivos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Amigo", "Ferramenta", "Data de Empréstimo",
                    "Devolução Prevista"
                }
        ));
        tableEmprestimosAtivos.setShowGrid(true);
        jScrollPanel.setViewportView(tableEmprestimosAtivos);
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
                                .addComponent(jScrollPanel,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, 785, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(315, 315, 315)
                                .addComponent(autualizarBD)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jScrollPanel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE, 228,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(autualizarBD)
                                .addContainerGap(76, Short.MAX_VALUE))
        );
        pack();
    }

    private void atualizarBanco() {
        DefaultTableModel model = (DefaultTableModel) tableEmprestimosAtivos.getModel();
        model.setRowCount(0);

        try {
            List<Modelo.Emprestimos> emprestimosAtivos
                    = new Controle.EmprestimosControle().listarEmprestimosAtivos();

            if (emprestimosAtivos != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                for (Modelo.Emprestimos emprestimo : emprestimosAtivos) {

                    Object[] rowData = {
                        emprestimo.getIdEmprestimo(),
                        emprestimo.getAmigoNome(),
                        emprestimo.getFerramentaNome(),
                        // Formata a data de empréstimo
                        dateFormat.format(emprestimo.getDataEmprestimo()),
                        emprestimo.getDataDevolucaoEsperada() != null
                        ? dateFormat.format(emprestimo.getDataDevolucaoEsperada()) : "---"
                    };
                    model.addRow(rowData);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum empréstimo ativo encontrado ou falha na comunicação.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar empréstimos ativos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void autualizarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event autualizarBDActionPerformed
        atualizarBanco(); // 
    }//GEN-LAST:event autualizarBDActionPerformed

    private javax.swing.JButton autualizarBD;
    private javax.swing.JScrollPane jScrollPanel;
    private javax.swing.JTable tableEmprestimosAtivos;
    // End of variables declaration//GEN-END:variables
}