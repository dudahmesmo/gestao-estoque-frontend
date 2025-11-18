package Visao;

import javax.swing.JOptionPane;

import Controle.FerramentasControle;

public class cadastrarFerramentas extends javax.swing.JFrame {
    
    private FerramentasControle ferramentaControle;

    public cadastrarFerramentas() {
        initComponents(); 
        this.ferramentaControle = new FerramentasControle();
        // Garante que o rótulo está correto para Custo/Preço 
        jLabel3.setText("Custo de Aquisição (R$):"); 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        txtCadastrarNomeFerramenta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCadastrarMarcaFerramenta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCadastrarCustoF = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Ferramentas");

        jLabel1.setText("Nome:");
        jLabel2.setText("Marca:");
        jLabel3.setText("Custo de Aquisição (R$):");

        jButton1.setText("Cadastrar Ferramenta");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));

        // Layout 
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCadastrarMarcaFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCadastrarNomeFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCadastrarCustoF, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(52, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(120, 120, 120))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadastrarNomeFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCadastrarMarcaFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCadastrarCustoF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(32, 32, 32)
                .addComponent(jButton1)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        try {
            String nome = txtCadastrarNomeFerramenta.getText();
            String marca = txtCadastrarMarcaFerramenta.getText();
            
            // Lógica de PREÇO (Troca vírgula por ponto)
            String precoTexto = txtCadastrarCustoF.getText().replace(",", ".");
            double preco = Double.parseDouble(precoTexto);

            if (nome.length() < 2) {
                JOptionPane.showMessageDialog(this, "Nome inválido.");
                return;
            }

            // Manda o preço para o controle, que envia os dados de adaptação ao Backend
            boolean sucesso = ferramentaControle.adicionarFerramenta(nome, marca, preco);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Ferramenta cadastrada!");
                txtCadastrarNomeFerramenta.setText("");
                txtCadastrarMarcaFerramenta.setText("");
                txtCadastrarCustoF.setText("");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O Custo deve ser um número válido.");
        }
    }                                        

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new cadastrarFerramentas().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtCadastrarCustoF;
    private javax.swing.JTextField txtCadastrarMarcaFerramenta;
    private javax.swing.JTextField txtCadastrarNomeFerramenta;
    // End of variables declaration//GEN-END:variables
}
