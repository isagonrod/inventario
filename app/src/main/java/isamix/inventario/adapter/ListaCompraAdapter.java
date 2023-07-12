package isamix.inventario.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_item_compra, viewGroup, false);
        return new CompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraViewHolder holder, int position) {
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

    public void eliminarItem(int position) {
        listaProductos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaProductos.size());
    }

    public class CompraViewHolder extends RecyclerView.ViewHolder {

        EditText txtCantidad;
        TextView txtNombre, txtPrecio;

        public CompraViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);

            itemView.setOnClickListener(view -> {
                int itemColor = view.getBackground() != null ? ((ColorDrawable) view.getBackground()).getColor() : 0xFFFFFFFF;
                if (itemColor == Color.WHITE) {
                    itemView.setBackgroundColor(Color.YELLOW);
                    listaProductos.get(getAdapterPosition()).setParaComprar(49);
                } else {
                    itemView.setBackgroundColor(Color.WHITE);
                    listaProductos.get(getAdapterPosition()).setParaComprar(48);
                }
            });
        }
    }
}
