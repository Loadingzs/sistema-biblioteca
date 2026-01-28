package biblioteca.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import dao.UsuariosDAO;
import modelo.Usuario;

public class UsuariosForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnNovo, btnEditar, btnExcluir, btnAtualizar;
    private JTextField txtPesquisa;
    private JComboBox<String> cbxFiltro;
    private UsuariosDAO usuariosDAO;
    
    public UsuariosForm() {
        usuariosDAO = new UsuariosDAO();
        initComponents();
        setupLayout();
        setupActions();
        carregarDados();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        setTitle("👥 Gerenciamento de Usuários");
        setSize(900, 500);
        setLocationRelativeTo(null);
        
        // Botões
        btnNovo = new JButton("➕ Novo Usuário");
        btnEditar = new JButton("✏️ Editar");
        btnExcluir = new JButton("🗑️ Excluir");
        btnAtualizar = new JButton("🔄 Atualizar");
        
        // Campo de pesquisa
        txtPesquisa = new JTextField(20);
        txtPesquisa.setToolTipText("Digite para pesquisar...");
        
        // Filtros
        String[] filtros = {"Todos", "Nome", "Email", "Telefone"};
        cbxFiltro = new JComboBox<>(filtros);
        
        // Modelo da tabela
        String[] colunas = {"ID", "NOME", "EMAIL", "TELEFONE", "DATA CADASTRO"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(200);  // NOME
        table.getColumnModel().getColumn(2).setPreferredWidth(200);  // EMAIL
        table.getColumnModel().getColumn(3).setPreferredWidth(120);  // TELEFONE
        table.getColumnModel().getColumn(4).setPreferredWidth(100);  // DATA CADASTRO
        
        // Ordenação da tabela
        table.setAutoCreateRowSorter(true);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Painel superior
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
        
        // Tabela
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Usuários"));
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel de status
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusPanel.add(new JLabel("Total de usuários: 0"));
        
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupActions() {
        // Botão Novo
        btnNovo.addActionListener(e -> abrirFormularioUsuario(null));
        
        // Botão Editar
        btnEditar.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = table.convertRowIndexToModel(selectedRow);
                int id = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());
                Usuario usuario = usuariosDAO.buscarUsuarioPorId(id);
                if (usuario != null) {
                    abrirFormularioUsuario(usuario);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Selecione um usuário para editar!", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Botão Excluir
        btnExcluir.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = table.convertRowIndexToModel(selectedRow);
                int id = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());
                String nome = tableModel.getValueAt(modelRow, 1).toString();
                
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o usuário:\n" + 
                    nome + "?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean sucesso = usuariosDAO.deletarUsuario(id);
                    if (sucesso) {
                        carregarDados();
                        JOptionPane.showMessageDialog(this, 
                            "Usuário excluído com sucesso!", 
                            "Sucesso", 
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Selecione um usuário para excluir!", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Botão Atualizar
        btnAtualizar.addActionListener(e -> carregarDados());
        
        // Pesquisa em tempo real
        txtPesquisa.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { pesquisar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { pesquisar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { pesquisar(); }
        });
        
        // Filtro
        cbxFiltro.addActionListener(e -> pesquisar());
        
        // Duplo clique na tabela
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = table.rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        int modelRow = table.convertRowIndexToModel(row);
                        int id = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());
                        Usuario usuario = usuariosDAO.buscarUsuarioPorId(id);
                        mostrarDetalhesUsuario(usuario);
                    }
                }
            }
        });
    }
    
    private void abrirFormularioUsuario(Usuario usuario) {
        JDialog dialog = new JDialog(this, 
            usuario == null ? "👤 Novo Usuário" : "✏️ Editar Usuário", 
            true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Campo Nome
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:*"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField txtNome = new JTextField(20);
        if (usuario != null) txtNome.setText(usuario.getNome());
        panel.add(txtNome, gbc);
        
        // Campo Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Email:*"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField txtEmail = new JTextField(20);
        if (usuario != null) txtEmail.setText(usuario.getEmail());
        panel.add(txtEmail, gbc);
        
        // Campo Telefone
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField txtTelefone = new JTextField(20);
        if (usuario != null) txtTelefone.setText(usuario.getTelefone());
        panel.add(txtTelefone, gbc);
        
        // Botões
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
        JButton btnSalvar = new JButton("💾 Salvar");
        panel.add(btnSalvar, gbc);
        
        gbc.gridx = 2;
        JButton btnCancelar = new JButton("❌ Cancelar");
        panel.add(btnCancelar, gbc);
        
        // Ação Salvar
        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            
            // Validações
            if (nome.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                    "Preencha todos os campos obrigatórios (*)!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validação de email
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(dialog,
                    "Email inválido!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Verificar se email já existe (apenas para novo usuário)
            if (usuario == null && usuariosDAO.emailExiste(email)) {
                JOptionPane.showMessageDialog(dialog,
                    "Este email já está cadastrado!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Usuario usuarioAtualizado = usuario == null ? new Usuario() : usuario;
            usuarioAtualizado.setNome(nome);
            usuarioAtualizado.setEmail(email);
            usuarioAtualizado.setTelefone(telefone);
            
            boolean sucesso;
            if (usuario == null) {
                sucesso = usuariosDAO.cadastrarUsuario(usuarioAtualizado);
            } else {
                sucesso = usuariosDAO.atualizarUsuario(usuarioAtualizado);
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
    
    private void mostrarDetalhesUsuario(Usuario usuario) {
        if (usuario == null) return;
        
        String detalhes = String.format("""
            👤 **Detalhes do Usuário**
            
            **ID:** %d
            **Nome:** %s
            **Email:** %s
            **Telefone:** %s
            **Data de Cadastro:** %s
            """,
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTelefone() != null ? usuario.getTelefone() : "Não informado",
            usuario.getDataCadastro()
        );
        
        JOptionPane.showMessageDialog(this, 
            detalhes, 
            "👤 Detalhes do Usuário", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void carregarDados() {
        tableModel.setRowCount(0);
        
        try {
            List<Usuario> usuarios = usuariosDAO.listarUsuarios();
            
            for (Usuario usuario : usuarios) {
                Object[] row = {
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getTelefone() != null ? usuario.getTelefone() : "",
                    usuario.getDataCadastro()
                };
                tableModel.addRow(row);
            }
            
            atualizarStatus(usuarios.size());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar dados: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void pesquisar() {
        String pesquisa = txtPesquisa.getText().trim();
        String filtro = (String) cbxFiltro.getSelectedItem();
        
        tableModel.setRowCount(0);
        
        try {
            List<Usuario> usuarios;
            
            if (pesquisa.isEmpty()) {
                usuarios = usuariosDAO.listarUsuarios();
            } else {
                switch (filtro) {
                    case "Nome":
                        usuarios = usuariosDAO.buscarUsuarioPorNome(pesquisa);
                        break;
                    case "Email":
                        Usuario usuario = usuariosDAO.buscarUsuarioPorEmail(pesquisa);
                        usuarios = new ArrayList<>();
                        if (usuario != null) usuarios.add(usuario);
                        break;
                    case "Telefone":
                        usuarios = usuariosDAO.listarUsuarios();
                        usuarios.removeIf(u -> 
                            u.getTelefone() == null || 
                            !u.getTelefone().contains(pesquisa));
                        break;
                    default:
                        usuarios = usuariosDAO.listarUsuarios();
                        usuarios.removeIf(u -> 
                            !u.getNome().toLowerCase().contains(pesquisa.toLowerCase()) &&
                            !u.getEmail().toLowerCase().contains(pesquisa.toLowerCase()) &&
                            (u.getTelefone() == null || 
                             !u.getTelefone().contains(pesquisa)));
                }
            }
            
            for (Usuario usuario : usuarios) {
                Object[] row = {
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getTelefone() != null ? usuario.getTelefone() : "",
                    usuario.getDataCadastro()
                };
                tableModel.addRow(row);
            }
            
            atualizarStatus(usuarios.size());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro na pesquisa: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarStatus(int total) {
        Container parent = getContentPane();
        Component[] components = parent.getComponents();
        
        for (Component comp : components) {
            if (comp instanceof JPanel && ((JPanel) comp).getLayout() instanceof FlowLayout) {
                JPanel statusPanel = (JPanel) comp;
                statusPanel.removeAll();
                statusPanel.add(new JLabel("👥 Total de usuários: " + total));
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
            
            UsuariosForm form = new UsuariosForm();
            form.setVisible(true);
        });
    }
}