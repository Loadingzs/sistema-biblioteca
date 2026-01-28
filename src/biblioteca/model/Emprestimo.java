package biblioteca.model;

import java.util.Date;

public class Emprestimo {
    private String id;
    private String livroId;
    private String usuarioId;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private String status;
    
    public Emprestimo(String id, String livroId, String usuarioId, Date dataEmprestimo, Date dataDevolucao, String status) {
        this.id = id;
        this.livroId = livroId;
        this.usuarioId = usuarioId;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
    }
    
    // Getters e Setters
    public String getId() { return id; }
    public String getLivroId() { return livroId; }
    public String getUsuarioId() { return usuarioId; }
    public Date getDataEmprestimo() { return dataEmprestimo; }
    public Date getDataDevolucao() { return dataDevolucao; }
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
    public void setDataDevolucao(Date dataDevolucao) { this.dataDevolucao = dataDevolucao; }
}