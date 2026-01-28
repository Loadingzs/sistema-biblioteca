package dao;

import conexao.ConnectionFactory;
import modelo.Livro; // Supondo que você tenha essa classe
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class LivrosDAO {
    
    // ==================== CREATE ====================
    public boolean cadastrarLivro(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, isbn, disponivel) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getIsbn());
            stmt.setBoolean(4, livro.isDisponivel());
            
            int rowsAffected = stmt.executeUpdate();
            boolean sucesso = rowsAffected > 0;
            
            if (sucesso) {
                System.out.println("✅ Livro cadastrado: " + livro.getTitulo());
            } else {
                System.out.println("⚠️  Nenhum livro cadastrado");
            }
            
            return sucesso;
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao cadastrar livro: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== READ (TODOS) ====================
    public List<Livro> listarLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setIsbn(rs.getString("isbn"));
                livro.setDisponivel(rs.getBoolean("disponivel"));
                
                // Se houver data_cadastro no banco
                if (rs.getDate("data_cadastro") != null) {
                    livro.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                }
                
                livros.add(livro);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar livros: " + e.getMessage());
        }
        
        return livros;
    }
    
    // ==================== READ (POR ID) ====================
    public Livro buscarLivroPorId(int id) {
        String sql = "SELECT * FROM livros WHERE id = ?";
        Livro livro = null;
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    livro = new Livro();
                    livro.setId(rs.getInt("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setIsbn(rs.getString("isbn"));
                    livro.setDisponivel(rs.getBoolean("disponivel"));
                    
                    if (rs.getDate("data_cadastro") != null) {
                        livro.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar livro por ID: " + e.getMessage());
        }
        
        return livro;
    }
    
    // ==================== UPDATE ====================
    public boolean atualizarLivro(Livro livro) {
        String sql = "UPDATE livros SET titulo = ?, autor = ?, isbn = ?, disponivel = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getIsbn());
            stmt.setBoolean(4, livro.isDisponivel());
            stmt.setInt(5, livro.getId());
            
            int rowsAffected = stmt.executeUpdate();
            boolean sucesso = rowsAffected > 0;
            
            if (sucesso) {
                System.out.println("✅ Livro atualizado: " + livro.getTitulo());
            }
            
            return sucesso;
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar livro: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== DELETE ====================
    public boolean deletarLivro(int id) {
        String sql = "DELETE FROM livros WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            boolean sucesso = rowsAffected > 0;
            
            if (sucesso) {
                System.out.println("✅ Livro deletado, ID: " + id);
            }
            
            return sucesso;
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao deletar livro: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== MÉTODOS ESPECÍFICOS ====================
    
    // Buscar livro por título
    public List<Livro> buscarLivroPorTitulo(String titulo) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE titulo LIKE ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + titulo + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new Livro();
                    livro.setId(rs.getInt("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setIsbn(rs.getString("isbn"));
                    livro.setDisponivel(rs.getBoolean("disponivel"));
                    
                    if (rs.getDate("data_cadastro") != null) {
                        livro.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                    }
                    
                    livros.add(livro);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar livro por título: " + e.getMessage());
        }
        
        return livros;
    }
    
    // Buscar livro por autor
    public List<Livro> buscarLivroPorAutor(String autor) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE autor LIKE ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + autor + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new Livro();
                    livro.setId(rs.getInt("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setIsbn(rs.getString("isbn"));
                    livro.setDisponivel(rs.getBoolean("disponivel"));
                    
                    if (rs.getDate("data_cadastro") != null) {
                        livro.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                    }
                    
                    livros.add(livro);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar livro por autor: " + e.getMessage());
        }
        
        return livros;
    }
    
    // Atualizar disponibilidade do livro
    public boolean atualizarDisponibilidade(int idLivro, boolean disponivel) {
        String sql = "UPDATE livros SET disponivel = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, idLivro);
            
            int rowsAffected = stmt.executeUpdate();
            boolean sucesso = rowsAffected > 0;
            
            String status = disponivel ? "disponível" : "indisponível";
            if (sucesso) {
                System.out.println("✅ Disponibilidade do livro ID " + idLivro + " atualizada para: " + status);
            }
            
            return sucesso;
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar disponibilidade: " + e.getMessage());
            return false;
        }
    }

    // Em LivrosDAO.java, adicione:

// Buscar livro por ISBN (exato ou parcial)
public List<Livro> buscarLivroPorISBN(String isbn) {
    List<Livro> livros = new ArrayList<>();
    String sql = "SELECT * FROM livros WHERE isbn LIKE ?";
    
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, "%" + isbn + "%");
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setIsbn(rs.getString("isbn"));
                livro.setDisponivel(rs.getBoolean("disponivel"));
                
                if (rs.getDate("data_cadastro") != null) {
                    livro.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                }
                
                livros.add(livro);
            }
        }
        
    } catch (SQLException e) {
        System.err.println("❌ Erro ao buscar livro por ISBN: " + e.getMessage());
    }
    
    return livros;
}

}