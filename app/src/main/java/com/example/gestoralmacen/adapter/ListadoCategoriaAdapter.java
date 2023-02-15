package com.example.gestoralmacen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.gestoralmacen.AddEntradaActivity;
import com.example.gestoralmacen.AddProductoActivity;
import com.example.gestoralmacen.AddSalidaActivity;
import com.example.gestoralmacen.R;
import com.example.gestoralmacen.model.Categoria;
import com.example.gestoralmacen.model.Producto;
import com.example.gestoralmacen.model.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class ListadoCategoriaAdapter extends RecyclerView.Adapter<ListadoCategoriaAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Categoria> notesList;
    private List<Categoria> movieListFiltered;
    String sproducto, sprecio, sstock, scategoria, sidcategoria;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView producto;
        public CardView linear1;

       public MyViewHolder(View view) {
            super(view);
             producto = view.findViewById(R.id.orderedResName);
             linear1 = view.findViewById(R.id.card);
        }
    }


    public ListadoCategoriaAdapter(Context context, List<Categoria> notesList , String sproducto,
                                   String sprecio, String sstock,
                                   String scategoria, String sidcategoria) {
        this.context = context;
        this.notesList = notesList;
        this.movieListFiltered = notesList;

        this.sproducto = sproducto;
        this.sprecio = sprecio;
        this.sstock = sstock;
        this.scategoria = scategoria;
        this.sidcategoria = sidcategoria;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tipo_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Categoria note = notesList.get(position);
        holder.producto.setText("Categoria: "+note.getCategoria());
        holder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddProductoActivity.class);
                intent.putExtra("producto",sproducto);
                intent.putExtra("precio",sprecio);
                intent.putExtra("stock",sstock);
                intent.putExtra("idcategoria",""+note.getId());
                intent.putExtra("categoria", note.getCategoria());
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
                    List<Categoria> filteredList = new ArrayList<>();
                    for (Categoria movie : notesList) {
                        if (movie.getCategoria().toLowerCase().contains(charString.toLowerCase())) {
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
                notesList = (ArrayList<Categoria>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

}
