package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.Producto;

public class DbProducto extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbProducto(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public long insertarProducto(String nombre, String cantidad, String precio, String tienda, String categoria, int paraComprar) {

        long id = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("cantidad", cantidad);
            values.put("precio", precio);
            values.put("tienda", tienda);
            values.put("categoria", categoria);
            values.put("paraComprar", paraComprar);

            id = db.insert(TABLE_PRODUCTO, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Producto> mostrarProductos() {

        ArrayList<Producto> listaProductos = new ArrayList<>();
        Producto producto;
        Cursor cursorProductos;

        cursorProductos = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO + " ORDER BY cantidad, nombre ASC", null);

        if (cursorProductos.moveToFirst()) {
            do {
                producto = new Producto();
                producto.setId(cursorProductos.getInt(0));
                producto.setNombre(cursorProductos.getString(1));
                producto.setCantidad(cursorProductos.getString(2));
                producto.setPrecio(cursorProductos.getString(3));
                producto.setTienda(cursorProductos.getString(4));
                producto.setCategoria(cursorProductos.getString(5));
                producto.setParaComprar(cursorProductos.getInt(6));
                listaProductos.add(producto);
            } while (cursorProductos.moveToNext());
        }
        cursorProductos.close();
        return listaProductos;
    }

    public List<Producto> mostrarProductosPorCategoria(String categoria) {

        List<Producto> listaProductos = new ArrayList<>();
        Producto producto;
        Cursor cursorProductos;

        String select = "SELECT * FROM " + TABLE_PRODUCTO + " WHERE categoria = '" + categoria + "' ORDER BY cantidad, nombre ASC";
        cursorProductos = db.rawQuery(select, null);

        if (cursorProductos.moveToFirst()) {
            do {
                producto = new Producto();
                producto.setId(cursorProductos.getInt(0));
                producto.setNombre(cursorProductos.getString(1));
                producto.setCantidad(cursorProductos.getString(2));
                producto.setPrecio(cursorProductos.getString(3));
                producto.setTienda(cursorProductos.getString(4));
                producto.setCategoria(cursorProductos.getString(5));
                producto.setParaComprar(cursorProductos.getInt(6));
                listaProductos.add(producto);
            } while (cursorProductos.moveToNext());
        }
        cursorProductos.close();
        return listaProductos;
    }

    public ArrayList<Producto> mostrarProductosParaComprar() {

        ArrayList<Producto> listaProductos = new ArrayList<>();
        Producto producto;
        Cursor cursorProductos;

        // 0 = false | 1 = true
        cursorProductos = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO + " WHERE cantidad = '0' OR paraComprar = 1 ORDER BY nombre ASC", null);

        if (cursorProductos.moveToFirst()) {
            do {
                producto = new Producto();
                producto.setId(cursorProductos.getInt(0));
                producto.setNombre(cursorProductos.getString(1));
                producto.setCantidad(cursorProductos.getString(2));
                producto.setPrecio(cursorProductos.getString(3));
                producto.setTienda(cursorProductos.getString(4));
                producto.setCategoria(cursorProductos.getString(5));
                producto.setParaComprar(cursorProductos.getInt(6));
                listaProductos.add(producto);
            } while (cursorProductos.moveToNext());
        }
        cursorProductos.close();
        return listaProductos;
    }

    public Producto verProducto(int id) {

        Producto producto = null;
        Cursor cursorProducto;

        cursorProducto = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorProducto.moveToFirst()) {
            producto = new Producto();
            producto.setId(cursorProducto.getInt(0));
            producto.setNombre(cursorProducto.getString(1));
            producto.setCantidad(cursorProducto.getString(2));
            producto.setPrecio(cursorProducto.getString(3));
            producto.setTienda(cursorProducto.getString(4));
            producto.setCategoria(cursorProducto.getString(5));
        }
        cursorProducto.close();
        return producto;
    }

    public boolean editarProducto(int id, String nombre, String cantidad, String precio, String tienda, String categoria, int paraComprar) {

        boolean correcto;

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_PRODUCTO + " SET " +
                    "nombre = '" + nombre + "', " +
                    "cantidad = '" + cantidad + "', " +
                    "precio = '" + precio + "', " +
                    "tienda = '" + tienda + "', " +
                    "categoria = '" + categoria + "', " +
                    "paraComprar = " + paraComprar +
                    " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }

        return correcto;
    }

    public void editarCantidad(int id, String cantidad) {
        db.execSQL("UPDATE " + TABLE_PRODUCTO + " SET cantidad = '" + cantidad + "' WHERE id = '" + id + "'");
    }

    public boolean finCompra(int id, String cantidad) {
        boolean correcto;

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_PRODUCTO + " SET cantidad = '" + cantidad + "', paraComprar = 0 WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }

        return correcto;
    }

    public boolean eliminarProducto(int id) {

        boolean correcto;

        try {
            db.execSQL("DELETE FROM " + TABLE_PRODUCTO + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }

        db.close();

        return correcto;
    }
}
