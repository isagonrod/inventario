package isamix.inventario.crud;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import isamix.inventario.R;
import isamix.inventario.adapter.ProductoAdapter;
import isamix.inventario.db.DbCategoria;
import isamix.inventario.db.DbProducto;
import isamix.inventario.modelo.Categoria;
import isamix.inventario.modelo.Producto;

public class ListaProductoPorCategoria extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaProductos;
    List<Producto> arrayProductos;
    ProductoAdapter adapter;
    Button btnAddProduct;
    TextView title;

    Intent intent;
    Bundle extra;
    String category;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_producto);

        intent = this.getIntent();
        extra = intent.getExtras();
        category = extra.getString("CATEGORIA");

        title = findViewById(R.id.title_category);
        title.setText(category);

        txtBuscar = findViewById(R.id.txtBuscar);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddProduct.setVisibility(View.GONE);
        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        DbProducto dbProducto = new DbProducto(ListaProductoPorCategoria.this);
        arrayProductos = dbProducto.mostrarProductosPorCategoria(category);

        adapter = new ProductoAdapter(arrayProductos);
        listaProductos.setAdapter(adapter);

        // Pinta la línea divisoria entre elementos de la lista
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

    /* *** *** *** MENÚ PRINCIPAL *** *** *** */

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuListaCompra:
                verLista(ListaCompra.class);
                return true;
            case R.id.menuGestionProductos:
                verLista(ListaProducto.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ListaProductoPorCategoria.this);
        builder.setTitle("NUEVA CATEGORÍA");

        final View customCategoriaAlert = getLayoutInflater().inflate(R.layout.custom_nueva_categoria, null);
        builder.setView(customCategoriaAlert);
        builder.setPositiveButton("CREAR", (dialogInterface, i) -> {
            DbCategoria dbCategoria = new DbCategoria(ListaProductoPorCategoria.this);
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