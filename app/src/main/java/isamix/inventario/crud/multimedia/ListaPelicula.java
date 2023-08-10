package isamix.inventario.crud.multimedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.adapter.PeliculaAdapter;
import isamix.inventario.FuncionamientoApp;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.juego.ListaTipoJuego;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.crud.producto.ListaCategoria;
import isamix.inventario.crud.textil.ListaTextil;
import isamix.inventario.db.DbPelicula;
import isamix.inventario.modelo.Pelicula;

public class ListaPelicula extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView buscador;
    RecyclerView listaPeliculas;
    Button btn_newFilm;
    List<Pelicula> arrayListPeliculas;
    PeliculaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_pelicula);

        buscador = findViewById(R.id.buscadorPelicula);
        listaPeliculas = findViewById(R.id.listaPeliculas);
        listaPeliculas.setLayoutManager(new LinearLayoutManager(this));

        DbPelicula dbPelicula = new DbPelicula(ListaPelicula.this);
        arrayListPeliculas = dbPelicula.mostrarPeliculas();
        adapter = new PeliculaAdapter(arrayListPeliculas);
        listaPeliculas.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                new LinearLayoutManager(this).getOrientation());
        listaPeliculas.addItemDecoration(dividerItemDecoration);

        buscador.setOnQueryTextListener(this);

        btn_newFilm = findViewById(R.id.btnAddNewFilm);
        btn_newFilm.setOnClickListener(v -> verLista(NuevaPelicula.class));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrarPeliculas(s);
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
