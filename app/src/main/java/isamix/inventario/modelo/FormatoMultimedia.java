package isamix.inventario.modelo;

public class FormatoMultimedia {

    private int id;
    private String formato;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String toString() {
        return this.formato;
    }
}
