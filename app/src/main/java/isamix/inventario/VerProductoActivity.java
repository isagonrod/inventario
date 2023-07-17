package isamix.inventario;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isamix.inventario.db.DbProductos;
import isamix.inventario.entity.Producto;

public class VerProductoActivity extends AppCompatActivity {

    TextView txtNombre, txtCantidad, txtPrecio, txtTienda;
    Button btnGuardar, fabEditar, fabEliminar;
    Producto producto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);

        txtNombre = findViewById(R.id.txtNombre);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtTienda = findViewById(R.id.txtTienda);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setVisibility(View.INVISIBLE);

        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);

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

        final DbProductos dbProductos = new DbProductos(VerProductoActivity.this);
        producto = dbProductos.verProducto(id);

        if (producto != null) {

            txtNombre.setText(producto.getNombre());
            txtCantidad.setText(producto.getCantidad());
            txtPrecio.setText(producto.getPrecio());
            txtTienda.setText(producto.getTienda());

            btnGuardar.setVisibility(View.INVISIBLE);

        }

        fabEditar.setOnClickListener(v -> {
            Intent intent = new Intent(VerProductoActivity.this, EditarProductoActivity.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        });

        fabEliminar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(VerProductoActivity.this);
            builder.setMessage("¿Desea eliminar este producto?")
                    .setPositiveButton("SÍ", (dialog, which) -> {
                        if (dbProductos.eliminarProducto(id)) {
                            lista();
                        }
                    })
                    .setNegativeButton("NO", (dialog, i) -> {

                    })
                    .show();
        });
    }

    private void lista() {
        Intent intent = new Intent(this, ListaProductoActivity.class);
        startActivity(intent);
    }
}