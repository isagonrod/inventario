package isamix.inventario.crud.juego;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.db.DbEstado;
import isamix.inventario.db.DbJuego;
import isamix.inventario.db.DbMarca;
import isamix.inventario.db.DbTipoJuego;
import isamix.inventario.modelo.Estado;
import isamix.inventario.modelo.Marca;
import isamix.inventario.modelo.TipoJuego;

public class NuevoJuego extends AppCompatActivity {

    EditText nombre, numJugadores;
    AutoCompleteTextView marca, tipoJuego, estado;
    Button btnGuardar, btnEditar, btnEliminar;
    DbJuego dbJuego;
    DbMarca dbMarca;
    DbTipoJuego dbTipoJuego;
    DbEstado dbEstado;
    List<Marca> marcas;
    List<TipoJuego> tiposJuego;
    List<Estado> estados;

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

        dbJuego = new DbJuego(NuevoJuego.this);
        dbMarca = new DbMarca(NuevoJuego.this);
        dbTipoJuego = new DbTipoJuego(NuevoJuego.this);
        dbEstado = new DbEstado(this);
        marcas = dbMarca.mostrarMarcas();
        tiposJuego = dbTipoJuego.mostrarTiposJuego();
        estados = dbEstado.mostrarEstados();

        ArrayAdapter<Marca> marcaArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, marcas);
        marca.setAdapter(marcaArrayAdapter);

        ArrayAdapter<TipoJuego> tipoJuegoArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, tiposJuego);
        tipoJuego.setAdapter(tipoJuegoArrayAdapter);

        ArrayAdapter<Estado> estadoAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, estados);
        estado.setAdapter(estadoAdapter);

        btnGuardar.setOnClickListener(v -> {
            if (!nombre.getText().toString().isEmpty()
                    && !marca.getText().toString().isEmpty()
                    && !tipoJuego.getText().toString().isEmpty()) {

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

                dbJuego.insertarJuego(
                        nombre.getText().toString(),
                        marca.getText().toString(),
                        tipoJuego.getText().toString(),
                        numJugadores.getText().toString(),
                        estado.getText().toString());

                Toast.makeText(NuevoJuego.this, "JUEGO GUARDADO", Toast.LENGTH_LONG).show();
                limpiarFormulario();
            } else {
                Toast.makeText(NuevoJuego.this, "ERROR AL GUARDAR JUEGO", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiarFormulario() {
        nombre.setText("");
        marca.setText("");
        tipoJuego.setText("");
        numJugadores.setText("");
        estado.setText("");
    }
}
