package Visao;

import Controle.FerramentasControle;
import Modelo.Ferramentas;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class gerenciarFerramentas extends javax.swing.JFrame {

    private FerramentasControle ferramentasControle;

    public gerenciarFerramentas() {
        initComponents();
        this.ferramentasControle = new FerramentasControle();
        atualizarBanco();
    }

    private void atualizarBanco() {
        DefaultTableModel model = (DefaultTableModel) TabelaFerramentas.getModel();
        model.setRowCount(0);

        List<Ferramentas> lista = ferramentasControle.listarFerramentas();

        if (lista != null) {
            for (Ferramentas f : lista) {
                String status = f.isDisponivel() ? "Sim" : "Não";
                
                model.addRow(new Object[]{
                    f.getId_ferramenta(),             // ID
                    f.getNome_ferramenta(),           // NOME
                    f.getMarca_ferramenta(),          // MARCA
                    f.getPreco(),                     // PREÇO
                    status                            // DISPONÍVEL
                });
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaFerramentas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        autualizarBD = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciar Ferramentas");

        TabelaFerramentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "Ferramenta", "Marca", "Custo de Aquisição", "Disponível"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaFerramentas.setShowGrid(true);
        jScrollPane1.setViewportView(TabelaFerramentas);

        jButton1.setText("Excluir Ferramentas");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        autualizarBD.setText("Atualizar Tabela");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(autualizarBD)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(241, 241, 241)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(autualizarBD)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        int rowIndex = TabelaFerramentas.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma ferramenta para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esta ferramenta?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        Object idObj = TabelaFerramentas.getValueAt(rowIndex, 0);
        int idFerramenta = ((Number) idObj).intValue();

        boolean sucesso = ferramentasControle.deletarFerramenta(idFerramenta);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Ferramenta excluída com sucesso.");
            atualizarBanco(); 
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao excluir a ferramenta. (Verifique o Controle)");
        }
    }

    private void autualizarBDActionPerformed(java.awt.event.ActionEvent evt) {
        atualizarBanco();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gerenciarFerramentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelaFerramentas;
    private javax.swing.JButton autualizarBD;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}