package Visao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JOptionPane;

import Visao.cadastrarAmigo;
import Visao.cadastrarFerramentas;
import Visao.gerenciarAmigo;
import Visao.gerenciarFerramentas;
import Visao.relatorioDevedores;
import Visao.relatorioEmprestimoAtivo;
import Visao.relatorioFerramenta;
import Visao.relatorioHistoricoEmprestimo;
import Visao.consultarEstoque; 

import Controle.AmigosControle;
import Controle.EmprestimosControle; 
import Controle.FerramentasControle;
import Modelo.Amigos;
import Modelo.Emprestimos;
import Modelo.Ferramentas;


/**
 * Registro de Empréstimo
 */
public class registroEmprestimo extends javax.swing.JFrame {
    
    private AmigosControle amigoControle;
    private FerramentasControle ferramentaControle; 
    private EmprestimosControle emprestimosControle; 
    
    
    // Listas para guardar os objetos
    private List<Ferramentas> ferramentasDisponiveis;
    private List<Emprestimos> emprestimosAtivos; 
    private List<Amigos> listaAmigos;
    

    public registroEmprestimo() {
        initComponents();

        this.amigoControle = new AmigosControle();
        this.ferramentaControle = new FerramentasControle();
        this.emprestimosControle = new EmprestimosControle();
        
        // Inicializa as listas
        this.ferramentasDisponiveis = new ArrayList<>();
        this.emprestimosAtivos = new ArrayList<>(); 
        this.listaAmigos = new ArrayList<>();
        
        this.setLocationRelativeTo(null);
        
        // Chama os métodos que carregam os combos
        updateComboAmigos(); 
        updateComboFerramentas(); 
        updateComboDevolucao(); 
    }

    
    /**
     * Atualiza a JComboBox de Amigos 
     */
    public void updateComboAmigos() {
        System.out.println("Atualizando ComboBox de Amigos");
        
        itemAmigoRegistro.removeAllItems();
        itemAmigoDevolucao.removeAllItems();
        
        this.listaAmigos = this.amigoControle.listarAmigos();

        if (this.listaAmigos != null) {
            // Ordena alfabeticamente
            Collections.sort(this.listaAmigos, Comparator.comparing(Amigos::getNome));
            
            for (Modelo.Amigos amigo : this.listaAmigos) {
                itemAmigoRegistro.addItem(amigo.getNome());
                itemAmigoDevolucao.addItem(amigo.getNome());
            }
        }
    }

    /**
     * Atualiza a JComboBox de Ferramentas (para EMPRÉSTIMO), aplicando o filtro de disponibilidade.
     */
    public void updateComboFerramentas() {
        System.out.println("Atualizando ComboBox de Ferramentas Disponíveis");
        
        itemFerramenta.removeAllItems();
        
        List<Ferramentas> listaCompleta = this.ferramentaControle.listarFerramentas(); 
        
        this.ferramentasDisponiveis.clear();
        
        if (listaCompleta != null) {
            // Ordena alfabeticamente
            Collections.sort(listaCompleta, Comparator.comparing(Ferramentas::getNome));
            
            for (Ferramentas ferramenta : listaCompleta) {
                if (ferramenta.isDisponivel()) { 
                    itemFerramenta.addItem(ferramenta.toString());
                    this.ferramentasDisponiveis.add(ferramenta);
                }
            }
        }
    }
    
