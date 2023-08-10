package isamix.inventario.crud.textil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.FuncionamientoApp;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.juego.ListaTipoJuego;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.crud.multimedia.ListaMultimedia;
import isamix.inventario.crud.producto.ListaCategoria;
import isamix.inventario.db.DbEstado;
import isamix.inventario.db.DbMarca;
import isamix.inventario.db.DbRopa;
import isamix.inventario.db.DbTienda;
import isamix.inventario.db.DbTipoRopa;
import isamix.inventario.modelo.Estado;
import isamix.inventario.modelo.Marca;
import isamix.inventario.modelo.Ropa;
import isamix.inventario.modelo.Tienda;
import isamix.inventario.modelo.TipoRopa;

public class EditarRopa extends AppCompatActivity {

    EditText nombre, talla, cantidad;
    AutoCompleteTextView marca, tienda, tipo, estado;
    Button btnGuardar, btnEditar, btnEliminar;
    //DbRopa dbRopa;
    DbMarca dbMarca;
    DbTienda dbTienda;
    DbTipoRopa dbTipoRopa;
    DbEstado dbEstado;
    List<Marca> marcas;
    List<Tienda> tiendas;
    List<TipoRopa> tiposRopa;
    List<Estado> estados;
    Ropa ropa;
    int id = 0;
    boolean correcto = false;

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

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbRopa dbRopa = new DbRopa(this);
        ropa = dbRopa.verRopa(id);

        if (ropa != null) {
            nombre.setText(ropa.getNombre());
            marca.setText(ropa.getMarca());
            tienda.setText(ropa.getTienda());
            talla.setText(ropa.getTalla());
            tipo.setText(ropa.getTipo());
            cantidad.setText(String.valueOf(ropa.getCantidad()));
            estado.setText(ropa.getEstado());
        }

        btnGuardar.setOnClickListener(v -> {
            if (!nombre.getText().toString().isEmpty()
                    && !tipo.getText().toString().isEmpty()) {

                correcto = dbRopa.editarRopa(id,
                        nombre.getText().toString(),
                        marca.getText().toString(),
                        tienda.getText().toString(),
                        talla.getText().toString(),
                        tipo.getText().toString(),
                        Integer.parseInt(cantidad.getText().toString()),
                        estado.getText().toString());

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

                if (correcto) {
                    Toast.makeText(this, "ROPA MODIFICADA", Toast.LENGTH_LONG).show();
                    recargarVista();
                } else {
                    Toast.makeText(this, "ERROR AL MODIFICAR ROPA", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "DEBE RELLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recargarVista() {
        Intent intent = new Intent(this, VerRopa.class);
        intent.putExtra("ID", id);
        intent.putExtra("TIPO", tipo.getText().toString());
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
