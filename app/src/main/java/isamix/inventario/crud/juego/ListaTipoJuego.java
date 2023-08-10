package isamix.inventario.crud.juego;

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
import isamix.inventario.adapter.TipoJuegoAdapter;
import isamix.inventario.FuncionamientoApp;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.crud.multimedia.ListaMultimedia;
import isamix.inventario.crud.producto.ListaCategoria;
import isamix.inventario.crud.textil.ListaTextil;
import isamix.inventario.db.DbTipoJuego;
import isamix.inventario.modelo.TipoJuego;

public class ListaTipoJuego extends AppCompatActivity {

    RecyclerView listaTipos;
    Button btn_newType, btn_newGame, btn_gameList;
    List<TipoJuego> arrayListTipos;
    TipoJuegoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_tipo_juego);

        btn_newType = findViewById(R.id.addNewTypeGame);
        btn_newGame = findViewById(R.id.addNewGame);
        btn_gameList = findViewById(R.id.getGameList);

        listaTipos = findViewById(R.id.listaTiposJuego);
        listaTipos.setLayoutManager(new GridLayoutManager(this, 2));

        DbTipoJuego dbTipoJuego = new DbTipoJuego(this);
        arrayListTipos = dbTipoJuego.mostrarTiposJuego();
        adapter = new TipoJuegoAdapter(arrayListTipos);
        listaTipos.setAdapter(adapter);

        btn_newType.setOnClickListener(v -> crearNuevoTipoJuego());
        btn_newGame.setOnClickListener(v -> verLista(NuevoJuego.class));
        btn_gameList.setOnClickListener(v -> verLista(ListaJuego.class));
    }

    public void crearNuevoTipoJuego() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NUEVO TIPO DE JUEGO");

        final View customTipoAlert = getLayoutInflater().inflate(R.layout.custom_nuevo_tipo_juego, null);
        builder.setView(customTipoAlert);
        builder.setPositiveButton("CREAR", (dialogInterface, i) -> {
            DbTipoJuego dbTipoJuego = new DbTipoJuego(this);
            EditText tipo = customTipoAlert.findViewById(R.id.nuevoTipoJuego);
            TipoJuego type = dbTipoJuego.getTipoJuego(tipo.getText().toString());
            if (type == null) {
                dbTipoJuego.insertarTipoJuego(tipo.getText().toString());
            } else {
                dbTipoJuego.editarTipoJuego(type.getId(), type.getTipo());
            }
            arrayListTipos = dbTipoJuego.mostrarTiposJuego();
            adapter = new TipoJuegoAdapter(arrayListTipos);
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
