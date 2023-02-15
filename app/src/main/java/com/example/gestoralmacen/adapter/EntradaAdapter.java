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
import com.example.gestoralmacen.EditarEntradaActivity;
import com.example.gestoralmacen.EditarSalidaActivity;
import com.example.gestoralmacen.R;
import com.example.gestoralmacen.model.Entrada;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class EntradaAdapter extends RecyclerView.Adapter<EntradaAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Entrada> notesList;
    private List<Entrada> movieListFiltered;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView producto;
        public TextView fecha;
        public TextView cantidad;
        public CardView linear1;
        public ImageView foto;

        public MyViewHolder(View view) {
            super(view);
            producto = view.findViewById(R.id.orderedResName);
            cantidad = view.findViewById(R.id.orderedItemsText);
            fecha = view.findViewById(R.id.orderedResAddress);
            foto = view.findViewById(R.id.orderedResImage);
            linear1 = view.findViewById(R.id.imageContainer);
        }

    }


    public EntradaAdapter(Context context, List<Entrada> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.movieListFiltered = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entrada_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Entrada note = notesList.get(position);
        holder.producto.setText("Producto:" +note.getNombre());
        holder.fecha.setText("Fecha Entrada: "+note.getFecha());
        holder.cantidad.setText("Cantidad: "+note.getCantidad());
        Glide.with(context).load("https://concursogtd.online/pages/producto/upload/"+note.getFoto()).into(holder.foto);
        holder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditarEntradaActivity.class);
                intent.putExtra("id",""+note.getId());
                intent.putExtra("idproducto",note.getIdProducto());
                intent.putExtra("producto",note.getNombre());
                intent.putExtra("fecha",note.getFecha());
                intent.putExtra("cantidad",note.getCantidad());
                intent.putExtra("foto",note.getFoto());
                intent.putExtra("idTipo",note.getIdTipo());
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
                    List<Entrada> filteredList = new ArrayList<>();
                    for (Entrada movie : notesList) {
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
                notesList = (ArrayList<Entrada>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

}
