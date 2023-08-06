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
import isamix.inventario.db.DbPelicula;
import isamix.inventario.db.DbPersona;
import isamix.inventario.modelo.Persona;

public class NuevaPelicula extends AppCompatActivity {

    EditText titulo, fechaEstreno, minDuracion;
    AutoCompleteTextView director;
    Button btnGuardar, btnEditar, btnEliminar;
    DbPelicula dbPelicula;
    DbPersona dbDirector;
    List<Persona> directores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_pelicula);

        titulo = findViewById(R.id.etFilmTitle);
        director = findViewById(R.id.etFilmDirector);
        fechaEstreno = findViewById(R.id.etFilmYear);
        minDuracion = findViewById(R.id.etFilmDuration);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.fabEditar);
        btnEditar.setVisibility(View.GONE);
        btnEliminar = findViewById(R.id.fabEliminar);
        btnEliminar.setVisibility(View.GONE);

        dbPelicula = new DbPelicula(NuevaPelicula.this);
        dbDirector = new DbPersona(NuevaPelicula.this);
        directores = dbDirector.mostrarPersonasPorProfesion("Director");

        ArrayAdapter<Persona> directorArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, directores);
        director.setAdapter(directorArrayAdapter);

        btnGuardar.setOnClickListener(v -> {
            if (!titulo.getText().toString().isEmpty() && !director.getText().toString().isEmpty()) {
                Persona filmDirector = dbDirector.getPersona(director.getText().toString(), "Director");

                if (filmDirector == null) {
                    dbDirector.insertarPersona(director.getText().toString(), "Director");
                } else {
                    dbDirector.editarPersona(filmDirector.getId(), filmDirector.getNombreCompleto(), filmDirector.getProfesion());
                }

                dbPelicula.insertarPelicula(
                        titulo.getText().toString(),
                        director.getText().toString(),
                        Integer.parseInt(fechaEstreno.getText().toString()),
                        Integer.parseInt(minDuracion.getText().toString()));

                Toast.makeText(NuevaPelicula.this, "PELÍCULA GUARDADA", Toast.LENGTH_LONG).show();
                limpiarFormulario();

            } else {
                Toast.makeText(NuevaPelicula.this, "DEBE RELLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiarFormulario() {
        titulo.setText("");
        director.setText("");
        fechaEstreno.setText("");
        minDuracion.setText("");
    }
}
