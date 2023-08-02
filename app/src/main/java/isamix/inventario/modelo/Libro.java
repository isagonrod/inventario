package isamix.inventario.modelo;

public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String editorial;
    private String genero;
    private int isbn;
    private String lugarImpresion;
    private int fechaImpresion;

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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getLugarImpresion() {
        return lugarImpresion;
    }

    public void setLugarImpresion(String lugarImpresion) {
        this.lugarImpresion = lugarImpresion;
    }

    public int getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(int fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
    }
}
