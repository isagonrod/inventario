package isamix.inventario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listar(View view) {
        int idSeleccionado = view.getId();
        Class listaSeleccionada = null;

        switch (idSeleccionado) {
            case R.id.listaProductos:
                listaSeleccionada = ListaProductoActivity.class;
                break;

            case R.id.listaTextil:
                break;

            case R.id.listaMuebles:
                break;
        }

        if (listaSeleccionada != null) {
            Intent nuevaActividad = new Intent(this, listaSeleccionada);
            startActivity(nuevaActividad);
        }
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

}