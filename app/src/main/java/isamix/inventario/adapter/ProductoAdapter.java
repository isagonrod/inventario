package isamix.inventario.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import isamix.inventario.R;
import isamix.inventario.crud.VerProducto;
import isamix.inventario.modelo.Producto;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {List<Producto> listaProductos;
    List<Producto> listaOriginal;

    public ProductoAdapter(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaProductos);
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_producto, null, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        holder.viewNombre.setText(listaProductos.get(position).getNombre());
        holder.viewCantidad.setText(listaProductos.get(position).getCantidad());
        holder.viewPrecio.setText(listaProductos.get(position).getPrecio());
        holder.viewTienda.setText(listaProductos.get(position).getTienda());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filtrado(String txtBuscar) {
        int longitud = txtBuscar.length();

        if (longitud == 0) {
            listaProductos.clear();
            listaProductos.addAll(listaOriginal);
        } else {
            List<Producto> collection = listaProductos.stream().filter(i -> i.getNombre().toLowerCase()
                    .contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            listaProductos.clear();
            listaProductos.addAll(collection);
        }
        notifyDataSetChanged();
    }

    public void eliminarItem(int position) {
        listaProductos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaProductos.size());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewCantidad, viewPrecio, viewTienda;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewCantidad = itemView.findViewById(R.id.viewCantidad);
            viewPrecio = itemView.findViewById(R.id.viewPrecio);
            viewTienda = itemView.findViewById(R.id.viewTienda);

            itemView.setOnClickListener(view -> {
                int itemColor = itemView.getBackground() != null ?
                        ((ColorDrawable) itemView.getBackground()).getColor() : Color.WHITE;

                if (itemColor == Color.WHITE) {
                    itemView.setBackgroundColor(Color.CYAN);
                } else {
                    itemView.setBackgroundColor(Color.WHITE);
                }
            });

            itemView.setOnLongClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, VerProducto.class);
                intent.putExtra("ID", listaProductos.get(getAdapterPosition()).getId());
                context.startActivity(intent);
                return true;
            });
        }
    }
}
