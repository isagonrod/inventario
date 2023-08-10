package isamix.inventario.crud.juego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.FuncionamientoApp;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.crud.multimedia.ListaMultimedia;
import isamix.inventario.crud.producto.ListaCategoria;
import isamix.inventario.crud.textil.ListaTextil;
import isamix.inventario.db.DbEstado;
import isamix.inventario.db.DbJuego;
import isamix.inventario.db.DbMarca;
import isamix.inventario.db.DbTipoJuego;
import isamix.inventario.modelo.Estado;
import isamix.inventario.modelo.Juego;
import isamix.inventario.modelo.Marca;
import isamix.inventario.modelo.TipoJuego;

public class EditarJuego extends AppCompatActivity {

    EditText nombre, numJugadores;
    AutoCompleteTextView marca, tipoJuego, estado;
    Button btnGuardar, btnEditar, btnEliminar;
    DbJuego dbJuego;
    DbTipoJuego dbTipoJuego;
    DbMarca dbMarca;
    DbEstado dbEstado;
    Juego juego;
    List<Juego> juegos;
    List<TipoJuego> tiposJuego;
    List<Marca> marcas;
    List<Estado> estados;
    int id = 0;
    boolean corr = false;

    @Override
    protected void onCreate(Bundle savedInstanteState) {
        super.onCreate(savedInstanteState);
        setContentView(R.layout.nuevo_juego);

        nombre = findViewById(R.id.etGameName);
        marca = findViewById(R.id.etGameBrand);
        tipoJuego = findViewById(R.id.etGameType);
        numJugadores = findViewById(R.id.etGamePlayers);
        estado = findViewById(R.id.etGameState);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.fabEditar);
        btnEditar.setVisibility(View.GONE);
        btnEliminar = findViewById(R.id.fabEliminar);
        btnEliminar.setVisibility(View.GONE);

        dbJuego = new DbJuego(EditarJuego.this);
        dbTipoJuego = new DbTipoJuego(EditarJuego.this);
        dbMarca = new DbMarca(EditarJuego.this);
        dbEstado = new DbEstado(this);
        juegos = dbJuego.mostrarJuegos();
        tiposJuego = dbTipoJuego.mostrarTiposJuego();
        marcas = dbMarca.mostrarMarcas();
        estados = dbEstado.mostrarEstados();

        ArrayAdapter<TipoJuego> tipoJuegoArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, tiposJuego);
        tipoJuego.setAdapter(tipoJuegoArrayAdapter);

        ArrayAdapter<Marca> marcaArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, marcas);
        marca.setAdapter(marcaArrayAdapter);

        ArrayAdapter<Estado> estadoAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, estados);
        estado.setAdapter(estadoAdapter);

        if (savedInstanteState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanteState.getSerializable("ID");
        }

        final DbJuego dbJuegos = new DbJuego(EditarJuego.this);
        juego = dbJuegos.verJuego(id);

        if (juego != null) {
            nombre.setText(juego.getNombre());
            marca.setText(juego.getMarca());
            tipoJuego.setText(juego.getTipoJuego());
            numJugadores.setText(juego.getNumJugadores());
            estado.setText(juego.getEstado());
        }

        btnGuardar.setOnClickListener(v -> {
            if (!nombre.getText().toString().isEmpty()
                    && !marca.getText().toString().isEmpty()
                    && !tipoJuego.getText().toString().isEmpty()) {

                corr = dbJuegos.editarJuego(id,
                        nombre.getText().toString(),
                        marca.getText().toString(),
                        tipoJuego.getText().toString(),
                        numJugadores.getText().toString(),
                        estado.getText().toString());

                Marca brand = dbMarca.getMarca(marca.getText().toString());
                TipoJuego type = dbTipoJuego.getTipoJuego(tipoJuego.getText().toString());
                Estado state = dbEstado.getEstado(estado.getText().toString());

                if (brand == null) {
                    dbMarca.insertarMarca(marca.getText().toString());
                } else {
                    dbMarca.editarMarca(brand.getId(), brand.getNombre());
                }

                if (type == null) {
                    dbTipoJuego.insertarTipoJuego(tipoJuego.getText().toString());
                } else {
                    dbTipoJuego.editarTipoJuego(type.getId(), type.getTipo());
                }

                if (state == null) {
                    dbEstado.insertarEstado(estado.getText().toString());
                } else {
                    dbEstado.editarEstado(state.getId(), state.getEstado());
                }

                if (corr) {
                    Toast.makeText(EditarJuego.this, "JUEGO MODIFICADO", Toast.LENGTH_LONG).show();
                    recargarVista();
                } else {
                    Toast.makeText(EditarJuego.this, "ERROR AL MODIFICAR JUEGO", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(EditarJuego.this, "DEBE RELLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recargarVista() {
        Intent intent = new Intent(this, VerJuego.class);
        intent.putExtra("ID", id);
        intent.putExtra("TIPO", tipoJuego.getText().toString());
        startActivity(intent);
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
