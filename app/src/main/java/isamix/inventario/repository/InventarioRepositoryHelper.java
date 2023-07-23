package isamix.inventario.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import isamix.inventario.R;

public class InventarioRepositoryHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "inventario.db";
    public static final String TABLA_PRODUCTO = "producto";
    public static final String TABLA_CATEGORIA = "categoria";
    public static final String TABLA_TIENDA = "tienda";

    public InventarioRepositoryHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLA_CATEGORIA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "icono INTEGER)");

        database.execSQL("CREATE TABLE " + TABLA_TIENDA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL)");

        database.execSQL("CREATE TABLE " + TABLA_PRODUCTO + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "cantidad INTEGER, " +
                "precio INTEGER, " +
                "tienda INTEGER, " +
                "categoria INTEGER," +
                "FOREIGN KEY(tienda) REFERENCES " + TABLA_TIENDA + "(id), " +
                "FOREIGN KEY (categoria) REFERENCES " + TABLA_CATEGORIA + "(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLA_PRODUCTO);
        database.execSQL("DROP TABLE IF EXISTS " + TABLA_CATEGORIA);
        database.execSQL("DROP TABLE IF EXISTS " + TABLA_TIENDA);
        onCreate(database);
    }

    public void addDefaultCategorias(SQLiteDatabase database) {
        database.execSQL("INSERT INTO " + TABLA_CATEGORIA + " VALUES ('Comida', R.drawable.cesto)");
        database.execSQL("INSERT INTO " + TABLA_CATEGORIA + " VALUES ('Bebida', R.drawable.batido)");
        database.execSQL("INSERT INTO " + TABLA_CATEGORIA + " VALUES ('Higiene', R.drawable.jabon)");
        database.execSQL("INSERT INTO " + TABLA_CATEGORIA + " VALUES ('Limpieza', R.drawable.fregona)");
        database.execSQL("INSERT INTO " + TABLA_CATEGORIA + " VALUES ('Juegos de mesa', R.drawable.cartas)");
        database.execSQL("INSERT INTO " + TABLA_CATEGORIA + " VALUES ('Videojuegos', R.drawable.gamesfolder)");
    }
}
