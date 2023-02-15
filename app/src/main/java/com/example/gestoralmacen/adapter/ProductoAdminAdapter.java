package com.example.gestoralmacen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.gestoralmacen.EditarProductActivity;
import com.example.gestoralmacen.EditarSalidaActivity;
import com.example.gestoralmacen.R;
import com.example.gestoralmacen.model.Producto;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class ProductoAdminAdapter extends RecyclerView.Adapter<ProductoAdminAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Producto> notesList;
    private List<Producto> movieListFiltered;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView producto;
        public TextView precio;
        public TextView stock;
        public TextView categoria;
        public CardView linear1;
        public ImageView foto;

        public MyViewHolder(View view) {
            super(view);
            foto = view.findViewById(R.id.orderedResImage);
            producto = view.findViewById(R.id.orderedResName);
            precio = view.findViewById(R.id.orderedResAddress);
            stock = view.findViewById(R.id.orderedItemsText);
            categoria = view.findViewById(R.id.orderedTimeStamp);
            linear1 = view.findViewById(R.id.imageContainer);
        }
    }


    public ProductoAdminAdapter(Context context, List<Producto> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.movieListFiltered = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.producto_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Producto note = notesList.get(position);
        holder.producto.setText("Producto: "+note.getNombre());
        holder.precio.setText("Precio: "+note.getPrecio());
        holder.stock.setText("Stock: "+note.getStock());
        holder.categoria.setText("Categoria: "+note.getCategoria());
        Glide.with(context).load("https://concursogtd.online/pages/producto/upload/"+note.getFoto()).into(holder.foto);
        holder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditarProductActivity.class);
                intent.putExtra("id",""+note.getId());
                intent.putExtra("nombre",note.getNombre());
                intent.putExtra("precio",note.getPrecio());
                intent.putExtra("stock",note.getStock());
                intent.putExtra("descripcion",note.getDescripcion());
                intent.putExtra("estado",note.getEstado());
                intent.putExtra("categoria",note.getCategoria());
                intent.putExtra("fkcategoria",note.getFkCategoria());
                intent.putExtra("foto",note.getFoto());
                context.startActivity(intent);
            }
        });
    }



    private void EliminarProducto(int id, int position) {
      //  mDatabase.deleteProducto(id);
        notesList.remove(position);
        this.notifyItemRemoved(position);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    notesList = movieListFiltered;
                } else {
                    List<Producto> filteredList = new ArrayList<>();
                    for (Producto movie : notesList) {
                        if (movie.getNombre().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                         //   Log.e(" moview tet rre"," Cliente "+movie.getNombre()+" "+movie.getNombre().toString());
                        }
                    }
                    notesList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = notesList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notesList = (ArrayList<Producto>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

}
