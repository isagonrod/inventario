package isamix.inventario.modelo;

public class Profesion {

    private int id;
    private String prof;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String toString() {
        return this.prof;
    }
}
