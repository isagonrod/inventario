package isamix.inventario.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.model.Tienda;

public class TiendaRepository extends InventarioRepositoryHelper {
    Context context;
    InventarioRepositoryHelper helper;
    SQLiteDatabase database;

    public TiendaRepository(@Nullable Context context) {
        super(context);
        this.context = context;
        this.helper = new InventarioRepositoryHelper(this.context);
        this.database = this.helper.getWritableDatabase();
    }

    public List<Tienda> getTiendas() {
        List<Tienda> tiendas = new ArrayList<>();
        Tienda tienda;
        Cursor cursor;

        cursor = database.rawQuery("SELECT * FROM " + TABLA_TIENDA + " ORDER BY nombre ASC", null);
        if (cursor.moveToFirst()) {
            do {
                tienda = new Tienda();
                tienda.setId(cursor.getInt(0));
                tienda.setNombre(cursor.getString(1));
                tiendas.add(tienda);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tiendas;
    }

    public long addTienda(String nombre) {
        long id;
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        id = database.insert(TABLA_TIENDA, null, values);
        return id;
    }

    public void updateTienda(int id, String nombre) {
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        database.update(TABLA_TIENDA, values, "id = " + id, null);
    }

    public Tienda getTienda(String nombre) {
        Tienda tienda = null;
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + TABLA_TIENDA + " WHERE nombre = '" + nombre + "'", null);
        if (cursor.moveToFirst()) {
            tienda = new Tienda();
            tienda.setId(cursor.getInt(0));
            tienda.setNombre(cursor.getString(1));
        }
        cursor.close();
        return tienda;
    }

    public void deleteTienda(int id) {
        database.execSQL("DELETE FROM " + TABLA_TIENDA + " WHERE id = '" + id + "'");
    }
}
