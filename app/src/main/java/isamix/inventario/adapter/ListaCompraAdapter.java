package isamix.inventario.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

import isamix.inventario.R;
import isamix.inventario.entity.Producto;

public class ListaCompraAdapter extends RecyclerView.Adapter<ListaCompraAdapter.CompraViewHolder> {

    ArrayList<Producto> listaProductos;
    ArrayList<Producto> listaOriginal;

    public ListaCompraAdapter(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        listaOriginal = new ArrayList<>(listaProductos);
    }

    @NonNull
    @Override
    public CompraViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_item_compra, null, false);
        return new CompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraViewHolder holder, int position) {
        holder.producto.setText(listaProductos.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class CompraViewHolder extends RecyclerView.ViewHolder {

        CheckBox producto;

        public CompraViewHolder(@NonNull View itemView) {
            super(itemView);
            producto = itemView.findViewById(R.id.checkBox);
            producto.setText(listaProductos.toString());

            itemView.setOnClickListener(view -> {
                listaProductos.get(getAdapterPosition()).setParaComprar(false);
                producto.setPaintFlags(producto.getPaintFlags());
            });
        }
    }
}
