package isamix.inventario.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        holder.checkBox.setDrawingCacheEnabled(false);
        holder.txtCantidad.setText(listaProductos.get(position).getCantidad());
        holder.txtNombre.setText(listaProductos.get(position).getNombre());
        holder.txtPrecio.setText(listaProductos.get(position).getPrecio());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void listaCompraPorTienda(String tienda) {
        int longitud = tienda.length();

        if (longitud == 0) {
            listaProductos.clear();
            listaProductos.addAll(listaOriginal);
        } else {
            List<Producto> collection = listaProductos.stream().filter(i -> i.getTienda().toLowerCase()
                    .contains(tienda.toLowerCase())).collect(Collectors.toList());
            listaProductos.clear();
            listaProductos.addAll(collection);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class CompraViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        EditText txtCantidad;
        TextView txtNombre, txtPrecio;

        public CompraViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);

            itemView.setOnClickListener(view -> {
                checkBox.setDrawingCacheEnabled(true);
                listaProductos.get(getAdapterPosition()).setParaComprar(0);
            });
        }
    }
}