    /**
     * MÉTODO: Atualiza o ComboBox para DEVOLUÇÃO (mostra ferramentas ativamente emprestadas).
     */
    public void updateComboDevolucao() {
        System.out.println("Atualizando ComboBox de Ferramentas para Devolução (Empréstimos Ativos)");
        
        itemFerramentaDevolucao.removeAllItems();
        // Busca a lista de empréstimos ativos 
        this.emprestimosAtivos = this.emprestimosControle.listarEmprestimosAtivos(); 
        
        if (this.emprestimosAtivos != null) {
            for (Emprestimos emprestimo : this.emprestimosAtivos) {
                // Formata o item do ComboBox para DEVOLUÇÃO (Amigo - Ferramenta)
                String itemCombo = emprestimo.getAmigoNome() + " - " + emprestimo.getFerramentaNome();
                itemFerramentaDevolucao.addItem(itemCombo);
            }
        }
    }

    
    /**
     * Método auxiliar para obter o ID da ferramenta pelo toString() do ComboBox
     */
    private Long getFerramentaIdByName(String nome) {
        if (nome == null) return null;
        
        // 1. Tenta achar na lista de disponíveis (para empréstimo)
        for (Ferramentas f : this.ferramentasDisponiveis) {
            if (f.toString().equals(nome)) return f.getId(); 
        }
        
        // 2. Para devolução, busca o ID da Ferramenta pelo item do Empréstimo Ativo
        if (this.emprestimosAtivos != null) {
            for (Emprestimos e : this.emprestimosAtivos) {
                String itemCombo = e.getAmigoNome() + " - " + e.getFerramentaNome();
                if (itemCombo.equals(nome)) {
                    return (long) e.getIdFerramenta(); // Retorna o ID da ferramenta
                }
            }
        }
        
        return null;
    }
    
    // Método auxiliar para obter o ID do amigo pelo nome
    private Long getAmigoIdByName(String nome) {
        if (nome == null) return null;
        for (Amigos a : this.listaAmigos) {
            if (a.getNome().equals(nome)) {
                return a.getId_amigo();
            }
        }
        return null;
    }
    
    // IMPLEMENTAÇÃO DOS BOTÕES
    
