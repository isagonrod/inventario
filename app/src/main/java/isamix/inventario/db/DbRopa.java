package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.Ropa;

public class DbRopa extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbRopa(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public long insertarRopa(String nombre, String marca, String tienda, String color, String talla,
                             String tipo, int cantidad, String estado) {
        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("marca", marca);
            values.put("tienda", tienda);
            values.put("color", color);
            values.put("talla", talla);
            values.put("tipo", tipo);
            values.put("cantidad", cantidad);
            values.put("estado", estado);
            id = db.insert(TABLE_ROPA, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public List<Ropa> mostrarRopas() {
        List<Ropa> listaRopa = new ArrayList<>();
        Ropa ropa;
        Cursor cursorRopa;
        String select = "SELECT * FROM " + TABLE_ROPA + " ORDER BY nombre ASC";
        cursorRopa = db.rawQuery(select, null);
        if (cursorRopa.moveToFirst()) {
            do {
                ropa = new Ropa();
                ropa.setId(cursorRopa.getInt(0));
                ropa.setNombre(cursorRopa.getString(1));
                ropa.setMarca(cursorRopa.getString(2));
                ropa.setTienda(cursorRopa.getString(3));
                ropa.setColor(cursorRopa.getString(4));
                ropa.setTalla(cursorRopa.getString(5));
                ropa.setTipo(cursorRopa.getString(6));
                ropa.setCantidad(cursorRopa.getInt(7));
                ropa.setEstado(cursorRopa.getString(8));
                listaRopa.add(ropa);
            } while (cursorRopa.moveToNext());
        }
        cursorRopa.close();
        return listaRopa;
    }

    public List<Ropa> mostrarRopasPorTipo(String tipo) {
        List<Ropa> listaRopa = new ArrayList<>();
        Ropa ropa;
        Cursor cursorRopa;
        String select = "SELECT * FROM " + TABLE_ROPA + " WHERE tipo = '" + tipo + "' ORDER BY nombre ASC";
        cursorRopa = db.rawQuery(select, null);
        if (cursorRopa.moveToFirst()) {
            do {
                ropa = new Ropa();
                ropa.setId(cursorRopa.getInt(0));
                ropa.setNombre(cursorRopa.getString(1));
                ropa.setMarca(cursorRopa.getString(2));
                ropa.setTienda(cursorRopa.getString(3));
                ropa.setColor(cursorRopa.getString(4));
                ropa.setTalla(cursorRopa.getString(5));
                ropa.setTipo(cursorRopa.getString(6));
                ropa.setCantidad(cursorRopa.getInt(7));
                ropa.setEstado(cursorRopa.getString(8));
                listaRopa.add(ropa);
            } while (cursorRopa.moveToNext());
        }
        cursorRopa.close();
        return listaRopa;
    }

    public List<Ropa> mostrarRopasTextilHogar() {
        List<Ropa> listaRopa = new ArrayList<>();
        Ropa ropa;
        Cursor cursorRopa;
        String select = "SELECT * FROM " + TABLE_ROPA + " WHERE tipo = 'Textil Hogar' ORDER BY nombre ASC";
        cursorRopa = db.rawQuery(select, null);
        if (cursorRopa.moveToFirst()) {
            do {
                ropa = new Ropa();
                ropa.setId(cursorRopa.getInt(0));
                ropa.setNombre(cursorRopa.getString(1));
                ropa.setMarca(cursorRopa.getString(2));
                ropa.setTienda(cursorRopa.getString(3));
                ropa.setColor(cursorRopa.getString(4));
                ropa.setTalla(cursorRopa.getString(5));
                ropa.setTipo(cursorRopa.getString(6));
                ropa.setCantidad(cursorRopa.getInt(7));
                ropa.setEstado(cursorRopa.getString(8));
                listaRopa.add(ropa);
            } while (cursorRopa.moveToNext());
        }
        cursorRopa.close();
        return listaRopa;
    }

    public Ropa verRopa(int id) {
        Ropa ropa = null;
        Cursor cursorRopa;
        String select = "SELECT * FROM " + TABLE_ROPA + " WHERE id = '" + id + "' LIMIT 1";
        cursorRopa = db.rawQuery(select, null);
        if (cursorRopa.moveToFirst()) {
            do {
                ropa = new Ropa();
                ropa.setId(cursorRopa.getInt(0));
                ropa.setNombre(cursorRopa.getString(1));
                ropa.setMarca(cursorRopa.getString(2));
                ropa.setTienda(cursorRopa.getString(3));
                ropa.setColor(cursorRopa.getString(4));
                ropa.setTalla(cursorRopa.getString(5));
                ropa.setTipo(cursorRopa.getString(6));
                ropa.setCantidad(cursorRopa.getInt(7));
                ropa.setEstado(cursorRopa.getString(8));
            } while (cursorRopa.moveToNext());
        }
        cursorRopa.close();
        return ropa;
    }

    public boolean editarRopa(int id, String nombre, String marca, String tienda, String color,
                              String talla, String tipo, int cantidad, String estado) {
        boolean correcto;
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_ROPA + " SET " +
                    "nombre = '" + nombre + "', " +
                    "marca = '" + marca + "', " +
                    "tienda = '" + tienda + "', " +
                    "color = '" + color + "', " +
                    "talla = '" + talla + "', " +
                    "tipo = '" + tipo + "', " +
                    "cantidad = '" + cantidad + "', " +
                    "estado = '" + estado + "' " +
                    "WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }
        return correcto;
    }

    public boolean eliminarRopa(int id) {
        boolean correcto;
        try {
            db.execSQL("DELETE FROM " + TABLE_ROPA + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }
        db.close();
        return correcto;
    }
}
