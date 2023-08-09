package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.Estado;

public class DbEstado extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbEstado(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<Estado> mostrarEstados() {
        List<Estado> listaEstados = new ArrayList<>();
        Estado estado;
        Cursor cursorEstados;
        String select = "SELECT * FROM " + TABLE_ESTADO + " ORDER BY estado ASC";
        cursorEstados = db.rawQuery(select, null);
        if (cursorEstados.moveToFirst()) {
            do {
                estado = new Estado();
                estado.setId(cursorEstados.getInt(0));
                estado.setEstado(cursorEstados.getString(1));
                listaEstados.add(estado);
            } while (cursorEstados.moveToNext());
        }
        cursorEstados.close();
        return listaEstados;
    }

    public long insertarEstado(String nuevoEstado) {
        long id = 0;
        try {
            ContentValues value = new ContentValues();
            value.put("estado", nuevoEstado);
            id = db.insert(TABLE_ESTADO, null, value);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public boolean editarEstado(int id, String estado) {
        boolean corr;
        ContentValues value = new ContentValues();
        value.put("estado", estado);
        try {
            db.update(TABLE_ESTADO, value, "id = '" + id, null);
            corr = true;
        } catch (Exception ex) {
            ex.toString();
            corr = false;
        }
        return corr;
    }

    public Estado getEstado(String nombre) {
        Estado estado = null;
        Cursor cursorEstado;
        String select = "SELECT * FROM " + TABLE_ESTADO + " WHERE estado = '" + nombre + "'";
        cursorEstado = db.rawQuery(select, null);
        if (cursorEstado.moveToFirst()) {
            estado = new Estado();
            estado.setId(cursorEstado.getInt(0));
            estado.setEstado(cursorEstado.getString(1));
        }
        cursorEstado.close();
        return estado;
    }
}
