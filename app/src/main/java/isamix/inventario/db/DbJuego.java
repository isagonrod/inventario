package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.Juego;

public class DbJuego extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbJuego(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public long insertarJuego(String nombre, String marca, String tipoJuego, String numJugadores) {
        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("marca", marca);
            values.put("tipoJuego", tipoJuego);
            values.put("numJugadores", numJugadores);
            id = db.insert(TABLE_JUEGO, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public List<Juego> mostrarJuegos() {
        List<Juego> listaJuegos = new ArrayList<>();
        Juego juego;
        Cursor cursorJuegos;
        cursorJuegos = db.rawQuery("SELECT * FROM " + TABLE_JUEGO + " ORDER BY nombre ASC", null);
        if (cursorJuegos.moveToFirst()) {
            do {
                juego = new Juego();
                juego.setId(cursorJuegos.getInt(0));
                juego.setNombre(cursorJuegos.getString(1));
                juego.setMarca(cursorJuegos.getString(2));
                juego.setTipoJuego(cursorJuegos.getString(3));
                juego.setNumJugadores(cursorJuegos.getString(4));
                listaJuegos.add(juego);
            } while (cursorJuegos.moveToNext());
        }
        cursorJuegos.close();
        return listaJuegos;
    }

    public List<Juego> mostrarJuegosPorTipo(String tipoJuego) {
        List<Juego> listaJuegos = new ArrayList<>();
        Juego juego;
        Cursor cursorJuegos;
        String select = "SELECT * FROM " + TABLE_JUEGO + " WHERE tipoJuego = '" + tipoJuego + "' ORDER BY nombre ASC";
        cursorJuegos = db.rawQuery(select, null);
        if (cursorJuegos.moveToFirst()) {
            do {
                juego = new Juego();
                juego.setId(cursorJuegos.getInt(0));
                juego.setNombre(cursorJuegos.getString(1));
                juego.setMarca(cursorJuegos.getString(2));
                juego.setTipoJuego(cursorJuegos.getString(3));
                juego.setNumJugadores(cursorJuegos.getString(4));
                listaJuegos.add(juego);
            } while (cursorJuegos.moveToNext());
        }
        cursorJuegos.close();
        return listaJuegos;
    }

    public Juego verJuego(int id) {
        Juego juego = null;
        Cursor cursorJuegos;
        cursorJuegos = db.rawQuery("SELECT * FROM " + TABLE_JUEGO + " WHERE id = '" + id + "' LIMIT 1", null);
        if (cursorJuegos.moveToFirst()) {
            juego = new Juego();
            juego.setId(cursorJuegos.getInt(0));
            juego.setNombre(cursorJuegos.getString(1));
            juego.setMarca(cursorJuegos.getString(2));
            juego.setTipoJuego(cursorJuegos.getString(3));
            juego.setNumJugadores(cursorJuegos.getString(4));
        }
        cursorJuegos.close();
        return juego;
    }

    public boolean editarJuego(int id, String nombre, String marca, String tipoJuego, String numJugadores) {
        boolean correct;
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_JUEGO + " SET " +
                    "nombre = '" + nombre + "', " +
                    "marca = '" + marca + "', " +
                    "tipoJuego = '" + tipoJuego + "', " +
                    "numJugadores = '" + numJugadores + "' " +
                    "WHERE id = '" + id + "'");
            correct = true;
        } catch (Exception ex) {
            ex.toString();
            correct = false;
        }
        return correct;
    }

    public boolean eliminarJuego(int id) {
        boolean correct;
        try {
            db.execSQL("DELETE FROM " + TABLE_JUEGO + " WHERE id = '" + id + "'");
            correct = true;
        } catch (Exception ex) {
            ex.toString();
            correct = false;
        }
        db.close();
        return correct;
    }
}
