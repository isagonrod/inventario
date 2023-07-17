package isamix.inventario;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.db.DbCategoria;
import isamix.inventario.entity.Categoria;

public class MainActivity extends AppCompatActivity {

    DbCategoria dbCategoria;
    List<Categoria> listaCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaCategorias = new ArrayList<>();
        listaCategorias = dbCategoria.mostrarCategorias();

        LinearLayout btnCategorias = new LinearLayout(getApplicationContext());
        btnCategorias.setLayoutParams(new LinearLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        btnCategorias.setOrientation(LinearLayout.HORIZONTAL);
        btnCategorias.setGravity(Gravity.CENTER);

        for (int i = 0; i < listaCategorias.size(); i++) {
            final LinearLayout buttonCateroria = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.lista_item_categoria, null);
            TextView btnTxt = (TextView) buttonCateroria.findViewById(R.id.txtCategoria);
            btnTxt.setText(listaCategorias.get(i).getNombre());
            buttonCateroria.setTag(i);

            int catId = i;
            buttonCateroria.setOnClickListener(view -> verListaCategoria(dbCategoria.getCategoriaPorId(catId)));
            buttonCateroria.addView(buttonCateroria);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(400, 1500, Gravity.CENTER);
        addContentView(btnCategorias, params);
    }

    public void crearNuevaCategoria(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("NUEVA CATEGORÍA");

        final View customCategoriaAlert = getLayoutInflater().inflate(R.layout.custom_categoria_alert, null);
        builder.setView(customCategoriaAlert);

        builder.setPositiveButton("CREAR", (dialogInterface, i) -> {
            EditText nombre = customCategoriaAlert.findViewById(R.id.nombreNuevaCategoria);
            dbCategoria.insertarCategoria(nombre.getText().toString());
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

    // Método para ir a la pantalla de la lista de la compra
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuListaCompra) {
            verListaCompra();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void verListaCompra() {
        Intent intent = new Intent(this, ListaCompraProductoActivity.class);
        startActivity(intent);
    }

    private void verListaCategoria(String categoria) {
        Intent intent = new Intent(this, ListaProductoActivity.class);
        intent.putExtra("categoria", categoria);
        startActivity(intent);
    }

}