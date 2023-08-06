package isamix.inventario.modelo;

public class Pelicula {

    private int id;
    private String titulo;
    private String director;
    private int fechaEstreno;
    private String formato;

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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(int fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}
