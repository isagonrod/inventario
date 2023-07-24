package isamix.inventario.crud;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isamix.inventario.R;
import isamix.inventario.db.DbProducto;
import isamix.inventario.modelo.Producto;

public class VerProducto extends AppCompatActivity {

    TextView txtNombre, txtCantidad, txtPrecio, txtTienda, txtCategoria;
    Button btnGuardar, fabEditar, fabEliminar;
    Producto producto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_producto);

        txtNombre = findViewById(R.id.txtNombre);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtTienda = findViewById(R.id.txtTienda);
        txtCategoria = findViewById(R.id.txtCategoria);

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

        final DbProducto dbProducto = new DbProducto(VerProducto.this);
        producto = dbProducto.verProducto(id);

        if (producto != null) {

            txtNombre.setText(producto.getNombre());
            txtCantidad.setText(producto.getCantidad());
            txtPrecio.setText(producto.getPrecio());
            txtTienda.setText(producto.getTienda());
            txtCategoria.setText(producto.getCategoria());

            btnGuardar.setVisibility(View.INVISIBLE);

        }

        fabEditar.setOnClickListener(v -> {
            Intent intent = new Intent(VerProducto.this, EditarProducto.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        });

        fabEliminar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(VerProducto.this);
            builder.setMessage("¿Desea eliminar este producto?")
                    .setPositiveButton("SÍ", (dialog, which) -> {
                        if (dbProducto.eliminarProducto(id)) {
                            lista();
                        }
                    })
                    .setNegativeButton("NO", (dialog, i) -> {

                    })
                    .show();
        });
    }

    private void lista() {
        Intent intent = new Intent(this, ListaProducto.class);
        startActivity(intent);
    }
}