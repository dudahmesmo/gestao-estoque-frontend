package Visao;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.DefaultListCellRenderer;

import Controle.FerramentasControle;
import Modelo.Ferramentas;
import Modelo.Categoria;

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
    private JComboBox<Categoria> comboBoxCategoria; // MUDEI PARA Categoria
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
        
        // Seleciona a categoria correta no combobox
        if (ferramentaOriginal.getCategoria() != null) {
            Categoria categoriaOriginal = ferramentaOriginal.getCategoria();
            
            // Procura a categoria no combobox pelo ID
            for (int i = 0; i < comboBoxCategoria.getItemCount(); i++) {
                Categoria cat = comboBoxCategoria.getItemAt(i);
                if (cat != null && cat.getId() != null && 
                    cat.getId().equals(categoriaOriginal.getId())) {
                    comboBoxCategoria.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void carregarCategorias() {
        try {
            List<Categoria> categorias = ferramentaControle.obterCategorias();
            comboBoxCategoria.removeAllItems();
            
            // Adiciona um item vazio/nulo para seleção
            comboBoxCategoria.addItem(null);
            
            if (categorias != null) {
                for (Categoria cat : categorias) {
                    comboBoxCategoria.addItem(cat);
                }
            } else {
                // Se não conseguir carregar, adiciona categorias padrão
                adicionarCategoriasPadrao();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Aviso: Falha ao carregar categorias do servidor.\nUsando categorias padrão.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            adicionarCategoriasPadrao();
        }
    }
    
    private void adicionarCategoriasPadrao() {
        comboBoxCategoria.addItem(new Categoria(1L, "Elétrica"));
        comboBoxCategoria.addItem(new Categoria(2L, "Manual"));
        comboBoxCategoria.addItem(new Categoria(3L, "Hidráulica"));
        comboBoxCategoria.addItem(new Categoria(4L, "Pneumática"));
        comboBoxCategoria.addItem(new Categoria(5L, "Geral"));
    }

    // Ação do botão SALVAR EDIÇÃO 
    private void btnSalvarActionPerformed(ActionEvent evt) {
        try {
            // Validações básicas
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome da ferramenta é obrigatório!");
                return;
            }
            
            if (txtMarca.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Marca da ferramenta é obrigatória!");
                return;
            }
            
            // Validação de preço
            double preco;
            try {
                String precoTexto = txtCustoF.getText().replace(",", ".");
                preco = Double.parseDouble(precoTexto);
                if (preco < 0) {
                    JOptionPane.showMessageDialog(this, "Preço não pode ser negativo!");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Preço inválido! Use números com ponto ou vírgula.");
                return;
            }
            
            // Validação de quantidades
            int quantidadeEstoque, quantidadeMinima, quantidadeMaxima;
            try {
                quantidadeEstoque = Integer.parseInt(txtQuantidade_estoque.getText());
                quantidadeMinima = Integer.parseInt(txtQuantidade_minima.getText());
                quantidadeMaxima = Integer.parseInt(txtQuantidade_maxima.getText());
                
                if (quantidadeEstoque < 0) {
                    JOptionPane.showMessageDialog(this, "Quantidade em estoque não pode ser negativa!");
                    return;
                }
                
                if (quantidadeMinima < 0) {
                    JOptionPane.showMessageDialog(this, "Quantidade mínima não pode ser negativa!");
                    return;
                }
                
                if (quantidadeMaxima <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantidade máxima deve ser maior que zero!");
                    return;
                }
                
                if (quantidadeMinima >= quantidadeMaxima) {
                    JOptionPane.showMessageDialog(this, "Quantidade mínima deve ser menor que quantidade máxima!");
                    return;
                }
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Quantidades devem ser números inteiros válidos!");
                return;
            }
            
            // Validação de categoria
            Categoria categoriaSelecionada = (Categoria) comboBoxCategoria.getSelectedItem();
            if (categoriaSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria!");
                return;
            }

            // 1. Atualiza o objeto ferramenta original
            ferramentaOriginal.setNome(txtNome.getText().trim());
            ferramentaOriginal.setMarca(txtMarca.getText().trim());
            ferramentaOriginal.setPreco(preco);
            ferramentaOriginal.setQuantidade_estoque(quantidadeEstoque);
            ferramentaOriginal.setQuantidade_minima(quantidadeMinima);
            ferramentaOriginal.setQuantidade_maxima(quantidadeMaxima);
            ferramentaOriginal.setCategoria(categoriaSelecionada); // AGORA É OBJETO CATEGORIA
            ferramentaOriginal.setDisponivel(quantidadeEstoque > 0);
            
            // 2. Chama o controle para atualizar (PUT)
            boolean sucesso = ferramentaControle.atualizarFerramenta(ferramentaOriginal);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Ferramenta atualizada com sucesso!\n" +
                    "Nome: " + ferramentaOriginal.getNome() + "\n" +
                    "Categoria: " + categoriaSelecionada.getNome() + "\n" +
                    "Estoque: " + quantidadeEstoque + " unidades", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); 
                telaPai.atualizarBanco(); // Atualiza a tabela na tela de gerenciamento
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Falha ao atualizar a ferramenta. Verifique o console do Back-end.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro inesperado ao salvar: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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

        // Configurar o renderer para mostrar o nome da categoria
        comboBoxCategoria.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Categoria) {
                    setText(((Categoria) value).getNome());
                } else if (value == null) {
                    setText("Selecione uma categoria");
                } else {
                    setText(value.toString());
                }
                return this;
            }
        });

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