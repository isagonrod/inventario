package isamix.inventario.crud;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.TextView;

public class ListaLibroPorGenero extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView buscador;
    RecyclerView listaLibros;
    List<Libro> arrayLibros;
    LibroAdapter adapter;
    TextView title;
}
