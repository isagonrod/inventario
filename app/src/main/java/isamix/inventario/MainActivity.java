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
import android.widget.Toast;

import java.util.List;

import isamix.inventario.adapter.ListaCategoriaAdapter;
import isamix.inventario.db.DbCategoria;
import isamix.inventario.db.DbProductos;
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

        final View customCategoriaAlert = getLayoutInflater().inflate(R.layout.custom_nueva_categoria_alert, null);
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

    public void eliminarCategoria() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("ELIMINAR CATEGORÍA");

        final View customCategoriaAlert = getLayoutInflater().inflate(R.layout.custom_eliminar_categoria_alert, null);
        builder.setView(customCategoriaAlert);
        builder.setPositiveButton("SÍ", (dialogInterface, i) -> {
            DbCategoria dbCategoria = new DbCategoria(MainActivity.this);
            DbProductos dbProductos = new DbProductos(MainActivity.this);
            EditText nombre = customCategoriaAlert.findViewById(R.id.nombreNuevaCategoria);
            Categoria category = dbCategoria.getCategoriaPorNombre(nombre.getText().toString());

            if (category != null) {
                if (dbProductos.mostrarProductosPorCategoria(nombre.getText().toString()).isEmpty()) {
                    dbCategoria.eliminarCategoria(nombre.getText().toString());
                } else {
                    Toast.makeText(this, "No se ha podido eliminar la categoría porque tiene productos asociados", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "No se ha podido eliminar la categoría porque no existe en la base de datos", Toast.LENGTH_LONG).show();
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
            case R.id.menuEliminarCategoria:
                eliminarCategoria();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void verLista(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}