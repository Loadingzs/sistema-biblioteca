package biblioteca.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnEntrar, btnSair;
    
    public LoginForm() {
        initComponents();
        setupLayout();
        setupActions();
    }
    
    private void initComponents() {
        setTitle("Sistema Biblioteca - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        txtUsuario = new JTextField(20);
        txtSenha = new JPasswordField(20);
        btnEntrar = new JButton("ENTRAR");
        btnSair = new JButton("SAIR");
    }
    
    private void setupLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        JLabel lblTitulo = new JLabel("SISTEMA BIBLIOTECA", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        
        JLabel lblSubtitulo = new JLabel("Biblioteca Senac", JLabel.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        panel.add(lblSubtitulo, gbc);
        
        // Campos de login
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Usuário:"), gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtUsuario, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Senha:"), gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtSenha, gbc);
        
        // Botões
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel panelBotoes = new JPanel(new FlowLayout());
        panelBotoes.add(btnEntrar);
        panelBotoes.add(btnSair);
        panel.add(panelBotoes, gbc);
        
        // Rodapé
        JLabel lblRodape = new JLabel("© 2025 Biblioteca - v1.0", JLabel.CENTER);
        gbc.gridy = 5;
        panel.add(lblRodape, gbc);
        
        add(panel);
    }
    
    private void setupActions() {
        // Ação do botão Entrar
        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });
        
        // Ação do botão Sair
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Enter no campo de senha também faz login
        txtSenha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });
    }
    
    private void fazerLogin() {
        String usuario = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();
        
        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, preencha usuário e senha!", 
                "Erro de Login", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Login bem-sucedido
        JOptionPane.showMessageDialog(this, 
            "Login realizado com sucesso!\nBem-vindo, " + usuario + "!", 
            "Sucesso", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // Abrir dashboard e fechar login
        new DashboardForm().setVisible(true);
        this.dispose();
    }
}