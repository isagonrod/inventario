package isamix.inventario.crud.multimedia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.db.DbDiscoMusica;
import isamix.inventario.db.DbEstado;
import isamix.inventario.db.DbPersona;
import isamix.inventario.modelo.Estado;
import isamix.inventario.modelo.Persona;

public class NuevoDisco extends AppCompatActivity {

    EditText titulo, fechaLanzamiento;
    AutoCompleteTextView artista, estado;
    Button btnGuardar, btnEditar, btnEliminar;
    DbDiscoMusica dbDiscoMusica;
    DbPersona dbArtista;
    DbEstado dbEstado;
    List<Persona> artistas, grupos;
    List<Estado> estados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_disco);

        titulo = findViewById(R.id.etDiscTitle);
        artista = findViewById(R.id.etDiscArtist);
        fechaLanzamiento = findViewById(R.id.etDiscYear);
        estado = findViewById(R.id.etDiscState);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.fabEditar);
        btnEditar.setVisibility(View.GONE);
        btnEliminar = findViewById(R.id.fabEliminar);
        btnEliminar.setVisibility(View.GONE);

        dbDiscoMusica = new DbDiscoMusica(NuevoDisco.this);
        dbArtista = new DbPersona(NuevoDisco.this);
        dbEstado = new DbEstado(this);
        artistas = dbArtista.mostrarPersonasPorProfesion("Artista");
        grupos = dbArtista.mostrarPersonasPorProfesion("Grupo/Banda");
        artistas.addAll(grupos);
        estados = dbEstado.mostrarEstados();

        ArrayAdapter<Persona> artistaAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, artistas);
        artista.setAdapter(artistaAdapter);

       ArrayAdapter<Estado> estadoAdapter = new ArrayAdapter<>(getApplicationContext(),
               android.support.design.R.layout.support_simple_spinner_dropdown_item, estados);
       estado.setAdapter(estadoAdapter);

        btnGuardar.setOnClickListener(v -> {
            if (!titulo.getText().toString().isEmpty() && !artista.getText().toString().isEmpty()) {
                Persona artist = dbArtista.getPersona(artista.getText().toString(), "Artista");
                Persona group = dbArtista.getPersona(artista.getText().toString(), "Grupo/Banda");
                Estado state = dbEstado.getEstado(estado.getText().toString());

                if (artist == null) {
                    dbArtista.insertarPersona(artista.getText().toString(), "Artista");
                } else {
                    dbArtista.editarPersona(artist.getId(), artist.getNombreCompleto(), artist.getProfesion());
                }

                if (group == null) {
                    dbArtista.insertarPersona(artista.getText().toString(), "Grupo/Banda");
                } else {
                    dbArtista.editarPersona(group.getId(), group.getNombreCompleto(), group.getProfesion());
                }

                if (state == null) {
                    dbEstado.insertarEstado(estado.getText().toString());
                } else {
                    dbEstado.editarEstado(state.getId(), state.getEstado());
                }

                dbDiscoMusica.insertarDiscoMusica(
                        titulo.getText().toString(),
                        artista.getText().toString(),
                        Integer.parseInt(fechaLanzamiento.getText().toString()),
                        estado.getText().toString());

                Toast.makeText(NuevoDisco.this, "DISCO DE MÚSICA GUARDADO", Toast.LENGTH_LONG).show();
                limpiarFormulario();

            } else {
                Toast.makeText(NuevoDisco.this, "DEBE RELLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiarFormulario() {
        titulo.setText("");
        artista.setText("");
        fechaLanzamiento.setText("");
        estado.setText("");
    }
}
