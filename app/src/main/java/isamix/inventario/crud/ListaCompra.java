package isamix.inventario.crud;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import isamix.inventario.R;
import isamix.inventario.adapter.CompraAdapter;
import isamix.inventario.db.DbProducto;
import isamix.inventario.modelo.Producto;

public class ListaCompra extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView listaProductos;
    ArrayList<Producto> listaCompra;
    CompraAdapter adapter;
    SearchView txtCompra;
    Button btnTerminarCompra;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_compra);

        txtCompra = findViewById(R.id.txtCompra);
        btnTerminarCompra = findViewById(R.id.btnTerminarCompra);
        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        DbProducto dbProducto = new DbProducto(ListaCompra.this);
        listaCompra = dbProducto.mostrarProductosParaComprar();
        adapter = new CompraAdapter(listaCompra);
        listaProductos.setAdapter(adapter);

        txtCompra.setOnQueryTextListener(this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = -1;
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        btnTerminarCompra.setOnClickListener(v -> {
            for (int i = 0; i < listaProductos.getChildCount(); i++) {
                View listItem = listaProductos.getChildAt(i);
                int itemColor = listItem.getBackground() != null ?
                        ((ColorDrawable) listItem.getBackground()).getColor() : Color.WHITE;
                if (itemColor == Color.YELLOW) {
                    dbProducto.finCompra(this.listaCompra.get(i).getId(), this.listaCompra.get(i).getCantidad());
                    adapter.eliminarItem(i);
                    i--;
                    listaProductos.removeView(listItem);
                }
            }
        });
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