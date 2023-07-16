package isamix.inventario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import isamix.inventario.db.DbProductos;
import isamix.inventario.db.DbTienda;
import isamix.inventario.entity.Producto;
import isamix.inventario.entity.Tienda;

public class EditarProductoActivity extends AppCompatActivity {

    EditText txtNombre, txtCantidad, txtPrecio;
    AutoCompleteTextView txtTienda;
    Button btnGuardar, fabEditar, fabEliminar;
    Producto producto;
    int id = 0;
    boolean correcto = false;
    DbProductos dbProductos;
    DbTienda dbTienda;
    List<Tienda> tiendas;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        txtNombre = findViewById(R.id.txtNombre);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtTienda = findViewById(R.id.txtTienda);

        btnGuardar = findViewById(R.id.btnGuardar);

        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);

        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        dbProductos = new DbProductos(EditarProductoActivity.this);
        dbTienda = new DbTienda(EditarProductoActivity.this);
        tiendas = dbTienda.mostrarTiendas();

        ArrayAdapter<Tienda> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, tiendas);
        txtTienda.setAdapter(arrayAdapter);

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
        DbTienda dbTienda = new DbTienda(EditarProductoActivity.this);
        producto = dbProductos.verProducto(id);

        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtCantidad.setText(producto.getCantidad());
            txtPrecio.setText(producto.getPrecio());
            txtTienda.setText(producto.getTienda());
        }

        btnGuardar.setOnClickListener(v -> {
            if (!txtNombre.getText().toString().equals("") && !txtCantidad.getText().toString().equals("") && !txtTienda.getText().toString().equals("")) {
                correcto = dbProductos.editarProducto(
                        id, txtNombre.getText().toString(),
                        txtCantidad.getText().toString(),
                        txtPrecio.getText().toString(),
                        txtTienda.getText().toString(),
                        0);

                /*
                 * TODO: Método para guardar la tienda si no existe en la base de datos
                if (dbTienda.getTienda(txtTienda.getText().toString()) == null) {
                    dbTienda.insertarTienda(txtTienda.getText().toString());
                }
                 */
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
}