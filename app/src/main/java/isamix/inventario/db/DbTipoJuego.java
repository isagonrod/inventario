package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.TipoJuego;

public class DbTipoJuego extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbTipoJuego(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<TipoJuego> mostrarTiposJuego() {
        List<TipoJuego> listaTipos = new ArrayList<>();
        TipoJuego tipo;
        Cursor cursorTipo;
        cursorTipo = db.rawQuery("SELECT * FROM " + TABLE_TIPO_JUEGO + " ORDER BY tipo ASC", null);
        if (cursorTipo.moveToFirst()) {
            do {
                tipo = new TipoJuego();
                tipo.setId(cursorTipo.getInt(0));
                tipo.setTipo(cursorTipo.getString(1));
                listaTipos.add(tipo);
            } while (cursorTipo.moveToNext());
        }
        cursorTipo.close();
        return listaTipos;
    }

    public long insertarTipoJuego(String tipo) {
        long id = 0;
        try {
            ContentValues value = new ContentValues();
            value.put("tipo", tipo);
            id = db.insert(TABLE_TIPO_JUEGO, null, value);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public boolean editarTipoJuego(int id, String tipo) {
        boolean correcto;
        ContentValues value = new ContentValues();
        value.put("tipo", tipo);
        try {
            db.update(TABLE_TIPO_JUEGO, value, "id = " + id, null);
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }
        return correcto;
    }

    public TipoJuego getTipoJuego(String tipo) {
        TipoJuego tipoJuego = null;
        Cursor cursorTipo;
        cursorTipo = db.rawQuery("SELECT * FROM " + TABLE_TIPO_JUEGO + " WHERE tipo = '" + tipo + "'", null);
        if (cursorTipo.moveToFirst()) {
            tipoJuego = new TipoJuego();
            tipoJuego.setId(cursorTipo.getInt(0));
            tipoJuego.setTipo(cursorTipo.getString(1));
        }
        cursorTipo.close();
        return tipoJuego;
    }

    public TipoJuego getTipoJuegoPorId(int id) {
        TipoJuego tipoJuego = null;
        Cursor cursorTipo;
        cursorTipo = db.rawQuery("SELECT * FROM " + TABLE_TIPO_JUEGO + " WHERE id = '" + id + "'", null);
        if (cursorTipo.moveToFirst()) {
            tipoJuego = new TipoJuego();
            tipoJuego.setId(cursorTipo.getInt(0));
            tipoJuego.setTipo(cursorTipo.getString(1));
        }
        cursorTipo.close();
        return tipoJuego;
    }

    public void eliminarTipoJuego(int id) {
        db.execSQL("DELETE FROM " + TABLE_TIPO_JUEGO + " WHERE id = '" + id + "'");
    }
}
