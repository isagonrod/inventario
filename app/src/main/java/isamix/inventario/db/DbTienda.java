package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.Tienda;

public class DbTienda extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbTienda(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<Tienda> mostrarTiendas() {

        List<Tienda> listaTiendas = new ArrayList<>();
        Tienda tienda;
        Cursor cursorTiendas;

        cursorTiendas = db.rawQuery("SELECT * FROM " + TABLE_TIENDA + " ORDER BY nombre ASC", null);

        if (cursorTiendas.moveToFirst()) {
            do {
                tienda = new Tienda();
                tienda.setId(cursorTiendas.getInt(0));
                tienda.setNombre(cursorTiendas.getString(1));
                listaTiendas.add(tienda);
            } while (cursorTiendas.moveToNext());
        }
        cursorTiendas.close();
        return listaTiendas;
    }

    public long insertarTienda(String nombre) {
        long id = 0;

        try {
            ContentValues value = new ContentValues();
            value.put("nombre", nombre);
            id = db.insert(TABLE_TIENDA, null, value);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public boolean editarTienda(int id, String nombre) {
        boolean correcto;

        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", nombre);

        try {
            db.update(TABLE_TIENDA, contentValues, "id = " + id, null);
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }
        return correcto;
    }

    public Tienda getTienda(String nombre) {
        Tienda tienda = null;
        Cursor cursorTienda;

        cursorTienda = db.rawQuery("SELECT * FROM " + TABLE_TIENDA + " WHERE nombre = '" + nombre + "'", null);

        if (cursorTienda.moveToFirst()) {
            tienda = new Tienda();
            tienda.setId(cursorTienda.getInt(0));
            tienda.setNombre(cursorTienda.getString(1));
        }
        cursorTienda.close();

        return tienda;
    }
}
