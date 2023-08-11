package isamix.inventario.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import isamix.inventario.R;
import isamix.inventario.crud.libro.ListaLibroPorGenero;
import isamix.inventario.db.DbGenero;
import isamix.inventario.db.DbLibro;
import isamix.inventario.modelo.Genero;

public class GeneroAdapter extends RecyclerView.Adapter<GeneroAdapter.GeneroViewHolder> {

    List<Genero> listaGeneros;
    List<Genero> listaOriginal;

    public GeneroAdapter(List<Genero> listaGeneros) {
        this.listaGeneros = new ArrayList<>(listaGeneros);
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaGeneros);
    }

    @NonNull
    @Override
    public GeneroViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_categoria, null, true);
        return new GeneroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneroViewHolder generoViewHolder, int i) {
        generoViewHolder.itemGenero.setText(listaGeneros.get(i).getNombre());
    }

    @Override
    public int getItemCount() {
        return listaGeneros.size();
    }

    public class GeneroViewHolder extends RecyclerView.ViewHolder {

        TextView itemGenero;

        public GeneroViewHolder(@NonNull View itemView) {
            super(itemView);
            itemGenero = itemView.findViewById(R.id.item);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ListaLibroPorGenero.class);
                intent.putExtra("GENERO", listaGeneros.get(getAdapterPosition()).getNombre());
                v.getContext().startActivity(intent);
            });

            itemView.setOnLongClickListener(v -> {
                DbGenero dbGenero = new DbGenero(v.getContext());
                DbLibro dbLibro = new DbLibro(v.getContext());
                Genero genre = dbGenero.getGeneroPorId(listaGeneros.get(getAdapterPosition()).getId());
                if (genre != null) {
                    if (dbLibro.mostrarLibrosPorGenero(listaGeneros.get(getAdapterPosition()).getNombre()).isEmpty()) {
                        dbGenero.eliminarGenero(genre.getId());
                        listaGeneros.remove(listaGeneros.get(getAdapterPosition()));
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), listaGeneros.size());
                        Toast.makeText(v.getContext(), "Género vacío borrado con éxito", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(v.getContext(), "No se ha podido eliminar el género porque tiene libros asociados", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(v.getContext(), "No se ha podido eliminar el género porque no existe en la base de datos", Toast.LENGTH_LONG).show();
                }
                return false;
            });
        }
    }
}
