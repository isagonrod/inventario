package isamix.inventario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;

import isamix.inventario.adapter.ListaProductoAdapter;
import isamix.inventario.db.DbProductos;
import isamix.inventario.entity.Producto;

public class ListaProductoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaProductos;
    ArrayList<Producto> listaArrayProductos;
    ArrayList<Producto> listaCompraProductos;
    ListaProductoAdapter adapter;
    Button addProduct, addListProduct, deleteProduct;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);

        txtBuscar = findViewById(R.id.txtBuscar);
        addProduct = findViewById(R.id.fabNuevo);
        addListProduct = findViewById(R.id.fabListaCompra);
        deleteProduct = findViewById(R.id.fabEliminar);
        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        DbProductos dbProductos = new DbProductos(ListaProductoActivity.this);

        listaArrayProductos = dbProductos.mostrarProductos();

        adapter = new ListaProductoAdapter(listaArrayProductos);
        listaProductos.setAdapter(adapter);

        // Pinta la línea divisoria entre elementos de la lista
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        listaProductos.addItemDecoration(dividerItemDecoration);

        txtBuscar.setOnQueryTextListener(this);

        addProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ListaProductoActivity.this, NuevoProductoActivity.class);
            startActivity(intent);
        });

        deleteProduct.setOnClickListener(v -> {
            for (int i = 0; i < listaProductos.getChildCount(); i++) {
                View listItem = listaProductos.getChildAt(i);
                int itemColor = listItem.getBackground() != null ?
                        ((ColorDrawable) listItem.getBackground()).getColor() : 0xFFFFFFFF;
                if (itemColor == Color.CYAN) {
                    // TODO: Peta y solo borra uno (si se seleccionan varios), si solo se selecciona uno, funciona perfectamente
                    dbProductos.eliminarProducto(this.listaArrayProductos.get(i).getId());
                    adapter.eliminarItem(i);
                }
            }
        });

        addListProduct.setOnClickListener(v -> {
            for (int i = 0; i < listaProductos.getChildCount(); i++) {
                View listItem = listaProductos.getChildAt(i);
                int itemColor = listItem.getBackground() != null ?
                        ((ColorDrawable) listItem.getBackground()).getColor() : 0xFFFFFFFF;
                if (itemColor == Color.CYAN) {
                    listaArrayProductos.get(i).setParaComprar(49);
                    listItem.setBackgroundColor(Color.WHITE);
                    dbProductos.editarProducto(
                            this.listaArrayProductos.get(i).getId(),
                            this.listaArrayProductos.get(i).getNombre(),
                            this.listaArrayProductos.get(i).getCantidad(),
                            this.listaArrayProductos.get(i).getPrecio(),
                            this.listaArrayProductos.get(i).getTienda(),
                            this.listaArrayProductos.get(i).isParaComprar());
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
        adapter.filtrado(s);
        return false;
    }
}