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
import com.example.gestoralmacen.adapter.ListadoCategoriaAdapter;
import com.example.gestoralmacen.adapter.ListadoTipoAdapter;
import com.example.gestoralmacen.model.Categoria;
import com.example.gestoralmacen.model.Tipo;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaCategoriaActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private List<Categoria> listAnimation = new ArrayList<>();
    private RecyclerView recyclerView;
    private RelativeLayout rcategoria, rproductos;
    private ImageView imagen;
    private TextView text1, text2;

    String producto, precio, stock , idcategoria, categoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categoria);
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
            producto = param.getString("producto");
            precio = param.getString("precio");
            stock = param.getString("stock");
            idcategoria = param.getString("idcategoria");
            categoria = param.getString("categoria");
        }
        CargarData(producto, precio, stock, idcategoria, categoria);
    }


    private void CargarData(String sproducto, String sprecio, String sstock, String sidcategoria,String scategoria) {
      String url = 	"https://concursogtd.online/api/ListaCategoria.php";
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
                  setupRecyclerView(listAnimation, sproducto, sprecio, sstock, sidcategoria, scategoria);
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

  private void setupRecyclerView(List<Categoria> listAnimation , String pproducto, String pprecio, String pstock, String pcategoria, String pidcategoria) {
      ListadoCategoriaAdapter myadapter = new ListadoCategoriaAdapter(this,listAnimation , pproducto, pprecio , pstock, pcategoria, pidcategoria);
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
