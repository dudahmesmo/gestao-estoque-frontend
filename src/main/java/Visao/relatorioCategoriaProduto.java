package Visao;

import java.awt.Dimension;
import java.util.List;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controle.FerramentasControle;

public class relatorioCategoriaProduto extends javax.swing.JFrame {

    private final FerramentasControle ferramentasControle = new FerramentasControle();

    private javax.swing.JButton autualizarBD;
    private javax.swing.JScrollPane jScrollPanel;
    private javax.swing.JTable tableRelatorio;

    public relatorioCategoriaProduto() {
        initComponents();
        this.setLocationRelativeTo(null);
        atualizarBanco();
    }

    // Atualização da tabela
    private void atualizarBanco() {
        DefaultTableModel model = (DefaultTableModel) tableRelatorio.getModel();
        model.setRowCount(0); // Limpar a tabela

        try {
            List<Object[]> resultados = ferramentasControle.getQuantidadeProdutosPorCategoria();

            if (resultados != null && !resultados.isEmpty()) {
                for (Object[] rowData : resultados) {

                    String nomeCategoria = (String) rowData[0];
                    Number quantidade = (Number) rowData[1];

                    Object[] linha = {
                        nomeCategoria,
                        quantidade.intValue()
                    };
                    model.addRow(linha);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum dado encontrado ou falha na comunicação com a API.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar relatório: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // GUI (initComponents)
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPanel = new JScrollPane();
        tableRelatorio = new JTable();
        autualizarBD = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatório de Produtos por Categoria");
        setResizable(true);

        setSize(new Dimension(800, 450));

        // Configuração da tabela
        tableRelatorio.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Categoria", "Quantidade de Produtos"
                }
        ));
        tableRelatorio.setShowGrid(true);
        jScrollPanel.setViewportView(tableRelatorio);

        // Botão Atualizar
        autualizarBD.setText("Atualizar Relatório");

        // Mapeamento de Ação
        autualizarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autualizarBDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(autualizarBD)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(autualizarBD)
                                .addContainerGap(40, Short.MAX_VALUE))
        );
        pack();
    }

    // Ação do botão
    private void autualizarBDActionPerformed(java.awt.event.ActionEvent evt) {
        atualizarBanco();
    }

}
