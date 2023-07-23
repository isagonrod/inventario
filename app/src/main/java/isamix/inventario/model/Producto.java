package isamix.inventario.model;

public class Producto {
    private int id;
    private String nombre;
    private int cantidad;
    private double precio;
    private String tienda;
    private String categoria;
    private boolean paraComprar;
    private boolean seleccionado;

    public Producto() {
        this.paraComprar = false;
        this.seleccionado = false;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
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

    public boolean isParaComprar() {
        return paraComprar;
    }

    public void setParaComprar(boolean paraComprar) {
        this.paraComprar = paraComprar;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
