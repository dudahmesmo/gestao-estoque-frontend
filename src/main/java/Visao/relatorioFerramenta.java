package Visao;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import Controle.FerramentasControle;
import http.ApiClient;

public class relatorioFerramenta extends javax.swing.JFrame {

    private final ApiClient apiClient = new ApiClient();
    private final DecimalFormat df = new DecimalFormat("0.00");

    public relatorioFerramenta() {
        initComponents();
        atualizarBanco();
    }

    // GUI (initComponents)
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPanel = new javax.swing.JScrollPane();
        toolsTable = new javax.swing.JTable();
        totalCostLabel = new javax.swing.JLabel();
        AtualizarBD = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatório de Ferramentas e Custo Total de Aquisição");
        setResizable(false);

        toolsTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Nome", "Marca", "Custo Unitário (R$)",
                    "Qtd. Estoque", "Custo Total (R$)"
                }
        ));
        toolsTable.setShowGrid(true);
        jScrollPanel.setViewportView(toolsTable);

        totalCostLabel.setFont(new java.awt.Font("Segoe UI", 0, 13));
        totalCostLabel.setText("Total de Custo: R$ 0.00");

        AtualizarBD.setText("Atualizar banco de dados");
        AtualizarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtualizarBDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPanel,
                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(totalCostLabel)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(350, 350, 350)
                                .addComponent(AtualizarBD)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(AtualizarBD)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPanel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE, 232,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(totalCostLabel)
                                .addContainerGap(25, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(null); // Centralizar a janela
    }

    private void atualizarBanco() {
        DefaultTableModel model = (DefaultTableModel) toolsTable.getModel();
        model.setRowCount(0);

        try {
            // Obtém o relatório de custo total (detalhado)
            Map<String, Object> resultado = apiClient.getCustoTotalEstoque();

            if (resultado != null && resultado.containsKey("detalhes_ferramentas")) {

                // 1. Processa a lista detalhada
                List<Map<String, Object>> detalhes = (List<Map<String, Object>>) resultado.get("detalhes_ferramentas");

                for (Map<String, Object> detalhe : detalhes) {
                    Object[] rowData = {
                        detalhe.get("id"),
                        detalhe.get("nome"),
                        "N/A",
                        df.format(detalhe.get("custo_unitario")),
                        detalhe.get("quantidade_em_estoque"),
                        df.format(detalhe.get("custo_total_ferramenta"))
                    };
                    model.addRow(rowData);
                }

                // 2. Atualiza o custo total geral
                double custoTotalGeral = (double) resultado.get("custo_total_geral");
                totalCostLabel.setText("Total de Custo: R$ " + df.format(custoTotalGeral));

            } else {
                JOptionPane.showMessageDialog(this, "Erro ao obter relatório de custos ou dados incompletos.");
                totalCostLabel.setText("Total de Custo: R$ 0.00");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar dados do relatório de ferramentas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void AtualizarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtualizarBDActionPerformed
        atualizarBanco(); // 
    }//GEN-LAST:event_AtualizarBDActionPerformed

    private javax.swing.JButton AtualizarBD;
    private javax.swing.JScrollPane jScrollPanel;
    private javax.swing.JTable toolsTable;
    private javax.swing.JLabel totalCostLabel;
    // End of variables declaration//GEN-END:variables
}