package isamix.inventario.adapter;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import isamix.inventario.R;
import isamix.inventario.crud.EditarProducto;
import isamix.inventario.crud.VerProducto;
import isamix.inventario.db.DbProducto;
import isamix.inventario.modelo.Producto;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    List<Producto> listaProductos;
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
        holder.viewCantidad.setText(String.valueOf(listaProductos.get(position).getCantidad()));
        holder.viewPrecio.setText(String.format("%.2f", listaProductos.get(position).getPrecio()));
        holder.viewTienda.setText(listaProductos.get(position).getTienda());
        holder.viewCategoria.setText(listaProductos.get(position).getCategoria());
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

        TextView viewNombre, viewCantidad, viewPrecio, viewTienda, viewCategoria;
        Button btnCompra, btnEditar, btnEliminar;
        DbProducto dbProducto;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewCantidad = itemView.findViewById(R.id.viewCantidad);
            viewPrecio = itemView.findViewById(R.id.viewPrecio);
            viewTienda = itemView.findViewById(R.id.tienda_pro);
            viewCategoria = itemView.findViewById(R.id.categoria_pro);

            btnCompra = itemView.findViewById(R.id.basketButton);
            btnEditar = itemView.findViewById(R.id.editButton);
            btnEliminar = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(view -> {
                disminuirCantidad();
            });

            itemView.setOnLongClickListener(view -> {
                Context context = view.getContext();
                Intent intent = new Intent(context, VerProducto.class);
                intent.putExtra("ID", listaProductos.get(getAdapterPosition()).getId());
                context.startActivity(intent);
                return true;
            });

            btnCompra.setOnClickListener(view -> {
                dbProducto = new DbProducto(btnCompra.getContext());
                dbProducto.editarProducto(
                        listaProductos.get(getAdapterPosition()).getId(),
                        listaProductos.get(getAdapterPosition()).getNombre(),
                        listaProductos.get(getAdapterPosition()).getCantidad(),
                        listaProductos.get(getAdapterPosition()).getPrecio(),
                        listaProductos.get(getAdapterPosition()).getTienda(),
                        listaProductos.get(getAdapterPosition()).getCategoria(),
                        1
                );
                Toast.makeText(btnCompra.getContext(),
                        listaProductos.get(getAdapterPosition()).getNombre() + "\nañadido a Lista de la Compra",
                        Toast.LENGTH_LONG).show();
            });

            btnEditar.setOnClickListener(view -> {
                Intent intent = new Intent(btnEditar.getContext(), EditarProducto.class);
                intent.putExtra("ID", listaProductos.get(getAdapterPosition()).getId());
                ContextCompat.startActivity(btnEditar.getContext(), intent, null);
            });

            btnEliminar.setOnClickListener(view -> {
                dbProducto = new DbProducto(btnEliminar.getContext());
                dbProducto.eliminarProducto(listaProductos.get(getAdapterPosition()).getId());
                eliminarItem(getAdapterPosition());
            });
        }

        public void disminuirCantidad() {
            String cantidadNueva;
            int cantidad = listaProductos.get(getAdapterPosition()).getCantidad();
            int result = cantidad - 1;
            if (result >= 0) {
                cantidadNueva = String.valueOf(result);
                viewCantidad.setText(cantidadNueva);
            } else if (result < 0) {
                result = 0;
                cantidadNueva = String.valueOf(result);
                viewCantidad.setText(cantidadNueva);
            }
            dbProducto = new DbProducto(itemView.getContext());
            dbProducto.editarCantidad(listaProductos.get(getAdapterPosition()).getId(), result);
        }
    }
}
