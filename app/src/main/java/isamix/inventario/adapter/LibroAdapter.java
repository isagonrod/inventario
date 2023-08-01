package isamix.inventario.adapter;

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
import isamix.inventario.crud.VerLibro;
import isamix.inventario.db.DbLibro;
import isamix.inventario.modelo.Libro;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.LibroViewHolder> {

    List<Libro> listaLibros;
    List<Libro> listaOriginal;

    public LibroAdapter(List<Libro> listaLibros) {
        this.listaLibros = listaLibros;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaLibros);
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_libro, null, false);
        return new LibroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        holder.titulo.setText(listaLibros.get(position).getTitulo());
        holder.autor.setText(listaLibros.get(position).getAutor());
        holder.editorial.setText(listaLibros.get(position).getEditorial());
        holder.genero.setText(listaLibros.get(position).getGenero());
    }

    @Override
    public int getItemCount() {
        return listaLibros.size();
    }

    public void eliminarItem(int position) {
        listaLibros.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaLibros.size());
    }

    public void filtradoLibros(String texto) {
        int longitud = texto.length();
        if (longitud == 0) {
            listaLibros.clear();
            listaLibros.addAll(listaOriginal);
        } else {
            List<Libro> collection = listaLibros.stream().filter(i -> i.getTitulo().toLowerCase().contains(texto.toLowerCase())).collect(Collectors.toList());
            listaLibros.clear();
            listaLibros.addAll(collection);
        }
        notifyDataSetChanged();
    }

    public class LibroViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, autor, editorial, genero;
        Button btnEditar, btnEliminar;
        DbLibro dbLibro;

        public LibroViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.viewBookTitle);
            autor = itemView.findViewById(R.id.viewBookAuthor);
            editorial = itemView.findViewById(R.id.viewBookEditorial);
            genero = itemView.findViewById(R.id.viewBookGenre);

            btnEditar = itemView.findViewById(R.id.editButton);
            btnEliminar = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, VerLibro.class);
                intent.putExtra("ID", listaLibros.get(getAdapterPosition()).getId());
                context.startActivity(intent);
            });

            btnEditar.setOnClickListener(v -> {
                Intent intent = new Intent(btnEditar.getContext(), EditarLibro.class);
                intent.putExtra("ID", listaLibros.get(getAdapterPosition()).getId());
                ContextCompat.startActivity(btnEditar.getContext(), intent, null);
            });

            btnEliminar.setOnClickListener(v -> {
                dbLibro = new DbLibro(btnEliminar.getContext());
                dbLibro.eliminarLibro(listaLibros.get(getAdapterPosition()).getId());
                eliminarItem(getAdapterPosition());
            });
        }
    }
}
