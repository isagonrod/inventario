package isamix.inventario.entity;

public class Producto {

    private int id;
    private String nombre;
    private String cantidad;
    private String precio;
    private String tienda;
    private String categoria;
    private int paraComprar;

    public Producto() {
        this.paraComprar = 0;
    }

    // getter/setter de ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // getter/setter de NOMBRE

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // getter/setter de CANTIDAD

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    // getter/setter de PRECIO

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    // getter/setter de TIENDA

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    // getter / setter de CATEGORIA

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // getter/setter de COMPRAR

    public int getParaComprar() {
        return paraComprar;
    }

    public void setParaComprar(int paraComprar) {
        this.paraComprar = paraComprar;
    }
}
