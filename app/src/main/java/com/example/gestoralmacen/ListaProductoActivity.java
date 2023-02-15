package com.example.gestoralmacen;

import static android.content.Context.MODE_PRIVATE;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestoralmacen.adapter.ListadoProductoAdapter;
import com.example.gestoralmacen.model.Producto;
import com.example.gestoralmacen.model.Tipo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaProductoActivity extends AppCompatActivity {

      private ProgressDialog pDialog;
      private List<Producto> listAnimation = new ArrayList<>();
      private RecyclerView recyclerView;
      private RelativeLayout rcategoria, rproductos;
      private ImageView imagen;
      private TextView text1, text2;
      String id, fecha,  idproducto ,producto, cantidad, idtipo ,tipo ,activit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lista_producto);
      Toolbar toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      Window window = this.getWindow();
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

      pDialog = new ProgressDialog(this);
      pDialog.setCancelable(false);

      imagen = findViewById(R.id.emptyCartImg);
      text1 = findViewById(R.id.emptyCartText);
      text2 = findViewById(R.id.addItemText);

      recyclerView = findViewById(R.id.favoriteResRecyclerView);

      Bundle param = this.getIntent().getExtras();
      if(param != null){
          id = param.getString("id");
          fecha = param.getString("fecha");
          idproducto = param.getString("idproducto");
          producto = param.getString("producto");
          cantidad = param.getString("cantidad");
          idtipo = param.getString("idtipo");
          tipo = param.getString("tipo");
          activit = param.getString("activity");

      }

      CargarData(id, idproducto ,producto, cantidad, fecha, idtipo ,tipo, activit);
    }



    private void CargarData(String sid, String sidproducto, String sproducto, String scantidad, String sfecha,String sidtipo ,String stipo, String sactivit) {
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
                      ob.setFkCategoria(myJsonArray.getJSONObject(i).getString("fkcategoria"));
                      listAnimation.add(ob);
                  }
                  setupRecyclerView(listAnimation, sid, sidproducto, sproducto, scantidad, sfecha, sidtipo ,stipo, sactivit);
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
      RequestQueue requestQueue = Volley.newRequestQueue(this);
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

  private void setupRecyclerView(List<Producto> listAnimation , String pid, String pidproducto, String pproducto, String pcantidad, String pfecha, String pidtipo, String ptipo, String  pactivit) {
      ListadoProductoAdapter myadapter = new ListadoProductoAdapter(this,listAnimation , pid, pidproducto ,pproducto, pcantidad, pfecha, pidtipo ,ptipo, pactivit);
      int numItems =  myadapter.getItemCount();
      if(numItems == 0){
          recyclerView.setVisibility(View.GONE);
          imagen.setVisibility(View.VISIBLE);
          text1.setVisibility(View.VISIBLE);
          text2.setVisibility(View.VISIBLE);
      } else{
          recyclerView.setLayoutManager(new LinearLayoutManager(this));
          recyclerView.setAdapter(myadapter);
          recyclerView.setVisibility(View.VISIBLE);
          imagen.setVisibility(View.GONE);
          text1.setVisibility(View.GONE);
          text2.setVisibility(View.GONE);
      }
  }

}
