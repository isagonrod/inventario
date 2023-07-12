package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import isamix.inventario.entity.Producto;

public class DbProductos extends DbHelper {

    Context context;

    public DbProductos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarProducto(String nombre, String cantidad, String precio, String tienda) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("cantidad", cantidad);
            values.put("precio", precio);
            values.put("tienda", tienda);

            id = db.insert(TABLE_INVENTARIO, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Producto> mostrarProductos() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Producto> listaProductos = new ArrayList<>();
        Producto producto;
        Cursor cursorProductos;

        cursorProductos = db.rawQuery("SELECT * FROM " + TABLE_INVENTARIO + " ORDER BY nombre ASC", null);

        if (cursorProductos.moveToFirst()) {
            do {
                producto = new Producto();
                producto.setId(cursorProductos.getInt(0));
                producto.setNombre(cursorProductos.getString(1));
                producto.setCantidad(cursorProductos.getString(2));
                producto.setPrecio(cursorProductos.getString(3));
                producto.setTienda(cursorProductos.getString(4));
                listaProductos.add(producto);
            } while (cursorProductos.moveToNext());
        }
        cursorProductos.close();
        return listaProductos;
    }

    public Producto verProducto(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Producto producto = null;
        Cursor cursorProducto;

        cursorProducto = db.rawQuery("SELECT * FROM " + TABLE_INVENTARIO + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorProducto.moveToFirst()) {
            producto = new Producto();
            producto.setId(cursorProducto.getInt(0));
            producto.setNombre(cursorProducto.getString(1));
            producto.setCantidad(cursorProducto.getString(2));
            producto.setPrecio(cursorProducto.getString(3));
            producto.setTienda(cursorProducto.getString(4));
        }
        cursorProducto.close();
        return producto;
    }

    public boolean editarProducto(int id, String nombre, String cantidad, String precio, String tienda) {

        boolean correcto;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_INVENTARIO + " SET " + "nombre = '" + nombre + "', " +
                    "cantidad = '"+ cantidad +"', " + "precio = '" + precio + "', " +
                    "tienda = '" + tienda + "' " + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarProducto(int id) {

        boolean correcto;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_INVENTARIO + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }

        db.close();

        return correcto;
    }
}
