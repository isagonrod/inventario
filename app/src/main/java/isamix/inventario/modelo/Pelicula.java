package isamix.inventario.modelo;

public class Pelicula {

    private int id;
    private String titulo;
    private String director;
    private int fechaEstreno;
    private int minDuracion;
    private String estado;

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

    public int getMinDuracion() {
        return minDuracion;
    }

    public void setMinDuracion(int minDuracion) {
        this.minDuracion = minDuracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
