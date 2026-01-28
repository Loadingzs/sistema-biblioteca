package biblioteca.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import dao.LivrosDAO;
import modelo.Livro;

public class LivrosForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnNovo, btnEditar, btnExcluir, btnAtualizar;
    private JTextField txtPesquisa;
    private JComboBox<String> cbxFiltro;
    private LivrosDAO livrosDAO;
    
    public LivrosForm() {
        livrosDAO = new LivrosDAO();
        initComponents();
        setupLayout();
        setupActions();
        carregarDados();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        setTitle("📚 Gerenciamento de Livros");
        setSize(900, 500);
        setLocationRelativeTo(null);
        
        // Botões
        btnNovo = new JButton("➕ Novo Livro");
        btnEditar = new JButton("✏️ Editar");
        btnExcluir = new JButton("🗑️ Excluir");
        btnAtualizar = new JButton("🔄 Atualizar");
        
        // Campo de pesquisa
        txtPesquisa = new JTextField(20);
        
        // Filtros
        String[] filtros = {"Todos", "Título", "Autor", "ISBN", "Disponíveis", "Indisponíveis"};
        cbxFiltro = new JComboBox<>(filtros);
        
        // Modelo da tabela
        String[] colunas = {"ID", "TÍTULO", "AUTOR", "ISBN", "DISPONÍVEL", "DATA CADASTRO"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) { // Coluna DISPONÍVEL
                    return Boolean.class;
                }
                return String.class;
            }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(250); // TÍTULO
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // AUTOR
        table.getColumnModel().getColumn(3).setPreferredWidth(120); // ISBN
        table.getColumnModel().getColumn(4).setPreferredWidth(80);  // DISPONÍVEL
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // DATA CADASTRO
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Painel superior com ferramentas
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel de pesquisa
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.add(new JLabel("🔍 Pesquisar:"));
        searchPanel.add(txtPesquisa);
        searchPanel.add(new JLabel("Filtrar por:"));
        searchPanel.add(cbxFiltro);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(btnNovo);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnAtualizar);
        
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Tabela com scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Livros"));
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel de status
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusPanel.add(new JLabel("Total de livros: 0"));
        
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupActions() {
        // Botão Novo
        btnNovo.addActionListener(e -> abrirFormularioLivro(null));
        
        // Botão Editar
        btnEditar.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                Livro livro = livrosDAO.buscarLivroPorId(id);
                if (livro != null) {
                    abrirFormularioLivro(livro);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Selecione um livro para editar!", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Botão Excluir
        btnExcluir.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String titulo = tableModel.getValueAt(selectedRow, 1).toString();
                
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o livro:\n" + 
                    titulo + "?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean sucesso = livrosDAO.deletarLivro(id);
                    if (sucesso) {
                        carregarDados();
                        JOptionPane.showMessageDialog(this, 
                            "Livro excluído com sucesso!", 
                            "Sucesso", 
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Selecione um livro para excluir!", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Botão Atualizar
        btnAtualizar.addActionListener(e -> carregarDados());
        
        // Campo de pesquisa
        txtPesquisa.addActionListener(e -> pesquisarLivros());
        
        // Filtro
        cbxFiltro.addActionListener(e -> pesquisarLivros());
        
        // Duplo clique na tabela
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = table.rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                        Livro livro = livrosDAO.buscarLivroPorId(id);
                        mostrarDetalhesLivro(livro);
                    }
                }
            }
        });
    }
    
    private void abrirFormularioLivro(Livro livro) {
        JDialog dialog = new JDialog(this, 
            livro == null ? "📖 Novo Livro" : "✏️ Editar Livro", 
            true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Campo Título
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Título:*"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField txtTitulo = new JTextField(20);
        if (livro != null) txtTitulo.setText(livro.getTitulo());
        panel.add(txtTitulo, gbc);
        
        // Campo Autor
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Autor:*"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField txtAutor = new JTextField(20);
        if (livro != null) txtAutor.setText(livro.getAutor());
        panel.add(txtAutor, gbc);
        
        // Campo ISBN
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("ISBN:*"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField txtIsbn = new JTextField(20);
        if (livro != null) txtIsbn.setText(livro.getIsbn());
        panel.add(txtIsbn, gbc);
        
        // Campo Disponibilidade
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Disponível:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        JCheckBox chkDisponivel = new JCheckBox("Sim");
        if (livro != null) chkDisponivel.setSelected(livro.isDisponivel());
        else chkDisponivel.setSelected(true);
        panel.add(chkDisponivel, gbc);
        
        // Botões
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 1;
        JButton btnSalvar = new JButton("💾 Salvar");
        panel.add(btnSalvar, gbc);
        
        gbc.gridx = 2;
        JButton btnCancelar = new JButton("❌ Cancelar");
        panel.add(btnCancelar, gbc);
        
        // Ação Salvar
        btnSalvar.addActionListener(e -> {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String isbn = txtIsbn.getText().trim();
            
            if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                    "Preencha todos os campos obrigatórios (*)!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Livro livroAtualizado = livro == null ? new Livro() : livro;
            livroAtualizado.setTitulo(titulo);
            livroAtualizado.setAutor(autor);
            livroAtualizado.setIsbn(isbn);
            livroAtualizado.setDisponivel(chkDisponivel.isSelected());
            
            boolean sucesso;
            if (livro == null) {
                sucesso = livrosDAO.cadastrarLivro(livroAtualizado);
            } else {
                sucesso = livrosDAO.atualizarLivro(livroAtualizado);
            }
            
            if (sucesso) {
                carregarDados();
                dialog.dispose();
            }
        });
        
        // Ação Cancelar
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void mostrarDetalhesLivro(Livro livro) {
        if (livro == null) return;
        
        String detalhes = String.format("""
            📖 **Detalhes do Livro**
            
            **ID:** %d
            **Título:** %s
            **Autor:** %s
            **ISBN:** %s
            **Disponível:** %s
            **Data de Cadastro:** %s
            """,
            livro.getId(),
            livro.getTitulo(),
            livro.getAutor(),
            livro.getIsbn(),
            livro.isDisponivel() ? "✅ Sim" : "❌ Não",
            livro.getDataCadastro()
        );
        
        JOptionPane.showMessageDialog(this, 
            detalhes, 
            "📚 Detalhes do Livro", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void carregarDados() {
        tableModel.setRowCount(0);
        
        try {
            List<Livro> livros = livrosDAO.listarLivros();
            
            for (Livro livro : livros) {
                Object[] row = {
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getIsbn(),
                    livro.isDisponivel(),
                    livro.getDataCadastro()
                };
                tableModel.addRow(row);
            }
            
            atualizarStatus(livros.size());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar dados: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void pesquisarLivros() {
        String pesquisa = txtPesquisa.getText().trim();
        String filtro = (String) cbxFiltro.getSelectedItem();
        
        tableModel.setRowCount(0);
        
        try {
            List<Livro> livros;
            
            if (pesquisa.isEmpty() || "Todos".equals(filtro)) {
                livros = livrosDAO.listarLivros();
            } else {
                switch (filtro) {
                    case "Título":
                        livros = livrosDAO.buscarLivroPorTitulo(pesquisa);
                        break;
                    case "Autor":
                        livros = livrosDAO.buscarLivroPorAutor(pesquisa);
                        break;
                    case "ISBN":
                        // Método adicional que você pode implementar no DAO
                        livros = livrosDAO.listarLivros(); // Placeholder
                        break;
                    case "Disponíveis":
                        livros = livrosDAO.listarLivros();
                        livros.removeIf(l -> !l.isDisponivel());
                        break;
                    case "Indisponíveis":
                        livros = livrosDAO.listarLivros();
                        livros.removeIf(Livro::isDisponivel);
                        break;
                    default:
                        livros = livrosDAO.listarLivros();
                }
            }
            
            for (Livro livro : livros) {
                Object[] row = {
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getIsbn(),
                    livro.isDisponivel(),
                    livro.getDataCadastro()
                };
                tableModel.addRow(row);
            }
            
            atualizarStatus(livros.size());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro na pesquisa: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarStatus(int total) {
        // Atualizar contador na status bar
        Container parent = getContentPane();
        Component[] components = parent.getComponents();
        
        for (Component comp : components) {
            if (comp instanceof JPanel && ((JPanel) comp).getLayout() instanceof FlowLayout) {
                JPanel statusPanel = (JPanel) comp;
                statusPanel.removeAll();
                statusPanel.add(new JLabel("📊 Total de livros: " + total));
                statusPanel.revalidate();
                statusPanel.repaint();
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            LivrosForm form = new LivrosForm();
            form.setVisible(true);
        });
    }
}