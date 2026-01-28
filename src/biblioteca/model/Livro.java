package biblioteca.model;

public class Livro {
    private String id;
    private String titulo;
    private String autor;
    private String isbn;
    private String genero;
    private boolean disponivel;
    
    public Livro(String id, String titulo, String autor, String isbn, String genero, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.genero = genero;
        this.disponivel = disponivel;
    }
    
    // Getters e Setters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getIsbn() { return isbn; }
    public String getGenero() { return genero; }
    public boolean isDisponivel() { return disponivel; }
    
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}