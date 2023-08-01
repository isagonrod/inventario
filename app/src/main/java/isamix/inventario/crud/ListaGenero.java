package isamix.inventario.crud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.adapter.GeneroAdapter;
import isamix.inventario.db.DbGenero;
import isamix.inventario.modelo.Genero;

public class ListaGenero extends AppCompatActivity {

    RecyclerView listaGeneros;
    Button btn_newGenre, btn_newBook, btn_bookList;
    List<Genero> arrayListGeneros;
    GeneroAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_genero);

        btn_newGenre = findViewById(R.id.addNewGenre);
        btn_newBook = findViewById(R.id.addNewBook);
        btn_bookList = findViewById(R.id.getBookList);

        listaGeneros = findViewById(R.id.listaGeneros);
        listaGeneros.setLayoutManager(new GridLayoutManager(this, 2));

        DbGenero dbGenero = new DbGenero(this);
        arrayListGeneros = dbGenero.mostrarGeneros();
        adapter = new GeneroAdapter(arrayListGeneros);
        listaGeneros.setAdapter(adapter);

        btn_newGenre.setOnClickListener(v -> crearNuevoGenero());
        btn_newBook.setOnClickListener(v -> verLista(NuevoLibro.class));
        btn_bookList.setOnClickListener(v -> verLista(ListaLibro.class));
    }

    public void crearNuevoGenero() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NUEVO GÉNERO");

        final View customGeneroAlert = getLayoutInflater().inflate(R.layout.custom_nuevo_genero, null);
        builder.setView(customGeneroAlert);
        builder.setPositiveButton("CREAR", (dialogInterface, i) -> {
            DbGenero dbGenero = new DbGenero(this);
            EditText nombre = customGeneroAlert.findViewById(R.id.nombreNuevoGenero);
            Genero genre = dbGenero.getGeneroPorNombre(nombre.getText().toString());
            if (genre == null) {
                dbGenero.insertarGenero(nombre.getText().toString());
            } else {
                dbGenero.editarGenero(genre.getId(), genre.getNombre());
            }
            arrayListGeneros = dbGenero.mostrarGeneros();
            adapter = new GeneroAdapter(arrayListGeneros);
            listaGeneros.setAdapter(adapter);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
