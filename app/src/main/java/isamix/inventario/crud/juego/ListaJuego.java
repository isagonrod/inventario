package isamix.inventario.crud.juego;

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

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.adapter.JuegoAdapter;
import isamix.inventario.crud.FuncionamientoApp;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.crud.multimedia.ListaMultimedia;
import isamix.inventario.crud.producto.ListaCategoria;
import isamix.inventario.crud.textil.ListaTextil;
import isamix.inventario.db.DbJuego;
import isamix.inventario.modelo.Juego;

public class ListaJuego extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView buscador;
    RecyclerView listaJuegos;
    List<Juego> juegosArrayList;
    JuegoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_juego);

        buscador = findViewById(R.id.buscadorJuego);
        listaJuegos = findViewById(R.id.listaJuegos);
        listaJuegos.setLayoutManager(new LinearLayoutManager(this));

        DbJuego dbJuego = new DbJuego(ListaJuego.this);
        juegosArrayList = dbJuego.mostrarJuegos();
        adapter = new JuegoAdapter(juegosArrayList);
        listaJuegos.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        listaJuegos.addItemDecoration(dividerItemDecoration);

        buscador.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtradoJuegos(s);
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
