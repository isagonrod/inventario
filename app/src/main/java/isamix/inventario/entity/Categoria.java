package isamix.inventario.entity;

public class Categoria {

    private int id;
    private String nombre;
    private int icono;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String toString() {
        return this.icono + " " + this.nombre;
    }
}
