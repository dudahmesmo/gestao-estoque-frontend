package Visao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import Controle.CategoriaControle;
import Modelo.Categoria;

public class gerenciarCategorias extends JFrame {
    private JTable categoriasTable;
    private DefaultTableModel tableModel;
    private JTextField nomeField;
    private JButton adicionarBtn, editarBtn, excluirBtn, limparBtn, atualizarBtn;
    private CategoriaControle categoriaControle;
    private JLabel statusLabel;
    private boolean modoEdicao = false;
    private Long idEmEdicao = null;
    
    public gerenciarCategorias() {
        setTitle("Gerenciador de Categorias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Inicializar controle
        categoriaControle = new CategoriaControle();
        
        initComponents();
        layoutComponents();
        configurarEventos();
        
        // Carregar categorias ao iniciar
        carregarCategorias();
    }
    
    private void initComponents() {
        // Modelo da tabela
        String[] colunas = {"ID", "Nome"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Long.class;
                return String.class;
            }
        };
        
        // Tabela
        categoriasTable = new JTable(tableModel);
        categoriasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoriasTable.setAutoCreateRowSorter(true);
        
        // Campos de formulário
        nomeField = new JTextField(20);
        
        // Botões
        adicionarBtn = new JButton("Adicionar");
        editarBtn = new JButton("Editar");
        excluirBtn = new JButton("Excluir");
        limparBtn = new JButton("Limpar");
        atualizarBtn = new JButton("Atualizar Lista");
        
        // Status
        statusLabel = new JLabel("Conectando ao servidor...");
        
        // Desabilitar botões inicialmente
        editarBtn.setEnabled(false);
        excluirBtn.setEnabled(false);
    }
    
    private void layoutComponents() {
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel superior - status e botão atualizar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(statusLabel, BorderLayout.WEST);
        topPanel.add(atualizarBtn, BorderLayout.EAST);
        
        // Painel da tabela
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Categorias"));
        JScrollPane tableScroll = new JScrollPane(categoriasTable);
        tablePanel.add(tableScroll, BorderLayout.CENTER);
        
        // Painel do formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Formulário de Categoria"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nome*:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(nomeField, gbc);
        
        // Painel de botões
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 3;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(adicionarBtn);
        buttonPanel.add(editarBtn);
        buttonPanel.add(excluirBtn);
        buttonPanel.add(limparBtn);
        formPanel.add(buttonPanel, gbc);
        
        // Adicionar componentes ao painel principal
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(formPanel, BorderLayout.SOUTH);
        
        // Menu
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Arquivo
        JMenu arquivoMenu = new JMenu("Arquivo");
        JMenuItem recarregarItem = new JMenuItem("Recarregar Categorias");
        recarregarItem.addActionListener(e -> carregarCategorias());
        
        JMenuItem testarConexaoItem = new JMenuItem("Testar Conexão");
        testarConexaoItem.addActionListener(e -> testarConexao());
        
        JMenuItem sairItem = new JMenuItem("Sair");
        sairItem.addActionListener(e -> System.exit(0));
        
        arquivoMenu.add(recarregarItem);
        arquivoMenu.add(testarConexaoItem);
        arquivoMenu.addSeparator();
        arquivoMenu.add(sairItem);
        
        // Menu Ajuda
        JMenu ajudaMenu = new JMenu("Ajuda");
        JMenuItem sobreItem = new JMenuItem("Sobre");
        sobreItem.addActionListener(e -> mostrarSobre());
        
        ajudaMenu.add(sobreItem);
        
        menuBar.add(arquivoMenu);
        menuBar.add(ajudaMenu);
        setJMenuBar(menuBar);
        
        setContentPane(mainPanel);
    }
    
