package biblioteca.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmprestimosForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnNovoEmprestimo;
    
    public EmprestimosForm() {
        initComponents();
        setupLayout();
        setupActions();
        carregarDados();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        setTitle("Gerenciamento de Empréstimos");
        setSize(900, 400);
        setLocationRelativeTo(null);
        
        btnNovoEmprestimo = new JButton("Novo Empréstimo");
        
        String[] colunas = {"ID", "LIVRO", "USUÁRIO", "DATA EMPRÉSTIMO", "DATA DEVOLUÇÃO", "STATUS", "AÇÕES"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolPanel.add(btnNovoEmprestimo);
        
        add(toolPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.add(new JLabel("Página 1 de 1"));
        
        add(paginationPanel, BorderLayout.SOUTH);
    }
    
    private void setupActions() {
        btnNovoEmprestimo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(EmprestimosForm.this, 
                    "Funcionalidade 'Novo Empréstimo' em desenvolvimento!");
            }
        });
    }
    
    private void carregarDados() {
        // Limpar dados existentes
        tableModel.setRowCount(0);
        
        Object[][] dados = {
            {"001", "Dom Casmurro", "João Silva", "10/10/2024", "25/10/2024", "EMPRESTADO", "Devolver"},
            {"002", "O Cortiço", "Maria Santos", "09/10/2024", "24/10/2024", "EMPRESTADO", "Devolver"},
            {"003", "Iracema", "Pedro Oliveira", "08/10/2024", "23/10/2024", "DEVOLVIDO", ""}
        };
        
        for (Object[] linha : dados) {
            tableModel.addRow(linha);
        }
    }
}