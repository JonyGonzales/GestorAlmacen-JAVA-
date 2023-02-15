package com.example.gestoralmacen.ui.salidaAdmin;

import static android.content.Context.MODE_PRIVATE;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.gestoralmacen.AddSalidaActivity;
import com.example.gestoralmacen.AddSalidaAdminActivity;
import com.example.gestoralmacen.R;
import com.example.gestoralmacen.adapter.SalidaAdminAdapter;
import com.example.gestoralmacen.model.Salida;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class SalidaAdminFragment extends Fragment {

    private ProgressDialog pDialog;
    private List<Salida> listAnimation = new ArrayList<>();
    private RecyclerView recyclerView;
    private RelativeLayout rcategoria, rproductos;
    private ImageView imagen;
    private TextView text1, text2;

    public static final String MY_PREFS_NAME = "MySession";
    public static final String id = "idKey";
    public static final String nombre = "nombreKey";
    public static final String apellido = "apellidoKey";
    public static final String rol = "rolKey";
    String crol;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

             // Inflate the layout for this fragment
         View root =  inflater.inflate(R.layout.fragment_entrada, container, false);
         setHasOptionsMenu(true);
         pDialog = new ProgressDialog(getActivity());
         pDialog.setCancelable(false);

         imagen = root.findViewById(R.id.emptyCartImg);
         text1 = root.findViewById(R.id.emptyCartText);
         text2 = root.findViewById(R.id.addItemText);

         SharedPreferences shared = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
         crol = (shared.getString(rol, ""));

         recyclerView = root.findViewById(R.id.favoriteResRecyclerView);
         CargarData();

         return root;
    }






    private void CargarData() {
          String url = "https://concursogtd.online/api/ListaSalidas.php";
          showDialog();
          StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  try {
                      hideDialog();
                      JSONArray myJsonArray = new JSONArray(response);
                      for(int i = 0; i<myJsonArray.length(); i++){
                          Salida ob = new Salida();
                          ob.setId(myJsonArray.getJSONObject(i).getInt("id"));
                          ob.setNombre(myJsonArray.getJSONObject(i).getString("nombre"));
                          ob.setFecha(myJsonArray.getJSONObject(i).getString(("fecha")));
                          ob.setFoto(myJsonArray.getJSONObject(i).getString(("foto")));
                          ob.setCantidad(myJsonArray.getJSONObject(i).getString(("cantidad")));
                          ob.setIdProducto(myJsonArray.getJSONObject(i).getString("idProducto"));
                          ob.setIdTipo(myJsonArray.getJSONObject(i).getString("idTipo"));
                          ob.setTipo(myJsonArray.getJSONObject(i).getString("tipo"));
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

      private void setupRecyclerView(List<Salida> listAnimation) {
          SalidaAdminAdapter myadapter = new SalidaAdminAdapter(getActivity(),listAnimation) ;
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
                Intent intent = new Intent(getActivity(), AddSalidaAdminActivity.class );
                intent.putExtra("id","");
                intent.putExtra("idproducto","");
                intent.putExtra("producto","");
                intent.putExtra("fecha","");
                intent.putExtra("cantidad","");
                intent.putExtra("foto","");
                intent.putExtra("idTipo","");
                intent.putExtra("tipo","");
                intent.putExtra("activity","admin");
                startActivity(intent);
              return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
