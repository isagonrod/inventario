package isamix.inventario.crud.textil;

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
import android.widget.TextView;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.adapter.RopaAdapter;
import isamix.inventario.FuncionamientoApp;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.juego.ListaTipoJuego;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.crud.multimedia.ListaMultimedia;
import isamix.inventario.crud.producto.ListaCategoria;
import isamix.inventario.db.DbRopa;
import isamix.inventario.modelo.Ropa;

public class ListaRopa extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView buscador;
    RecyclerView listaRopa;
    Button btnNewClothes;
    List<Ropa> arrayListRopa;
    RopaAdapter adapter;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_ropa);

        buscador = findViewById(R.id.buscadorTextil);
        listaRopa = findViewById(R.id.listaRopa);
        listaRopa.setLayoutManager(new LinearLayoutManager(this));

        title = findViewById(R.id.title_category);

        DbRopa dbRopa = new DbRopa(ListaRopa.this);

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                arrayListRopa = dbRopa.mostrarRopas();
            } else {
                extra.getInt("TIPO");
                title.setText(R.string.textil_hogar);
                arrayListRopa = dbRopa.mostrarRopasTextilHogar();
            }
        }

        adapter = new RopaAdapter(arrayListRopa);
        listaRopa.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                new LinearLayoutManager(this).getOrientation());
        listaRopa.addItemDecoration(dividerItemDecoration);

        buscador.setOnQueryTextListener(this);

        btnNewClothes = findViewById(R.id.btnAddNewTextil);
        btnNewClothes.setOnClickListener(v -> verLista(NuevaRopa.class));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtradoRopa(s);
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
