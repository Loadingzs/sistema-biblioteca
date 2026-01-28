package biblioteca.view;

import javax.swing.*;
import java.awt.*;

public class DashboardForm extends JFrame {
    
    public DashboardForm() {
        super("📚 Sistema Biblioteca - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Layout básico
        getContentPane().setBackground(Color.WHITE);
        
        JLabel lblBemVindo = new JLabel("Bem-vindo ao Sistema Biblioteca SENAC", JLabel.CENTER);
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 24));
        lblBemVindo.setForeground(new Color(0, 102, 204));
        
        setLayout(new BorderLayout());
        add(lblBemVindo, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnLivros = new JButton("📖 Livros");
        JButton btnUsuarios = new JButton("👥 Usuários");
        JButton btnSair = new JButton("🚪 Sair");
        
        btnLivros.addActionListener(e -> new LivrosForm().setVisible(true));
        btnUsuarios.addActionListener(e -> new UsuariosForm().setVisible(true));
        btnSair.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(btnLivros);
        buttonPanel.add(btnUsuarios);
        buttonPanel.add(btnSair);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardForm().setVisible(true);
        });
    }
}