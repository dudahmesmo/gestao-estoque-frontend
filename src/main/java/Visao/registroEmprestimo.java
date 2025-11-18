
package Visao;


import javax.swing.JTextField; 
import Controle.AmigosControle;
// import DAO.AmigosDAO; 
// import projetodb.projetoa3sql.Conexao; 
import javax.swing.JOptionPane;
// import java.sql.Connection; 
// import java.sql.PreparedStatement; 
// import java.sql.ResultSet;
// import java.sql.SQLException; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import Modelo.Emprestimos;
// import DAO.EmprestimosDAO; 
// import java.util.Calendar; 
import java.util.Date;
// import java.text.SimpleDateFormat; 
// import java.text.ParseException; 
import javax.swing.JComboBox;
// import DAO.FerramentasDAO; 
import Modelo.Ferramentas;
import Controle.FerramentasControle; 
import Controle.EmprestimosControle; 

/**
 * Registro de Empréstimo
 * Autor: Maria
 */

public class registroEmprestimo extends javax.swing.JFrame {
   
    // private Connection conexao; 
    private AmigosControle amigoControle;
    private FerramentasControle ferramentaControle; 
    private EmprestimosControle emprestimosControle; 
    
    private JTextField JTFAmigo; 
    private JTextField JTFFerramenta;
    
    // private AmigosDAO amigosDAO;
    // private FerramentasDAO ferramentasDAO; 
    // private EmprestimosDAO emprestimosDAO; 

