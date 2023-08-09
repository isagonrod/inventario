package isamix.inventario.modelo;

public class TipoRopa {

    private int id;
    private String tipoRopa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoRopa() {
        return tipoRopa;
    }

    public void setTipoRopa(String tipoRopa) {
        this.tipoRopa = tipoRopa;
    }

    public String toString() {
        return this.tipoRopa;
    }
}