    /**
     * Ação do botão para registrar um novo empréstimo.
     */
    private void buttonRegistrarEmprestimoActionPerformed(java.awt.event.ActionEvent evt) { 
        try {
            String amigoSelecionado = (String) itemAmigoRegistro.getSelectedItem();
            String ferramentaSelecionada = (String) itemFerramenta.getSelectedItem();
            String dataEmprestimoStr = dataEmprestimo.getText();

            // 1. Validações
            if (amigoSelecionado == null || ferramentaSelecionada == null || dataEmprestimoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
                return;
            }

            // 2. Resolve IDs 
            Long idAmigo = getAmigoIdByName(amigoSelecionado);
            Long idFerramenta = getFerramentaIdByName(ferramentaSelecionada);
            
            if (idAmigo == null || idFerramenta == null) {
                JOptionPane.showMessageDialog(this, "Erro: Amigo ou Ferramenta não encontrados no sistema.");
                return;
            }
            
            // 3. Converte a data (formato dd-MM-yyyy)
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            format.setLenient(false); 
            java.util.Date parsedEmprestimoDate = format.parse(dataEmprestimoStr);
            
            // Cria o objeto empréstimo (ID=0 é placeholder, a API gera o ID)
            Emprestimos novoEmprestimo = new Emprestimos(0, idFerramenta.intValue(), ferramentaSelecionada, 
                                                         parsedEmprestimoDate, new Date(), 
                                                         idAmigo.intValue(), amigoSelecionado, "", "Em dia"); 
            
            // 4. Chama o Controle (API)
            boolean sucesso = emprestimosControle.registrarEmprestimo(novoEmprestimo);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso.");
                updateComboFerramentas(); 
                updateComboDevolucao(); 
                dataEmprestimo.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao registrar empréstimo.");
            }
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao converter data. Use o formato DD-MM-YYYY.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro inesperado ao registrar empréstimo.");
        }
    } 

    /**
     * Ação do botão para registrar uma devolução.
     */
    private void buttonRegistrarDevolucaoActionPerformed(java.awt.event.ActionEvent evt) { 
        try {
            String ferramentaSelecionada = (String) itemFerramentaDevolucao.getSelectedItem();
            
            if (ferramentaSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma ferramenta para devolução.");
                return;
            }
            
            // Resolve ID da Ferramenta pelo nome do item no combo box (corrigido para usar a lógica de empréstimos)
            Long idFerramenta = getFerramentaIdByName(ferramentaSelecionada); 
            
            if (idFerramenta == null) {
                 JOptionPane.showMessageDialog(this, "Erro: Ferramenta não encontrada no sistema.");
                 return;
            }

            // A chamada de devolução precisa do ID da Ferramenta.
            boolean sucesso = emprestimosControle.registrarDevolucao(idFerramenta.intValue()); 
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Devolução registrada com sucesso.");
                updateComboFerramentas(); 
                updateComboDevolucao(); 
                dataDeDevolucao.setText(""); 
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao registrar devolução.");
            }

        } catch (Exception e) {
             e.printStackTrace();
             JOptionPane.showMessageDialog(this, "Erro inesperado ao registrar devolução.");
        }
    } 
    
    /**
     * Ação do botão "Atualizar banco de dados"
     */
    private void autualizarBancoActionPerformed(java.awt.event.ActionEvent evt) { 
        updateComboAmigos(); 
        updateComboFerramentas();
        updateComboDevolucao(); 
    } 
    
    // GERADO PELO NETBEANS (java swing)
    
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
        jMenuItemConsultarEstoque = new javax.swing.JMenuItem(); 
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
        jLabel8.setText("Escreva da seguinte forma: DD - MM - YYYY");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel9.setText("Exemplo: 24-06-2025 (dia 24 de junho de 2025)");

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
                        .addGap(0, 244, Short.MAX_VALUE)))
                .addContainerGap())
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
        jLabel10.setText("Escreva da seguinte forma: DD - MM - YYYY");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel11.setText("Exemplo: 24-07-2025 (dia 24 de julho de 2025)");

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
        
        // Consultar Estoque
        jMenuItemConsultarEstoque.setText("Consultar Estoque");
        jMenuItemConsultarEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConsultarEstoqueActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemConsultarEstoque); // Adiciona ao menu Gerenciar

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

    
    // MÉTODOS DE MANIPULAÇÃO DE EVENTOS 
    
    private void itemAmigoRegistroItemStateChanged(java.awt.event.ItemEvent evt) {                                                  
    }                                                 

    private void itemAmigoRegistroActionPerformed(java.awt.event.ActionEvent evt) {                                                  
    }                                                 

    private void itemFerramentaActionPerformed(java.awt.event.ActionEvent evt) {                                               
    }                                              

    private void dataEmprestimoActionPerformed(java.awt.event.ActionEvent evt) {                                               
    }                                              

    private void dataDeDevolucaoActionPerformed(java.awt.event.ActionEvent evt) {                                                
    }                                               

    private void itemAmigoDevolucaoActionPerformed(java.awt.event.ActionEvent evt) {                                                   
    }                                                  

    private void itemFerramentaDevolucaoActionPerformed(java.awt.event.ActionEvent evt) {                                                        
    }                                                       

    
    private void menuItemCadastrarAmigoActionPerformed(java.awt.event.ActionEvent evt) {                                                       
         new cadastrarAmigo().setVisible(true);
    }                                                      

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
         new cadastrarFerramentas().setVisible(true);
    }                                        

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        new gerenciarAmigo().setVisible(true); 
    }                                        

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        new gerenciarFerramentas().setVisible(true);
    }                                        

    // Abre a tela de consultar estoque
    private void jMenuItemConsultarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {
        new consultarEstoque().setVisible(true);
    }

    private void menuRFerramentaActionPerformed(java.awt.event.ActionEvent evt) {                                                
        new relatorioFerramenta().setVisible(true);
    }                                               

    private void menuREativosActionPerformed(java.awt.event.ActionEvent evt) {                                             
        new relatorioEmprestimoAtivo().setVisible(true);
    }                                            

    private void menuRHistoricosActionPerformed(java.awt.event.ActionEvent evt) {                                                
        new relatorioHistoricoEmprestimo().setVisible(true);
    }                                               

    private void menuRDevedoresActionPerformed(java.awt.event.ActionEvent evt) {                                              
         new relatorioDevedores().setVisible(true);
    }                                             


    // MÉTODO main
    
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
    private javax.swing.JMenuItem jMenuItemConsultarEstoque;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JMenu menuCadastrar;
    private javax.swing.JMenuItem menuItemCadastrarAmigo;
    private javax.swing.JMenuItem menuRDevedores;
    private javax.swing.JMenuItem menuREativos;
    private javax.swing.JMenuItem menuRFerramenta;
    private javax.swing.JMenuItem menuRHistoricos;
    // End of variables declaration                   
}