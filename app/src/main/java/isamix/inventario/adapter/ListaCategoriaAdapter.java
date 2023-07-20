package isamix.inventario.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import isamix.inventario.ListaProductoActivity;
import isamix.inventario.ListaProductoPorCategoriasActivity;
import isamix.inventario.MainActivity;
import isamix.inventario.R;
import isamix.inventario.db.DbCategoria;
import isamix.inventario.db.DbProductos;
import isamix.inventario.entity.Categoria;

public class ListaCategoriaAdapter extends RecyclerView.Adapter<ListaCategoriaAdapter.CategoriaViewHolder> {

    List<Categoria> listaCategorias;
    List<Categoria> listaOriginal;

    public ListaCategoriaAdapter(List<Categoria> listaCategorias) {
        this.listaCategorias = new ArrayList<>(listaCategorias);
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaCategorias);
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_item_categoria, null, true);
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

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView viewCategoria;

        @SuppressLint("NotifyDataSetChanged")
        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            viewCategoria = itemView.findViewById(R.id.viewCategoria);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), ListaProductoPorCategoriasActivity.class);
                intent.putExtra("CATEGORIA", listaCategorias.get(getAdapterPosition()).getNombre());
                view.getContext().startActivity(intent);
            });

            itemView.setOnLongClickListener(view -> {
                DbCategoria dbCategoria = new DbCategoria(view.getContext());
                DbProductos dbProductos = new DbProductos(view.getContext());
                Categoria category = dbCategoria.getCategoriaPorId(listaCategorias.get(getAdapterPosition()).getId());

                if (category != null) {
                    if (dbProductos.mostrarProductosPorCategoria(listaCategorias.get(getAdapterPosition()).getNombre()).isEmpty()) {
                        dbCategoria.eliminarCategoria(category.getId());
                        listaCategorias.remove(listaCategorias.get(getAdapterPosition()));
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), listaCategorias.size());
                        Toast.makeText(view.getContext(),
                                "Categoría vacía borrada con éxito",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(view.getContext(),
                                "No se ha podido eliminar la categoría porque tiene productos asociados",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(view.getContext(),
                            "No se ha podido eliminar la categoría porque no existe en la base de datos",
                            Toast.LENGTH_LONG).show();
                }
                return false;
            });
        }
    }
}