    // Método construtor da classe
    public registroEmprestimo() {
        initComponents();
        JTFAmigo = new JTextField();
        JTFFerramenta = new JTextField();

        // try {
        //     // Inicialização das variáveis e conexão com o banco de dados
        //     conexao = Conexao.conectar();
        //     amigosDAO = new AmigosDAO(conexao);
        //     amigoControle = new AmigosControle(new AmigosDAO(conexao));
        //     ferramentasDAO = new FerramentasDAO(conexao);
        //     emprestimosDAO = new EmprestimosDAO();
        //     this.setLocationRelativeTo(null);
        //     updateCombo();
        //     updateComboFerramentas();
        // } catch (SQLException e) {
        //     JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage());
        // }
        
        // Inicializa os controles com os novos construtores vazios
        this.amigoControle = new AmigosControle();
        this.ferramentaControle = new FerramentasControle();
        this.emprestimosControle = new EmprestimosControle();
        
        this.setLocationRelativeTo(null);
        
        // Chama os métodos limpos
        updateCombo(); 
        updateComboFerramentas();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        buttonRegistrarEmprestimo = new javax.swing.JButton();
        itemAmigoRegistro = new javax.swing.JComboBox<>();
        itemFerramenta = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dataEmprestimo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        autualizarBanco = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        itemAmigoDevolucao = new javax.swing.JComboBox<>();
        itemFerramentaDevolucao = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        buttonRegistrarDevolucao = new javax.swing.JButton();
        dataDeDevolucao = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuCadastrar = new javax.swing.JMenu();
        menuItemCadastrarAmigo = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuRFerramenta = new javax.swing.JMenuItem();
        menuREativos = new javax.swing.JMenuItem();
        menuRHistoricos = new javax.swing.JMenuItem();
        menuRDevedores = new javax.swing.JMenuItem();

        jFormattedTextField1.setText("jFormattedTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistema de Empréstimo de Ferramentas");
        setBackground(new java.awt.Color(204, 0, 51));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registro de Empréstimo", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setText("Amigo:");

        jLabel2.setText("Ferramentas:");

        buttonRegistrarEmprestimo.setText("Registrar Empréstimo");
        buttonRegistrarEmprestimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRegistrarEmprestimoActionPerformed(evt);
            }
        });

        itemAmigoRegistro.setMaximumRowCount(50);
        itemAmigoRegistro.setToolTipText("");
        itemAmigoRegistro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                itemAmigoRegistroItemStateChanged(evt);
            }
        });
        itemAmigoRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAmigoRegistroActionPerformed(evt);
            }
        });

        itemFerramenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        itemFerramenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFerramentaActionPerformed(evt);
            }
        });

        jLabel5.setText("Data de Empréstimo:");

        jLabel3.setText("Data de Empréstimo:");

        dataEmprestimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataEmprestimoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel8.setText("Escreva da seguinte forma: YYYY - MM - DD");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel9.setText("Exemplo: 2024-06-02 (dia 2 de junho de 2024)");

        autualizarBanco.setText("Atualizar banco de dados");
        autualizarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autualizarBancoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(311, 311, 311)
                .addComponent(buttonRegistrarEmprestimo)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(itemAmigoRegistro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(itemFerramenta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dataEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addGap(0, 244, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(autualizarBanco))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(autualizarBanco)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(itemAmigoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(itemFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(dataEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(buttonRegistrarEmprestimo)
                .addGap(27, 27, 27))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registro de Devolução", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel4.setText("Amigo:");

        jLabel7.setText("Ferramenta:");

        itemAmigoDevolucao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAmigoDevolucaoActionPerformed(evt);
            }
        });

        itemFerramentaDevolucao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFerramentaDevolucaoActionPerformed(evt);
            }
        });

        jLabel6.setText("Data de Devolução:");

        buttonRegistrarDevolucao.setText("Registrar Devolução");
        buttonRegistrarDevolucao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRegistrarDevolucaoActionPerformed(evt);
            }
        });

        dataDeDevolucao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataDeDevolucaoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel10.setText("Escreva da seguinte forma: YYYY - MM - DD");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel11.setText("Exemplo: 2024-06-02 (dia 2 de junho de 2024)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(40, 40, 40)
                        .addComponent(itemAmigoDevolucao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(itemFerramentaDevolucao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(dataDeDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 236, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(319, 319, 319)
                .addComponent(buttonRegistrarDevolucao)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(itemAmigoDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(itemFerramentaDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(dataDeDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(buttonRegistrarDevolucao)
                .addGap(43, 43, 43))
        );

        menuCadastrar.setText("Cadastrar");

        menuItemCadastrarAmigo.setText("Amigos");
        menuItemCadastrarAmigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCadastrarAmigoActionPerformed(evt);
            }
        });
        menuCadastrar.add(menuItemCadastrarAmigo);

        jMenuItem2.setText("Ferramentas");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menuCadastrar.add(jMenuItem2);

        jMenuBar1.add(menuCadastrar);

        jMenu2.setText("Gerenciar");

        jMenuItem1.setText("Gerenciar Amigos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem3.setText("Gerenciar Ferramentas");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Relatórios");

        menuRFerramenta.setText("Ferramentas e Custos");
        menuRFerramenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRFerramentaActionPerformed(evt);
            }
        });
        jMenu3.add(menuRFerramenta);

        menuREativos.setText("Empréstimos Ativos");
        menuREativos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuREativosActionPerformed(evt);
            }
        });
        jMenu3.add(menuREativos);

        menuRHistoricos.setText("Histórico de Empréstimos");
        menuRHistoricos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRHistoricosActionPerformed(evt);
            }
        });
        jMenu3.add(menuRHistoricos);

        menuRDevedores.setText("Devedores");
        menuRDevedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRDevedoresActionPerformed(evt);
            }
        });
        jMenu3.add(menuRDevedores);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void buttonRegistrarEmprestimoActionPerformed(java.awt.event.ActionEvent evt) {                                                          
        
        // try {
        //     // Obtém os dados do formulário
        //     String amigoSelecionado = (String) itemAmigoRegistro.getSelectedItem();
        //     String ferramentaSelecionada = (String) itemFerramenta.getSelectedItem();
        //     String dataEmprestimoStr = (String) dataEmprestimo.getText();

        //     // Verifica se todos os campos foram preenchidos
        //     if (amigoSelecionado.isEmpty() || ferramentaSelecionada.isEmpty() || dataEmprestimoStr.isEmpty()) {
        //         JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
        //         return;
        //     }

        //     // Converte a data de empréstimo para o formato apropriado
        //     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //     java.util.Date parsedEmprestimoDate = format.parse(dataEmprestimoStr);
        //     java.sql.Date sqlDateEmprestimo = new java.sql.Date(parsedEmprestimoDate.getTime());

        //     // Calcula a data de devolução esperada
        //     Calendar calendar = Calendar.getInstance();
        //     calendar.setTime(parsedEmprestimoDate);
        //     calendar.add(Calendar.DAY_OF_YEAR, 7);
        //     java.sql.Date sqlDateDevolucao = new java.sql.Date(calendar.getTimeInMillis());

        //     // Define o status do empréstimo com base na data atual
        //     String statusEmprestimo;
        //     if (Calendar.getInstance().after(calendar)) {
        //         statusEmprestimo = "Atrasado";
        //     } else {
        //         statusEmprestimo = "Em dia";
        //     }

        //     // Obtém o ID do usuário e da ferramenta selecionados
        //     int idUsuario = amigosDAO.obterIdUsuario(amigoSelecionado);
        //     if (idUsuario == -1) {
        //         JOptionPane.showMessageDialog(this, "O amigo selecionado não possui um ID válido.");
        //         return;
        //     }

        //     String telefoneUsuario = amigosDAO.obterTelefoneUsuario(amigoSelecionado);
        //     int idFerramenta = ferramentasDAO.getFerramentaId(ferramentaSelecionada);

        //     // Cria um objeto de empréstimo com os dados do formulário
        //     Emprestimos emprestimo = new Emprestimos(0, idFerramenta, ferramentaSelecionada, sqlDateEmprestimo, sqlDateDevolucao, idUsuario, amigoSelecionado, telefoneUsuario, statusEmprestimo);
        //     emprestimosDAO.criarEmprestimo(conexao, emprestimo, idUsuario);

        //     JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso.");
        // } catch (ParseException e) {
        //     JOptionPane.showMessageDialog(this, "Erro ao converter data: " + e.getMessage());
        // } catch (SQLException e) {
        //     JOptionPane.showMessageDialog(this, "Erro ao registrar empréstimo: " + e.getMessage());
        // }
        
    
        System.out.println("Lógica de 'Registrar Empréstimo' será implementada na Tarefa 13 (ApiClient).");
        JOptionPane.showMessageDialog(this, "Lógica de 'Registrar Empréstimo' (API) ainda não implementada.");

// Método auxiliar para obter o ID da ferramenta pelo nome


// Método auxiliar para obter o ID do usuário pelo nome


    }                                                         

    private void itemAmigoRegistroItemStateChanged(java.awt.event.ItemEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  
// Método para atualizar a JComboBox com nomes de amigos
public void updateCombo() {
    System.out.println("Atualizando ComboBox de Amigos");
    
    
    // // Consulta SQL para selecionar os nomes dos amigos
    // String sql = "SELECT nome_usuario FROM amigos";
    // try (PreparedStatement pst = conexao.prepareStatement(sql);
    //      ResultSet rs = pst.executeQuery()) {
    //     // Limpar os itens existentes na JComboBox
    //     itemAmigoRegistro.removeAllItems();
    //     // Adicionar os nomes dos amigos à JComboBox
    //     while (rs.next()) {
    //         String nome = rs.getString("nome_usuario");
    //         System.out.println("Adicionando nome do amigo: " + nome);
    //         itemAmigoRegistro.addItem(nome);
    //         itemAmigoDevolucao.addItem(nome);
    //     }
    // } catch (SQLException e) {
    //     JOptionPane.showMessageDialog(this, "Erro ao carregar amigos: " + e.getMessage());
    // }
    
    
    System.out.println("Lógica de 'updateCombo' (amigos) será implementada na Tarefa 13 (ApiClient).");
    // Limpa os combos (isto podemos manter, pois não dá erro)
    itemAmigoRegistro.removeAllItems();
    itemAmigoDevolucao.removeAllItems();
}

  // Método para atualizar a JComboBox com nomes de ferramentas
public void updateComboFerramentas() {
    System.out.println("Atualizando ComboBox de Ferramentas");
    
   
    // // Consulta SQL para selecionar os nomes das ferramentas
    // String sql = "SELECT nome_ferramenta FROM ferramentas";
    // try (PreparedStatement pst = conexao.prepareStatement(sql);
    //      ResultSet rs = pst.executeQuery()) {
    //     // Limpar os itens existentes na JComboBox
    //     itemFerramenta.removeAllItems();
    //     // Adicionar os nomes das ferramentas à JComboBox
    //     while (rs.next()) {
    //         String nome = rs.getString("nome_ferramenta");
    //         System.out.println("Adicionando nome da ferramenta: " + nome);
    //         itemFerramenta.addItem(nome);
    //         itemFerramentaDevolucao.addItem(nome);
    //     }
    // } catch (SQLException e) {
    //     JOptionPane.showMessageDialog(this, "Erro ao carregar ferramentas: " + e.getMessage());
    // }
    
  
    System.out.println("Lógica de 'updateComboFerramentas' será implementada na Tarefa 13 (ApiClient).");
    // Limpa os combos (isto podemos manter, pois não dá erro)
    itemFerramenta.removeAllItems();
    itemFerramentaDevolucao.removeAllItems();
    }
 
    private void menuItemCadastrarAmigoActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        // TODO add your handling code here:
        new cadastrarAmigo().setVisible(true);
    }                                                      

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        new cadastrarFerramentas().setVisible(true);
    }                                          

