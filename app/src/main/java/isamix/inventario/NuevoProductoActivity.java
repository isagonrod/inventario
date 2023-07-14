package isamix.inventario;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import isamix.inventario.entity.Tienda;

public class NuevoProductoActivity extends AppCompatActivity {

    EditText txtNombre, txtCantidad, txtPrecio;
    TextView txtTienda;
    Spinner spinnerTienda;
    Button btnGuardar, favEditar, favEliminar;
    String shop;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        txtNombre = findViewById(R.id.txtNombre);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtTienda = findViewById(R.id.txtTienda);
        txtTienda.setVisibility(View.INVISIBLE);
        spinnerTienda = findViewById(R.id.spinnerTienda);
        btnGuardar = findViewById(R.id.btnGuardar);
        favEditar = findViewById(R.id.fabEditar);
        favEditar.setVisibility(View.INVISIBLE);
        favEliminar = findViewById(R.id.fabEliminar);
        favEliminar.setVisibility(View.INVISIBLE);

        List<Tienda> tiendas = llenarTiendas();
        ArrayAdapter<Tienda> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, tiendas);
        spinnerTienda.setAdapter(arrayAdapter);

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

        btnGuardar.setOnClickListener(v -> {
            DbProductos dbProductos = new DbProductos(NuevoProductoActivity.this);
            long id = dbProductos.insertarProducto(
                    txtNombre.getText().toString(),
                    txtCantidad.getText().toString(),
                    txtPrecio.getText().toString(),
                    shop,
                    0
            );

            if (id > 0) {
                Toast.makeText(NuevoProductoActivity.this, "PRODUCTO GUARDADO", Toast.LENGTH_LONG).show();
                limpiar();
            } else {
                Toast.makeText(NuevoProductoActivity.this, "ERROR AL GUARDAR PRODUCTO", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiar() {
        txtNombre.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
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