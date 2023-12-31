package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.Categoria;

public class DbCategoria extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbCategoria(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<Categoria> mostrarCategorias() {

        List<Categoria> listaCategorias = new ArrayList<>();
        Categoria categoria;
        Cursor cursorCategoria;

        cursorCategoria = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIA + " ORDER BY nombre ASC", null);

        if (cursorCategoria.moveToFirst()) {
            do {
                categoria = new Categoria();
                categoria.setId(cursorCategoria.getInt(0));
                categoria.setNombre(cursorCategoria.getString(1));
                listaCategorias.add(categoria);
            } while (cursorCategoria.moveToNext());
        }
        cursorCategoria.close();
        return listaCategorias;
    }

    public long insertarCategoria(String nombre) {
        long id = 0;

        try {
            ContentValues value = new ContentValues();
            value.put("nombre", nombre);
            id = db.insert(TABLE_CATEGORIA, null, value);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public boolean editarCategoria(int id, String nombre) {
        boolean correcto;

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);

        try {
            db.update(TABLE_CATEGORIA, values, "id = " + id, null);
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }

        return correcto;
    }

    public Categoria getCategoriaPorNombre(String nombre) {
        Categoria categoria = null;
        Cursor cursorCategoria;

        cursorCategoria = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIA + " WHERE nombre = '" + nombre + "'", null);

        if (cursorCategoria.moveToFirst()) {
            categoria = new Categoria();
            categoria.setId(cursorCategoria.getInt(0));
            categoria.setNombre(cursorCategoria.getString(1));
        }

        cursorCategoria.close();
        return categoria;
    }

    public Categoria getCategoriaPorId(int id) {
        Categoria categoria = null;
        Cursor cursorCategoria;

        cursorCategoria = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIA + " WHERE id = '" + id + "'", null);

        if (cursorCategoria.moveToFirst()) {
            categoria = new Categoria();
            categoria.setId(cursorCategoria.getInt(0));
            categoria.setNombre(cursorCategoria.getString(1));
        }

        cursorCategoria.close();
        return categoria;
    }

    public boolean eliminarCategoria(String nombreCategoria) {
        boolean correcto;
        try {
            db.execSQL("DELETE FROM " + TABLE_CATEGORIA + " WHERE nombre = '" + nombreCategoria + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }
        return correcto;
    }

    public void eliminarCategoria(int idCategoria) {
        db.execSQL("DELETE FROM " + TABLE_CATEGORIA + " WHERE id = '" + idCategoria + "'");
    }
}
