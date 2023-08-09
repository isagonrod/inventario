package isamix.inventario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import isamix.inventario.crud.FuncionamientoApp;
import isamix.inventario.crud.juego.ListaTipoJuego;
import isamix.inventario.crud.multimedia.ListaMultimedia;
import isamix.inventario.crud.producto.ListaCategoria;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.crud.textil.ListaTextil;

public class MainActivity extends AppCompatActivity {

    Button gestionProductos, gestionLibros, gestionJuegos, gestionMultimedia, gestionTextil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestionProductos = findViewById(R.id.gestionProductos);
        gestionProductos.setOnClickListener(v -> verLista(ListaCategoria.class));

        gestionLibros = findViewById(R.id.gestionLibros);
        gestionLibros.setOnClickListener(v -> verLista(ListaGenero.class));

        gestionJuegos = findViewById(R.id.gestionJuegos);
        gestionJuegos.setOnClickListener(v -> verLista(ListaTipoJuego.class));

        gestionMultimedia = findViewById(R.id.gestionMultimedia);
        gestionMultimedia.setOnClickListener(v -> verLista(ListaMultimedia.class));

        gestionTextil = findViewById(R.id.gestionTextil);
        gestionTextil.setOnClickListener(v -> verLista(ListaTextil.class));
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
            case R.id.menuFuncionamiento:
                verLista(FuncionamientoApp.class);
                return true;
            case R.id.menuListaCompra:
                verLista(ListaCompra.class);
                return true;
            case R.id.menuGestionProductos:
                verLista(ListaCategoria.class);
                return true;
            case R.id.menuGestionLibros:
                verLista(ListaGenero.class);
                return true;
            case R.id.menuGestionJuegos:
                verLista(ListaTipoJuego.class);
                return true;
            case R.id.menuGestionMultimedia:
                verLista(ListaMultimedia.class);
                return true;
            case R.id.menuGestionTextil:
                verLista(ListaTextil.class);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void verLista(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}
