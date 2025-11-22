package visao;

import Controle.FerramentasControle;
import javax.swing.JOptionPane; 

public class cadastrarFerramentas extends javax.swing.JFrame {
    
    private FerramentasControle ferramentaControle;

    public cadastrarFerramentas() {
        initComponents(); 
        this.ferramentaControle = new FerramentasControle();
        jLabel3.setText("Custo de Aquisição (R$):");
        jLabel4.setText("Quantidade_estoque:");
        jLabel5.setText("Quantidade_minima:"); 
        jLabel6.setText("Quantidade_maxima:");
        jLabel7.setText("Categoria:");
        
        // Carregar categorias do back-end
        carregarCategorias();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        txtCadastrarNomeFerramenta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCadastrarMarcaFerramenta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCadastrarCustoF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtQuantidade_estoque = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtQuantidade_minima = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtQuantidade_maxima = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        comboBoxCategoria = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Ferramentas");

        jLabel1.setText("Nome:");
        jLabel2.setText("Marca:");
        jLabel3.setText("Custo de Aquisição (R$):");
        jLabel4.setText("Quantidade_estoque:");
        jLabel5.setText("Quantidade_minima:");
        jLabel6.setText("Quantidade_maxima:");
        jLabel7.setText("Categoria:");

        // Configurar valores padrão
        txtQuantidade_estoque.setText("0");
        txtQuantidade_minima.setText("1");
        txtQuantidade_maxima.setText("100");

        jButton1.setText("Cadastrar Ferramenta");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));

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
                        .addComponent(txtCadastrarCustoF, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtQuantidade_estoque, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtQuantidade_minima, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtQuantidade_maxima, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantidade_estoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantidade_minima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantidade_maxima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(32, 32, 32)
                .addComponent(jButton1)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        pack();
    }

    private void carregarCategorias() {
        try {
            // Obter categorias do back-end através do controle
            java.util.List<String> categorias = ferramentaControle.obterCategorias();
            
            // Limpar e adicionar as categorias no comboBox
            comboBoxCategoria.removeAllItems();
            
            // Adicionar item vazio ou padrão
            comboBoxCategoria.addItem("Selecione uma categoria");
            
            // Adicionar categorias obtidas do back-end
            for (String categoria : categorias) {
                comboBoxCategoria.addItem(categoria);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar categorias: " + e.getMessage());
            // Adicionar categorias padrão em caso de erro
            comboBoxCategoria.removeAllItems();
            comboBoxCategoria.addItem("Selecione uma categoria");
            comboBoxCategoria.addItem("Elétrica");
            comboBoxCategoria.addItem("Manual");
            comboBoxCategoria.addItem("Hidráulica");
            comboBoxCategoria.addItem("Pneumática");
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String nome = txtCadastrarNomeFerramenta.getText();
            String marca = txtCadastrarMarcaFerramenta.getText();
            
            // Lógica de PREÇO (Troca vírgula por ponto)
            String precoTexto = txtCadastrarCustoF.getText().replace(",", ".");
            double preco = Double.parseDouble(precoTexto);
            
            // Lógica para quantidades
            int Quantidade_estoque = Integer.parseInt(txtQuantidade_estoque.getText());
            int Quantidade_minima = Integer.parseInt(txtQuantidade_minima.getText());
            int Quantidade_maxima = Integer.parseInt(txtQuantidade_maxima.getText());
            
            // Obter categoria selecionada
            String categoria = (String) comboBoxCategoria.getSelectedItem();

            // Validações
            if (nome.length() < 2) {
                JOptionPane.showMessageDialog(this, "Nome inválido.");
                return;
            }
            
            if (categoria == null || categoria.equals("Selecione uma categoria")) {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria.");
                return;
            }
            
            if (Quantidade_estoque < 0) {
                JOptionPane.showMessageDialog(this, "Quantidade_estoque não pode ser negativa.");
                return;
            }
            
            if (Quantidade_minima < 0) {
                JOptionPane.showMessageDialog(this, "Quantidade_minima não pode ser negativa.");
                return;
            }
            
            if (Quantidade_maxima <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade_maxima deve ser maior que zero.");
                return;
            }
            
            if (Quantidade_minima >= Quantidade_maxima) {
                JOptionPane.showMessageDialog(this, "Quantidade_minima deve ser menor que Quantidade_maxima.");
                return;
            }

            // Passa os dados para o Controle (incluindo a categoria)
            boolean sucesso = ferramentaControle.adicionarFerramenta(nome, marca, preco, Quantidade_estoque, Quantidade_minima, Quantidade_maxima, categoria);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Ferramenta cadastrada com sucesso!");
                // Limpa todos os campos
                txtCadastrarNomeFerramenta.setText("");
                txtCadastrarMarcaFerramenta.setText("");
                txtCadastrarCustoF.setText("");
                txtQuantidade_estoque.setText("0");
                txtQuantidade_minima.setText("1");
                txtQuantidade_maxima.setText("100");
                comboBoxCategoria.setSelectedIndex(0);
            } 
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique se todos os valores numéricos estão corretos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new cadastrarFerramentas().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtCadastrarCustoF;
    private javax.swing.JTextField txtCadastrarMarcaFerramenta;
    private javax.swing.JTextField txtCadastrarNomeFerramenta;
    private javax.swing.JTextField txtQuantidade_estoque;
    private javax.swing.JTextField txtQuantidade_minima;
    private javax.swing.JTextField txtQuantidade_maxima;
    private javax.swing.JComboBox<String> comboBoxCategoria;
    // End of variables declaration                   
}