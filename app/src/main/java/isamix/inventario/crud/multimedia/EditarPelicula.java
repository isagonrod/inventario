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
import isamix.inventario.FuncionamientoApp;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.juego.ListaTipoJuego;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.crud.producto.ListaCategoria;
import isamix.inventario.crud.textil.ListaTextil;
import isamix.inventario.db.DbEstado;
import isamix.inventario.db.DbPelicula;
import isamix.inventario.db.DbPersona;
import isamix.inventario.modelo.Estado;
import isamix.inventario.modelo.Pelicula;
import isamix.inventario.modelo.Persona;

public class EditarPelicula extends AppCompatActivity {

    EditText titulo, fechaEstreno, minDuracion;
    AutoCompleteTextView director, estado;
    Button btnGuardar, btnEditar, btnEliminar;
    DbPelicula dbPelicula;
    DbPersona dbDirector;
    DbEstado dbEstado;
    Pelicula pelicula;
    List<Persona> directores;
    List<Estado> estados;
    int id = 0;
    boolean correcto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_pelicula);

        titulo = findViewById(R.id.etFilmTitle);
        director = findViewById(R.id.etFilmDirector);
        fechaEstreno = findViewById(R.id.etFilmYear);
        minDuracion = findViewById(R.id.etFilmDuration);
        estado = findViewById(R.id.etFilmState);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.fabEditar);
        btnEditar.setVisibility(View.GONE);
        btnEliminar = findViewById(R.id.fabEliminar);
        btnEliminar.setVisibility(View.GONE);

        dbPelicula = new DbPelicula(EditarPelicula.this);
        dbDirector = new DbPersona(EditarPelicula.this);
        dbEstado = new DbEstado(this);
        directores = dbDirector.mostrarPersonasPorProfesion("Director");
        estados = dbEstado.mostrarEstados();

        ArrayAdapter<Persona> directorArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, directores);
        director.setAdapter(directorArrayAdapter);

        ArrayAdapter<Estado> estadoAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, estados);
        estado.setAdapter(estadoAdapter);

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

        final DbPelicula dbPeliculas = new DbPelicula(EditarPelicula.this);
        pelicula = dbPeliculas.verPelicula(id);

        if (pelicula != null) {
            titulo.setText(pelicula.getTitulo());
            director.setText(pelicula.getDirector());
            fechaEstreno.setText(String.valueOf(pelicula.getFechaEstreno()));
            minDuracion.setText(String.valueOf(pelicula.getMinDuracion()));
            estado.setText(pelicula.getEstado());
        }

        btnGuardar.setOnClickListener(v -> {
            if (!titulo.getText().toString().isEmpty() && !director.getText().toString().isEmpty()) {
                correcto = dbPeliculas.editarPelicula(id,
                        titulo.getText().toString(),
                        director.getText().toString(),
                        Integer.parseInt(fechaEstreno.getText().toString()),
                        Integer.parseInt(minDuracion.getText().toString()),
                        estado.getText().toString());

                Persona filmDirector = dbDirector.getPersona(director.getText().toString(), "Director");
                Estado state = dbEstado.getEstado(estado.getText().toString());

                if (filmDirector == null) {
                    dbDirector.insertarPersona(director.getText().toString(), "Director");
                } else {
                    dbDirector.editarPersona(filmDirector.getId(), filmDirector.getNombreCompleto(), filmDirector.getProfesion());
                }

                if (state == null) {
                    dbEstado.insertarEstado(estado.getText().toString());
                } else {
                    dbEstado.editarEstado(state.getId(), state.getEstado());
                }

                if (correcto) {
                    Toast.makeText(EditarPelicula.this, "PELÍCULA MODIFICADA", Toast.LENGTH_LONG).show();
                    recargarVista();
                } else {
                    Toast.makeText(EditarPelicula.this, "ERROR AL MODIFICAR PELÍCULA", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(EditarPelicula.this, "DEBE RELLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recargarVista() {
        Intent intent = new Intent(this, VerPelicula.class);
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
            case R.id.menuGestionTextil:
                verLista(ListaTextil.class);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void verLista(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
