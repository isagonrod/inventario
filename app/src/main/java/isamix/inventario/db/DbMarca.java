package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.Marca;

public class DbMarca extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbMarca(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<Marca> mostrarMarcas() {
        List<Marca> listaMarcas = new ArrayList<>();
        Marca marca;

        Cursor cursorMarcas;
        cursorMarcas = db.rawQuery("SELECT * FROM " + TABLE_MARCA + " ORDER BY nombre ASC", null);

        if (cursorMarcas.moveToFirst()) {
            do {
                marca = new Marca();
                marca.setId(cursorMarcas.getInt(0));
                marca.setNombre(cursorMarcas.getString(1));
                listaMarcas.add(marca);
            } while (cursorMarcas.moveToNext());
        }
        cursorMarcas.close();
        return listaMarcas;
    }

    public long insertarMarca(String nombre) {
        long id = 0;

        try {
            ContentValues value = new ContentValues();
            value.put("nombre", nombre);
            id = db.insert(TABLE_MARCA, null, value);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public boolean editarMarca(int id, String nombre) {
        boolean correcto;
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);

        try {
            db.update(TABLE_MARCA, values, "id = " + id, null);
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }

        return correcto;
    }

    public Marca getMarca(String nombre) {
        Marca marca = null;
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM " + TABLE_MARCA + " WHERE nombre = '" + nombre + "'", null);

        if (cursor.moveToFirst()) {
            marca = new Marca();
            marca.setId(cursor.getInt(0));
            marca.setNombre(cursor.getString(1));
        }
        cursor.close();

        return marca;
    }
}
