package isamix.inventario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

import isamix.inventario.adapter.ListaProductoAdapter;
import isamix.inventario.db.DbCategoria;
import isamix.inventario.db.DbProductos;
import isamix.inventario.entity.Categoria;
import isamix.inventario.entity.Producto;

public class ListaProductoPorCategoriasActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaProductos;
    List<Producto> arrayProductos;
    ListaProductoAdapter adapter;
    Button addProduct, addListProduct, deleteProduct;
    TextView title;

    Intent intent;
    Bundle extra;
    String category;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);

        intent = this.getIntent();
        extra = intent.getExtras();
        category = extra.getString("CATEGORIA");

        title = findViewById(R.id.title_category);
        title.setText(category);

        txtBuscar = findViewById(R.id.txtBuscar);
        addProduct = findViewById(R.id.fabNuevo);
        addListProduct = findViewById(R.id.fabListaCompra);
        deleteProduct = findViewById(R.id.fabEliminar);
        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        DbProductos dbProductos = new DbProductos(ListaProductoPorCategoriasActivity.this);
        arrayProductos = dbProductos.mostrarProductosPorCategoria(category);

        adapter = new ListaProductoAdapter(arrayProductos);
        listaProductos.setAdapter(adapter);

        // Pinta la línea divisoria entre elementos de la lista
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        listaProductos.addItemDecoration(dividerItemDecoration);

        txtBuscar.setOnQueryTextListener(this);

        addProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ListaProductoPorCategoriasActivity.this, NuevoProductoActivity.class);
            startActivity(intent);
        });

        deleteProduct.setOnClickListener(v -> {
            for (int i = 0; i < listaProductos.getChildCount(); i++) {
                View listItem = listaProductos.getChildAt(i);
                int itemColor = listItem.getBackground() != null ?
                        ((ColorDrawable) listItem.getBackground()).getColor() : 0xFFFFFFFF;
                if (itemColor == Color.CYAN) {
                    dbProductos.eliminarProducto(this.arrayProductos.get(i).getId());
                    adapter.eliminarItem(i);
                    listaProductos.removeView(listItem);
                }
            }
        });

        addListProduct.setOnClickListener(v -> {
            for (int i = 0; i < listaProductos.getChildCount(); i++) {
                View listItem = listaProductos.getChildAt(i);
                int itemColor = listItem.getBackground() != null ?
                        ((ColorDrawable) listItem.getBackground()).getColor() : 0xFFFFFFFF;
                if (itemColor == Color.CYAN) {
                    arrayProductos.get(i).setParaComprar(0);
                    dbProductos.editarProducto(
                            this.arrayProductos.get(i).getId(),
                            this.arrayProductos.get(i).getNombre(),
                            this.arrayProductos.get(i).getCantidad(),
                            this.arrayProductos.get(i).getPrecio(),
                            this.arrayProductos.get(i).getTienda(),
                            this.arrayProductos.get(i).getCategoria(),
                            1);
                    listItem.setBackgroundColor(Color.WHITE);
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

    /* *** *** *** MENÚ PRINCIPAL *** *** *** */

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuListaCompra:
                verLista(ListaCompraProductoActivity.class);
                return true;
            case R.id.menuListaProductos:
                verLista(ListaProductoActivity.class);
                return true;
            case R.id.menuNuevaCategoria:
                crearNuevaCategoria();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void verLista(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void crearNuevaCategoria() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListaProductoPorCategoriasActivity.this);
        builder.setTitle("NUEVA CATEGORÍA");

        final View customCategoriaAlert = getLayoutInflater().inflate(R.layout.custom_categoria_alert, null);
        builder.setView(customCategoriaAlert);
        builder.setPositiveButton("CREAR", (dialogInterface, i) -> {
            DbCategoria dbCategoria = new DbCategoria(ListaProductoPorCategoriasActivity.this);
            EditText nombre = customCategoriaAlert.findViewById(R.id.nombreNuevaCategoria);

            Categoria category = dbCategoria.getCategoriaPorNombre(nombre.getText().toString());
            if (category == null) {
                dbCategoria.insertarCategoria(nombre.getText().toString());
            } else {
                dbCategoria.editarCategoria(category.getId(), category.getNombre());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}