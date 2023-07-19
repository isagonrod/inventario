package isamix.inventario;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import isamix.inventario.adapter.ListaCategoriaAdapter;
import isamix.inventario.db.DbCategoria;
import isamix.inventario.entity.Categoria;

public class MainActivity extends AppCompatActivity {
    RecyclerView listaCategorias;
    List<Categoria> arrayListCategorias;
    ListaCategoriaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaCategorias = findViewById(R.id.listaCategorias);
        listaCategorias.setLayoutManager(new GridLayoutManager(this, 2));

        DbCategoria dbCategoria = new DbCategoria(MainActivity.this);
        arrayListCategorias = dbCategoria.mostrarCategorias();
        adapter = new ListaCategoriaAdapter(arrayListCategorias);
        listaCategorias.setAdapter(adapter);
    }

    public void crearNuevaCategoria() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("NUEVA CATEGORÍA");

        final View customCategoriaAlert = getLayoutInflater().inflate(R.layout.custom_categoria_alert, null);
        builder.setView(customCategoriaAlert);
        builder.setPositiveButton("CREAR", (dialogInterface, i) -> {
            DbCategoria dbCategoria = new DbCategoria(MainActivity.this);
            EditText nombre = customCategoriaAlert.findViewById(R.id.nombreNuevaCategoria);

            Categoria category = dbCategoria.getCategoriaPorNombre(nombre.getText().toString());
            if (category == null) {
                dbCategoria.insertarCategoria(nombre.getText().toString());
            } else {
                dbCategoria.editarCategoria(category.getId(), category.getNombre());
            }

            arrayListCategorias = dbCategoria.mostrarCategorias();
            adapter = new ListaCategoriaAdapter(arrayListCategorias);
            listaCategorias.setAdapter(adapter);
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Método para mostrar el ménu principal
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    // Método para cambiar de actividad según se seleccione una opción u otra en el menú
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuListaCompra:
                verLista(ListaCompraProductoActivity.class);
                return true;
            case R.id.menuGestionProductos:
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

}