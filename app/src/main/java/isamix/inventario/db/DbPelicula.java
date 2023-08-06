package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.Pelicula;

public class DbPelicula extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbPelicula(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public long insertarPelicula(String titulo, String director, int fecha, int minDuracion) {
        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("titulo", titulo);
            values.put("director", director);
            values.put("fechaEstreno", fecha);
            values.put("minDuracion", minDuracion);
            id = db.insert(TABLE_PELICULA, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public List<Pelicula> mostrarPeliculas() {
        List<Pelicula> listaPeliculas = new ArrayList<>();
        Pelicula pelicula;
        Cursor cursorPelicula;
        String select = "SELECT * FROM " + TABLE_PELICULA + " ORDER BY titulo ASC";
        cursorPelicula = db.rawQuery(select, null);
        if (cursorPelicula.moveToFirst()) {
            do {
                pelicula = new Pelicula();
                pelicula.setId(cursorPelicula.getInt(0));
                pelicula.setTitulo(cursorPelicula.getString(1));
                pelicula.setDirector(cursorPelicula.getString(2));
                pelicula.setFechaEstreno(cursorPelicula.getInt(3));
                pelicula.setMinDuracion(cursorPelicula.getInt(4));
                listaPeliculas.add(pelicula);
            } while (cursorPelicula.moveToNext());
        }
        cursorPelicula.close();
        return listaPeliculas;
    }

    public Pelicula verPelicula(int id) {
        Pelicula pelicula = null;
        Cursor cursorPelicula;
        String select = "SELECT * FROM " + TABLE_PELICULA + " WHERE id = '" + id + "' LIMIT 1";
        cursorPelicula = db.rawQuery(select, null);
        if (cursorPelicula.moveToFirst()) {
            do {
                pelicula = new Pelicula();
                pelicula.setId(cursorPelicula.getInt(0));
                pelicula.setTitulo(cursorPelicula.getString(1));
                pelicula.setDirector(cursorPelicula.getString(2));
                pelicula.setFechaEstreno(cursorPelicula.getInt(3));
                pelicula.setMinDuracion(cursorPelicula.getInt(4));
            } while (cursorPelicula.moveToNext());
        }
        cursorPelicula.close();
        return pelicula;
    }

    public boolean editarPelicula(int id, String titulo, String director, int fecha, int minDuracion) {
        boolean correcto;
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_PELICULA + " SET " +
                    "titulo = '" + titulo +"', " +
                    "director = '" + director + "', " +
                    "fechaEstreno = '" + fecha + "' " +
                    "minDuracion = '" + minDuracion + "', " +
                    "WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }
        return correcto;
    }

    public boolean eliminarPelicula(int id) {
        boolean correcto;
        try {
            db.execSQL("DELETE FROM " + TABLE_PELICULA + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }
        db.close();
        return correcto;
    }
}
