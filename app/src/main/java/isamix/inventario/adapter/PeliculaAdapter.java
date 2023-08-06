package isamix.inventario.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import isamix.inventario.R;
import isamix.inventario.db.DbPelicula;
import isamix.inventario.modelo.Pelicula;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder> {

    List<Pelicula> listaPeliculas;
    List<Pelicula> listaOriginal;

    public PeliculaAdapter(List<Pelicula> listaPeliculas) {
        this.listaPeliculas = new ArrayList<>(listaPeliculas);
        this.listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaPeliculas);
    }

    @NonNull
    @Override
    public PeliculaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pelicula, null, true);
        return new PeliculaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaViewHolder peliculaViewHolder, int i) {
        peliculaViewHolder.titulo.setText(listaPeliculas.get(i).getTitulo());
        peliculaViewHolder.director.setText(listaPeliculas.get(i).getDirector());
        peliculaViewHolder.fecha.setText(listaPeliculas.get(i).getFechaEstreno());
    }

    @Override
    public int getItemCount() {
        return listaPeliculas.size();
    }

    public void eliminarItem(int position) {
        listaPeliculas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaPeliculas.size());
    }

    public void filtrarPeliculas(String texto) {
        int longitud = texto.length();
        if (longitud == 0) {
            listaPeliculas.clear();
            listaPeliculas.addAll(listaOriginal);
        } else {
            List<Pelicula> collection = listaPeliculas.stream().filter(i -> i.getTitulo().toLowerCase().contains(texto.toLowerCase())).collect(Collectors.toList());
            listaPeliculas.clear();
            listaPeliculas.addAll(collection);
        }
        notifyDataSetChanged();
    }

    public class PeliculaViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, director, fecha;
        Button btnEditar, btnEliminar;
        DbPelicula dbPelicula;

        public PeliculaViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.viewFilmTitle);
            director = itemView.findViewById(R.id.viewFilmDirector);
            fecha = itemView.findViewById(R.id.viewFilmYear);

            btnEditar = itemView.findViewById(R.id.editButton);
            btnEliminar = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, VerPelicula.class);
                intent.putExtra("ID", listaPeliculas.get(getAdapterPosition()).getId());
                context.startActivity(intent);
            });

            btnEditar.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), EditarPelicula.class);
                intent.putExtra("ID", listaPeliculas.get(getAdapterPosition()).getId());
                ContextCompat.startActivity(btnEditar.getContext(), intent, null);
            });

            btnEliminar.setOnClickListener(v -> {
                dbPelicula = new DbPelicula(btnEliminar.getContext());
                dbPelicula.eliminarPelicula(listaPeliculas.get(getAdapterPosition()).getId());
                eliminarItem(getAdapterPosition());
            });
        }
    }
}
