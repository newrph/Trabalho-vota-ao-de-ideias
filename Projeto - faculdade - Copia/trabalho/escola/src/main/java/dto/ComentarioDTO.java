package dto;

public class ComentarioDTO {
    private int ideiaId;
    private String texto;
    private int usuarioId;

    public int getIdeiaId() { return ideiaId; }
    public void setIdeiaId(int ideiaId) { this.ideiaId = ideiaId; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
}