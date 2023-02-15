package com.example.gestoralmacen.ui.categoria;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestoralmacen.AddCategoriaActivity;
import com.example.gestoralmacen.AddEntradaActivity;
import com.example.gestoralmacen.R;
import com.example.gestoralmacen.adapter.CategoriaAdapter;
import com.example.gestoralmacen.adapter.ProductoAdapter;
import com.example.gestoralmacen.model.Categoria;
import com.example.gestoralmacen.model.Producto;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class CategoriaFragment extends Fragment {

    private ProgressDialog pDialog;
    private List<Categoria> listAnimation = new ArrayList<>();
    private RecyclerView recyclerView;
    private RelativeLayout rcategoria, rproductos;
    private ImageView imagen;
    private TextView text1, text2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

         View root =  inflater.inflate(R.layout.fragment_categoria, container, false);
         setHasOptionsMenu(true);
         pDialog = new ProgressDialog(getActivity());
         pDialog.setCancelable(false);

         imagen = root.findViewById(R.id.emptyCartImg);
         text1 = root.findViewById(R.id.emptyCartText);
         text2 = root.findViewById(R.id.addItemText);

         recyclerView = root.findViewById(R.id.favoriteResRecyclerView);
         CargarData();

         return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_status, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getActivity(), AddCategoriaActivity.class );
                intent.putExtra("activity","Admin");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


        private void CargarData() {
          String url = "https://concursogtd.online/api/ListaCategoria.php";
          showDialog();
          StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  try {
                      hideDialog();
                      JSONArray myJsonArray = new JSONArray(response);
                      for(int i = 0; i<myJsonArray.length(); i++){
                          Categoria ob = new Categoria();
                          ob.setId(myJsonArray.getJSONObject(i).getInt("id"));
                          ob.setCategoria(myJsonArray.getJSONObject(i).getString("categoria"));
                          listAnimation.add(ob);
                      }
                      setupRecyclerView(listAnimation);
                  } catch (Exception e) {
                      e.printStackTrace();
                      hideDialog();
                   }
              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  hideDialog();

              }
          });
          RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
          requestQueue.add(stringRequest);
      }

      private void showDialog() {
          if (!pDialog.isShowing())
              pDialog.show();
      }
      private void hideDialog() {
          if (pDialog.isShowing())
              pDialog.dismiss();
      }

      private void setupRecyclerView(List<Categoria> listAnimation) {
          CategoriaAdapter myadapter = new CategoriaAdapter(getActivity(),listAnimation) ;
          int numItems =  myadapter.getItemCount();
          if(numItems == 0){
              recyclerView.setVisibility(View.GONE);
              imagen.setVisibility(View.VISIBLE);
              text1.setVisibility(View.VISIBLE);
              text2.setVisibility(View.VISIBLE);
          } else{
              recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
              recyclerView.setAdapter(myadapter);
              recyclerView.setVisibility(View.VISIBLE);
              imagen.setVisibility(View.GONE);
              text1.setVisibility(View.GONE);
              text2.setVisibility(View.GONE);
          }
      }
}
