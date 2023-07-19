package isamix.inventario.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import isamix.inventario.ListaProductoActivity;
import isamix.inventario.ListaProductoPorCategoriasActivity;
import isamix.inventario.MainActivity;
import isamix.inventario.R;
import isamix.inventario.db.DbCategoria;
import isamix.inventario.entity.Categoria;

public class ListaCategoriaAdapter extends RecyclerView.Adapter<ListaCategoriaAdapter.CategoriaViewHolder> {

    List<Categoria> listaCategorias;
    List<Categoria> listaOriginal;

    public ListaCategoriaAdapter(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaCategorias);
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_item_categoria, null, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        holder.viewCategoria.setText(listaCategorias.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    public void deleteItem(int position) {
        listaCategorias.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaCategorias.size());
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView viewCategoria;

        @SuppressLint("NotifyDataSetChanged")
        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            viewCategoria = itemView.findViewById(R.id.viewCategoria);
            itemView.setOnClickListener(view -> {
                Context context = view.getContext();
                Intent intent = new Intent(context, ListaProductoPorCategoriasActivity.class);
                intent.putExtra("CATEGORIA", listaCategorias.get(getAdapterPosition()).getNombre());
                context.startActivity(intent);
            });
        }
    }
}