// Definição do comportamento para quando um item é selecionado na JComboBox de amigos
    private void itemAmigoRegistroActionPerformed(java.awt.event.ActionEvent evt) {                                                  
       // Obter o nome do amigo selecionado na JComboBox
    String nomeSelecionado = (String) itemAmigoRegistro.getSelectedItem();
    // Definir o texto do JTextField JTFAmigo como o nome selecionado
    JTFAmigo.setText(nomeSelecionado);

    }                                                 

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        new gerenciarAmigo().setVisible(true);
    }                                          

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        new gerenciarFerramentas().setVisible(true);
    }                                          

    private void menuREativosActionPerformed(java.awt.event.ActionEvent evt) {                                             
        new relatorioEmprestimoAtivo().setVisible(true);
    }                                            

    private void menuRDevedoresActionPerformed(java.awt.event.ActionEvent evt) {                                               
        new relatorioDevedores().setVisible(true);
    }                                              

    private void menuRFerramentaActionPerformed(java.awt.event.ActionEvent evt) {                                                
        new relatorioFerramenta().setVisible(true);
    }                                               

    private void menuRHistoricosActionPerformed(java.awt.event.ActionEvent evt) {                                                
        new relatorioHistoricoEmprestimo().setVisible(true);
    }                                               

    private void buttonRegistrarDevolucaoActionPerformed(java.awt.event.ActionEvent evt) {                                                          
                                                                                    
        
        // try {
        // // Obter o nome do amigo selecionado
        // String amigoSelecionado = (String) itemAmigoDevolucao.getSelectedItem();
        // // Obter o nome da ferramenta selecionada
        // String ferramentaSelecionada = (String) itemFerramentaDevolucao.getSelectedItem();

        // // Verificar se um amigo e uma ferramenta foram selecionados
        // if (amigoSelecionado.isEmpty() || ferramentaSelecionada.isEmpty()) {
        //     JOptionPane.showMessageDialog(this, "Por favor, selecione um amigo e uma ferramenta para devolução.");
        //     return;
        // }

        // // Obter o ID da ferramenta com base no nome da ferramenta selecionada
        // int idFerramenta = ferramentasDAO.getFerramentaId(ferramentaSelecionada);

        // // Obter o ID do usuário com base no nome do amigo selecionado
        // int idUsuario = amigosDAO.obterIdUsuario(amigoSelecionado);

        // // Construir a consulta SQL para remover o empréstimo
        // String sql = "UPDATE Emprestimos SET status_emprestimo = 'Devolvido' WHERE id_amigo = ? AND id_ferramenta = ?";

        // // Preparar a declaração SQL
        // try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
        //     pstmt.setInt(1, idUsuario); // Utilizamos o ID do usuário
        //     pstmt.setInt(2, idFerramenta); // Utilizamos o ID da ferramenta

        //     // Executar a declaração SQL
        //     int rowsAffected = pstmt.executeUpdate();

        //     // Verificar se a devolução foi bem-sucedida
        //     if (rowsAffected > 0) {
        //         JOptionPane.showMessageDialog(this, "Devolução realizada com sucesso.");
        //     } else {
        //         JOptionPane.showMessageDialog(this, "Não foram encontrados empréstimos para devolução.");
        //     }
        // }
        // } catch (SQLException e) {
        //     JOptionPane.showMessageDialog(this, "Erro ao realizar a devolução: " + e.getMessage());
        // }
        
       
        System.out.println("Lógica de 'Registrar Devolução' será implementada na Tarefa 13 (ApiClient).");
        JOptionPane.showMessageDialog(this, "Lógica de 'Registrar Devolução' (API) ainda não implementada.");
    }                                                         
 
