package visao;

import Controle.FerramentasControle;
import Modelo.Ferramentas;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class consultarEstoque extends javax.swing.JFrame {

    private FerramentasControle ferramentasControle;
    private javax.swing.JTable tabelaEstoque;
    private javax.swing.JLabel lblResumoEstoque;
    private javax.swing.JComboBox<String> comboFiltroCategoria;
    private javax.swing.JComboBox<String> comboFiltroStatus;
    private javax.swing.JTextField txtPesquisa;

    public consultarEstoque() {
        initComponents();
        this.ferramentasControle = new FerramentasControle();
        configurarRenderizadorTabela();
        carregarDados();
        atualizarResumoEstoque();
    }

    private void configurarRenderizadorTabela() {
        // Configurar renderizador personalizado para a coluna de Status (índice 5)
        tabelaEstoque.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected && value != null) {
                    String status = value.toString();
                    if (status.contains("FORA DE ESTOQUE")) {
                        cell.setBackground(new Color(255, 200, 200)); // Vermelho claro
                        cell.setForeground(Color.RED);
                        setHorizontalAlignment(CENTER);
                    } else if (status.contains("ESTOQUE BAIXO")) {
                        cell.setBackground(new Color(255, 245, 200)); // Amarelo claro
                        cell.setForeground(new Color(255, 140, 0));
                        setHorizontalAlignment(CENTER);
                    } else if (status.contains("ESTOQUE EXCEDIDO")) {
                        cell.setBackground(new Color(200, 220, 255)); // Azul claro
                        cell.setForeground(new Color(0, 0, 200));
                        setHorizontalAlignment(CENTER);
                    } else {
                        cell.setBackground(new Color(200, 255, 200)); // Verde claro
                        cell.setForeground(new Color(0, 100, 0));
                        setHorizontalAlignment(CENTER);
                    }
                }
                return cell;
            }
        });

        // Configurar renderizador para quantidade em estoque (índice 3)
        tabelaEstoque.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    try {
                        int quantidade = Integer.parseInt(value.toString());
                        if (quantidade <= 0) {
                            cell.setForeground(Color.RED);
                            cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                        } else if (quantidade <= 5) {
                            cell.setForeground(new Color(255, 140, 0)); // Laranja
                            cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                        } else {
                            cell.setForeground(new Color(0, 100, 0)); // Verde
                        }
                        setHorizontalAlignment(CENTER);
                    } catch (NumberFormatException e) {
                        // Ignora erro de parsing
                    }
                }
                return cell;
            }
        });
    }

    private void carregarDados() {
        DefaultTableModel model = (DefaultTableModel) tabelaEstoque.getModel();
        model.setRowCount(0);

        List<Ferramentas> lista = ferramentasControle.listarFerramentas();

        if (lista != null && !lista.isEmpty()) {
            for (Ferramentas f : lista) {
                model.addRow(new Object[]{
                    f.getId_ferramenta(),
                    f.getNome_ferramenta(),
                    f.getMarca_ferramenta(),
                    f.getQuantidade_estoque(),
                    f.getCategoria(),
                    f.getStatusEstoque(),
                    String.format("R$ %.2f", f.getPreco()),
                    f.getResumoEstoque()
                });
            }
        }
        
        atualizarResumoEstoque();
    }

    private void atualizarResumoEstoque() {
        List<Ferramentas> ferramentas = ferramentasControle.listarFerramentas();
        if (ferramentas == null || ferramentas.isEmpty()) {
            lblResumoEstoque.setText("Nenhuma ferramenta cadastrada");
            return;
        }

        int totalFerramentas = ferramentas.size();
        int foraEstoque = 0;
        int estoqueBaixo = 0;
        int estoqueExcedido = 0;
        int emEstoque = 0;
        double valorTotalEstoque = 0;

        for (Ferramentas f : ferramentas) {
            valorTotalEstoque += f.getPreco() * f.getQuantidade_estoque();
            
            if (f.getQuantidade_estoque() <= 0) {
                foraEstoque++;
            } else if (f.isEstoqueBaixo()) {
                estoqueBaixo++;
            } else if (f.isEstoqueExcedido()) {
                estoqueExcedido++;
            } else {
                emEstoque++;
            }
        }

        String resumo = String.format(
            "<html><b>📊 RESUMO DO ESTOQUE:</b> | " +
            "<font color='green'>✅ Em Estoque: %d</font> | " +
            "<font color='orange'>⚠️ Estoque Baixo: %d</font> | " +
            "<font color='red'>❌ Fora de Estoque: %d</font> | " +
            "<font color='blue'>📦 Excedido: %d</font> | " +
            "Total: %d | " +
            "<font color='darkgreen'>💰 Valor Total: R$ %.2f</font></html>",
            emEstoque, estoqueBaixo, foraEstoque, estoqueExcedido, totalFerramentas, valorTotalEstoque
        );

        lblResumoEstoque.setText(resumo);
    }

    private void aplicarFiltros() {
        DefaultTableModel model = (DefaultTableModel) tabelaEstoque.getModel();
        String filtroCategoria = (String) comboFiltroCategoria.getSelectedItem();
        String filtroStatus = (String) comboFiltroStatus.getSelectedItem();
        String textoPesquisa = txtPesquisa.getText().toLowerCase();

        // Primeiro, carrega todos os dados
        List<Ferramentas> todasFerramentas = ferramentasControle.listarFerramentas();
        model.setRowCount(0);

        if (todasFerramentas != null) {
            for (Ferramentas f : todasFerramentas) {
                // Aplicar filtros
                boolean atendeCategoria = filtroCategoria.equals("Todas") || 
                                         f.getCategoria().equals(filtroCategoria);
                
                boolean atendeStatus = filtroStatus.equals("Todos") || 
                                      f.getStatusEstoque().contains(filtroStatus.toUpperCase());
                
                boolean atendePesquisa = textoPesquisa.isEmpty() ||
                                        f.getNome_ferramenta().toLowerCase().contains(textoPesquisa) ||
                                        f.getMarca_ferramenta().toLowerCase().contains(textoPesquisa) ||
                                        f.getCategoria().toLowerCase().contains(textoPesquisa);

                if (atendeCategoria && atendeStatus && atendePesquisa) {
                    model.addRow(new Object[]{
                        f.getId_ferramenta(),
                        f.getNome_ferramenta(),
                        f.getMarca_ferramenta(),
                        f.getQuantidade_estoque(),
                        f.getCategoria(),
                        f.getStatusEstoque(),
                        String.format("R$ %.2f", f.getPreco()),
                        f.getResumoEstoque()
                    });
                }
            }
        }
        
        atualizarResumoEstoque();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaEstoque = new javax.swing.JTable();
        lblResumoEstoque = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        comboFiltroCategoria = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        comboFiltroStatus = new javax.swing.JComboBox<>();
        btnLimparFiltros = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        btnRelatorioCompleto = new javax.swing.JButton();
        btnEstoqueCritico = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta de Estoque - Sistema de Ferramentas");
        setPreferredSize(new java.awt.Dimension(1000, 600));

        // Configurar tabela
        tabelaEstoque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Nome", "Marca", "Qtd Estoque", "Categoria", "Status", "Preço", "Detalhes"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, 
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        // Melhorar aparência da tabela
        tabelaEstoque.setRowHeight(25);
        tabelaEstoque.getTableHeader().setReorderingAllowed(false);
        tabelaEstoque.setAutoCreateRowSorter(true);
        
        // Ajustar largura das colunas
        tabelaEstoque.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tabelaEstoque.getColumnModel().getColumn(1).setPreferredWidth(150); // Nome
        tabelaEstoque.getColumnModel().getColumn(2).setPreferredWidth(100); // Marca
        tabelaEstoque.getColumnModel().getColumn(3).setPreferredWidth(80);  // Qtd
        tabelaEstoque.getColumnModel().getColumn(4).setPreferredWidth(100); // Categoria
        tabelaEstoque.getColumnModel().getColumn(5).setPreferredWidth(120); // Status
        tabelaEstoque.getColumnModel().getColumn(6).setPreferredWidth(80);  // Preço
        tabelaEstoque.getColumnModel().getColumn(7).setPreferredWidth(200); // Detalhes

        jScrollPane1.setViewportView(tabelaEstoque);

        // Label de resumo
        lblResumoEstoque.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblResumoEstoque.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResumoEstoque.setText("Carregando resumo do estoque...");
        lblResumoEstoque.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblResumoEstoque.setOpaque(true);
        lblResumoEstoque.setBackground(new Color(240, 240, 240));

        // Painel de filtros
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros e Pesquisa"));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jLabel1.setText("Pesquisar:");
        jPanel1.add(jLabel1);

        txtPesquisa.setPreferredSize(new java.awt.Dimension(150, 25));
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                aplicarFiltros();
            }
        });
        jPanel1.add(txtPesquisa);

        jLabel2.setText("Categoria:");
        jPanel1.add(jLabel2);

        comboFiltroCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas" }));
        comboFiltroCategoria.setPreferredSize(new java.awt.Dimension(120, 25));
        comboFiltroCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicarFiltros();
            }
        });
        jPanel1.add(comboFiltroCategoria);

        jLabel3.setText("Status:");
        jPanel1.add(jLabel3);

        comboFiltroStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
            "Todos", "FORA DE ESTOQUE", "ESTOQUE BAIXO", "ESTOQUE EXCEDIDO", "EM ESTOQUE" 
        }));
        comboFiltroStatus.setPreferredSize(new java.awt.Dimension(120, 25));
        comboFiltroStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicarFiltros();
            }
        });
        jPanel1.add(comboFiltroStatus);

        btnLimparFiltros.setText("Limpar Filtros");
        btnLimparFiltros.setBackground(new Color(200, 200, 200));
        btnLimparFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparFiltrosActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimparFiltros);

        // Botões de ação
        btnAtualizar.setText("Atualizar");
        btnAtualizar.setBackground(new Color(70, 130, 180));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        btnRelatorioCompleto.setText("Relatório Completo");
        btnRelatorioCompleto.setBackground(new Color(60, 179, 113));
        btnRelatorioCompleto.setForeground(Color.WHITE);
        btnRelatorioCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioCompletoActionPerformed(evt);
            }
        });

        btnEstoqueCritico.setText("Estoque Crítico");
        btnEstoqueCritico.setBackground(new Color(255, 140, 0));
        btnEstoqueCritico.setForeground(Color.WHITE);
        btnEstoqueCritico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstoqueCriticoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(lblResumoEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEstoqueCritico)
                        .addGap(18, 18, 18)
                        .addComponent(btnRelatorioCompleto)
                        .addGap(18, 18, 18)
                        .addComponent(btnAtualizar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEstoqueCritico)
                    .addComponent(btnRelatorioCompleto)
                    .addComponent(btnAtualizar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblResumoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null); // Centralizar na tela
        
        // Carregar categorias para o filtro
        carregarCategoriasFiltro();
    }

    private void carregarCategoriasFiltro() {
        try {
            List<String> categorias = ferramentasControle.obterCategorias();
            if (categorias != null && !categorias.isEmpty()) {
                comboFiltroCategoria.removeAllItems();
                comboFiltroCategoria.addItem("Todas");
                for (String categoria : categorias) {
                    comboFiltroCategoria.addItem(categoria);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar categorias para filtro: " + e.getMessage());
        }
    }

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {
        carregarDados();
        JOptionPane.showMessageDialog(this, 
            "Estoque atualizado com sucesso!", 
            "Atualização", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnLimparFiltrosActionPerformed(java.awt.event.ActionEvent evt) {
        txtPesquisa.setText("");
        comboFiltroCategoria.setSelectedIndex(0);
        comboFiltroStatus.setSelectedIndex(0);
        carregarDados();
    }

    private void btnRelatorioCompletoActionPerformed(java.awt.event.ActionEvent evt) {
        List<Ferramentas> todasFerramentas = ferramentasControle.listarFerramentas();
        if (todasFerramentas != null && !todasFerramentas.isEmpty()) {
            int total = todasFerramentas.size();
            int foraEstoque = 0;
            int estoqueBaixo = 0;
            int estoqueExcedido = 0;
            int emEstoque = 0;
            double valorTotalEstoque = 0;

            StringBuilder relatorio = new StringBuilder();
            relatorio.append("<html><b>📊 RELATÓRIO COMPLETO DE ESTOQUE</b><br><br>");

            for (Ferramentas f : todasFerramentas) {
                valorTotalEstoque += f.getPreco() * f.getQuantidade_estoque();
                
                if (f.getQuantidade_estoque() <= 0) {
                    foraEstoque++;
                } else if (f.isEstoqueBaixo()) {
                    estoqueBaixo++;
                } else if (f.isEstoqueExcedido()) {
                    estoqueExcedido++;
                } else {
                    emEstoque++;
                }
            }

            relatorio.append(String.format(
                "<b>ESTATÍSTICAS GERAIS:</b><br>"
                + "• Total de Itens: <b>%d</b><br>"
                + "• ✅ Em Estoque: <font color='green'><b>%d</b></font><br>"
                + "• ⚠️ Estoque Baixo: <font color='orange'><b>%d</b></font><br>"
                + "• ❌ Fora de Estoque: <font color='red'><b>%d</b></font><br>"
                + "• 📦 Estoque Excedido: <font color='blue'><b>%d</b></font><br>"
                + "• 💰 Valor Total em Estoque: <font color='darkgreen'><b>R$ %.2f</b></font><br><br>",
                total, emEstoque, estoqueBaixo, foraEstoque, estoqueExcedido, valorTotalEstoque
            ));

            // Alertas críticos
            if (foraEstoque > 0 || estoqueBaixo > 0) {
                relatorio.append("<b>🔔 ALERTAS CRÍTICOS:</b><br>");
                if (foraEstoque > 0) {
                    relatorio.append(String.format("• ⚠️ <b>%d item(s) FORA DE ESTOQUE</b> - Necessário reposição urgente!<br>", foraEstoque));
                }
                if (estoqueBaixo > 0) {
                    relatorio.append(String.format("• ⚠️ <b>%d item(s) com ESTOQUE BAIXO</b> - Atenção para reposição<br>", estoqueBaixo));
                }
            } else {
                relatorio.append("✅ <b>Todos os estoques estão em condições adequadas!</b><br>");
            }

            relatorio.append("</html>");

            JOptionPane.showMessageDialog(this, 
                relatorio.toString(), 
                "📊 Relatório Completo de Estoque", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Nenhuma ferramenta cadastrada no sistema.", 
                "Relatório Vazio", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void btnEstoqueCriticoActionPerformed(java.awt.event.ActionEvent evt) {
        List<Ferramentas> estoqueCritico = ferramentasControle.getFerramentasComEstoqueBaixo();
        List<Ferramentas> todasFerramentas = ferramentasControle.listarFerramentas();
        
        if (todasFerramentas != null && !todasFerramentas.isEmpty()) {
            StringBuilder mensagem = new StringBuilder();
            mensagem.append("<html><b>🚨 ESTOQUE CRÍTICO - ATENÇÃO</b><br><br>");
            
            int foraEstoque = 0;
            int estoqueBaixo = 0;
            
            for (Ferramentas f : todasFerramentas) {
                if (f.getQuantidade_estoque() <= 0) {
                    foraEstoque++;
                } else if (f.isEstoqueBaixo()) {
                    estoqueBaixo++;
                }
            }
            
            mensagem.append(String.format(
                "<b>Situação Crítica:</b><br>"
                + "• ❌ Itens FORA DE ESTOQUE: <font color='red'><b>%d</b></font><br>"
                + "• ⚠️ Itens com ESTOQUE BAIXO: <font color='orange'><b>%d</b></font><br><br>",
                foraEstoque, estoqueBaixo
            ));
            
            if (estoqueCritico != null && !estoqueCritico.isEmpty()) {
                mensagem.append("<b>Itens que necessitam de atenção imediata:</b><br>");
                mensagem.append("<table border='1' cellpadding='5' style='border-collapse: collapse;'>");
                mensagem.append("<tr style='background-color: #f0f0f0;'><th>Item</th><th>Estoque Atual</th><th>Mínimo</th><th>Status</th></tr>");
                
                for (Ferramentas f : estoqueCritico) {
                    String cor = f.getQuantidade_estoque() <= 0 ? "red" : "orange";
                    mensagem.append(String.format(
                        "<tr><td><b>%s</b> (%s)</td><td align='center' style='color: %s;'><b>%d</b></td><td align='center'>%d</td><td>%s</td></tr>",
                        f.getNome(), f.getMarca(), cor, f.getQuantidade_estoque(), f.getQuantidade_minima(), f.getStatusEstoque()
                    ));
                }
                mensagem.append("</table>");
            } else {
                mensagem.append("✅ <b>Nenhum item em situação crítica no momento!</b>");
            }
            
            mensagem.append("</html>");
            
            JOptionPane.showMessageDialog(this, 
                mensagem.toString(), 
                "🚨 Estoque Crítico", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new consultarEstoque().setVisible(true);
            }
        });
    }

    // Variables declaration
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnLimparFiltros;
    private javax.swing.JButton btnRelatorioCompleto;
    private javax.swing.JButton btnEstoqueCritico;
    // End of variables declaration
}