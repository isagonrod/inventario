package isamix.inventario.fragments.productos;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import isamix.inventario.R;
import isamix.inventario.model.Producto;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Producto}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private final List<Producto> values;

    public ProductoAdapter(List<Producto> items) {
        this.values = items;
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_producto_lista, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductoViewHolder holder, int position) {
        holder.viewNombre.setText(values.get(position).getNombre());
        holder.viewCantidad.setText(values.get(position).getCantidad());
        holder.viewPrecio.setText((int) values.get(position).getPrecio());
        holder.viewTienda.setText(values.get(position).getTienda());
        holder.viewCategoria.setText(values.get(position).getCategoria());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void deleteItem(int position) {
        values.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView viewNombre, viewCantidad, viewPrecio, viewTienda, viewCategoria;
        Button delete, edit, basket;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewCantidad = itemView.findViewById(R.id.viewCantidad);
            viewPrecio = itemView.findViewById(R.id.viewPrecio);
            viewTienda = itemView.findViewById(R.id.viewTienda);
            viewCategoria = itemView.findViewById(R.id.viewCategoria);
            delete = itemView.findViewById(R.id.deleteButton);
            edit = itemView.findViewById(R.id.editButton);
            basket = itemView.findViewById(R.id.basketButton);

            itemView.setOnClickListener(view -> {
                int itemColor = itemView.getBackground() != null ? ((ColorDrawable) itemView.getBackground()).getColor() : Color.WHITE;
                if (itemColor == Color.WHITE) {
                    itemView.setBackgroundColor(Color.CYAN);
                } else {
                    itemView.setBackgroundColor(Color.WHITE);
                }
            });
        }
    }
}