package isamix.inventario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.modelo.Persona;

public class DbPersona extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DbPersona(@Nullable Context context) {
        super(context);
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<Persona> mostrarPersonas() {
        List<Persona> listaPersonas = new ArrayList<>();
        Persona persona;
        Cursor cursorPersona;

        cursorPersona = db.rawQuery("SELECT * FROM " + TABLE_PERSONA + " ORDER BY nombreCompleto ASC", null);

        if (cursorPersona.moveToFirst()) {
            do {
                persona = new Persona();
                persona.setId(cursorPersona.getInt(0));
                persona.setNombreCompleto(cursorPersona.getString(1));
                persona.setProfesion(cursorPersona.getString(2));
            } while (cursorPersona.moveToNext());
        }
        return listaPersonas;
    }

    public List<Persona> mostrarPersonasPorProfesion(String prof) {
        List<Persona> listaPersonas = new ArrayList<>();
        Persona persona;
        Cursor cursorPersona;

        cursorPersona = db.rawQuery("SELECT * FROM " + TABLE_PERSONA + " WHERE profesion = '" + prof + "' ORDER BY nombreCompleto ASC", null);

        if (cursorPersona.moveToFirst()) {
            do {
                persona = new Persona();
                persona.setId(cursorPersona.getInt(0));
                persona.setNombreCompleto(cursorPersona.getString(1));
                persona.setProfesion(cursorPersona.getString(2));
            } while (cursorPersona.moveToNext());
        }
        return listaPersonas;
    }

    public long insertarPersona(String nombreCompleto, String profesion) {
        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("nombreCompleto", nombreCompleto);
            values.put("profesion", profesion);
            id = db.insert(TABLE_PERSONA, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public boolean editarPersona(int id, String nombreCompleto, String profesion) {
        boolean correcto;
        ContentValues values = new ContentValues();
        values.put("nombreCompleto", nombreCompleto);
        values.put("profesion", profesion);
        try {
            db.update(TABLE_PERSONA, values, "id = " + id, null);
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        }
        return correcto;
    }

    public Persona getPersona(String nombreCompleto, String profesion) {
        Persona persona = null;
        Cursor cursorPersona;
        cursorPersona = db.rawQuery("SELECT * FROM " + TABLE_PERSONA + " WHERE nombreCompleto = '" + nombreCompleto + "' AND profesion = '" + profesion + "'", null);
        if (cursorPersona.moveToFirst()) {
            persona = new Persona();
            persona.setId(cursorPersona.getInt(0));
            persona.setNombreCompleto(cursorPersona.getString(1));
            persona.setProfesion(cursorPersona.getString(2));
        }
        cursorPersona.close();
        return persona;
    }
}
