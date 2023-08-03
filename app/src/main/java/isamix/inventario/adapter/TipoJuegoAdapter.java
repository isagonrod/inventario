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
import isamix.inventario.crud.juego.ListaJuegoPorTipo;
import isamix.inventario.crud.juego.ListaTipoJuego;
import isamix.inventario.db.DbJuego;
import isamix.inventario.db.DbTipoJuego;
import isamix.inventario.modelo.TipoJuego;

public class TipoJuegoAdapter extends RecyclerView.Adapter<TipoJuegoAdapter.TipoViewHolder> {

    List<TipoJuego> listaTipos;
    List<TipoJuego> listaOriginal;

    public TipoJuegoAdapter(List<TipoJuego> listaTipos) {
        this.listaTipos = new ArrayList<>(listaTipos);
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaTipos);
    }

    @NonNull
    @Override
    public TipoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tipo, null, true);
        return new TipoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipoViewHolder tipoViewHolder, int i) {
        tipoViewHolder.itemTipo.setText(listaTipos.get(i).getTipo());
    }

    @Override
    public int getItemCount() {
        return listaTipos.size();
    }

    public class TipoViewHolder extends RecyclerView.ViewHolder {

        TextView itemTipo;

        public TipoViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTipo = itemView.findViewById(R.id.itemTipo);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ListaJuegoPorTipo.class);
                intent.putExtra("TIPO", listaTipos.get(getAdapterPosition()).getTipo());
                v.getContext().startActivity(intent);
            });

            itemView.setOnLongClickListener(v -> {
                DbTipoJuego dbTipoJuego = new DbTipoJuego(v.getContext());
                DbJuego dbJuego = new DbJuego(v.getContext());
                TipoJuego type = dbTipoJuego.getTipoJuegoPorId(listaTipos.get(getAdapterPosition()).getId());
                if (type != null) {
                    if (dbJuego.mostrarJuegosPorTipo(listaTipos.get(getAdapterPosition()).getTipo()).isEmpty()) {
                        dbTipoJuego.eliminarTipoJuego(type.getId());
                        listaTipos.remove(listaTipos.get(getAdapterPosition()));
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), listaTipos.size());
                        Toast.makeText(v.getContext(), "Tipo de juego vacío borrado con éxito", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(v.getContext(), "No se ha podido eliminar el tipo de juego porque tiene juegos asociados", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(v.getContext(), "No se ha podido eliminar el tipo de juego porque no existe en la base de datos", Toast.LENGTH_LONG).show();
                }
                return false;
            });
        }
    }
}
