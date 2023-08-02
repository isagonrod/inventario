package isamix.inventario.crud.producto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import isamix.inventario.R;
import isamix.inventario.crud.FuncionamientoApp;
import isamix.inventario.crud.ListaCategoria;
import isamix.inventario.crud.ListaCompra;
import isamix.inventario.crud.libro.ListaGenero;
import isamix.inventario.db.DbCategoria;
import isamix.inventario.db.DbMarca;
import isamix.inventario.db.DbProducto;
import isamix.inventario.db.DbTienda;
import isamix.inventario.modelo.Categoria;
import isamix.inventario.modelo.Marca;
import isamix.inventario.modelo.Producto;
import isamix.inventario.modelo.Tienda;

public class EditarProducto extends AppCompatActivity {

    EditText txtNombre, txtCantidad, txtPrecio;
    AutoCompleteTextView txtMarca, txtTienda, txtCategoria;
    Button btnGuardar, fabEditar, fabEliminar;
    Producto producto;
    int id = 0;
    boolean correcto = false;
    DbProducto dbProducto;
    DbTienda dbTienda;
    DbCategoria dbCategoria;
    List<Tienda> tiendas;
    List<Categoria> categorias;
    DbMarca dbMarca;
    List<Marca> marcas;

    @SuppressLint({"MissingInflatedId", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_producto);

        txtNombre = findViewById(R.id.nombre);
        txtMarca = findViewById(R.id.marca);
        txtCantidad = findViewById(R.id.cantidad);
        txtPrecio = findViewById(R.id.precio);
        txtTienda = findViewById(R.id.tienda);
        txtCategoria = findViewById(R.id.categoria);

        btnGuardar = findViewById(R.id.btnGuardar);

        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.GONE);

        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.GONE);

        dbProducto = new DbProducto(EditarProducto.this);
        dbTienda = new DbTienda(EditarProducto.this);
        dbCategoria = new DbCategoria(EditarProducto.this);
        tiendas = dbTienda.mostrarTiendas();
        categorias = dbCategoria.mostrarCategorias();
        dbMarca = new DbMarca(EditarProducto.this);
        marcas = dbMarca.mostrarMarcas();

        ArrayAdapter<Tienda> arrayAdapterTienda = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, tiendas);
        txtTienda.setAdapter(arrayAdapterTienda);

        ArrayAdapter<Categoria> arrayAdapterCategoria = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, categorias);
        txtCategoria.setAdapter(arrayAdapterCategoria);

        ArrayAdapter<Marca> arrayAdapterMarca = new ArrayAdapter<>(getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item, marcas);
        txtMarca.setAdapter(arrayAdapterMarca);

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

        final DbProducto dbProductos = new DbProducto(EditarProducto.this);
        DbTienda dbTienda = new DbTienda(EditarProducto.this);
        producto = dbProductos.verProducto(id);

        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtMarca.setText(producto.getMarca());
            txtCantidad.setText(String.valueOf(producto.getCantidad()));
            txtPrecio.setText(String.format("%.2f", producto.getPrecio()));
            txtTienda.setText(producto.getTienda());
            txtCategoria.setText(producto.getCategoria());
        }

        btnGuardar.setOnClickListener(v -> {
            if (!txtNombre.getText().toString().equals("") && !txtCantidad.getText().toString().equals("") && !txtTienda.getText().toString().equals("")) {
                correcto = dbProductos.editarProducto(
                        id, txtNombre.getText().toString(),
                        txtMarca.getText().toString(),
                        Integer.parseInt(txtCantidad.getText().toString()),
                        Double.parseDouble(txtPrecio.getText().toString().replace(",", ".")),
                        txtTienda.getText().toString(),
                        txtCategoria.getText().toString(),
                        0);

                Tienda shop = dbTienda.getTienda(txtTienda.getText().toString());
                Categoria category = dbCategoria.getCategoriaPorNombre(txtCategoria.getText().toString());
                Marca brand = dbMarca.getMarca(txtMarca.getText().toString());

                if (shop == null) {
                    dbTienda.insertarTienda(txtTienda.getText().toString());
                } else {
                    dbTienda.editarTienda(shop.getId(), shop.getNombre());
                }

                if (category == null) {
                    dbCategoria.insertarCategoria(txtCategoria.getText().toString());
                } else {
                    dbCategoria.editarCategoria(category.getId(), category.getNombre());
                }

                if (brand == null) {
                    dbMarca.insertarMarca(txtMarca.getText().toString());
                } else {
                    dbMarca.editarMarca(brand.getId(), brand.getNombre());
                }

                if (correcto) {
                    Toast.makeText(EditarProducto.this, "PRODUCTO MODIFICADO", Toast.LENGTH_LONG).show();
                    recargarVista();
                } else {
                    Toast.makeText(EditarProducto.this, "ERROR AL MODIFICAR PRODUCTO", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(EditarProducto.this, "DEBE RELLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recargarVista() {
        Intent intent = new Intent(this, VerProducto.class);
        intent.putExtra("ID", id);
        intent.putExtra("categoria", txtCategoria.getText().toString());
        startActivity(intent);
    }

    /* *** *** *** MENÚ PRINCIPAL *** *** *** */

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuFuncionamiento:
                verLista(FuncionamientoApp.class);
                return true;
            case R.id.menuListaCompra:
                verLista(ListaCompra.class);
                return true;
            case R.id.menuGestionProductos:
                verLista(ListaCategoria.class);
                return true;
            case R.id.menuGestionLibros:
                verLista(ListaGenero.class);
                return true;
//            case R.id.menuGestionJuegos:
//                return true;
//            case R.id.menuGestionMultimedia:
//                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void verLista(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}