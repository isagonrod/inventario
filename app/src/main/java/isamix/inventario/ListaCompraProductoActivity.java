package isamix.inventario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;

import isamix.inventario.adapter.ListaCompraAdapter;
import isamix.inventario.adapter.ListaProductoAdapter;
import isamix.inventario.db.DbProductos;
import isamix.inventario.entity.Producto;

public class ListaCompraProductoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView listaProductos;
    ArrayList<Producto> listaCompra;
    ListaCompraAdapter adapter;
    SearchView txtCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra_producto);

        txtCompra = findViewById(R.id.txtCompra);
        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        DbProductos dbProductos = new DbProductos(ListaCompraProductoActivity.this);
        listaCompra = dbProductos.mostrarProductosParaComprar();
        adapter = new ListaCompraAdapter(listaCompra);
        listaProductos.setAdapter(adapter);

        txtCompra.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.listaCompraPorTienda(s);
        return false;
    }
}