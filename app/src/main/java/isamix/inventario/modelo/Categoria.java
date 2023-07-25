package isamix.inventario.modelo;

public class Categoria {

    private int id;
    private String nombre;

    // getter y setter de ID

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

    public String toString() {
        return this.nombre;
    }
}
