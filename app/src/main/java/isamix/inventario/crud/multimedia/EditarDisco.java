package isamix.inventario.crud.multimedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.crud.FuncionamientoApp;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.juego.ListaTipoJuego;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.crud.producto.ListaCategoria;
import isamix.inventario.db.DbDiscoMusica;
import isamix.inventario.db.DbPersona;
import isamix.inventario.modelo.DiscoMusica;
import isamix.inventario.modelo.Persona;

public class EditarDisco extends AppCompatActivity {

    EditText titulo, fechaLanzamiento;
    AutoCompleteTextView artista;
    Button btnGuardar, btnEditar, btnEliminar;
    DbDiscoMusica dbDiscoMusica;
    DbPersona dbArtista;
    DiscoMusica discoMusica;
    List<Persona> artistas, grupos;
    int id = 0;
    boolean correcto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_disco);

        titulo = findViewById(R.id.etDiscTitle);
        artista = findViewById(R.id.etDiscArtist);
        fechaLanzamiento = findViewById(R.id.etDiscYear);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.fabEditar);
        btnEditar.setVisibility(View.GONE);
        btnEliminar = findViewById(R.id.fabEliminar);
        btnEliminar.setVisibility(View.GONE);

        dbDiscoMusica = new DbDiscoMusica(EditarDisco.this);
        dbArtista = new DbPersona(EditarDisco.this);
        artistas = dbArtista.mostrarPersonasPorProfesion("Artista");
        grupos = dbArtista.mostrarPersonasPorProfesion("Grupo/Banda");
        artistas.addAll(grupos);

        ArrayAdapter<Persona> artistaAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, artistas);
        artista.setAdapter(artistaAdapter);

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

        final DbDiscoMusica dbDiscosMusica = new DbDiscoMusica(EditarDisco.this);
        discoMusica = dbDiscosMusica.verDiscoMusica(id);

        if (discoMusica != null) {
            titulo.setText(discoMusica.getTitulo());
            artista.setText(discoMusica.getArtista_grupo());
            fechaLanzamiento.setText(String.valueOf(discoMusica.getFechaLanzamiento()));
        }

        btnGuardar.setOnClickListener(v -> {
            if (!titulo.getText().toString().isEmpty() && !artista.getText().toString().isEmpty()) {

                correcto = dbDiscosMusica.editarDiscoMusica(id,
                        titulo.getText().toString(),
                        artista.getText().toString(),
                        Integer.parseInt(fechaLanzamiento.getText().toString()));

                Persona artist = dbArtista.getPersona(artista.getText().toString(), "Artista");
                Persona group = dbArtista.getPersona(artista.getText().toString(), "Grupo/Banda");

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

                if (correcto) {
                    Toast.makeText(EditarDisco.this, "DISCO DE MÚSICA MODIFICADO", Toast.LENGTH_LONG).show();
                    recargarVista();
                } else {
                    Toast.makeText(EditarDisco.this, "ERROR AL MODIFICAR DISCO DE MÚSICA", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(EditarDisco.this, "DEBE RELLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recargarVista() {
        Intent intent = new Intent(this, VerDisco.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    /* *** *** *** MENÚ PRINCIPAL *** *** *** */

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuFuncionamiento:
                verLista(FuncionamientoApp.class);
                return true;
            case R.id.menuListaCompra:
                verLista(ListaCompra.class);
                return true;
            case R.id.menuGestionProductos:
                verLista(ListaCategoria.class);
                return true;
            case R.id.menuGestionLibros:
                verLista(ListaGenero.class);
                return true;
            case R.id.menuGestionJuegos:
                verLista(ListaTipoJuego.class);
                return true;
            case R.id.menuGestionMultimedia:
                verLista(ListaMultimedia.class);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void verLista(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
