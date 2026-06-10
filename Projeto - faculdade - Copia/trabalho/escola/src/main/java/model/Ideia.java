package model;

import java.util.List;

public class Ideia {

    private int id;
    private String titulo;
    private String descricao;
    private int usuario_id;
    
    // --- NOVOS ATRIBUTOS PARA SUPORTAR A CAMADA DE SERVICE E DAOS ---
    private int totalVotos;
    private List<String> comentarios;

    // Construtor padrão baleio (vazio)
    public Ideia() {
    }

    // Construtor antigo
    public Ideia(int id, String titulo, String descricao, int usuario_id) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario_id = usuario_id;
    }

    // Novo construtor completo (caso vá usar em testes ou novas listagens)
    public Ideia(int id, String titulo, String descricao, int usuario_id, int totalVotos, List<String> comentarios) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario_id = usuario_id;
        this.totalVotos = totalVotos;
        this.comentarios = comentarios;
    }

    // --- GETTERS E SETTERS TRADICIONAIS ---

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

    // --- NOVOS GETTERS E SETTERS ---

    public int getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(int totalVotos) {
        this.totalVotos = totalVotos;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }
}