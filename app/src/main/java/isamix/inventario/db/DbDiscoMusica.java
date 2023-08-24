package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.DiscoMusica;
import isamix.inventario.modelo.Pelicula;

public class DbDiscoMusica extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbDiscoMusica(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public long insertarDiscoMusica(String titulo, String artista, int fecha, String estado) {
        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("titulo", titulo);
            values.put("artista_grupo", artista);
            values.put("fechaLanzamiento", fecha);
            values.put("estado", estado);
            id = db.insert(TABLE_DISCO_MUSICA, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public List<DiscoMusica> mostrarDiscosMusica() {
        List<DiscoMusica> listaDiscosMusica = new ArrayList<>();
        DiscoMusica discoMusica;
        Cursor cursorMusica;
        String select = "SELECT * FROM " + TABLE_DISCO_MUSICA + " ORDER BY titulo ASC";
        cursorMusica = db.rawQuery(select, null);
        if (cursorMusica.moveToFirst()) {
            do {
                discoMusica = new DiscoMusica();
                discoMusica.setId(cursorMusica.getInt(0));
                discoMusica.setTitulo(cursorMusica.getString(1));
                discoMusica.setArtista_grupo(cursorMusica.getString(2));
                discoMusica.setFechaLanzamiento(cursorMusica.getInt(3));
                discoMusica.setEstado(cursorMusica.getString(4));
                listaDiscosMusica.add(discoMusica);
            } while (cursorMusica.moveToNext());
        }
        cursorMusica.close();
        return listaDiscosMusica;
    }

    public DiscoMusica verDiscoMusica(int id) {
        DiscoMusica discoMusica = null;
        Cursor cursorMusica;
        String select = "SELECT * FROM " + TABLE_DISCO_MUSICA + " WHERE id = '" + id + "' LIMIT 1";
        cursorMusica = db.rawQuery(select, null);
        if (cursorMusica.moveToFirst()) {
            do {
                discoMusica = new DiscoMusica();
                discoMusica.setId(cursorMusica.getInt(0));
                discoMusica.setTitulo(cursorMusica.getString(1));
                discoMusica.setArtista_grupo(cursorMusica.getString(2));
                discoMusica.setFechaLanzamiento(cursorMusica.getInt(3));
                discoMusica.setEstado(cursorMusica.getString(4));
            } while (cursorMusica.moveToNext());
        }
        cursorMusica.close();
        return discoMusica;
    }

    public boolean editarDiscoMusica(int id, String titulo, String artista, int fecha, String estado) {
        boolean correcto;
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_DISCO_MUSICA + " SET " +
                    "titulo = '" + titulo +"', " +
                    "artista_grupo = '" + artista + "', " +
                    "fechaLanzamiento = '" + fecha + "', " +
                    "estado = '" + estado + "' " +
                    "WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }
        return correcto;
    }

    public boolean eliminarDiscoMusica(int id) {
        boolean correcto;
        try {
            db.execSQL("DELETE FROM " + TABLE_DISCO_MUSICA + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }
        db.close();
        return correcto;
    }
}
