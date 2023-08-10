package isamix.inventario.crud.multimedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isamix.inventario.R;
import isamix.inventario.db.DbDiscoMusica;
import isamix.inventario.modelo.DiscoMusica;

public class VerDisco extends AppCompatActivity {

    TextView titulo, artista, fechaLanzamiento, estado;
    Button btnGuardar, btnEditar, btnEliminar;
    DiscoMusica disco;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_disco);

        titulo = findViewById(R.id.discTitle);
        artista = findViewById(R.id.discArtist);
        fechaLanzamiento = findViewById(R.id.discYear);
        estado = findViewById(R.id.discState);

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

        final DbDiscoMusica dbDiscos = new DbDiscoMusica(VerDisco.this);
        disco = dbDiscos.verDiscoMusica(id);

        if (disco != null) {
            titulo.setText(disco.getTitulo());
            artista.setText(disco.getArtista_grupo());
            fechaLanzamiento.setText(String.valueOf(disco.getFechaLanzamiento()));
            estado.setText(disco.getEstado());
            btnGuardar.setVisibility(View.GONE);
        }

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(VerDisco.this, EditarDisco.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        });

        btnEliminar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(VerDisco.this);
            builder.setTitle("ELIMINAR DISCO DE MÚSICA");
            builder.setMessage("¿Desea eliminar este disco?");
            builder.setPositiveButton("SÍ", (dialogInterface, i) -> {
                if (dbDiscos.eliminarDiscoMusica(id)) {
                    lista();
                }
            }).setNegativeButton("NO", (dialogInterface, i) -> {}).show();
        });
    }

    private void lista() {
        Intent intent = new Intent(this, ListaDiscoMusica.class);
        startActivity(intent);
    }
}
