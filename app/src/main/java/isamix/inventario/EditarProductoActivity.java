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

import isamix.inventario.db.DbCategoria;
import isamix.inventario.db.DbProductos;
import isamix.inventario.db.DbTienda;
import isamix.inventario.entity.Categoria;
import isamix.inventario.entity.Producto;
import isamix.inventario.entity.Tienda;

public class EditarProductoActivity extends AppCompatActivity {

    EditText txtNombre, txtCantidad, txtPrecio;
    AutoCompleteTextView txtTienda, txtCategoria;
    Button btnGuardar, fabEditar, fabEliminar;
    Producto producto;
    int id = 0;
    boolean correcto = false;
    DbProductos dbProductos;
    DbTienda dbTienda;
    DbCategoria dbCategoria;
    List<Tienda> tiendas;
    List<Categoria> categorias;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        txtNombre = findViewById(R.id.txtNombre);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtTienda = findViewById(R.id.txtTienda);
        txtCategoria = findViewById(R.id.txtCategoria);

        btnGuardar = findViewById(R.id.btnGuardar);

        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);

        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        dbProductos = new DbProductos(EditarProductoActivity.this);
        dbTienda = new DbTienda(EditarProductoActivity.this);
        dbCategoria = new DbCategoria(EditarProductoActivity.this);
        tiendas = dbTienda.mostrarTiendas();
        categorias = dbCategoria.mostrarCategorias();

        ArrayAdapter<Tienda> arrayAdapterTienda = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, tiendas);
        txtTienda.setAdapter(arrayAdapterTienda);

        ArrayAdapter<Categoria> arrayAdapterCategoria = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, categorias);
        txtCategoria.setAdapter(arrayAdapterCategoria);

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
                        txtCategoria.getText().toString(),
                        0);

                Tienda shop = dbTienda.getTienda(txtTienda.getText().toString());
                if (shop == null) {
                    dbTienda.insertarTienda(txtTienda.getText().toString());
                } else {
                    dbTienda.editarTienda(shop.getId(), shop.getNombre());
                }

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