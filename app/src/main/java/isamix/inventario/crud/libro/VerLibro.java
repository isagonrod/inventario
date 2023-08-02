package isamix.inventario.crud.libro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isamix.inventario.R;
import isamix.inventario.db.DbLibro;
import isamix.inventario.modelo.Libro;

public class VerLibro extends AppCompatActivity {

    TextView titulo, autor, editorial, genero, isbn, lugarImpresion, fechaImpresion;
    Button btnGuardar, btnEditar, btnEliminar;
    Libro libro;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanteState) {
        super.onCreate(savedInstanteState);
        setContentView(R.layout.ver_libro);

        titulo = findViewById(R.id.bookTitle);
        autor = findViewById(R.id.bookAuthor);
        editorial = findViewById(R.id.bookEditorial);
        genero = findViewById(R.id.bookGenre);
        isbn = findViewById(R.id.bookIsbn);
        lugarImpresion = findViewById(R.id.bookPrintPlace);
        fechaImpresion = findViewById(R.id.bookPrintYear);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.fabEditar);
        btnEliminar = findViewById(R.id.fabEliminar);

        if (savedInstanteState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanteState.getSerializable("ID");
        }

        final DbLibro dbLibro = new DbLibro(VerLibro.this);
        libro = dbLibro.verLibro(id);

        if (libro != null) {
            titulo.setText(libro.getTitulo());
            autor.setText(libro.getAutor());
            editorial.setText(libro.getEditorial());
            genero.setText(libro.getGenero());
            isbn.setText(String.valueOf(libro.getIsbn()));
            lugarImpresion.setText(libro.getLugarImpresion());
            fechaImpresion.setText(String.valueOf(libro.getFechaImpresion()));
            btnGuardar.setVisibility(View.INVISIBLE);
        }

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(VerLibro.this, EditarLibro.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        });

        btnEliminar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(VerLibro.this);
            builder.setMessage("¿Desea eliminar este libro?");
            builder.setPositiveButton("SÍ", (dialogInterface, i) -> {
                if (dbLibro.eliminarLibro(id)) {
                    lista();
                }
            }).setNegativeButton("NO", (dialogInterface, i) -> {}).show();
        });
    }

    private void lista() {
        Intent intent = new Intent(this, ListaLibro.class);
        startActivity(intent);
    }
}
