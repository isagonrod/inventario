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
import isamix.inventario.db.DbRopa;
import isamix.inventario.modelo.Ropa;

public class RopaAdapter extends RecyclerView.Adapter<RopaAdapter.RopaViewHolder> {

    List<Ropa> listaRopa;
    List<Ropa> listaOriginal;

    public RopaAdapter(List<Ropa> listaRopa) {
        this.listaRopa = new ArrayList<>(listaRopa);
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaRopa);
    }

    public void eliminarItem(int position) {
        listaRopa.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaRopa.size());
    }

    @NonNull
    @Override
    public RopaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ropa, null, true);
        return new RopaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RopaViewHolder ropaViewHolder, int i) {
        ropaViewHolder.nombre.setText(listaRopa.get(i).getNombre());
        ropaViewHolder.tipoRopa.setText(listaRopa.get(i).getTipo());
        ropaViewHolder.talla.setText(listaRopa.get(i).getTalla());
        ropaViewHolder.estadoRopa.setText(listaRopa.get(i).getEstado());
    }

    @Override
    public int getItemCount() {
        return listaRopa.size();
    }

    public void filtradoRopa(String inputText) {
        int longitud = inputText.length();
        if (longitud == 0) {
            listaRopa.clear();
            listaRopa.addAll(listaOriginal);
        } else {
            List<Ropa> collection = listaRopa.stream().filter(i -> i.getNombre().toLowerCase().contains(inputText.toLowerCase())).collect(Collectors.toList());
            listaRopa.clear();
            listaRopa.addAll(collection);
        }
        notifyDataSetChanged();
    }

    public class RopaViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, tipoRopa, talla, estadoRopa;
        Button btnCarrito, btnEditar, btnEliminar;
        DbRopa dbRopa;

        public RopaViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.NombreRopa);
            tipoRopa = itemView.findViewById(R.id.TipoRopa);
            talla = itemView.findViewById(R.id.TallaRopa);
            estadoRopa = itemView.findViewById(R.id.EstadoRopa);

            btnCarrito = itemView.findViewById(R.id.basketButton);
            btnEditar = itemView.findViewById(R.id.editButton);
            btnEliminar = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, VerRopa.class);
                intent.putExtra("ID", listaRopa.get(getAdapterPosition()).getId());
                context.startActivity(intent);
            });

            btnEditar.setOnClickListener(v -> {
                Intent intent = new Intent(btnEditar.getContext(), EditarRopa.class);
                intent.putExtra("ID", listaRopa.get(getAdapterPosition()).getId());
                ContextCompat.startActivity(btnEditar.getContext(), intent, null);
            });

            btnEliminar.setOnClickListener(v -> {
                dbRopa = new DbRopa(btnEliminar.getContext());
                dbRopa.eliminarRopa(listaRopa.get(getAdapterPosition()).getId());
                eliminarItem(getAdapterPosition());
            });
        }
    }
}
