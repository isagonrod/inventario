package isamix.inventario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import isamix.inventario.db.DbProductos;
import isamix.inventario.entity.Producto;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtCantidad, txtPrecio, txtTienda;
    Button btnGuardar, fabEditar, fabEliminar;
    Producto producto;
    int id = 0;
    boolean correcto = false;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre = findViewById(R.id.txtNombre);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtTienda = findViewById(R.id.txtTienda);

        btnGuardar = findViewById(R.id.btnGuardar);

        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);

        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

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

        final DbProductos dbProductos = new DbProductos(EditarActivity.this);
        producto = dbProductos.verProducto(id);

        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtCantidad.setText(producto.getCantidad());
            txtPrecio.setText(producto.getPrecio());
            txtTienda.setText(producto.getTienda());
        }

        btnGuardar.setOnClickListener(v -> {
            if (!txtNombre.getText().toString().equals("") && !txtCantidad.getText().toString().equals("")) {
                correcto = dbProductos.editarProducto(id, txtNombre.getText().toString(),
                        txtCantidad.getText().toString(), txtPrecio.getText().toString(),
                        txtTienda.getText().toString());
                if (correcto) {
                    Toast.makeText(EditarActivity.this, "PRODUCTO MODIFICADO", Toast.LENGTH_LONG).show();
                    verRegistro();
                } else {
                    Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR PRODUCTO", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(EditarActivity.this, "DEBE RELLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void verRegistro() {
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}