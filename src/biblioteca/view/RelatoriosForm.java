package biblioteca.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RelatoriosForm extends JFrame {
    private JButton btnGerarRelatorio;
    
    public RelatoriosForm() {
        initComponents();
        setupLayout();
        setupActions();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        setTitle("Relatórios");
        setSize(400, 350);
        setLocationRelativeTo(null);
        
        btnGerarRelatorio = new JButton("GERAR RELATÓRIO");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("RELATÓRIOS", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Opções de relatório
        String[] relatorios = {
            "Livros Mais Emprestados",
            "Usuários com Mais Empréstimos", 
            "Empréstimos em Atraso",
            "Acervo por Gênero"
        };
        
        ButtonGroup group = new ButtonGroup();
        for (String relatorio : relatorios) {
            JRadioButton radio = new JRadioButton(relatorio);
            group.add(radio);
            mainPanel.add(radio);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        
        // Período
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        JPanel periodoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        periodoPanel.add(new JLabel("Período: De"));
        periodoPanel.add(new JTextField(8));
        periodoPanel.add(new JLabel("Até"));
        periodoPanel.add(new JTextField(8));
        mainPanel.add(periodoPanel);
        
        // Botão gerar relatório
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        btnGerarRelatorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(btnGerarRelatorio);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void setupActions() {
        btnGerarRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(RelatoriosForm.this, 
                    "Relatório gerado com sucesso!\n(Simulação)");
            }
        });
    }
}