package isamix.inventario.crud;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.db.DbCategoria;
import isamix.inventario.db.DbProducto;
import isamix.inventario.db.DbTienda;
import isamix.inventario.modelo.Categoria;
import isamix.inventario.modelo.Tienda;

public class NuevoProducto extends AppCompatActivity {

    EditText txtNombre, txtCantidad, txtPrecio;
    AutoCompleteTextView txtTienda, txtCategoria;
    Button btnGuardar, favEditar, favEliminar;
    DbProducto dbProducto;
    DbTienda dbTienda;
    DbCategoria dbCategoria;
    List<Tienda> tiendas;
    List<Categoria> categorias;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_producto);

        txtNombre = findViewById(R.id.nombre);
        txtCantidad = findViewById(R.id.cantidad);
        txtPrecio = findViewById(R.id.precio);
        txtTienda = findViewById(R.id.tienda);
        txtCategoria = findViewById(R.id.categoria);

        btnGuardar = findViewById(R.id.btnGuardar);
        favEditar = findViewById(R.id.fabEditar);
        favEditar.setVisibility(View.INVISIBLE);
        favEliminar = findViewById(R.id.fabEliminar);
        favEliminar.setVisibility(View.INVISIBLE);

        dbProducto = new DbProducto(NuevoProducto.this);
        dbTienda = new DbTienda(NuevoProducto.this);
        dbCategoria = new DbCategoria(NuevoProducto.this);
        tiendas = dbTienda.mostrarTiendas();
        categorias = dbCategoria.mostrarCategorias();

        ArrayAdapter<Tienda> arrayAdapterTienda = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, tiendas);
        txtTienda.setAdapter(arrayAdapterTienda);

        ArrayAdapter<Categoria> arrayAdapterCategoria = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, categorias);
        txtCategoria.setAdapter(arrayAdapterCategoria);

        btnGuardar.setOnClickListener(v -> {
            if (!txtNombre.getText().toString().isEmpty()
                    && !txtTienda.getText().toString().isEmpty()
                    && !txtCategoria.getText().toString().isEmpty()) {

                Tienda shop = dbTienda.getTienda(txtTienda.getText().toString());
                if (shop == null) {
                    dbTienda.insertarTienda(txtTienda.getText().toString());
                } else {
                    dbTienda.editarTienda(shop.getId(), shop.getNombre());
                }

                Categoria category = dbCategoria.getCategoriaPorNombre(txtCategoria.getText().toString());
                if (category == null) {
                    dbCategoria.insertarCategoria(txtCategoria.getText().toString());
                } else {
                    dbCategoria.editarCategoria(category.getId(), category.getNombre());
                }

                dbProducto.insertarProducto(
                        txtNombre.getText().toString(),
                        txtCantidad.getText().toString(),
                        txtPrecio.getText().toString(),
                        txtTienda.getText().toString(),
                        txtCategoria.getText().toString(),
                        0);

                Toast.makeText(NuevoProducto.this, "PRODUCTO GUARDADO", Toast.LENGTH_LONG).show();
                limpiar();
            } else {
                Toast.makeText(NuevoProducto.this, "ERROR AL GUARDAR PRODUCTO", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiar() {
        txtNombre.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        txtTienda.setText("");
        txtCategoria.setText("");
    }
}