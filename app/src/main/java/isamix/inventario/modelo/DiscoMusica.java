package isamix.inventario.modelo;

public class DiscoMusica {

    private int id;
    private String titulo;
    private String artista_grupo;
    private int fechaLanzamiento;

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

    public String getArtista_grupo() {
        return artista_grupo;
    }

    public void setArtista_grupo(String artista_grupo) {
        this.artista_grupo = artista_grupo;
    }

    public int getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(int fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }
}
