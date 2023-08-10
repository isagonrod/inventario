package isamix.inventario.crud.textil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isamix.inventario.R;
import isamix.inventario.crud.libro.VerLibro;
import isamix.inventario.db.DbRopa;
import isamix.inventario.modelo.Ropa;

public class VerRopa extends AppCompatActivity {

    TextView nombre, marca, tienda, talla, tipo, cantidad, estado;
    Button btnGuardar, btnEditar, btnEliminar;
    Ropa ropa;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_ropa);

        nombre = findViewById(R.id.etNombreRopa);
        marca = findViewById(R.id.etMarcaRopa);
        tienda = findViewById(R.id.etTiendaRopa);
        talla = findViewById(R.id.etTallaRopa);
        tipo = findViewById(R.id.etTipoRopa);
        cantidad = findViewById(R.id.etCantidadRopa);
        estado = findViewById(R.id.etEstadoRopa);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.fabEditar);
        btnEliminar = findViewById(R.id.fabEliminar);

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

        final DbRopa dbRopa = new DbRopa(this);
        ropa = dbRopa.verRopa(id);

        if (ropa != null) {
            nombre.setText(ropa.getNombre());
            marca.setText(ropa.getMarca());
            tienda.setText(ropa.getTienda());
            talla.setText(ropa.getTalla());
            tipo.setText(ropa.getTipo());
            cantidad.setText(String.valueOf(ropa.getCantidad()));
            estado.setText(ropa.getEstado());
            btnGuardar.setVisibility(View.GONE);
        }

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarRopa.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        });

        btnEliminar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ELIMINAR ROPA");
            builder.setMessage("¿Desea eliminar esta prenda / ropa / textil?");
            builder.setPositiveButton("SÍ", (dialogInterface, i) -> {
                if (dbRopa.eliminarRopa(id)) {
                    lista();
                }
            }).setNegativeButton("NO", (dialogInterface, i) -> {}).show();
        });
    }

    private void lista() {
        Intent intent = new Intent(this, ListaRopa.class);
        startActivity(intent);
    }
}
