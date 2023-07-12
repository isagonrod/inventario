package isamix.inventario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import isamix.inventario.db.DbProductos;
import isamix.inventario.entity.Producto;

public class EditarProductoActivity extends AppCompatActivity {

    EditText txtNombre, txtCantidad, txtPrecio, txtTienda;
    CheckBox viewParaComprar;
    Button btnGuardar, fabEditar, fabEliminar;
    Producto producto;
    int id = 0;
    boolean correcto = false;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        txtNombre = findViewById(R.id.txtNombre);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtTienda = findViewById(R.id.txtTienda);
        viewParaComprar = findViewById(R.id.viewParaComprar);

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

        final DbProductos dbProductos = new DbProductos(EditarProductoActivity.this);
        producto = dbProductos.verProducto(id);

        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtCantidad.setText(producto.getCantidad());
            txtPrecio.setText(producto.getPrecio());
            txtTienda.setText(producto.getTienda());
            viewParaComprar.setChecked(producto.isParaComprar() != 0);
        }

        btnGuardar.setOnClickListener(v -> {
            if (!txtNombre.getText().toString().equals("") && !txtCantidad.getText().toString().equals("")) {
                correcto = dbProductos.editarProducto(
                        id, txtNombre.getText().toString(),
                        txtCantidad.getText().toString(),
                        txtPrecio.getText().toString(),
                        txtTienda.getText().toString(),
                        viewParaComprar.isChecked()?'1':'0');
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