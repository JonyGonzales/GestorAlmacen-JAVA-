package com.example.gestoralmacen;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EditarEntradaAdminActivity extends AppCompatActivity {

    String bid, bidproducto, bproducto, bfecha, bfoto ,bcantidad, btipo, bidtipo;
    private EditText identrada, user, fecha, idproducto, producto, cantidad, idtipo, tipo;
    private Button submit;
    public static final String MY_PREFS_NAME = "MySession";
    public static final String id = "idKey";
    public static final String nombre = "nombreKey";
    public static final String apellido = "apellidoKey";
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_entrada);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String channel = (shared.getString(id, ""));

        identrada = (EditText) findViewById(R.id.identrada);
        user = (EditText) findViewById(R.id.user);
        fecha = (EditText) findViewById(R.id.fecha);
        producto = (EditText) findViewById(R.id.producto);
        idproducto = (EditText) findViewById(R.id.idProducto);
        cantidad = (EditText) findViewById(R.id.cantidad);
        idtipo = (EditText) findViewById(R.id.idTipo);
        tipo = (EditText) findViewById(R.id.tipo);


        Bundle param = this.getIntent().getExtras();
        if(param != null){
            bid = String.valueOf(param.getString("id"));
            bidproducto = String.valueOf(param.getString("idproducto"));
            bproducto = String.valueOf(param.getString("producto"));
            bfecha = String.valueOf(param.getString("fecha"));
            bcantidad = String.valueOf(param.getString("cantidad"));
            bfoto = String.valueOf(param.getString("foto"));
            bidtipo = String.valueOf(param.getString("idTipo"));
            btipo = String.valueOf(param.getString("tipo"));

            identrada.setText(""+bid);
            idproducto.setText(""+bidproducto);
            producto.setText(bproducto);
            fecha.setText(bfecha);
            cantidad.setText(""+bcantidad);
            idtipo.setText(""+bidtipo);
            tipo.setText(btipo);
        }



        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            EditEntrada(channel,idproducto.getText().toString(), fecha.getText().toString(), cantidad.getText().toString(), idtipo.getText().toString());
          }
        });
    }



    private void EditEntrada(final String sid, final String sidproducto, final String sfecha, final String scantidad, final String sidtipo) {
  		String tag_string_req = "req_register";
  		pDialog.setMessage("Editando Entrada ...");
  		showDialog();
  		StringRequest strReq = new StringRequest(Request.Method.POST,
  						"https://concursogtd.online/api/editarEntrada.php", new Response.Listener<String>() {
  				@Override
  				public void onResponse(String response) {
  					hideDialog();
            idproducto.setText("");
            producto.setText("");
            fecha.setText("");
            cantidad.setText("");
            idtipo.setText("");
            tipo.setText("");

  					Toast.makeText(EditarEntradaAdminActivity.this, ""+response, Toast.LENGTH_LONG).show();
  				}
  			}, new Response.ErrorListener() {
  				@Override
  				public void onErrorResponse(VolleyError error) {
  					hideDialog();
  					Toast.makeText(EditarEntradaAdminActivity.this, "Error: " +error.getMessage(), Toast.LENGTH_LONG).show();
  				}
  			}) {
  				@Override
  				protected Map<String, String> getParams() {
  						Map<String, String> params = new HashMap<String, String>();
              params.put("user", sid);
              params.put("tipo", sidtipo.toString());
              params.put("producto", sidproducto.toString());
              params.put("fecha",sfecha);
              params.put("cantidad",scantidad);
  						return params;
  				}
  		};
  	 	AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
  	}


	private void showDialog() {
	if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hideDialog() {
		if (pDialog.isShowing())
				pDialog.dismiss();
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
         if (id == R.id.action_add) {
             AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
             dialogo1.setTitle("Mensage");
             dialogo1.setMessage("¿Deseas elimininar este registro ?");
             dialogo1.setCancelable(false);
             dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialogo1, int id) {
                     EliminarEntrada(identrada.getText().toString(), idproducto.getText().toString(), cantidad.getText().toString());
                 }
             });
             dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialogo1, int id) {
                     dialogo1.dismiss();
                 }
             });
             dialogo1.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  private void EliminarEntrada(final String sidentrada, final String sidproducto, final String scantidad) {
    String tag_string_req = "req_register";
    pDialog.setMessage("Eliminando Entrada ...");
    showDialog();
    StringRequest strReq = new StringRequest(Request.Method.POST,
            "https://concursogtd.online/api/eliminarEntrada.php", new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
          hideDialog();

        }
      }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
          hideDialog();
          Toast.makeText(EditarEntradaAdminActivity.this, "Error: " +error.getMessage(), Toast.LENGTH_LONG).show();
        }
      }) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("identrada", sidentrada);
            params.put("idproducto", sidproducto);
            params.put("cantidad", scantidad);
            return params;
        }
    };
    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
  }





  @Override
   public void onBackPressed() {
     Intent intent = new Intent(EditarEntradaAdminActivity.this, AdminMainActivity.class );
     startActivity(intent);
   }

}
