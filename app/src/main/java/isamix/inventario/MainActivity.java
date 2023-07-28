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
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import isamix.inventario.adapter.CategoriaAdapter;
import isamix.inventario.crud.ListaCategoria;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.ListaProducto;
import isamix.inventario.db.DbCategoria;
import isamix.inventario.modelo.Categoria;

public class MainActivity extends AppCompatActivity {

    Button gestionCompra, gestionProductos, gestionLibros, gestionJuegos, gestionMultimedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestionCompra = findViewById(R.id.gestionCompra);
        gestionCompra.setOnClickListener(v -> verLista(ListaCompra.class));

        gestionProductos = findViewById(R.id.gestionProductos);
        gestionProductos.setOnClickListener(v -> verLista(ListaCategoria.class));

        gestionLibros = findViewById(R.id.gestionLibros);
        gestionJuegos = findViewById(R.id.gestionJuegos);
        gestionMultimedia = findViewById(R.id.gestionMultimedia);
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
