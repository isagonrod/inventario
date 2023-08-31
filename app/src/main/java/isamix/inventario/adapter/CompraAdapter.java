package isamix.inventario.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import isamix.inventario.R;
import isamix.inventario.modelo.Producto;

public class CompraAdapter extends RecyclerView.Adapter<CompraAdapter.CompraViewHolder> {
    ArrayList<Producto> listaProductos;
    ArrayList<Producto> listaOriginal;

    public CompraAdapter(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        listaOriginal = new ArrayList<>(listaProductos);
    }

    @NonNull
    @Override
    public CompraViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_compra, viewGroup, false);
        return new CompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraViewHolder holder, int position) {
        holder.txtNombre.setText(listaProductos.get(position).getNombre());
        holder.txtMarca.setText(listaProductos.get(position).getMarca());
        holder.txtTienda.setText(listaProductos.get(position).getTienda());
        holder.txtCategoria.setText(listaProductos.get(position).getCategoria());

        holder.checkBox.setOnClickListener(v -> {
            if (((CheckBox)v).isChecked()) {
                listaProductos.get(position).setParaComprar(0);
                if (!holder.txtCantidad.getText().toString().isEmpty()) {
                    listaProductos.get(position).setCantidad(Integer.parseInt(String.valueOf(holder.txtCantidad.getText())));
                } else {
                    listaProductos.get(position).setCantidad(0);
                }
            } else {
                listaProductos.get(position).setParaComprar(1);
            }
        });
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
        TextView txtNombre, txtMarca, txtTienda, txtCategoria;
        CheckBox checkBox;

        public CompraViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtMarca = itemView.findViewById(R.id.txtMarca);
            txtTienda = itemView.findViewById(R.id.txtTienda);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
