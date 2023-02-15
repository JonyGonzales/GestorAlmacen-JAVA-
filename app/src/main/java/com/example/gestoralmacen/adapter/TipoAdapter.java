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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.gestoralmacen.EditarCategoriaActivity;
import com.example.gestoralmacen.EditarTipoActivity;
import com.example.gestoralmacen.R;
import com.example.gestoralmacen.model.Producto;
import com.example.gestoralmacen.model.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class TipoAdapter extends RecyclerView.Adapter<TipoAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Tipo> notesList;
    private List<Tipo> movieListFiltered;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView producto;
        public TextView precio;
        public CardView linear1;

        public MyViewHolder(View view) {
            super(view);
            producto = view.findViewById(R.id.orderedResName);
            precio = view.findViewById(R.id.orderedResAddress);
            linear1 = view.findViewById(R.id.card);
        }
    }


    public TipoAdapter(Context context, List<Tipo> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.movieListFiltered = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tipo_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Tipo note = notesList.get(position);
        holder.producto.setText("Id: "+note.getId());
        holder.precio.setText("Tipo: "+note.getTipo());
        holder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditarTipoActivity.class);
                intent.putExtra("id",""+note.getId());
                intent.putExtra("tipo",note.getTipo());
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
                    List<Tipo> filteredList = new ArrayList<>();
                    for (Tipo movie : notesList) {
                        if (movie.getTipo().toLowerCase().contains(charString.toLowerCase())) {
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
                notesList = (ArrayList<Tipo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

}
