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
import isamix.inventario.crud.textil.ListaRopaPorTipo;
import isamix.inventario.db.DbRopa;
import isamix.inventario.db.DbTipoRopa;
import isamix.inventario.modelo.TipoRopa;

public class TipoRopaAdapter extends RecyclerView.Adapter<TipoRopaAdapter.TipoRopaViewHolder> {

    List<TipoRopa> listaTipos;
    List<TipoRopa> listaOriginal;

    public TipoRopaAdapter(List<TipoRopa> listaTiposRopa) {
        this.listaTipos = new ArrayList<>(listaTiposRopa);
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaTiposRopa);
    }

    @NonNull
    @Override
    public TipoRopaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tipo_ropa, null, true);
        return new TipoRopaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipoRopaViewHolder tipoRopaViewHolder, int i) {
        tipoRopaViewHolder.item.setText(listaTipos.get(i).getTipoRopa());
    }

    @Override
    public int getItemCount() {
        return listaTipos.size();
    }

    public class TipoRopaViewHolder extends RecyclerView.ViewHolder {

        TextView item;

        public TipoRopaViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.itemTipoRopa);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ListaRopaPorTipo.class);
                intent.putExtra("TIPO", listaTipos.get(getAdapterPosition()).getTipoRopa());
                v.getContext().startActivity(intent);
            });

            itemView.setOnLongClickListener(v -> {
                DbTipoRopa dbTipoRopa = new DbTipoRopa(v.getContext());
                DbRopa dbRopa = new DbRopa(v.getContext());
                TipoRopa type = dbTipoRopa.getTipoRopaPorId(listaTipos.get(getAdapterPosition()).getId());
                if (type != null) {
                    if (dbRopa.mostrarRopasPorTipo(listaTipos.get(getAdapterPosition()).getTipoRopa()).isEmpty()) {
                        dbTipoRopa.eliminarTipoRopa(type.getId());
                        listaTipos.remove(listaTipos.get(getAdapterPosition()).getId());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), listaTipos.size());
                        Toast.makeText(v.getContext(), "Tipo de ropa vacío borrado con éxito", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(v.getContext(), "No se ha podido eliminar el tipo de ropa porque tiene prendas asociadas", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(v.getContext(), "No se ha podido eliminar el tipo de ropa porque no existe en la base de datos", Toast.LENGTH_LONG).show();
                }
                return false;
            });
        }
    }
}
