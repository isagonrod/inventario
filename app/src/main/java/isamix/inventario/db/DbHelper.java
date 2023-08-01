package isamix.inventario.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "inventario.db";
    public static final String TABLE_PRODUCTO = "t_producto";
    public static final String TABLE_TIENDA = "t_tienda";
    public static final String TABLE_CATEGORIA = "t_categoria";
    public static final String TABLE_MARCA = "t_marca";
    public static final String TABLE_LIBRO = "t_libro";
    public static final String TABLE_GENERO = "t_genero";
    public static final String TABLE_PERSONA = "t_persona";
    public static final String TABLE_PROFESION = "t_profesion";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TIENDA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_CATEGORIA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_MARCA + "(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_PRODUCTO + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "marca TEXT, " +
                "cantidad INTEGER NOT NULL, " +
                "precio REAL, " +
                "tienda INTEGER, " +
                "categoria INTEGER, " +
                "paraComprar INTEGER," +
                "FOREIGN KEY(tienda) REFERENCES " + TABLE_TIENDA + "(id), " +
                "FOREIGN KEY(categoria) REFERENCES " + TABLE_CATEGORIA + "(id)" +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_PROFESION + "(id INTEGER PRIMARY KEY AUTOINCREMENT, prof TEXT NOT NULL)");
        db.execSQL("INSERT INTO " + TABLE_PROFESION + "(prof) VALUES ('Autor', 'Director', 'Artista', 'Grupo/Banda')");

        db.execSQL("CREATE TABLE " + TABLE_PERSONA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "apellido TEXT, " +
                "profesion TEXT," +
                "FOREIGN KEY(profesion) REFERENCES " + TABLE_PROFESION + "(id))");

        db.execSQL("CREATE TABLE " + TABLE_GENERO + "(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIENDA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
        onCreate(db);
    }
}
