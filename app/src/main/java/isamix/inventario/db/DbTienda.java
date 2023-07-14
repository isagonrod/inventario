package isamix.inventario.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

public class DbTienda extends DbHelper {

    public DbTienda(@Nullable Context context) {
        super(context);
    }

    public Cursor mostrarTiendas() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursorTienda = db.rawQuery("SELECT * FROM " + TABLE_TIENDA + " ORDER BY nombre", null);
            if (cursorTienda.moveToFirst()) {
                return cursorTienda;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
    }
}
