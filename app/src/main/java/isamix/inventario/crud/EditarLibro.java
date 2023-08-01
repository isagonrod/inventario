package isamix.inventario.crud;

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
import isamix.inventario.db.DbGenero;
import isamix.inventario.db.DbLibro;
import isamix.inventario.db.DbMarca;
import isamix.inventario.db.DbPersona;
import isamix.inventario.modelo.Genero;
import isamix.inventario.modelo.Libro;
import isamix.inventario.modelo.Marca;
import isamix.inventario.modelo.Persona;

public class EditarLibro extends AppCompatActivity {

    EditText titulo, isbn, lugarImpresion, fechaImpresion;
    AutoCompleteTextView autor, editorial, genero;
    Button btnGuardar, btnEditar, btnEliminar;
    DbLibro dbLibro;
    DbPersona dbAutor;
    DbMarca dbEditorial;
    DbGenero dbGenero;
    Libro libro;
    List<Persona> autores;
    List<Marca> editoriales;
    List<Genero> generos;
    int id = 0;
    boolean corr = false;

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

        dbLibro = new DbLibro(EditarLibro.this);
        dbAutor = new DbPersona(EditarLibro.this);
        dbEditorial = new DbMarca(EditarLibro.this);
        dbGenero = new DbGenero(EditarLibro.this);
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

        final DbLibro dbLibros = new DbLibro(EditarLibro.this);
        libro = dbLibros.verLibro(id);

        if (libro != null) {
            titulo.setText(libro.getTitulo());
            autor.setText(libro.getAutor());
            editorial.setText(libro.getEditorial());
            isbn.setText(libro.getIsbn());
            genero.setText(libro.getGenero());
            lugarImpresion.setText(libro.getLugarImpresion());
            fechaImpresion.setText(libro.getFechaImpresion());
        }

        btnGuardar.setOnClickListener(v -> {
            if (!titulo.getText().toString().isEmpty()
                    && !autor.getText().toString().isEmpty()
                    && !editorial.getText().toString().isEmpty()
                    && !genero.getText().toString().isEmpty()) {

                corr = dbLibros.editarLibro(id,
                        titulo.getText().toString(),
                        autor.getText().toString(),
                        editorial.getText().toString(),
                        isbn.getText().toString(),
                        Integer.parseInt(genero.getText().toString()),
                        lugarImpresion.getText().toString(),
                        Integer.parseInt(fechaImpresion.getText().toString()));

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

                if (corr) {
                    Toast.makeText(EditarLibro.this, "LIBRO MODIFICADO", Toast.LENGTH_LONG).show();
                    recargarVista();
                } else {
                    Toast.makeText(EditarLibro.this, "ERROR AL MODIFICAR LIBRO", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(EditarLibro.this, "DEBE RELLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recargarVista() {
        Intent intent = new Intent(this, VerLibro.class);
        intent.putExtra("ID", id);
        intent.putExtra("GENERO", genero.getText().toString());
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
//            case R.id.menuGestionJuegos:
//                return true;
//            case R.id.menuGestionMultimedia:
//                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void verLista(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
