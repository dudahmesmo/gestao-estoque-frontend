package visao;

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
                String statusEstoque = f.getStatusEstoque();
                String resumoEstoque = f.getResumoEstoque();
                
                model.addRow(new Object[]{
                    f.getId_ferramenta(),             // ID
                    f.getNome_ferramenta(),           // NOME
                    f.getMarca_ferramenta(),          // MARCA
                    f.getPreco(),                     // PREÇO
                    statusEstoque,                    // STATUS DO ESTOQUE
                    resumoEstoque                     // RESUMO COMPLETO
                });
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaFerramentas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        autualizarBD = new javax.swing.JButton();
        btnEstoqueBaixo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciar Ferramentas");

        TabelaFerramentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Ferramenta", "Marca", "Custo de Aquisição", "Status Estoque", "Detalhes Estoque"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, 
                java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
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

        autualizarBD.setText("Atualizar banco de dados");
        autualizarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autualizarBDActionPerformed(evt);
            }
        });

        btnEstoqueBaixo.setText("Ver Estoque Baixo");
        btnEstoqueBaixo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstoqueBaixoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnEstoqueBaixo)
                        .addGap(18, 18, 18)
                        .addComponent(autualizarBD)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(autualizarBD)
                    .addComponent(btnEstoqueBaixo))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        pack();
    }                     
    
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

    private void btnEstoqueBaixoActionPerformed(java.awt.event.ActionEvent evt) {
        List<Ferramentas> estoqueBaixo = ferramentasControle.getFerramentasComEstoqueBaixo();
        if (estoqueBaixo != null && !estoqueBaixo.isEmpty()) {
            StringBuilder mensagem = new StringBuilder("Ferramentas com estoque baixo:\n\n");
            for (Ferramentas f : estoqueBaixo) {
                mensagem.append(f.getNome()).append(" - ").append(f.getStatusEstoque()).append("\n");
            }
            JOptionPane.showMessageDialog(this, mensagem.toString(), "Estoque Baixo", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Nenhuma ferramenta com estoque baixo encontrada.", "Estoque Baixo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gerenciarFerramentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JTable TabelaFerramentas;
    private javax.swing.JButton autualizarBD;
    private javax.swing.JButton btnEstoqueBaixo;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration                   
}