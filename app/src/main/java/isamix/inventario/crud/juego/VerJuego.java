package isamix.inventario.crud.juego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isamix.inventario.R;
import isamix.inventario.db.DbJuego;
import isamix.inventario.modelo.Juego;

public class VerJuego extends AppCompatActivity {

    TextView nombre, marca, tipoJuego, numJugadores;
    Button btnGuardar, btnEditar, btnEliminar;
    Juego juego;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_juego);

        nombre = findViewById(R.id.gameName);
        marca = findViewById(R.id.gameBrand);
        tipoJuego = findViewById(R.id.gameType);
        numJugadores = findViewById(R.id.gamePlayers);

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

        final DbJuego dbJuego = new DbJuego(VerJuego.this);
        juego = dbJuego.verJuego(id);

        if (juego != null) {
            nombre.setText(juego.getNombre());
            marca.setText(juego.getMarca());
            tipoJuego.setText(juego.getTipoJuego());
            numJugadores.setText(juego.getNumJugadores());
            btnGuardar.setVisibility(View.GONE);
        }

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(VerJuego.this, EditarJuego.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        });

        btnEliminar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(VerJuego.this);
            builder.setTitle("ELIMINAR JUEGO");
            builder.setMessage("¿Desea eliminar este juego?");
            builder.setPositiveButton("SÍ", (dialogInterface, i) -> {
                if (dbJuego.eliminarJuego(id)) {
                    lista();
                }
            }).setNegativeButton("NO", (dialogInterface, i) -> {}).show();
        });
    }

    private void lista() {
        Intent intent = new Intent(this, ListaJuego.class);
        startActivity(intent);
    }
}
