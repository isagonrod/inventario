package isamix.inventario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import isamix.inventario.db.DbProductos;

public class NuevoProductoActivity extends AppCompatActivity {

    EditText txtNombre, txtCantidad, txtPrecio, txtTienda;
    CheckBox viewParaComprar;
    Button btnGuardar, favEditar, favEliminar;

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
        favEditar = findViewById(R.id.fabEditar);
        favEditar.setVisibility(View.INVISIBLE);
        favEliminar = findViewById(R.id.fabEliminar);
        favEliminar.setVisibility(View.INVISIBLE);

        btnGuardar.setOnClickListener(v -> {
            DbProductos dbProductos = new DbProductos(NuevoProductoActivity.this);
            long id = dbProductos.insertarProducto(
                    txtNombre.getText().toString(),
                    txtCantidad.getText().toString(),
                    txtPrecio.getText().toString(),
                    txtTienda.getText().toString(),
                    Integer.parseInt(viewParaComprar.isChecked()? "49" : "48"));

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
        txtTienda.setText("");
        viewParaComprar.setChecked(false);
    }
}