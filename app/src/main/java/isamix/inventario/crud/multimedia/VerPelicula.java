package isamix.inventario.crud.multimedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isamix.inventario.R;
import isamix.inventario.db.DbPelicula;
import isamix.inventario.modelo.Pelicula;

public class VerPelicula extends AppCompatActivity {

    TextView titulo, director, fechaEstreno, minDuracion, estado;
    Button btnGuardar, btnEditar, btnEliminar;
    Pelicula pelicula;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_pelicula);

        titulo = findViewById(R.id.filmTitle);
        director = findViewById(R.id.filmDirector);
        fechaEstreno = findViewById(R.id.filmYear);
        minDuracion = findViewById(R.id.filmDuration);
        estado = findViewById(R.id.filmState);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.fabEditar);
        btnEliminar = findViewById(R.id.fabEliminar);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbPelicula dbPelicula = new DbPelicula(VerPelicula.this);
        pelicula = dbPelicula.verPelicula(id);

        if (pelicula != null) {
            titulo.setText(pelicula.getTitulo());
            director.setText(pelicula.getDirector());
            fechaEstreno.setText(String.valueOf(pelicula.getFechaEstreno()));
            minDuracion.setText(String.valueOf(pelicula.getMinDuracion()));
            estado.setText(pelicula.getEstado());
            btnGuardar.setVisibility(View.GONE);
        }

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(VerPelicula.this, EditarPelicula.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        });

        btnEliminar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(VerPelicula.this);
            builder.setTitle("ELIMINAR PELÍCULA");
            builder.setMessage("¿Desea eliminar esta película?");
            builder.setPositiveButton("SÍ", (dialogInterface, i) -> {
                if (dbPelicula.eliminarPelicula(id)) {
                    lista();
                }
            }).setNegativeButton("NO", (dialogInterface, i) -> {}).show();
        });
    }

    private void lista() {
        Intent intent = new Intent(this, ListaPelicula.class);
        startActivity(intent);
    }
}
