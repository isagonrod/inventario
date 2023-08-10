package isamix.inventario.crud.textil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;

import isamix.inventario.R;
import isamix.inventario.db.DbEstado;
import isamix.inventario.db.DbMarca;
import isamix.inventario.db.DbRopa;
import isamix.inventario.db.DbTienda;
import isamix.inventario.db.DbTipoRopa;
import isamix.inventario.modelo.Estado;
import isamix.inventario.modelo.Marca;
import isamix.inventario.modelo.Tienda;
import isamix.inventario.modelo.TipoRopa;

public class NuevaRopa extends AppCompatActivity {

    EditText nombre, talla, cantidad;
    AutoCompleteTextView marca, tienda, tipo, estado;
    Button btnGuardar, btnEditar, btnEliminar;
    DbRopa dbRopa;
    DbMarca dbMarca;
    DbTienda dbTienda;
    DbTipoRopa dbTipoRopa;
    DbEstado dbEstado;
    List<Marca> marcas;
    List<Tienda> tiendas;
    List<TipoRopa> tiposRopa;
    List<Estado> estados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_ropa);

        nombre = findViewById(R.id.etNombreRopa);
        marca = findViewById(R.id.etMarcaRopa);
        tienda = findViewById(R.id.etTiendaRopa);
        talla = findViewById(R.id.etTallaRopa);
        tipo = findViewById(R.id.etTipoRopa);
        cantidad = findViewById(R.id.etCantidadRopa);
        estado = findViewById(R.id.etEstadoRopa);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.fabEditar);
        btnEditar.setVisibility(View.GONE);
        btnEliminar = findViewById(R.id.fabEliminar);
        btnEliminar.setVisibility(View.GONE);

        dbRopa = new DbRopa(this);
        dbMarca = new DbMarca(this);
        dbTienda = new DbTienda(this);
        dbTipoRopa = new DbTipoRopa(this);
        dbEstado = new DbEstado(this);

        marcas = dbMarca.mostrarMarcas();
        tiendas = dbTienda.mostrarTiendas();
        tiposRopa = dbTipoRopa.mostrarTiposRopa();
        estados = dbEstado.mostrarEstados();

        ArrayAdapter<Marca> marcaAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, marcas);
        marca.setAdapter(marcaAdapter);

        ArrayAdapter<Tienda> tiendaAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, tiendas);
        tienda.setAdapter(tiendaAdapter);

        ArrayAdapter<TipoRopa> tipoRopaAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, tiposRopa);
        tipo.setAdapter(tipoRopaAdapter);

        ArrayAdapter<Estado> estadoAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, estados);
        estado.setAdapter(estadoAdapter);

        btnGuardar.setOnClickListener(v -> {
            if (!nombre.getText().toString().isEmpty()
                    && !tipo.getText().toString().isEmpty()) {

                Marca brand = dbMarca.getMarca(marca.getText().toString());
                Tienda shop = dbTienda.getTienda(tienda.getText().toString());
                TipoRopa type = dbTipoRopa.getTipoRopa(tipo.getText().toString());
                Estado state = dbEstado.getEstado(estado.getText().toString());

                if (brand == null) {
                    dbMarca.insertarMarca(marca.getText().toString());
                } else {
                    dbMarca.editarMarca(brand.getId(), brand.getNombre());
                }

                if (shop == null) {
                    dbTienda.insertarTienda(tienda.getText().toString());
                } else {
                    dbTienda.editarTienda(shop.getId(), shop.getNombre());
                }

                if (type == null) {
                    dbTipoRopa.insertarTipoRopa(tipo.getText().toString());
                } else {
                    dbTipoRopa.editarTipoRopa(type.getId(), type.getTipoRopa());
                }

                if (state == null) {
                    dbEstado.insertarEstado(estado.getText().toString());
                } else {
                    dbEstado.editarEstado(state.getId(), state.getEstado());
                }

                dbRopa.insertarRopa(
                        nombre.getText().toString(),
                        marca.getText().toString(),
                        tienda.getText().toString(),
                        talla.getText().toString(),
                        tipo.getText().toString(),
                        Integer.parseInt(cantidad.getText().toString()),
                        estado.getText().toString());

                Toast.makeText(this, "ROPA GUARDADA", Toast.LENGTH_LONG).show();
                limpiarFormulario();

            } else {
                Toast.makeText(this, "DEBE RELLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiarFormulario() {
        nombre.setText("");
        marca.setText("");
        tienda.setText("");
        talla.setText("");
        tipo.setText("");
        cantidad.setText("");
        estado.setText("");
    }
}
