package Visao;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Controle.FerramentasControle;
import Modelo.Ferramentas;

public class gerenciarFerramentas extends javax.swing.JFrame {

    private FerramentasControle ferramentasControle;
    private javax.swing.JLabel lblEstatisticas;
    private javax.swing.JPanel panelEstatisticas;
    private List<Ferramentas> listaCompletaFerramentas; 

    // variáveis para pesquisa
    private javax.swing.JTextField txtPesquisa;
    private TableRowSorter<javax.swing.table.TableModel> sorter; 

    public gerenciarFerramentas() {
        initComponents();
        this.ferramentasControle = new FerramentasControle();
        this.listaCompletaFerramentas = new ArrayList<>();
        
        // Inicializa o sorter após a tabela ser criada no initComponents()
        sorter = new TableRowSorter<>(TabelaFerramentas.getModel());
        TabelaFerramentas.setRowSorter(sorter);
        
        configurarRenderizadorTabela();
        adicionarListenerPesquisa();
        atualizarBanco();
        this.setLocationRelativeTo(null);
    }
    
    // ADICIONA O LISTENER À BARRA DE PESQUISA
    private void adicionarListenerPesquisa() {
        txtPesquisa.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { aplicarFiltro(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { aplicarFiltro(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { aplicarFiltro(); }
        });
    }
    
    // APLICA O FILTRO NA TABELA INTEIRA
    private void aplicarFiltro() {
        String texto = txtPesquisa.getText();
        if (texto.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            // (?i) torna a pesquisa case-insensitive (ignora maiúsculas/minúsculas)
            // Busca em TODAS as colunas.
            try {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
            } catch (java.util.regex.PatternSyntaxException e) {
                // Ignora filtro inválido (evita crash se o usuário digitar um caractere regex)
                sorter.setRowFilter(null);
            }
        }
    }

    private void configurarRenderizadorTabela() {
       
        TabelaFerramentas.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected && value != null) {
                    String status = value.toString();
                    if (status.contains("FORA DE ESTOQUE")) {
                        cell.setBackground(new Color(255, 200, 200)); // Vermelho claro
                        cell.setForeground(Color.RED);
                        setText("❌ " + status);
                    } else if (status.contains("ESTOQUE BAIXO")) {
                        cell.setBackground(new Color(255, 245, 200)); // Amarelo claro
                        cell.setForeground(new Color(255, 140, 0));
                        setText("⚠️ " + status);
                    } else if (status.contains("ESTOQUE EXCEDIDO")) {
                        cell.setBackground(new Color(200, 220, 255)); // Azul claro
                        cell.setForeground(new Color(0, 0, 200));
                        setText("📦 " + status);
                    } else {
                        cell.setBackground(new Color(200, 255, 200)); // Verde claro
                        cell.setForeground(new Color(0, 100, 0));
                        setText("✅ " + status);
                    }
                }
                
                return cell;
            }
        });

        // Configurar renderizador para a coluna de Detalhes Estoque (índice 5)
        TabelaFerramentas.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    cell.setBackground(new Color(240, 240, 240)); // Cinza muito claro
                    cell.setForeground(Color.DARK_GRAY);
                }
                
                return cell;
            }
        });

        // Configurar renderizador para a coluna de Preço (índice 3)
        TabelaFerramentas.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    cell.setForeground(new Color(0, 100, 0)); // Verde escuro para valores monetários
                    setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                }
                
                return cell;
            }
        });
    }

    public void atualizarBanco() {
        DefaultTableModel model = (DefaultTableModel) TabelaFerramentas.getModel();
        model.setRowCount(0);
        this.listaCompletaFerramentas.clear();

        List<Ferramentas> lista = ferramentasControle.listarFerramentas();

        if (lista != null) {
            this.listaCompletaFerramentas = lista; // Armazena a lista completa
            for (Ferramentas f : lista) {
                String statusEstoque = f.getStatusEstoque();
                String resumoEstoque = f.getResumoEstoque();
                String categoria = f.getCategoria() != null ? f.getCategoria() : "N/D"; // Pega Categoria
                
                model.addRow(new Object[]{
                    f.getId(), // 0: ID
                    f.getNome(), // 1: Nome
                    f.getMarca(), // 2: Marca
                    categoria, // 3: Categoria 
                    String.format("R$ %.2f", f.getPreco()), // 4: Preço
                    statusEstoque, // 5: Status Estoque
                    resumoEstoque // 6: Detalhes do Estoque
                });
            }
        }
        
        aplicarFiltro(); // Reaplica o filtro após atualizar os dados
        atualizarEstatisticasEstoque(lista);
    }

    private void atualizarEstatisticasEstoque(List<Ferramentas> ferramentas) {
        if (ferramentas == null) {
            lblEstatisticas.setText("Estatísticas: Nenhum dado disponível");
            return;
        }
        
        int totalFerramentas = ferramentas.size();
        int foraEstoque = 0;
        int estoqueBaixo = 0;
        int estoqueExcedido = 0;
        int emEstoque = 0;
        
        for (Ferramentas f : ferramentas) {
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
        
        // Atualizar label com estatísticas coloridas
        String estatisticas = String.format(
            "<html><b>ESTATÍSTICAS DO ESTOQUE:</b> | " +
            "<font color='green'>Em Estoque: %d</font> | " +
            "<font color='orange'>Estoque Baixo: %d</font> | " +
            "<font color='red'>Fora de Estoque: %d</font> | " +
            "<font color='blue'>Estoque Excedido: %d</font> | " +
            "Total: %d</html>",
            emEstoque, estoqueBaixo, foraEstoque, estoqueExcedido, totalFerramentas
        );
        
        lblEstatisticas.setText(estatisticas);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaFerramentas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        autualizarBD = new javax.swing.JButton();
        btnEstoqueBaixo = new javax.swing.JButton();
        lblEstatisticas = new javax.swing.JLabel();
        panelEstatisticas = new javax.swing.JPanel();
        btnEditarFerramenta = new javax.swing.JButton();
        btnRelatorioEstoque = new javax.swing.JButton();
        txtPesquisa = new javax.swing.JTextField(); 

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciar Ferramentas - Controle de Estoque");
        setPreferredSize(new java.awt.Dimension(1100, 650));

        TabelaFerramentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Ferramenta", "Marca", "Categoria", "Custo de Aquisição", "Status Estoque", "Detalhes do Estoque"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, 
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        TabelaFerramentas.setShowGrid(true);
        TabelaFerramentas.setGridColor(new Color(220, 220, 220));
        TabelaFerramentas.setRowHeight(25);
        TabelaFerramentas.getTableHeader().setReorderingAllowed(false);
        
        jScrollPane1.setViewportView(TabelaFerramentas);

        jButton1.setText("Excluir Ferramenta");
        jButton1.setBackground(new Color(255, 100, 100));
        jButton1.setForeground(Color.WHITE);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        autualizarBD.setText("Atualizar Tabela");
        autualizarBD.setBackground(new Color(70, 130, 180));
        autualizarBD.setForeground(Color.WHITE);
        autualizarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autualizarBDActionPerformed(evt);
            }
        });

        btnEstoqueBaixo.setText("Ver Estoque Baixo");
        btnEstoqueBaixo.setBackground(new Color(255, 165, 0));
        btnEstoqueBaixo.setForeground(Color.WHITE);
        btnEstoqueBaixo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstoqueBaixoActionPerformed(evt);
            }
        });

        // Label de estatísticas
        lblEstatisticas.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblEstatisticas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstatisticas.setText("Carregando estatísticas...");
        lblEstatisticas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        // Painel de estatísticas
        panelEstatisticas.setBackground(new Color(240, 240, 240));
        panelEstatisticas.setBorder(javax.swing.BorderFactory.createTitledBorder("Visão Geral do Estoque"));

        btnEditarFerramenta.setText("Editar Ferramenta");
        btnEditarFerramenta.setBackground(new Color(70, 130, 180));
        btnEditarFerramenta.setForeground(Color.WHITE);
        btnEditarFerramenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarFerramentaActionPerformed(evt);
            }
        });

        btnRelatorioEstoque.setText("Relatório Completo");
        btnRelatorioEstoque.setBackground(new Color(60, 179, 113));
        btnRelatorioEstoque.setForeground(Color.WHITE);
        btnRelatorioEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioEstoqueActionPerformed(evt);
            }
        });
        
        // Configuração do campo de pesquisa
        txtPesquisa.setPreferredSize(new java.awt.Dimension(300, 37)); 
        txtPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar (Nome, Marca, Status)"));


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(lblEstatisticas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEstoqueBaixo)
                        .addGap(18, 18, 18)
                        .addComponent(btnRelatorioEstoque)
                        .addGap(18, 18, 18)
                        .addComponent(autualizarBD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)) // LARGURA 300
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEditarFerramenta)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEstoqueBaixo)
                    .addComponent(btnRelatorioEstoque)
                    .addComponent(autualizarBD)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblEstatisticas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarFerramenta)
                    .addComponent(jButton1))
                .addContainerGap(20, Short.MAX_VALUE))
        );


        pack();
        setLocationRelativeTo(null); 
    } 
    
    // Retorna o objeto selecionado da lista completa
    private Ferramentas getFerramentaSelecionada() {
        int viewRow = TabelaFerramentas.getSelectedRow();
        if (viewRow == -1 || listaCompletaFerramentas == null || listaCompletaFerramentas.isEmpty()) {
            return null;
        }
        
        // Converte a linha da View (após filtragem/ordenação) para a linha do Modelo
        int modelRow = TabelaFerramentas.convertRowIndexToModel(viewRow);

        // Obtém o ID da ferramenta da primeira coluna do modelo
        Object idObj = TabelaFerramentas.getModel().getValueAt(modelRow, 0);
        Long idSelecionado = ((Number) idObj).longValue();

        // Encontra o objeto Ferramentas na lista completa usando o ID
        for (Ferramentas f : listaCompletaFerramentas) {
            if (f.getId() != null && f.getId().equals(idSelecionado)) {
                return f;
            }
        }
        return null;
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        Ferramentas ferramenta = getFerramentaSelecionada();
        if (ferramenta == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma ferramenta para excluir.", 
                "Seleção Necessária", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Usa Long do modelo
        int idFerramenta = ferramenta.getId().intValue(); 
        String nomeFerramenta = ferramenta.getNome();
        String statusEstoque = ferramenta.getStatusEstoque();

        String mensagemConfirmacao = String.format(
            "<html><b>Confirma exclusão da ferramenta?</b><br><br>"
            + "ID: %d<br>"
            + "Nome: %s<br>"
            + "Status: %s</html>",
            idFerramenta, nomeFerramenta, statusEstoque
        );

        int confirm = JOptionPane.showConfirmDialog(this, 
            mensagemConfirmacao, 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean sucesso = ferramentasControle.deletarFerramenta(idFerramenta);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, 
                "Ferramenta excluída com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            atualizarBanco(); 
        } else {
            JOptionPane.showMessageDialog(this, 
                "Falha ao excluir a ferramenta. Verifique se não existem empréstimos ativos.", 
                "Erro na Exclusão", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void autualizarBDActionPerformed(java.awt.event.ActionEvent evt) {
        atualizarBanco();
        JOptionPane.showMessageDialog(this, 
            "Tabela atualizada com sucesso!", 
            "Atualização", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnEstoqueBaixoActionPerformed(java.awt.event.ActionEvent evt) {
        List<Ferramentas> estoqueBaixo = ferramentasControle.getFerramentasComEstoqueBaixo();
        if (estoqueBaixo != null && !estoqueBaixo.isEmpty()) {
            StringBuilder mensagem = new StringBuilder();
            mensagem.append("<html><b>FERRAMENTAS COM ESTOQUE BAIXO:</b><br><br>");
            mensagem.append("<table border='0' cellpadding='3'>");
            mensagem.append("<tr><th>Ferramenta</th><th>Estoque Atual</th><th>Mínimo</th><th>Status</th></tr>");
            
            for (Ferramentas f : estoqueBaixo) {
                mensagem.append(String.format(
                    "<tr><td>%s</td><td align='center'>%d</td><td align='center'>%d</td><td>%s</td></tr>",
                    f.getNome(), f.getQuantidade_estoque(), f.getQuantidade_minima(), f.getStatusEstoque()
                ));
            }
            mensagem.append("</table></html>");
            
            JOptionPane.showMessageDialog(this, 
                mensagem.toString(), 
                "⚠️ Estoque Baixo - Atenção", 
                JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "✅ Nenhuma ferramenta com estoque baixo encontrada.\nO estoque está em bom estado!", 
                "Estoque em Dia", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Ação do botão EDITAR
    private void btnEditarFerramentaActionPerformed(java.awt.event.ActionEvent evt) {
        Ferramentas ferramentaParaEditar = getFerramentaSelecionada();
        
        if (ferramentaParaEditar == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma ferramenta para editar.", 
                "Seleção Necessária", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // 1. Chama a nova tela de edição, passando o objeto e a ref da tela pai
        new editarFerramentas(ferramentaParaEditar, this).setVisible(true);
    }

    private void btnRelatorioEstoqueActionPerformed(java.awt.event.ActionEvent evt) {
        List<Ferramentas> todasFerramentas = ferramentasControle.listarFerramentas();
        if (todasFerramentas != null && !todasFerramentas.isEmpty()) {
            int total = todasFerramentas.size();
            int foraEstoque = 0;
            int estoqueBaixo = 0;
            int estoqueExcedido = 0;
            int emEstoque = 0;
            
            double valorTotalEstoque = 0;
            
            StringBuilder relatorio = new StringBuilder();
            relatorio.append("<html><b>RELATÓRIO COMPLETO DE ESTOQUE</b><br><br>");
            
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
                + "• Total de Ferramentas: %d<br>"
                + "• ✅ Em Estoque: %d<br>"
                + "• ⚠️ Estoque Baixo: %d<br>"
                + "• ❌ Fora de Estoque: %d<br>"
                + "• 📦 Estoque Excedido: %d<br>"
                + "• 💰 Valor Total em Estoque: R$ %.2f<br><br>",
                total, emEstoque, estoqueBaixo, foraEstoque, estoqueExcedido, valorTotalEstoque
            ));
            
            // Adicionar alertas
            if (estoqueBaixo > 0 || foraEstoque > 0) {
                relatorio.append("<b>🔔 ALERTAS:</b><br>");
                if (foraEstoque > 0) {
                    relatorio.append(String.format("• %d ferramenta(s) fora de estoque<br>", foraEstoque));
                }
                if (estoqueBaixo > 0) {
                    relatorio.append(String.format("• %d ferramenta(s) com estoque baixo<br>", estoqueBaixo));
                }
            } else {
                relatorio.append("✅ <b>Todos os estoques estão em condições adequadas!</b>");
            }
            
            relatorio.append("</html>");
            
            JOptionPane.showMessageDialog(this, 
                relatorio.toString(), 
                "📊 Relatório de Estoque", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Nenhuma ferramenta cadastrada no sistema.", 
                "Relatório Vazio", 
                JOptionPane.INFORMATION_MESSAGE);
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
    private javax.swing.JButton btnEditarFerramenta;
    private javax.swing.JButton btnRelatorioEstoque;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;

    // End of variables declaration                   
}