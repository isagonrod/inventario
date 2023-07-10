package isamix.inventario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import java.util.ArrayList;

import isamix.inventario.adapter.ListaProductosAdapter;
import isamix.inventario.db.DbProductos;
import isamix.inventario.entity.Producto;

public class ListaActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaProductos;
    ArrayList<Producto> listaArrayProductos;
    ListaProductosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        txtBuscar = findViewById(R.id.txtBuscar);
        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        DbProductos dbProductos = new DbProductos(ListaActivity.this);

        listaArrayProductos = new ArrayList<>();

        adapter = new ListaProductosAdapter(dbProductos.mostrarProductos());
        listaProductos.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        listaProductos.addItemDecoration(dividerItemDecoration);

        txtBuscar.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
}