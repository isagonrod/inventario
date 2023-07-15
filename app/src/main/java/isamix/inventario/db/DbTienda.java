package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import isamix.inventario.entity.Tienda;

public class DbTienda extends DbHelper {

    Context context;

    public DbTienda(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public Cursor mostrarTiendas() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursorTienda = db.rawQuery("SELECT * FROM " + TABLE_TIENDA + " ORDER BY nombre", null);
            if (cursorTienda.moveToFirst()) {
                return cursorTienda;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
    }

    public long insertarTienda(String nombre) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues value = new ContentValues();
            value.put("nombre", nombre);

            id = db.insert(TABLE_TIENDA, null, value);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public String getTienda(String nombre) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Tienda tienda = null;
        Cursor cursorTienda;

        cursorTienda = db.rawQuery("SELECT * FROM " + TABLE_TIENDA + " WHERE nombre = '" + nombre + "'", null);

        if (cursorTienda.moveToFirst()) {
            tienda = new Tienda();
            tienda.setId(cursorTienda.getInt(0));
            tienda.setNombre(cursorTienda.getString(1));
        }
        cursorTienda.close();

        assert tienda != null;
        return tienda.getNombre();
    }
}
