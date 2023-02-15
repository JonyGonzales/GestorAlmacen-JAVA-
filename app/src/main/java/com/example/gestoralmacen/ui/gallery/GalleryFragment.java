package com.example.gestoralmacen.ui.gallery;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestoralmacen.AddProductoActivity;
import com.example.gestoralmacen.R;
import com.example.gestoralmacen.adapter.ProductoAdapter;
import com.example.gestoralmacen.model.Producto;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private ProgressDialog pDialog;
    private List<Producto> listAnimation = new ArrayList<>();
    private RecyclerView recyclerView;
    private RelativeLayout rcategoria, rproductos;
    private ImageView imagen;
    private TextView text1, text2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

         View root =  inflater.inflate(R.layout.fragment_gallery, container, false);
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

    private void CargarData() {
          String url = 	"https://concursogtd.online/api/ListaProductos.php";
          showDialog();
          StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  try {
                      hideDialog();
                      JSONArray myJsonArray = new JSONArray(response);
                      for(int i = 0; i<myJsonArray.length(); i++){
                          Producto ob = new Producto();
                          ob.setId(myJsonArray.getJSONObject(i).getInt("id"));
                          ob.setNombre(myJsonArray.getJSONObject(i).getString("nombre"));
                          ob.setFoto(myJsonArray.getJSONObject(i).getString(("foto")));
                          ob.setEstado(myJsonArray.getJSONObject(i).getString(("estado")));
                          ob.setDescripcion(myJsonArray.getJSONObject(i).getString(("descripcion")));
                          ob.setPrecio(myJsonArray.getJSONObject(i).getString(("precio")));
                          ob.setStock(myJsonArray.getJSONObject(i).getString("stock"));
                          ob.setCategoria(myJsonArray.getJSONObject(i).getString("categoria"));
                          ob.setFkCategoria(myJsonArray.getJSONObject(i).getString("fkcategoria"));
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

      private void setupRecyclerView(List<Producto> listAnimation) {
          ProductoAdapter myadapter = new ProductoAdapter(getActivity(),listAnimation) ;
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