// Definição do comportamento para quando um item é selecionado na JComboBox de devolução de ferramenta
    private void itemAmigoDevolucaoActionPerformed(java.awt.event.ActionEvent evt) {                                                   
     // Obter o nome da ferramenta selecionada na JComboBox de devolução
    String nomeSelecionado = (String) itemFerramentaDevolucao.getSelectedItem();
    // Definir o texto do JTextField JTFFerramenta como o nome selecionado
    JTFFerramenta.setText(nomeSelecionado);
    }                                                  

// Definição do comportamento para quando um item é selecionado na JComboBox de ferramentas
    private void itemFerramentaActionPerformed(java.awt.event.ActionEvent evt) {                                               
      
          // Obter o nome da ferramenta selecionada na JComboBox
    String nomeSelecionado = (String) itemFerramenta.getSelectedItem();
    // Definir o texto do JTextField JTFFerramenta como o nome selecionado
    JTFFerramenta.setText(nomeSelecionado);
    }                                              

// Definição do comportamento para quando um item é selecionado na JComboBox de ferramentas
    private void itemFerramentaDevolucaoActionPerformed(java.awt.event.ActionEvent evt) {                                                        
      // Obter o nome da ferramenta selecionada na JComboBox
    String nomeSelecionado = (String) itemFerramenta.getSelectedItem();
    // Definir o texto do JTextField JTFFerramenta como o nome selecionado
    JTFFerramenta.setText(nomeSelecionado);
    }                                                       

    private void dataDeDevolucaoActionPerformed(java.awt.event.ActionEvent evt) {                                                
    }                                               

    private void dataEmprestimoActionPerformed(java.awt.event.ActionEvent evt) {                                               
    }                                              

    private void autualizarBancoActionPerformed(java.awt.event.ActionEvent evt) {                                                
        /* registroEmprestimo registroEmprestimo = new registroEmprestimo();
    registroEmprestimo.limparCombo(itemFerramenta);
    registroEmprestimo.limparCombo(itemAmigoRegistro);
    registroEmprestimo.limparCombo(itemAmigoDevolucao);
    registroEmprestimo.limparCombo(itemFerramentaDevolucao); */

        updateCombo();
        updateComboFerramentas();

    }                                               

   public void limparCombo(JComboBox combo) {
    combo.removeAllItems();
}

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(registroEmprestimo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(registroEmprestimo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(registroEmprestimo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(registroEmprestimo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registroEmprestimo().setVisible(true);
            }
            
        }
        );  
    }

    // Variables declaration - do not modify                        
    private javax.swing.JButton autualizarBanco;
    private javax.swing.JButton buttonRegistrarDevolucao;
    private javax.swing.JButton buttonRegistrarEmprestimo;
    private javax.swing.JTextField dataDeDevolucao;
    private javax.swing.JTextField dataEmprestimo;
    private javax.swing.JComboBox<String> itemAmigoDevolucao;
    private javax.swing.JComboBox<String> itemAmigoRegistro;
    private javax.swing.JComboBox<String> itemFerramenta;
    private javax.swing.JComboBox<String> itemFerramentaDevolucao;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JMenu menuCadastrar;
    private javax.swing.JMenuItem menuItemCadastrarAmigo;
    private javax.swing.JMenuItem menuRDevedores;
    private javax.swing.JMenuItem menuREativos;
    private javax.swing.JMenuItem menuRFerramenta;
    private javax.swing.JMenuItem menuRHistoricos;
    // End of variables declaration                      

   // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

