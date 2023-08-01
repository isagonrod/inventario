package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.Genero;

public class DbGenero extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbGenero(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<Genero> mostrarGeneros() {
        List<Genero> listaGeneros = new ArrayList<>();
        Genero genero;
        Cursor cursorGenero;
        cursorGenero = db.rawQuery("SELECT * FROM " + TABLE_GENERO + " ORDER BY nombre ASC", null);
        if (cursorGenero.moveToFirst()) {
            do {
                genero = new Genero();
                genero.setId(cursorGenero.getInt(0));
                genero.setNombre(cursorGenero.getString(1));
                listaGeneros.add(genero);
            } while (cursorGenero.moveToNext());
        }
        cursorGenero.close();
        return listaGeneros;
    }

    public long insertarGenero(String nombre) {
        long id = 0;
        try {
            ContentValues value = new ContentValues();
            value.put("nombre", nombre);
            id = db.insert(TABLE_GENERO, null, value);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public boolean editarGenero(int id, String nombre) {
        boolean corr;
        ContentValues value = new ContentValues();
        value.put("nombre", nombre);
        try {
            db.update(TABLE_GENERO, value, "id = " + id, null);
            corr = true;
        } catch (Exception ex) {
            ex.toString();
            corr = false;
        }
        return corr;
    }

    public Genero getGeneroPorNombre(String nombre) {
        Genero genero = null;
        Cursor cursorGenero;
        cursorGenero = db.rawQuery("SELECT * FROM " + TABLE_GENERO + " WHERE nombre = '" + nombre + "'", null);
        if (cursorGenero.moveToFirst()) {
            genero = new Genero();
            genero.setId(cursorGenero.getInt(0));
            genero.setNombre(cursorGenero.getString(1));
        }
        cursorGenero.close();
        return genero;
    }

    public Genero getGeneroPorId(int id) {
        Genero genero = null;
        Cursor cursorGenero;
        cursorGenero = db.rawQuery("SELECT * FROM " + TABLE_GENERO + " WHERE id = '" + id + "'", null);
        if (cursorGenero.moveToFirst()) {
            genero = new Genero();
            genero.setId(cursorGenero.getInt(0));
            genero.setNombre(cursorGenero.getString(1));
        }
        cursorGenero.close();
        return genero;
    }

    public void eliminarGenero(int id) {
        db.execSQL("DELETE FROM " + TABLE_GENERO + " WHERE id = '" + id + "'");
    }
}
