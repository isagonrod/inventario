package isamix.inventario.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import isamix.inventario.R;
import isamix.inventario.crud.juego.EditarJuego;
import isamix.inventario.crud.juego.ListaJuego;
import isamix.inventario.crud.juego.ListaJuegoPorTipo;
import isamix.inventario.crud.juego.VerJuego;
import isamix.inventario.db.DbJuego;
import isamix.inventario.modelo.Juego;

public class JuegoAdapter extends RecyclerView.Adapter<JuegoAdapter.JuegoViewHolder> {

    List<Juego> listaJuegos;
    List<Juego> listaOriginal;

    public JuegoAdapter(List<Juego> listaJuegos) {
        this.listaJuegos = new ArrayList<>(listaJuegos);
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaJuegos);
    }

    @NonNull
    @Override
    public JuegoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_juego, null, true);
        return new JuegoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JuegoViewHolder juegoViewHolder, int i) {
        juegoViewHolder.nombre.setText(listaJuegos.get(i).getNombre());
        juegoViewHolder.marcaJuego.setText(listaJuegos.get(i).getMarca());
        juegoViewHolder.numJugadores.setText(listaJuegos.get(i).getNumJugadores());
    }

    @Override
    public int getItemCount() {
        return listaJuegos.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filtradoJuegos(String texto) {
        int longitud = texto.length();
        if (longitud == 0) {
            listaJuegos.clear();
            listaJuegos.addAll(listaOriginal);
        } else {
            List<Juego> collection = listaJuegos.stream().filter(i -> i.getNombre().toLowerCase().contains(texto.toLowerCase())).collect(Collectors.toList());
            listaJuegos.clear();
            listaJuegos.addAll(collection);
        }
        notifyDataSetChanged();
    }

    public void eliminarItem(int position) {
        listaJuegos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaJuegos.size());
    }

    public class JuegoViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, marcaJuego, numJugadores;
        Button btnEditar, btnEliminar;
        DbJuego dbJuego;

        public JuegoViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.viewGameName);
            marcaJuego = itemView.findViewById(R.id.viewGameBrand);
            numJugadores = itemView.findViewById(R.id.viewGameNum);

            btnEditar = itemView.findViewById(R.id.editButton);
            btnEliminar = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, VerJuego.class);
                intent.putExtra("ID", listaJuegos.get(getAdapterPosition()).getId());
                context.startActivity(intent);
            });

            btnEditar.setOnClickListener(v -> {
                Intent intent = new Intent(btnEditar.getContext(), EditarJuego.class);
                intent.putExtra("ID", listaJuegos.get(getAdapterPosition()).getId());
                ContextCompat.startActivity(btnEditar.getContext(), intent, null);
            });

            btnEliminar.setOnClickListener(v -> {
                dbJuego = new DbJuego(btnEliminar.getContext());
                dbJuego.eliminarJuego(listaJuegos.get(getAdapterPosition()).getId());
                eliminarItem(getAdapterPosition());
            });
        }
    }
}
