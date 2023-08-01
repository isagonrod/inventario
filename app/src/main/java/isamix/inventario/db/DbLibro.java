package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.Libro;

public class DbLibro extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbLibro(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public long insertarLibro(String titulo, String autor, String editorial, String genero, int isbn,
                              String lugarImpresion, int fechaImpresion) {
        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("titulo", titulo);
            values.put("autor", autor);
            values.put("editorial", editorial);
            values.put("genero", genero);
            values.put("isbn", isbn);
            values.put("lugarImpresion", lugarImpresion);
            values.put("fechaImpresion", fechaImpresion);
            id = db.insert(TABLE_LIBRO, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public List<Libro> mostrarLibros() {
        List<Libro> listaLibros = new ArrayList<>();
        Libro libro;
        Cursor cursorLibro;
        cursorLibro = db.rawQuery("SELECT * FROM " + TABLE_LIBRO + " ORDER BY titulo ASC", null);
        if (cursorLibro.moveToFirst()) {
            do {
                libro = new Libro();
                libro.setId(cursorLibro.getInt(0));
                libro.setTitulo(cursorLibro.getString(1));
                libro.setAutor(cursorLibro.getString(2));
                libro.setEditorial(cursorLibro.getString(3));
                libro.setEditorial(cursorLibro.getString(4));
                libro.setIsbn(cursorLibro.getInt(5));
                libro.setLugarImpresion(cursorLibro.getString(6));
                libro.setFechaImpresion(cursorLibro.getInt(7));
                listaLibros.add(libro);
            } while (cursorLibro.moveToNext());
        }
        cursorLibro.close();
        return listaLibros;
    }

    public List<Libro> mostrarLibrosPorGenero(String genero) {
        List<Libro> listaLibros = new ArrayList<>();
        Libro libro;
        Cursor cursorLibro;
        String select = "SELECT * FROM " + TABLE_LIBRO + " WHERE genero = '" + genero + "' ORDER BY titulo ASC";
        cursorLibro = db.rawQuery(select, null);
        if (cursorLibro.moveToFirst()) {
            do {
                libro = new Libro();
                libro.setId(cursorLibro.getInt(0));
                libro.setTitulo(cursorLibro.getString(1));
                libro.setAutor(cursorLibro.getString(2));
                libro.setEditorial(cursorLibro.getString(3));
                libro.setEditorial(cursorLibro.getString(4));
                libro.setIsbn(cursorLibro.getInt(5));
                libro.setLugarImpresion(cursorLibro.getString(6));
                libro.setFechaImpresion(cursorLibro.getInt(7));
                listaLibros.add(libro);
            } while (cursorLibro.moveToNext());
        }
        cursorLibro.close();
        return listaLibros;
    }

    public Libro verLibro(int id) {
        Libro libro = null;
        Cursor cursorLibro;
        cursorLibro = db.rawQuery("SELECT * FROM " + TABLE_LIBRO + " WHERE id = '" + id + "' LIMIT 1", null);
        if (cursorLibro.moveToFirst()) {
            do {
                libro = new Libro();
                libro.setId(cursorLibro.getInt(0));
                libro.setTitulo(cursorLibro.getString(1));
                libro.setAutor(cursorLibro.getString(2));
                libro.setEditorial(cursorLibro.getString(3));
                libro.setEditorial(cursorLibro.getString(4));
                libro.setIsbn(cursorLibro.getInt(5));
                libro.setLugarImpresion(cursorLibro.getString(6));
                libro.setFechaImpresion(cursorLibro.getInt(7));
            } while (cursorLibro.moveToNext());
        }
        cursorLibro.close();
        return libro;
    }

    public boolean editarLibro(int id, String titulo, String autor, String editorial, String genero,
                               int isbn, String lugarImpresion, int fechaImpresion) {
        boolean corr;
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_LIBRO + " SET " +
                    "titulo = '" + titulo + "', " +
                    "autor = '" + autor + "', " +
                    "editorial = '" + editorial + "', " +
                    "genero = '" + genero + "', " +
                    "isbn = '" + isbn + "', " +
                    "lugarImpresion = '" + lugarImpresion + "', " +
                    "fechaImpresion = '" + fechaImpresion + "' " +
                    "WHERE id = '" + id + "'");
            corr = true;
        } catch (Exception ex) {
            ex.toString();
            corr = false;
        }
        return corr;
    }

    public boolean eliminarLibro(int id) {
        boolean corr;
        try {
            db.execSQL("DELETE FROM " + TABLE_LIBRO + " WHERE id = '" + id + "'");
            corr = true;
        } catch (Exception ex) {
            ex.toString();
            corr = false;
        }
        db.close();
        return corr;
    }
}
