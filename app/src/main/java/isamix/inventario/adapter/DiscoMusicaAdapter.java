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
import isamix.inventario.db.DbDiscoMusica;
import isamix.inventario.db.DbPelicula;
import isamix.inventario.modelo.DiscoMusica;
import isamix.inventario.modelo.Pelicula;

public class DiscoMusicaAdapter extends RecyclerView.Adapter<DiscoMusicaAdapter.DiscoViewHolder> {

    List<DiscoMusica> listaDiscos;
    List<DiscoMusica> listaOriginal;

    public DiscoMusicaAdapter(List<DiscoMusica> listaDiscos) {
        this.listaDiscos = new ArrayList<>(listaDiscos);
        this.listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaDiscos);
    }

    @NonNull
    @Override
    public DiscoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_disco, null, true);
        return new DiscoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoViewHolder discoViewHolder, int i) {
        discoViewHolder.titulo.setText(listaDiscos.get(i).getTitulo());
        discoViewHolder.director.setText(listaDiscos.get(i).getArtista_grupo());
        discoViewHolder.fecha.setText(listaDiscos.get(i).getFechaLanzamiento());
    }

    @Override
    public int getItemCount() {
        return listaDiscos.size();
    }

    public void eliminarItem(int position) {
        listaDiscos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaDiscos.size());
    }

    public void filtrarDiscos(String texto) {
        int longitud = texto.length();
        if (longitud == 0) {
            listaDiscos.clear();
            listaDiscos.addAll(listaOriginal);
        } else {
            List<DiscoMusica> collection = listaDiscos.stream().filter(i -> i.getTitulo().toLowerCase().contains(texto.toLowerCase())).collect(Collectors.toList());
            listaDiscos.clear();
            listaDiscos.addAll(collection);
        }
        notifyDataSetChanged();
    }

    public class DiscoViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, artista, fecha;
        Button btnEditar, btnEliminar;
        DbDiscoMusica dbDiscoMusica;

        public DiscoViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.viewDiscTitle);
            artista = itemView.findViewById(R.id.viewDiscArtist);
            fecha = itemView.findViewById(R.id.viewDiscYear);

            btnEditar = itemView.findViewById(R.id.editButton);
            btnEliminar = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, VerDisco.class);
                intent.putExtra("ID", listaDiscos.get(getAdapterPosition()).getId());
                context.startActivity(intent);
            });

            btnEditar.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), EditarDisco.class);
                intent.putExtra("ID", listaDiscos.get(getAdapterPosition()).getId());
                ContextCompat.startActivity(btnEditar.getContext(), intent, null);
            });

            btnEliminar.setOnClickListener(v -> {
                dbDiscoMusica = new DbDiscoMusica(btnEliminar.getContext());
                dbDiscoMusica.eliminarDiscoMusica(listaDiscos.get(getAdapterPosition()).getId());
                eliminarItem(getAdapterPosition());
            });
        }
    }
}
