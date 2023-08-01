package isamix.inventario.crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.adapter.LibroAdapter;
import isamix.inventario.db.DbLibro;
import isamix.inventario.modelo.Libro;

public class ListaLibroPorGenero extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView buscador;
    RecyclerView listaLibros;
    List<Libro> arrayLibros;
    LibroAdapter adapter;
    TextView title;

    Intent intent;
    Bundle extra;
    String genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_libro);

        intent = this.getIntent();
        extra = intent.getExtras();
        genre = extra.getString("GENERO");

        title = findViewById(R.id.title_category);
        title.setText(genre);

        buscador = findViewById(R.id.buscador);
        listaLibros = findViewById(R.id.listaLibros);
        listaLibros.setLayoutManager(new LinearLayoutManager(this));

        DbLibro dbLibro = new DbLibro(ListaLibroPorGenero.this);
        arrayLibros = dbLibro.mostrarLibrosPorGenero(genre);

        adapter = new LibroAdapter(arrayLibros);
        listaLibros.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        listaLibros.addItemDecoration(dividerItemDecoration);

        buscador.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtradoLibros(s);
        return false;
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
