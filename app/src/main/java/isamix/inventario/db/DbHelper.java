package isamix.inventario.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "inventario.db";
    public static final String TABLE_PRODUCTO = "t_producto";
    public static final String TABLE_TIENDA = "t_tienda";
    public static final String TABLE_CATEGORIA = "t_categoria";
    public static final String TABLE_MARCA = "t_marca";
    public static final String TABLE_LIBRO = "t_libro";
    public static final String TABLE_GENERO = "t_genero";
    public static final String TABLE_PERSONA = "t_persona";
    public static final String TABLE_PROFESION = "t_profesion";
    public static final String TABLE_TIPO_JUEGO = "t_tipo_juego";
    public static final String TABLE_JUEGO = "t_juego";

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
                "FOREIGN KEY(marca) REFERENCES " + TABLE_MARCA + "(id), " +
                "FOREIGN KEY(tienda) REFERENCES " + TABLE_TIENDA + "(id), " +
                "FOREIGN KEY(categoria) REFERENCES " + TABLE_CATEGORIA + "(id)" +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_PROFESION + "(id INTEGER PRIMARY KEY AUTOINCREMENT, prof TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_PERSONA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombreCompleto TEXT, " +
                "profesion TEXT," +
                "FOREIGN KEY(profesion) REFERENCES " + TABLE_PROFESION + "(id))");

        db.execSQL("CREATE TABLE " + TABLE_GENERO + "(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_LIBRO + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "autor TEXT NOT NULL, " +
                "editorial TEXT, " +
                "genero TEXT, " +
                "isbn INTEGER, " +
                "lugarImpresion TEXT, " +
                "fechaImpresion INTEGER, " +
                "FOREIGN KEY(autor) REFERENCES " + TABLE_PERSONA + "(id), " +
                "FOREIGN KEY(editorial) REFERENCES " + TABLE_MARCA + "(id)," +
                "FOREIGN KEY(genero) REFERENCES " + TABLE_GENERO + "(id))");

        db.execSQL("CREATE TABLE " + TABLE_TIPO_JUEGO + "(id INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_JUEGO + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "marca TEXT, " +
                "tipoJuego TEXT, " +
                "numJugadores TEXT, " +
                "FOREIGN KEY(marca) REFERENCES "+ TABLE_MARCA + "(id)," +
                "FOREIGN KEY(tipoJuego) REFERENCES " + TABLE_TIPO_JUEGO + "(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIENDA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARCA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFESION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIBRO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENERO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JUEGO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO_JUEGO);
        onCreate(db);
    }
}
