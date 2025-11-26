package Visao;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controle.FerramentasControle;
import Modelo.Ferramentas;

public class editarFerramentas extends JFrame {

    private final FerramentasControle ferramentaControle;
    private final Ferramentas ferramentaOriginal;
    private final gerenciarFerramentas telaPai; 

    private JTextField txtNome;
    private JTextField txtMarca;
    private JTextField txtCustoF; 
    private JTextField txtQuantidade_estoque;
    private JTextField txtQuantidade_minima;
    private JTextField txtQuantidade_maxima;
    private JComboBox<String> comboBoxCategoria;
    private JLabel lblId;

    public editarFerramentas(Ferramentas ferramentaParaEditar, gerenciarFerramentas telaGerenciamento) {
        this.ferramentaControle = new FerramentasControle();
        this.ferramentaOriginal = ferramentaParaEditar;
        this.telaPai = telaGerenciamento;
        
        // Se a ferramenta for nula, não inicia
        if (ferramentaParaEditar == null) {
            throw new IllegalArgumentException("Objeto Ferramenta não pode ser nulo para edição.");
        }

        initComponents(); 
        carregarCategorias();
        preencherCampos(); 
        
        this.setLocationRelativeTo(null);
    }
    
    private void preencherCampos() {
        lblId.setText("ID: " + ferramentaOriginal.getId());
        txtNome.setText(ferramentaOriginal.getNome());
        txtMarca.setText(ferramentaOriginal.getMarca());
        txtCustoF.setText(String.format("%.2f", ferramentaOriginal.getPreco()).replace(",", "."));
        txtQuantidade_estoque.setText(String.valueOf(ferramentaOriginal.getQuantidade_estoque()));
        txtQuantidade_minima.setText(String.valueOf(ferramentaOriginal.getQuantidade_minima()));
        txtQuantidade_maxima.setText(String.valueOf(ferramentaOriginal.getQuantidade_maxima()));
        
        if (ferramentaOriginal.getCategoria() != null && !ferramentaOriginal.getCategoria().isEmpty()) {
     
     comboBoxCategoria.setSelectedItem(ferramentaOriginal.getCategoria());  
        }
    }

    private void carregarCategorias() {
        try {
            List<String> categorias = ferramentaControle.obterCategorias();
            comboBoxCategoria.removeAllItems();
            comboBoxCategoria.addItem("Selecione uma categoria");
            if (categorias != null) {
                for (String cat : categorias) {
                    comboBoxCategoria.addItem(cat);
                }
            }
        } catch (Exception e) {
            // Em caso de falha, adiciona categorias padrão para evitar crash
            comboBoxCategoria.addItem("Geral");
            JOptionPane.showMessageDialog(this, "Aviso: Falha ao carregar categorias do servidor.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Ação do botão SALVAR EDIÇÃO 
    private void btnSalvarActionPerformed(ActionEvent evt) {
        try {
            // 1. Coleta dos dados
            double preco = Double.parseDouble(txtCustoF.getText().replace(",", "."));
            String categoriaNome = (String) comboBoxCategoria.getSelectedItem(); // Obtém o nome

            // 2. Cria o objeto Ferramentas 
            Ferramentas ferramentaAtualizada = new Ferramentas(
                ferramentaOriginal.getId(), 
                txtNome.getText().trim(), 
                txtMarca.getText().trim(), 
                preco, 
                Integer.parseInt(txtQuantidade_estoque.getText()), 
                Integer.parseInt(txtQuantidade_minima.getText()), 
                Integer.parseInt(txtQuantidade_maxima.getText()), 
                categoriaNome 
            );
            
            // 3. Chama o controle para atualizar (PUT)
            boolean sucesso = ferramentaControle.atualizarFerramenta(ferramentaAtualizada);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Ferramenta atualizada com sucesso!");
                this.dispose(); 
                telaPai.atualizarBanco(); // Atualiza a tabela na tela de gerenciamento
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao atualizar a ferramenta. Verifique o console do Back-end.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro de formato numérico. Preço ou Quantidades inválidos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao salvar: " + e.getMessage());
        }
    }

    // Interface Gráfica 

@SuppressWarnings("unchecked")
private void initComponents() {
    // Inicializa os componentes
    JLabel lblTitulo = new JLabel("EDITAR FERRAMENTA");
    lblId = new JLabel("ID: "); 
    txtNome = new JTextField();
    txtMarca = new JTextField();
    txtCustoF = new JTextField();
    txtQuantidade_estoque = new JTextField();
    txtQuantidade_minima = new JTextField();
    txtQuantidade_maxima = new JTextField();
    comboBoxCategoria = new JComboBox<>();
    JButton btnSalvar = new JButton("Salvar Alterações");

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Edição de Ferramenta");

    JPanel panelCabecalho = new JPanel(new GridLayout(2, 1));
    lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 16));
    lblTitulo.setHorizontalAlignment(JLabel.CENTER);
    lblId.setHorizontalAlignment(JLabel.CENTER);
    panelCabecalho.add(lblTitulo);
    panelCabecalho.add(lblId);

    JPanel panelCampos = new JPanel(new GridLayout(7, 2, 15, 10));
    
    JLabel lblNome = new JLabel("Nome:");
    lblNome.setHorizontalAlignment(JLabel.RIGHT);
    
    JLabel lblMarca = new JLabel("Marca:");
    lblMarca.setHorizontalAlignment(JLabel.RIGHT);

    JLabel lblCusto = new JLabel("Custo (R$):");
    lblCusto.setHorizontalAlignment(JLabel.RIGHT);

    JLabel lblQtdEstoque = new JLabel("Qtd. Estoque:");
    lblQtdEstoque.setHorizontalAlignment(JLabel.RIGHT);

    JLabel lblQtdMinima = new JLabel("Qtd. Mínima:");
    lblQtdMinima.setHorizontalAlignment(JLabel.RIGHT);

    JLabel lblQtdMaxima = new JLabel("Qtd. Máxima:");
    lblQtdMaxima.setHorizontalAlignment(JLabel.RIGHT);

    JLabel lblCategoria = new JLabel("Categoria:");
    lblCategoria.setHorizontalAlignment(JLabel.RIGHT);


    panelCampos.add(lblNome); panelCampos.add(txtNome);
    panelCampos.add(lblMarca); panelCampos.add(txtMarca);
    panelCampos.add(lblCusto); panelCampos.add(txtCustoF);
    panelCampos.add(lblQtdEstoque); panelCampos.add(txtQuantidade_estoque);
    panelCampos.add(lblQtdMinima); panelCampos.add(txtQuantidade_minima);
    panelCampos.add(lblQtdMaxima); panelCampos.add(txtQuantidade_maxima);
    panelCampos.add(lblCategoria); panelCampos.add(comboBoxCategoria);

    JPanel panelBotoes = new JPanel();
    panelBotoes.add(btnSalvar);

    JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10)); 
    panelPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

    panelPrincipal.add(panelCabecalho, BorderLayout.NORTH);
    panelPrincipal.add(panelCampos, BorderLayout.CENTER);
    panelPrincipal.add(panelBotoes, BorderLayout.SOUTH);
    
    getContentPane().add(panelPrincipal);
    
    pack();
    
    setSize(450, 480); 
    
    setLocationRelativeTo(null); 
    
    btnSalvar.addActionListener(this::btnSalvarActionPerformed);
    }
}