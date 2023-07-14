package isamix.inventario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.db.DbProductos;
import isamix.inventario.db.DbTienda;
import isamix.inventario.entity.Producto;
import isamix.inventario.entity.Tienda;

public class EditarProductoActivity extends AppCompatActivity {

    EditText txtNombre, txtCantidad, txtPrecio;
    TextView txtTienda;
    Spinner spinnerTienda;
    Button btnGuardar, fabEditar, fabEliminar;
    Producto producto;
    String shop;
    int id = 0;
    boolean correcto = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        txtNombre = findViewById(R.id.txtNombre);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtTienda = findViewById(R.id.txtTienda);
        txtTienda.setVisibility(View.VISIBLE);
        spinnerTienda = findViewById(R.id.spinnerTienda);

        btnGuardar = findViewById(R.id.btnGuardar);

        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);

        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        List<Tienda> tiendas = llenarTiendas();
        ArrayAdapter<Tienda> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, tiendas);
        spinnerTienda.setAdapter(arrayAdapter);

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

        final DbProductos dbProductos = new DbProductos(EditarProductoActivity.this);
        producto = dbProductos.verProducto(id);

        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtCantidad.setText(producto.getCantidad());
            txtPrecio.setText(producto.getPrecio());
            txtTienda.setText(producto.getTienda());
            spinnerTienda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    shop = ((Tienda) parent.getSelectedItem()).getNombre();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    shop = "Otra";
                }
            });
        }

        btnGuardar.setOnClickListener(v -> {
            if (!txtNombre.getText().toString().equals("") && !txtCantidad.getText().toString().equals("")) {
                correcto = dbProductos.editarProducto(
                        id, txtNombre.getText().toString(),
                        txtCantidad.getText().toString(),
                        txtPrecio.getText().toString(),
                        shop,
                        0);
                if (correcto) {
                    Toast.makeText(EditarProductoActivity.this, "PRODUCTO MODIFICADO", Toast.LENGTH_LONG).show();
                    verRegistro();
                } else {
                    Toast.makeText(EditarProductoActivity.this, "ERROR AL MODIFICAR PRODUCTO", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(EditarProductoActivity.this, "DEBE RELLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void verRegistro() {
        Intent intent = new Intent(this, VerProductoActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    private List<Tienda> llenarTiendas() {
        List<Tienda> listaTiendas = new ArrayList<>();
        DbTienda dbTienda = new DbTienda(this);
        Cursor cursor = dbTienda.mostrarTiendas();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Tienda tienda = new Tienda();
                    tienda.setId(cursor.getInt(0));
                    tienda.setNombre(cursor.getString(1));
                    listaTiendas.add(tienda);
                } while (cursor.moveToNext());
            }
        }
        dbTienda.close();
        return listaTiendas;
    }
}