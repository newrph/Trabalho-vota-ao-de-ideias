package model;

public class Ideia {

    private int id;
    private String titulo;
    private String descricao;
    private int usuario_id;

    public Ideia() {
    }

    public Ideia(int id, String titulo, String descricao, int usuario_id) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario_id = usuario_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}