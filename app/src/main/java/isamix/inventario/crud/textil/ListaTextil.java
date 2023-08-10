package isamix.inventario.crud.textil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import isamix.inventario.R;
import isamix.inventario.FuncionamientoApp;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.juego.ListaTipoJuego;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.crud.multimedia.ListaMultimedia;
import isamix.inventario.crud.producto.ListaCategoria;

public class ListaTextil extends AppCompatActivity {

    Button armario, textilHogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_textil);

        armario = findViewById(R.id.btnArmario);
        armario.setOnClickListener(v -> verLista(ListaTipoRopa.class));

        textilHogar = findViewById(R.id.btnTextilHogar);
        textilHogar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ListaRopa.class);
            intent.putExtra("TIPO", "Textil Hogar");
            startActivity(intent);
        });
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