    private void configurarEventos() {
        // Botão Adicionar
        adicionarBtn.addActionListener(e -> adicionarCategoria());
        
        // Botão Editar
        editarBtn.addActionListener(e -> editarCategoria());
        
        // Botão Excluir
        excluirBtn.addActionListener(e -> excluirCategoria());
        
        // Botão Limpar
        limparBtn.addActionListener(e -> limparCampos());
        
        // Botão Atualizar
        atualizarBtn.addActionListener(e -> carregarCategorias());
        
        // Seleção na tabela
        categoriasTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && categoriasTable.getSelectedRow() != -1) {
                preencherCamposComSelecao();
            }
        });
    }
    
    private void carregarCategorias() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                statusLabel.setText("Carregando categorias...");
                SwingUtilities.invokeLater(() -> {
                    adicionarBtn.setEnabled(false);
                    atualizarBtn.setEnabled(false);
                });
                
                List<Categoria> categorias = categoriaControle.obterTodasCategorias();
                
                SwingUtilities.invokeLater(() -> {
                    tableModel.setRowCount(0);
                    
                    if (categorias != null && !categorias.isEmpty()) {
                        for (Categoria cat : categorias) {
                            tableModel.addRow(new Object[]{cat.getId(), cat.getNome()});
                        }
                        statusLabel.setText("Total de categorias: " + categorias.size());
                    } else {
                        statusLabel.setText("Nenhuma categoria encontrada");
                    }
                    
                    adicionarBtn.setEnabled(true);
                    atualizarBtn.setEnabled(true);
                    editarBtn.setEnabled(false);
                    excluirBtn.setEnabled(false);
                });
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    get();
                } catch (Exception e) {
                    statusLabel.setText("Erro ao carregar categorias");
                    JOptionPane.showMessageDialog(gerenciarCategorias.this,
                        "Erro ao carregar categorias: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                    adicionarBtn.setEnabled(true);
                    atualizarBtn.setEnabled(true);
                }
            }
        };
        worker.execute();
    }
    
    private void adicionarCategoria() {
        String nome = nomeField.getText().trim();
        
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "O nome da categoria é obrigatório!",
                "Validação", JOptionPane.WARNING_MESSAGE);
            nomeField.requestFocus();
            return;
        }
        
        // Verificar se já existe
        if (categoriaControle.categoriaExiste(nome)) {
            JOptionPane.showMessageDialog(this,
                "Já existe uma categoria com este nome!",
                "Categoria Existente", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                statusLabel.setText("Cadastrando categoria...");
                adicionarBtn.setEnabled(false);
                
                return categoriaControle.cadastrarCategoria(nome);
            }
            
            @Override
            protected void done() {
                try {
                    boolean sucesso = get();
                    if (sucesso) {
                        limparCampos();
                        carregarCategorias(); // Recarrega a lista
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(gerenciarCategorias.this,
                        "Erro ao cadastrar categoria: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                } finally {
                    adicionarBtn.setEnabled(true);
                }
            }
        };
        worker.execute();
    }
    
    private void editarCategoria() {
        if (!modoEdicao) {
            // Entrar no modo de edição
            int selectedRow = categoriasTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione uma categoria para editar!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int modelRow = categoriasTable.convertRowIndexToModel(selectedRow);
            idEmEdicao = (Long) tableModel.getValueAt(modelRow, 0);
            
            modoEdicao = true;
            adicionarBtn.setEnabled(false);
            editarBtn.setText("Salvar");
            excluirBtn.setEnabled(false);
            categoriasTable.setEnabled(false);
            atualizarBtn.setEnabled(false);
            
        } else {
            // Salvar a edição
            String novoNome = nomeField.getText().trim();
            
            if (novoNome.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "O nome da categoria é obrigatório!",
                    "Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Verificar se o nome já existe (excluindo a categoria que está sendo editada)
            Categoria existente = categoriaControle.buscarCategoriaPorNome(novoNome);
            if (existente != null && !existente.getId().equals(idEmEdicao)) {
                JOptionPane.showMessageDialog(this,
                    "Já existe uma categoria com este nome!",
                    "Categoria Existente", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    statusLabel.setText("Atualizando categoria...");
                    editarBtn.setEnabled(false);
                    
                    Categoria categoria = new Categoria(idEmEdicao, novoNome);
                    return categoriaControle.atualizarCategoria(categoria);
                }
                
                @Override
                protected void done() {
                    try {
                        boolean sucesso = get();
                        if (sucesso) {
                            modoEdicao = false;
                            idEmEdicao = null;
                            limparCampos();
                            carregarCategorias();
                            
                            adicionarBtn.setEnabled(true);
                            editarBtn.setText("Editar");
                            editarBtn.setEnabled(false);
                            excluirBtn.setEnabled(false);
                            categoriasTable.setEnabled(true);
                            atualizarBtn.setEnabled(true);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(gerenciarCategorias.this,
                            "Erro ao atualizar categoria: " + e.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                        editarBtn.setEnabled(true);
                    }
                }
            };
            worker.execute();
        }
    }
    
    private void excluirCategoria() {
        int selectedRow = categoriasTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione uma categoria para excluir!",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Converter linha da view para linha do modelo
        int modelRow = categoriasTable.convertRowIndexToModel(selectedRow);
        Long id = (Long) tableModel.getValueAt(modelRow, 0);
        String nome = (String) tableModel.getValueAt(modelRow, 1);
        
        // Confirmação
        int confirm = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja excluir a categoria '" + nome + "'?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                statusLabel.setText("Excluindo categoria...");
                excluirBtn.setEnabled(false);
                
                return categoriaControle.excluirCategoria(id);
            }
            
            @Override
            protected void done() {
                try {
                    boolean sucesso = get();
                    if (sucesso) {
                        carregarCategorias(); // Recarrega a lista
                        limparCampos();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(gerenciarCategorias.this,
                        "Erro ao excluir categoria: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                } finally {
                    excluirBtn.setEnabled(categoriasTable.getSelectedRow() != -1);
                }
            }
        };
        worker.execute();
    }
    
    private void preencherCamposComSelecao() {
        int selectedRow = categoriasTable.getSelectedRow();
        if (selectedRow != -1 && !modoEdicao) {
            int modelRow = categoriasTable.convertRowIndexToModel(selectedRow);
            String nome = (String) tableModel.getValueAt(modelRow, 1);
            nomeField.setText(nome);
            editarBtn.setEnabled(true);
            excluirBtn.setEnabled(true);
        }
    }
    
    private void limparCampos() {
        nomeField.setText("");
        categoriasTable.clearSelection();
        nomeField.requestFocus();
        
        if (!modoEdicao) {
            editarBtn.setEnabled(false);
            excluirBtn.setEnabled(false);
        }
    }
    
    private void testarConexao() {
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                statusLabel.setText("Testando conexão...");
                return categoriaControle.testarConexao();
            }
            
            @Override
            protected void done() {
                try {
                    boolean conectado = get();
                    if (conectado) {
                        JOptionPane.showMessageDialog(gerenciarCategorias.this,
                            "Conexão com o servidor estabelecida com sucesso!",
                            "Conexão Testada", JOptionPane.INFORMATION_MESSAGE);
                        statusLabel.setText("Conectado ao servidor");
                    } else {
                        JOptionPane.showMessageDialog(gerenciarCategorias.this,
                            "Não foi possível conectar ao servidor.",
                            "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                        statusLabel.setText("Desconectado do servidor");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(gerenciarCategorias.this,
                        "Erro ao testar conexão: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                    statusLabel.setText("Erro de conexão");
                }
            }
        };
        worker.execute();
    }
    
    private void mostrarSobre() {
        JOptionPane.showMessageDialog(this,
            "Gerenciador de Categorias v1.0\n" +
            "Integração com Backend Spring Boot\n" +
            "Desenvolvido em Java Swing",
            "Sobre",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Usar look and feel do sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                // Definir cores e estilos
                UIManager.put("Button.foreground", Color.BLUE);
                UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 12));
                UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 12));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            gerenciarCategorias gui = new gerenciarCategorias();
            gui.setVisible(true);
            
            // Testar conexão automaticamente
            SwingUtilities.invokeLater(() -> gui.testarConexao());
        });
    }
}