package isamix.inventario.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.R;
import isamix.inventario.model.Categoria;

public class CategoriaRepository extends InventarioRepositoryHelper {
    Context context;
    InventarioRepositoryHelper helper;
    SQLiteDatabase database;

    public CategoriaRepository(@Nullable Context context) {
        super(context);
        this.context = context;
        this.helper = new InventarioRepositoryHelper(this.context);
        this.database = helper.getWritableDatabase();
    }

    public List<Categoria> getCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        Categoria categoria;
        Cursor cursor;

        cursor = database.rawQuery("SELECT * FROM " + TABLA_CATEGORIA + " ORDER BY nombre ASC", null);
        if (cursor.moveToFirst()) {
            do {
                categoria = new Categoria();
                categoria.setId(cursor.getInt(0));
                categoria.setNombre(cursor.getString(1));
                categoria.setIcono(cursor.getInt(2));
                categorias.add(categoria);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categorias;
    }

    public long addCategoria(String nombre) {
        long id;
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("icono", R.drawable.cesta);
        id = database.insert(TABLA_CATEGORIA, null, values);
        return id;
    }

    public void updateCategoria(int id, String nombre) {
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        database.update(TABLA_CATEGORIA, values, "id = " + id, null);
    }

    public Categoria getCategoriaByNombre(String nombre) {
        Categoria categoria = null;
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + TABLA_CATEGORIA + " WHERE nombre = '" + nombre + "'", null);
        if (cursor.moveToFirst()) {
            categoria = new Categoria();
            categoria.setId(cursor.getInt(0));
            categoria.setNombre(cursor.getString(1));
            categoria.setIcono(cursor.getInt(2));
        }
        cursor.close();
        return categoria;
    }

    public Categoria getCategoriaById(int id) {
        Categoria categoria = null;
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + TABLA_CATEGORIA + " WHERE id = '" + id + "'", null);
        if (cursor.moveToFirst()) {
            categoria = new Categoria();
            categoria.setId(cursor.getInt(0));
            categoria.setNombre(cursor.getString(1));
            categoria.setIcono(cursor.getInt(2));
        }
        cursor.close();
        return categoria;
    }

    public void deleteCategoria(int id) {
        database.execSQL("DELETE FROM " + TABLA_CATEGORIA + " WHERE id = '" + id + "'");
    }
}
