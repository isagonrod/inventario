package isamix.inventario;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    Button btnTerminarCompra;
    Producto producto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra_producto);

        txtCompra = findViewById(R.id.txtCompra);
        btnTerminarCompra = findViewById(R.id.btnTerminarCompra);
        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        DbProductos dbProductos = new DbProductos(ListaCompraProductoActivity.this);
        listaCompra = dbProductos.mostrarProductosParaComprar();
        adapter = new ListaCompraAdapter(listaCompra);
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

        producto = dbProductos.verProducto(id);

        /*
         * TODO: No funciona el botón.
         *  Se supone que debería funcionar borrando los elementos marcados.
         *  Solo borra si se marca de uno en uno, pero si hay varios marcados, peta la app.
         */
        btnTerminarCompra.setOnClickListener(v -> {
            for (int i = 0; i < listaProductos.getChildCount(); i++) {
                View listItem = listaProductos.getChildAt(i);

                // Método para inicializar el "ID"
                if (producto != null) {
                    producto.setId(listItem.getId());
                    id = producto.getId();
                }

                int itemColor = listItem.getBackground() != null ? ((ColorDrawable) listItem.getBackground()).getColor() : Color.WHITE;
                if (itemColor == Color.YELLOW) {
                    /*
                     * TODO: Lo suyo sería que se pudiera poner una cantidad
                     *  y se sumara al total que ya hay
                     *  además de que se borrara de la lista de la compra
                     */
                    dbProductos.finCompra(id);
                    adapter.eliminarItem(i);
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