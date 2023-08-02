package isamix.inventario.crud;

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
import isamix.inventario.db.DbGenero;
import isamix.inventario.db.DbLibro;
import isamix.inventario.db.DbMarca;
import isamix.inventario.db.DbPersona;
import isamix.inventario.modelo.Genero;
import isamix.inventario.modelo.Marca;
import isamix.inventario.modelo.Persona;

public class NuevoLibro extends AppCompatActivity {

    EditText titulo, isbn, lugarImpresion, fechaImpresion;
    AutoCompleteTextView autor, editorial, genero;
    Button btnGuardar, btnEditar, btnEliminar;
    DbLibro dbLibro;
    DbPersona dbAutor;
    DbMarca dbEditorial;
    DbGenero dbGenero;
    List<Persona> autores;
    List<Marca> editoriales;
    List<Genero> generos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_libro);

        titulo = findViewById(R.id.titulo);
        autor = findViewById(R.id.autor);
        editorial = findViewById(R.id.editorial);
        isbn = findViewById(R.id.isbn);
        genero = findViewById(R.id.genero);
        lugarImpresion = findViewById(R.id.lugarImpresion);
        fechaImpresion = findViewById(R.id.fechaImpresion);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.fabEditar);
        btnEditar.setVisibility(View.GONE);
        btnEliminar = findViewById(R.id.fabEliminar);
        btnEliminar.setVisibility(View.GONE);

        dbLibro = new DbLibro(NuevoLibro.this);
        dbAutor = new DbPersona(NuevoLibro.this);
        dbEditorial = new DbMarca(NuevoLibro.this);
        dbGenero = new DbGenero(NuevoLibro.this);
        autores = dbAutor.mostrarPersonasPorProfesion("Autor");
        editoriales = dbEditorial.mostrarMarcas();
        generos = dbGenero.mostrarGeneros();

        ArrayAdapter<Persona> autorArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, autores);
        autor.setAdapter(autorArrayAdapter);

        ArrayAdapter<Marca> marcaArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, editoriales);
        editorial.setAdapter(marcaArrayAdapter);

        ArrayAdapter<Genero> generoArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, generos);
        genero.setAdapter(generoArrayAdapter);

        btnGuardar.setOnClickListener(v -> {
            if (!titulo.getText().toString().isEmpty()
                    && !autor.getText().toString().isEmpty()
                    && !editorial.getText().toString().isEmpty()
                    && !genero.getText().toString().isEmpty()) {

                Persona author = dbAutor.getPersona(autor.getText().toString(), "Autor");
                Marca edit = dbEditorial.getMarca(editorial.getText().toString());
                Genero genre = dbGenero.getGeneroPorNombre(genero.getText().toString());

                if (author == null) {
                    dbAutor.insertarPersona(autor.getText().toString(), "Autor");
                } else {
                    dbAutor.editarPersona(author.getId(), author.getNombreCompleto(), author.getProfesion());
                }

                if (edit == null) {
                    dbEditorial.insertarMarca(editorial.getText().toString());
                } else {
                    dbEditorial.editarMarca(edit.getId(), edit.getNombre());
                }

                if (genre == null) {
                    dbGenero.insertarGenero(genero.getText().toString());
                } else {
                    dbGenero.editarGenero(genre.getId(), genre.getNombre());
                }

                dbLibro.insertarLibro(
                        titulo.getText().toString(),
                        autor.getText().toString(),
                        editorial.getText().toString(),
                        genero.getText().toString(),
                        isbn.getText().toString(),
                        lugarImpresion.getText().toString(),
                        fechaImpresion.getText().toString());

                Toast.makeText(NuevoLibro.this, "LIBRO GUARDADO", Toast.LENGTH_LONG).show();
                limpiarFormulario();
            } else {
                Toast.makeText(NuevoLibro.this, "ERROR AL GUARDAR LIBRO", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiarFormulario() {
        titulo.setText("");
        autor.setText("");
        editorial.setText("");
        genero.setText("");
        isbn.setText("");
        lugarImpresion.setText("");
        fechaImpresion.setText("");
    }
}
