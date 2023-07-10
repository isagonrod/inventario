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
                listaSeleccionada = ListaActivity.class;
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

    // Método para ir cambiando de pantalla al hacer click sobre las opciones
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuNuevoProducto:
                nuevoProducto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void nuevoProducto() {
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }

}