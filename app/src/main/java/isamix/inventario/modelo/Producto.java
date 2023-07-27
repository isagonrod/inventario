package isamix.inventario.modelo;

public class Producto {

    private int id;
    private String nombre;
    private int cantidad;
    private String precio;
    private String tienda;
    private String categoria;
    private int paraComprar;

    public Producto() {
        this.paraComprar = 0;
    }

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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getParaComprar() {
        return paraComprar;
    }

    public void setParaComprar(int paraComprar) {
        this.paraComprar = paraComprar;
    }
}
