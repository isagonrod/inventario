package isamix.inventario.crud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.adapter.CategoriaAdapter;
import isamix.inventario.db.DbCategoria;
import isamix.inventario.modelo.Categoria;

public class ListaCategoria extends AppCompatActivity {

    RecyclerView listaCategorias;
    Button btn_newCategory, btn_newProduct, btn_productList;
    List<Categoria> arrayListCategorias;
    CategoriaAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_categoria);

        btn_newCategory = findViewById(R.id.addNewCategory);
        btn_newProduct = findViewById(R.id.addNewProduct);
        btn_productList = findViewById(R.id.getProductList);

        listaCategorias = findViewById(R.id.listaCategorias);
        listaCategorias.setLayoutManager(new GridLayoutManager(this, 2));

        DbCategoria dbCategoria = new DbCategoria(this);
        arrayListCategorias = dbCategoria.mostrarCategorias();
        adapter = new CategoriaAdapter(arrayListCategorias);
        listaCategorias.setAdapter(adapter);

        btn_newCategory.setOnClickListener(v -> crearNuevaCategoria());
        btn_newProduct.setOnClickListener(v -> verLista(NuevoProducto.class));
        btn_productList.setOnClickListener(v -> verLista(ListaProducto.class));
    }

    public void crearNuevaCategoria() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NUEVA CATEGORÍA");

        final View customCategoriaAlert = getLayoutInflater().inflate(R.layout.custom_nueva_categoria, null);
        builder.setView(customCategoriaAlert);
        builder.setPositiveButton("CREAR", (dialogInterface, i) -> {
            DbCategoria dbCategoria = new DbCategoria(this);
            EditText nombre = customCategoriaAlert.findViewById(R.id.nombreNuevaCategoria);

            Categoria category = dbCategoria.getCategoriaPorNombre(nombre.getText().toString());
            if (category == null) {
                dbCategoria.insertarCategoria(nombre.getText().toString());
            } else {
                dbCategoria.editarCategoria(category.getId(), category.getNombre());
            }

            arrayListCategorias = dbCategoria.mostrarCategorias();
            adapter = new CategoriaAdapter(arrayListCategorias);
            listaCategorias.setAdapter(adapter);
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
//            case R.id.menuGestionLibros:
//                return true;
//            case R.id.menuGestionJuegos:
//                return true;
//            case R.id.menuGestionMultimedia:
//                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void verLista(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
