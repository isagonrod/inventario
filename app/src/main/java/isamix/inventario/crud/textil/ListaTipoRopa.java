package isamix.inventario.crud.textil;

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
import isamix.inventario.adapter.TipoRopaAdapter;
import isamix.inventario.FuncionamientoApp;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.juego.ListaTipoJuego;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.crud.multimedia.ListaMultimedia;
import isamix.inventario.crud.producto.ListaCategoria;
import isamix.inventario.db.DbTipoRopa;
import isamix.inventario.modelo.TipoRopa;

public class ListaTipoRopa extends AppCompatActivity {

    RecyclerView listaTipos;
    Button btnNewType, btnNewClothes, btnClothesList;
    List<TipoRopa> arrayListTipoRopa;
    TipoRopaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_tipo_ropa);

        btnNewType = findViewById(R.id.addNewTypeClothes);
        btnNewClothes = findViewById(R.id.addNewClothes);
        btnClothesList = findViewById(R.id.getClothesList);

        listaTipos = findViewById(R.id.listaTiposRopa);
        listaTipos.setLayoutManager(new GridLayoutManager(this, 2));

        DbTipoRopa dbTipoRopa = new DbTipoRopa(this);
        arrayListTipoRopa = dbTipoRopa.mostrarTiposRopa();
        adapter = new TipoRopaAdapter(arrayListTipoRopa);
        listaTipos.setAdapter(adapter);

        btnNewType.setOnClickListener(v -> crearNuevoTipoRopa());
        btnNewClothes.setOnClickListener(v -> verLista(NuevaRopa.class));
        btnClothesList.setOnClickListener(v -> verLista(ListaRopa.class));
    }

    public void crearNuevoTipoRopa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NUEVO TIPO DE ROPA");

        final View customTipoAlert = getLayoutInflater().inflate(R.layout.custom_nuevo_tipo_ropa, null);
        builder.setView(customTipoAlert);
        builder.setPositiveButton("CREAR", (dialogInterface, i) -> {
            DbTipoRopa dbTipoRopa = new DbTipoRopa(this);
            EditText tipo = customTipoAlert.findViewById(R.id.nuevoTipoJuego);
            TipoRopa type = dbTipoRopa.getTipoRopa(tipo.getText().toString());
            if (type == null) {
                dbTipoRopa.insertarTipoRopa(tipo.getText().toString());
            } else {
                dbTipoRopa.editarTipoRopa(type.getId(), type.getTipoRopa());
            }
            arrayListTipoRopa = dbTipoRopa.mostrarTiposRopa();
            adapter = new TipoRopaAdapter(arrayListTipoRopa);
            listaTipos.setAdapter(adapter);
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
