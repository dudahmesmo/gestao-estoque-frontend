package visao;

import Controle.FerramentasControle;
import javax.swing.JOptionPane; 
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class cadastrarFerramentas extends javax.swing.JFrame {
    
    private FerramentasControle ferramentaControle;
    private javax.swing.JLabel lblStatusEstoque;

    public cadastrarFerramentas() {
        initComponents(); 
        this.ferramentaControle = new FerramentasControle();
        
        // Configurar labels
        jLabel3.setText("Custo de Aquisição (R$):");
        jLabel4.setText("Quantidade em Estoque:");
        jLabel5.setText("Quantidade Mínima:"); 
        jLabel6.setText("Quantidade Máxima:");
        jLabel7.setText("Categoria:");
        
        // Configurar valores padrão
        txtQuantidade_estoque.setText("0");
        txtQuantidade_minima.setText("1");
        txtQuantidade_maxima.setText("100");
        
        // Carregar categorias do back-end - COM TRATAMENTO MELHORADO
        carregarCategorias();
        
        // Adicionar listeners para atualização em tempo real do status
        adicionarListenersEstoque();
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
        lblStatusEstoque = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Ferramentas");

        jLabel1.setText("Nome:");
        jLabel2.setText("Marca:");
        jLabel3.setText("Custo de Aquisição (R$):");
        jLabel4.setText("Quantidade em Estoque:");
        jLabel5.setText("Quantidade Mínima:");
        jLabel6.setText("Quantidade Máxima:");
        jLabel7.setText("Categoria:");

        // Configurar label de status
        lblStatusEstoque.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblStatusEstoque.setForeground(new java.awt.Color(100, 100, 100));
        lblStatusEstoque.setText("Status: Aguardando dados...");
        lblStatusEstoque.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

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
                        .addComponent(comboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblStatusEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(18, 18, 18)
                .addComponent(lblStatusEstoque)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(null); // Centralizar a janela
    }

    private void carregarCategorias() {
        System.out.println("Iniciando carregamento de categorias...");
        
        try {
            // Limpar o comboBox primeiro
            comboBoxCategoria.removeAllItems();
            
            // Adicionar item padrão
            comboBoxCategoria.addItem("Selecione uma categoria");
            
            // Obter categorias do back-end através do controle
            System.out.println("Solicitando categorias ao controle...");
            List<String> categorias = ferramentaControle.obterCategorias();
            
            if (categorias != null && !categorias.isEmpty()) {
                System.out.println("Categorias recebidas: " + categorias.size());
                
                // Adicionar categorias obtidas do back-end
                for (String categoria : categorias) {
                    System.out.println("Adicionando categoria: " + categoria);
                    comboBoxCategoria.addItem(categoria);
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Categorias carregadas com sucesso! Total: " + categorias.size(), 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } else {
                System.out.println("Lista de categorias vazia ou nula");
                // Adicionar categorias padrão em caso de erro
                adicionarCategoriasPadrao();
                JOptionPane.showMessageDialog(this, 
                    "Usando categorias padrão (back-end não disponível)", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar categorias: " + e.getMessage());
            e.printStackTrace();
            
            // Adicionar categorias padrão em caso de erro
            adicionarCategoriasPadrao();
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar categorias. Usando categorias padrão.\nErro: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarCategoriasPadrao() {
        String[] categoriasPadrao = {
            "Elétrica", 
            "Manual", 
            "Hidráulica", 
            "Pneumática", 
            "Medição", 
            "Corte", 
            "Fixação", 
            "Jardim", 
            "Construção", 
            "Outros"
        };
        
        for (String categoria : categoriasPadrao) {
            comboBoxCategoria.addItem(categoria);
        }
    }

    private void adicionarListenersEstoque() {
        // Listener para quantidade em estoque
        DocumentListener listener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { atualizarStatusEstoque(); }
            public void removeUpdate(DocumentEvent e) { atualizarStatusEstoque(); }
            public void insertUpdate(DocumentEvent e) { atualizarStatusEstoque(); }
        };

        txtQuantidade_estoque.getDocument().addDocumentListener(listener);
        txtQuantidade_minima.getDocument().addDocumentListener(listener);
    }

    private void atualizarStatusEstoque() {
        try {
            int estoque = txtQuantidade_estoque.getText().isEmpty() ? 0 : Integer.parseInt(txtQuantidade_estoque.getText());
            int minimo = txtQuantidade_minima.getText().isEmpty() ? 1 : Integer.parseInt(txtQuantidade_minima.getText());
            
            if (estoque <= 0) {
                lblStatusEstoque.setText("STATUS: ❌ FORA DE ESTOQUE");
                lblStatusEstoque.setForeground(new java.awt.Color(200, 0, 0)); // Vermelho
            } else if (estoque <= minimo) {
                lblStatusEstoque.setText("STATUS: ⚠️ ESTOQUE BAIXO (" + estoque + " unidades)");
                lblStatusEstoque.setForeground(new java.awt.Color(255, 140, 0)); // Laranja
            } else {
                lblStatusEstoque.setText("STATUS: ✅ EM ESTOQUE (" + estoque + " unidades disponíveis)");
                lblStatusEstoque.setForeground(new java.awt.Color(0, 100, 0)); // Verde
            }
        } catch (NumberFormatException e) {
            lblStatusEstoque.setText("STATUS: ⚠️ Dados inválidos");
            lblStatusEstoque.setForeground(new java.awt.Color(100, 100, 100)); // Cinza
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String nome = txtCadastrarNomeFerramenta.getText().trim();
            String marca = txtCadastrarMarcaFerramenta.getText().trim();
            
            // Validações básicas
            if (nome.isEmpty() || marca.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nome e marca são obrigatórios.", 
                    "Campos Obrigatórios", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
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
                JOptionPane.showMessageDialog(this, "Nome deve ter pelo menos 2 caracteres.");
                return;
            }
            
            if (categoria == null || categoria.equals("Selecione uma categoria")) {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria válida.");
                return;
            }
            
            if (Quantidade_estoque < 0) {
                JOptionPane.showMessageDialog(this, "Quantidade em estoque não pode ser negativa.");
                return;
            }
            
            if (Quantidade_minima < 0) {
                JOptionPane.showMessageDialog(this, "Quantidade mínima não pode ser negativa.");
                return;
            }
            
            if (Quantidade_maxima <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade máxima deve ser maior que zero.");
                return;
            }
            
            if (Quantidade_minima >= Quantidade_maxima) {
                JOptionPane.showMessageDialog(this, "Quantidade mínima deve ser menor que quantidade máxima.");
                return;
            }

            // Verificar se estoque está acima do máximo permitido
            if (Quantidade_estoque > Quantidade_maxima) {
                int resposta = JOptionPane.showConfirmDialog(this, 
                    "A quantidade em estoque (" + Quantidade_estoque + ") excede o máximo permitido (" + Quantidade_maxima + ").\nDeseja continuar mesmo assim?",
                    "Estoque excedido",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (resposta != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Passa os dados para o Controle (incluindo a categoria)
            boolean sucesso = ferramentaControle.adicionarFerramenta(nome, marca, preco, Quantidade_estoque, Quantidade_minima, Quantidade_maxima, categoria);

            if (sucesso) {
                String mensagemSucesso = "Ferramenta cadastrada com sucesso!\n";
                mensagemSucesso += "Status do estoque: ";
                
                if (Quantidade_estoque <= 0) {
                    mensagemSucesso += "FORA DE ESTOQUE";
                } else if (Quantidade_estoque <= Quantidade_minima) {
                    mensagemSucesso += "ESTOQUE BAIXO";
                } else {
                    mensagemSucesso += "EM ESTOQUE";
                }
                
                JOptionPane.showMessageDialog(this, mensagemSucesso, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                // Limpa todos os campos
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao cadastrar ferramenta. Verifique o console para mais detalhes.", 
                    "Erro no Cadastro", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Verifique se todos os valores numéricos estão corretos.\n• Preço deve ser um número decimal\n• Quantidades devem ser números inteiros", 
                "Erro de Formatação", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro inesperado: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtCadastrarNomeFerramenta.setText("");
        txtCadastrarMarcaFerramenta.setText("");
        txtCadastrarCustoF.setText("");
        txtQuantidade_estoque.setText("0");
        txtQuantidade_minima.setText("1");
        txtQuantidade_maxima.setText("100");
        comboBoxCategoria.setSelectedIndex(0);
        lblStatusEstoque.setText("Status: Aguardando dados...");
        lblStatusEstoque.setForeground(new java.awt.Color(100, 100, 100));
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new cadastrarFerramentas().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Erro ao iniciar a tela de cadastro: " + e.getMessage(), 
                    "Erro de Inicialização", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
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
   // private javax.swing.JLabel lblStatusEstoque;
    // End of variables declaration                                
}