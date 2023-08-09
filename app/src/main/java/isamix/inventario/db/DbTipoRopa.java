package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.TipoRopa;

public class DbTipoRopa extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbTipoRopa(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<TipoRopa> mostrarTiposRopa() {
        List<TipoRopa> listaTipos = new ArrayList<>();
        TipoRopa tipo;
        Cursor cursorTipo;
        String select = "SELECT * FROM " + TABLE_TIPO_ROPA + " WHERE tipoRopa != 'Textil Hogar' ORDER BY tipoRopa ASC";
        cursorTipo = db.rawQuery(select, null);
        if (cursorTipo.moveToFirst()) {
            do {
                tipo = new TipoRopa();
                tipo.setId(cursorTipo.getInt(0));
                tipo.setTipoRopa(cursorTipo.getString(1));
                listaTipos.add(tipo);
            } while (cursorTipo.moveToNext());
        }
        cursorTipo.close();
        return listaTipos;
    }

    public long insertarTipoRopa(String tipo) {
        long id = 0;
        try {
            ContentValues value = new ContentValues();
            value.put("tipoRopa", tipo);
            id = db.insert(TABLE_TIPO_ROPA, null, value);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public boolean editarTipoRopa(int id, String tipo) {
        boolean corr;
        ContentValues value = new ContentValues();
        value.put("tipoRopa", tipo);
        try {
            db.update(TABLE_TIPO_ROPA, value, "id = '" + id, null);
            corr = true;
        } catch (Exception ex) {
            ex.toString();
            corr = false;
        }
        return corr;
    }

    public TipoRopa getTipoRopa(String tipo) {
        TipoRopa tipoRopa = null;
        Cursor cursorTipo;
        String select = "SELECT * FROM " + TABLE_TIPO_ROPA + " WHERE tipoRopa = '" + tipo + "'";
        cursorTipo = db.rawQuery(select, null);
        if (cursorTipo.moveToFirst()) {
            tipoRopa = new TipoRopa();
            tipoRopa.setId(cursorTipo.getInt(0));
            tipoRopa.setTipoRopa(cursorTipo.getString(1));
        }
        cursorTipo.close();
        return tipoRopa;
    }

    public TipoRopa getTipoRopaPorId(int id) {
        TipoRopa tipoRopa = null;
        Cursor cursorTipo;
        String select = "SELECT * FROM " + TABLE_TIPO_ROPA + " WHERE id = '" + id + "'";
        cursorTipo = db.rawQuery(select, null);
        if (cursorTipo.moveToFirst()) {
            tipoRopa = new TipoRopa();
            tipoRopa.setId(cursorTipo.getInt(0));
            tipoRopa.setTipoRopa(cursorTipo.getString(1));
        }
        cursorTipo.close();
        return tipoRopa;
    }

    public TipoRopa getTipoRopaTextilHogar() {
        TipoRopa tipoRopa = null;
        Cursor cursorTipo;
        String select = "SELECT * FROM " + TABLE_TIPO_ROPA + " WHERE tipoRopa = 'Textil Hogar'";
        cursorTipo = db.rawQuery(select, null);
        if (cursorTipo.moveToFirst()) {
            tipoRopa = new TipoRopa();
            tipoRopa.setId(cursorTipo.getInt(0));
            tipoRopa.setTipoRopa(cursorTipo.getString(1));
        }
        cursorTipo.close();
        return tipoRopa;
    }

    public void eliminarTipoRopa(int id) {
        db.execSQL("DELETE FROM " + TABLE_TIPO_ROPA + " WHERE id = '" + id + "'");
    }
}
