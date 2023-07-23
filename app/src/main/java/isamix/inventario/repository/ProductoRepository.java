package isamix.inventario.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.model.Producto;

public class ProductoRepository extends InventarioRepositoryHelper {
    Context context;
    InventarioRepositoryHelper helper;
    SQLiteDatabase database;

    public ProductoRepository(@Nullable Context context) {
        super(context);
        this.context = context;
        this.helper = new InventarioRepositoryHelper(this.context);
        this.database = helper.getWritableDatabase();
    }

    public long addProducto(String nombre, int cantidad, double precio, String tienda, String categoria) {
        long id;
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("cantidad", cantidad);
        values.put("precio", precio);
        values.put("tienda", tienda);
        values.put("categoria", categoria);
        id = database.insert(TABLA_PRODUCTO, null, values);
        return id;
    }

    public List<Producto> getProductos() {
        List<Producto> productos = new ArrayList<>();
        Producto producto;
        Cursor cursor;

        cursor = database.rawQuery("SELECT * FROM " + TABLA_PRODUCTO + " ORDER BY nombre ASC", null);
        if (cursor.moveToFirst()) {
            do {
                producto = new Producto();
                producto.setId(cursor.getInt(0));
                producto.setNombre(cursor.getString(1));
                producto.setCantidad(cursor.getInt(2));
                producto.setPrecio(cursor.getDouble(3));
                producto.setTienda(cursor.getString(4));
                producto.setCategoria(cursor.getString(5));
                producto.setParaComprar(false);
                producto.setSeleccionado(false);
                productos.add(producto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productos;
    }

    public List<Producto> getProductosByCategoria(String categoria) {
        List<Producto> productos = new ArrayList<>();
        Producto producto;
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + TABLA_PRODUCTO + " WHERE categoria = '" + categoria + "' ORDER BY nombre ASC", null);
        if (cursor.moveToFirst()) {
            do {
                producto = new Producto();
                producto.setId(cursor.getInt(0));
                producto.setNombre(cursor.getString(1));
                producto.setCantidad(cursor.getInt(2));
                producto.setPrecio(cursor.getDouble(3));
                producto.setTienda(cursor.getString(4));
                producto.setCategoria(cursor.getString(5));
                producto.setParaComprar(false);
                producto.setSeleccionado(false);
                productos.add(producto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productos;
    }

    public List<Producto> getProductosByComprar() {
        List<Producto> listaCompra = new ArrayList<>();
        Producto producto;
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + TABLA_PRODUCTO + " WHERE cantidad = 0 ORDER BY nombre ASC", null);
        if (cursor.moveToFirst() || listaCompra.get(cursor.getInt(0)).isParaComprar()) {
            do {
                producto = new Producto();
                producto.setId(cursor.getInt(0));
                producto.setNombre(cursor.getString(1));
                producto.setCantidad(cursor.getInt(2));
                producto.setPrecio(cursor.getDouble(3));
                producto.setTienda(cursor.getString(4));
                producto.setCategoria(cursor.getString(5));
                producto.setParaComprar(true);
                producto.setSeleccionado(false);
                listaCompra.add(producto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaCompra;
    }

    public Producto getProduct(int id) {
        // TODO: Incorporar los boolean paraComprar y seleccionado
        Producto producto = null;
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + TABLA_PRODUCTO + " WHERE id = '" + id + "' LIMIT 1", null);
        if (cursor.moveToFirst()) {
            producto = new Producto();
            producto.setId(cursor.getInt(0));
            producto.setNombre(cursor.getString(1));
            producto.setCantidad(cursor.getInt(2));
            producto.setPrecio(cursor.getDouble(3));
            producto.setTienda(cursor.getString(4));
            producto.setCategoria(cursor.getString(5));
            //producto.setParaComprar(false);
            //producto.setSeleccionado(false);
        }
        cursor.close();
        return producto;
    }

    public void updateProducto(int id, String nombre, int cantidad, double precio, String tienda, String categoria) {
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("cantidad", cantidad);
        values.put("precio", precio);
        values.put("tienda", tienda);
        values.put("categoria", categoria);
        database.update(TABLA_PRODUCTO, values, "id = " + id, null);
    }

    public void finCompra(int id, int cantidad) {
        // TODO: Incorporar el cambio del boolean paraComprar = false
        database.execSQL("UPDATE " + TABLA_PRODUCTO + " SET cantidad = '" + cantidad + "' WHERE id = '" + id + "'");
    }

    public void deleteProducto(int id) {
        database.execSQL("DELETE FROM " + TABLA_PRODUCTO + " WHERE id = '" + id + "'");
    }
}
