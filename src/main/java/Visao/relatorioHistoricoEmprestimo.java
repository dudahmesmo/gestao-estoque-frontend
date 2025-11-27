package Visao;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.util.List;
import Modelo.Emprestimos; 
import Controle.EmprestimosControle; 
import java.text.SimpleDateFormat;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Color; 
import java.awt.Dimension; 
import javax.swing.LayoutStyle.ComponentPlacement;

public class relatorioHistoricoEmprestimo extends javax.swing.JFrame {

    private final EmprestimosControle emprestimosControle = new EmprestimosControle();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private javax.swing.JButton autualizarBD; 
    private javax.swing.JScrollPane jScrollPanel; 
    private javax.swing.JTable tableEmprestimosAtivos; 
    private javax.swing.JButton btnEstatisticasES;


    public relatorioHistoricoEmprestimo() {
        initComponents(); 
        atualizarBanco();
    }
    
    // Exibir estatísticas de E/S
    private void mostrarEstatisticasEntradaSaida() {
        try {
            // 1. Ferramenta Mais Emprestada (Saída)
            List<Object[]> maisEmprestadas = new http.ApiClient().getFerramentasMaisEmprestadas();
            String saidaInfo = "N/A";
            if (maisEmprestadas != null && !maisEmprestadas.isEmpty()) {
                Object[] maisEmprestada = maisEmprestadas.get(0);
                String nome = (String) maisEmprestada[0];
                Number contagem = (Number) maisEmprestada[1]; 
                saidaInfo = nome + " (Total: " + contagem + "x)";
            }

            // 2. Ferramenta Mais Devolvida (Entrada)
            List<Object[]> maisDevolvidas = new http.ApiClient().getFerramentasMaisDevolvidas();
            String entradaInfo = "N/A";
            if (maisDevolvidas != null && !maisDevolvidas.isEmpty()) {
                Object[] maisDevolvida = maisDevolvidas.get(0);
                String nome = (String) maisDevolvida[0];
                Number contagem = (Number) maisDevolvida[1]; 
                entradaInfo = nome + " (Total: " + contagem + "x)";
            }

            JOptionPane.showMessageDialog(this,
                "Estatísticas de Uso (Entrada/Saída):\n\n" +
                "🛠️ Ferramenta Mais EMPRESTADA (Saída):\n" + saidaInfo + "\n\n" +
                "🔄 Ferramenta Mais DEVOLVIDA (Entrada):\n" + entradaInfo,
                "Relatório (E/S) - Ferramentas",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao obter estatísticas de E/S. Detalhe: " + e.getMessage(),
                "Erro de Integração",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // GUI (initComponents)
    @SuppressWarnings ("unchecked")
    private void initComponents() {
        
        jScrollPanel  = new javax.swing.JScrollPane();
        tableEmprestimosAtivos  = new javax.swing.JTable(); 
        autualizarBD  = new javax.swing.JButton();
        btnEstatisticasES = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Histórico de Empréstimos");
        setResizable (true);
        
        setSize(new Dimension(1000, 500)); 
        this.setLocationRelativeTo(null); // Centraliza 
        
        // Configuração da tabela
        tableEmprestimosAtivos.setModel (new javax.swing.table.DefaultTableModel (
            new Object[][] {}, 
            new String[] { 
                "ID", "Nome Amigo", "Telefone Amigo", "Ferramenta", "Marca Ferramenta", "Custo (R$)", 
                "Data Empréstimo", "Devolução Esperada", "Status"
            }
        ));
        tableEmprestimosAtivos.setShowGrid(true);
        jScrollPanel.setViewportView(tableEmprestimosAtivos);
        
        // Botões
        autualizarBD.setText("Atualizar banco de dados"); 
        btnEstatisticasES.setText("Relatorio (E/S)"); 
        
        btnEstatisticasES.setBackground(new Color(40, 167, 69)); // Verde
        btnEstatisticasES.setForeground(Color.WHITE); // Texto branco
        
        // Mapeamento de Ações
        autualizarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                autualizarBDActionPerformed (evt); 
            }
        });
        
        btnEstatisticasES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                btnEstatisticasESActionPerformed (evt); 
            }
        });


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout (getContentPane()); 
        getContentPane().setLayout (layout); 
        layout.setHorizontalGroup( 
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEstatisticasES) 
                .addPreferredGap(ComponentPlacement.RELATED) 
                .addComponent(autualizarBD) 
                .addGap(0, 0, Short.MAX_VALUE)) 
        );
        
        layout.setVerticalGroup( 
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15) 
                .addComponent(jScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18) 
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(autualizarBD)
                    .addComponent(btnEstatisticasES))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        pack(); 
    }
    
    private void btnEstatisticasESActionPerformed(ActionEvent evt) {
        mostrarEstatisticasEntradaSaida();
    }

    private void atualizarBanco () {
        DefaultTableModel model = (DefaultTableModel) tableEmprestimosAtivos.getModel();
        model.setRowCount(0); 

        try {
            List<Modelo.Emprestimos> historico = emprestimosControle.listarHistoricoEmprestimos();

            if (historico != null) {
                SimpleDateFormat localDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                for (Modelo.Emprestimos emprestimo : historico) {
                    
                    Object[] rowData = {
                        emprestimo.getIdEmprestimo(), 
                        emprestimo.getAmigoNome(),
                        emprestimo.getTelefoneUsuario(),
                        emprestimo.getFerramentaNome(),
                        emprestimo.getMarcaFerramenta(), 
                        emprestimo.getPreco(), 
                        localDateFormat.format(emprestimo.getDataEmprestimo()),
                        emprestimo.getDataDevolucaoEsperada() != null ? localDateFormat.format(emprestimo.getDataDevolucaoEsperada()) : "N/A",
                        emprestimo.getStatusEmprestimo()
                    };
                    model.addRow(rowData);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum histórico encontrado ou falha na comunicação.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar histórico de empréstimos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void autualizarBDActionPerformed (java.awt.event.ActionEvent evt) { 
        atualizarBanco(); 
    } 
}