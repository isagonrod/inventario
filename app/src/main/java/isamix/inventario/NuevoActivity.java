package isamix.inventario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import isamix.inventario.db.DbProductos;

public class NuevoActivity extends AppCompatActivity {

    EditText txtNombre, txtCantidad, txtPrecio, txtTienda;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        txtNombre = findViewById(R.id.txtNombre);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtTienda = findViewById(R.id.txtTienda);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> {
            DbProductos dbProductos = new DbProductos(NuevoActivity.this);
            long id = dbProductos.insertarProducto(
                    txtNombre.getText().toString(),
                    txtCantidad.getText().toString(),
                    txtPrecio.getText().toString(),
                    txtTienda.getText().toString());

            if (id > 0) {
                Toast.makeText(NuevoActivity.this, "PRODUCTO GUARDADO", Toast.LENGTH_LONG).show();
                limpiar();
            } else {
                Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR PRODUCTO", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiar() {
        txtNombre.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        txtTienda.setText("");
    }
}