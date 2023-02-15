package com.example.gestoralmacen;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import static android.content.Context.MODE_PRIVATE;


public class AddSalidaAdminActivity extends AppCompatActivity {

    private Button boton;
    private EditText email, password;
  	private ProgressDialog pDialog;
    private EditText idproducto, producto, fecha, cantidad, idtipo ,tipo, user;
    private Button submit, addFecha;
    public static final String MY_PREFS_NAME = "MySession";
    public static final String id = "idKey";
    public static final String nombre = "nombreKey";
    public static final String apellido = "apellidoKey";
  	private ImageView btn_fecha, btn_producto, btn_tipo;
    String bid, bfecha, bidproducto, bproducto, bcantidad, bidtipo, btipo, bactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_salida);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

       SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
       String channel = (shared.getString(id, ""));

      idproducto = (EditText) findViewById(R.id.idProducto);
      producto = (EditText) findViewById(R.id.producto);
      fecha =  (EditText) findViewById(R.id.fecha);
      cantidad =  (EditText) findViewById(R.id.cantidad);
      idtipo = (EditText) findViewById(R.id.idTipo);
      tipo =  (EditText) findViewById(R.id.tipo);
      user = findViewById(R.id.user);
      submit = findViewById(R.id.submit);
      user.setText(""+channel);

      Bundle param = this.getIntent().getExtras();
      if(param != null){
          bid = String.valueOf(param.getString("id"));
          bfecha = String.valueOf(param.getString("fecha"));
          bidproducto = String.valueOf(param.getString("idproducto"));
          bproducto = String.valueOf(param.getString("producto"));
          bcantidad = String.valueOf(param.getString("cantidad"));
          bidtipo = String.valueOf(param.getString("idtipo"));
          btipo = String.valueOf(param.getString("tipo"));
          bactivity = String.valueOf(param.getString("activity"));

          //user.setText(bid):
          idproducto.setText(""+bidproducto);
          producto.setText(bproducto);
          fecha.setText(bfecha);
          cantidad.setText(bcantidad);
          idtipo.setText(""+bidtipo);
          tipo.setText(btipo);
      }

      btn_producto = (ImageView) findViewById(R.id.btn_producto);
      btn_tipo = (ImageView) findViewById(R.id.btn_tipo);
      btn_fecha = (ImageView) findViewById(R.id.btn_fecha);

		btn_producto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent( AddSalidaAdminActivity.this, ListaProductoActivity.class );
        intent.putExtra("id",  user.getText().toString());
        intent.putExtra("fecha", fecha.getText().toString());
        intent.putExtra("idproducto", ""+idproducto.getText().toString());
        intent.putExtra("producto", producto.getText().toString());
        intent.putExtra("cantidad", cantidad.getText().toString());
        intent.putExtra("idtipo", ""+idtipo.getText().toString());
        intent.putExtra("tipo", tipo.getText().toString());
        intent.putExtra("activity", "Salida");
        startActivity(intent);
			}
		});

		btn_tipo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent( AddSalidaAdminActivity.this, ListaTipoActivity.class );
        intent.putExtra("id",  user.getText().toString());
        intent.putExtra("fecha", fecha.getText().toString());
        intent.putExtra("idproducto", ""+idproducto.getText().toString());
        intent.putExtra("producto", producto.getText().toString());
        intent.putExtra("cantidad", cantidad.getText().toString());
        intent.putExtra("idtipo", ""+idtipo.getText().toString());
        intent.putExtra("tipo", tipo.getText().toString());
        intent.putExtra("activity", "Salida");
        startActivity(intent);
			}
		});


		btn_fecha.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showDatePickerDialog();
			}
		});

        submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            SaveSalida(channel,idproducto.getText().toString(), fecha.getText().toString(), cantidad.getText().toString(), idtipo.getText().toString());
          }
        });
    }

	private void showDatePickerDialog() {
		DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month, int day) {
				// +1 because January is zero
        final String selectedDate = year + "-" + (month+1) + "-" + day;
     		fecha.setText(selectedDate);
			}
		});

		newFragment.show(this.getSupportFragmentManager(), "datePicker");
	}



  private void SaveSalida(final String sid, final String sidproducto, final String sfecha, final String scantidad, final String sidtipo ) {
		String tag_string_req = "req_register";
		pDialog.setMessage("Guardando Salida ...");
		showDialog();
		StringRequest strReq = new StringRequest(Request.Method.POST,
						"https://concursogtd.online/api/registrarSalida.php", new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					hideDialog();
          idproducto.setText("");
          producto.setText("");
          fecha.setText("");
          cantidad.setText("");
          idtipo.setText("");
          tipo.setText("");
					Toast.makeText(AddSalidaAdminActivity.this, ""+response, Toast.LENGTH_LONG).show();
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					hideDialog();
					Toast.makeText(AddSalidaAdminActivity.this, "Error: " +error.getMessage(), Toast.LENGTH_LONG).show();
				}
			}) {
				@Override
				protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", sid);
                params.put("producto", sidproducto);
                params.put("tipo", sidtipo);
                params.put("fecha",sfecha);
                params.put("cantidad",scantidad);
                return params;
				}
		};
	 	AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
	}



  @Override
   public void onBackPressed() {
        Intent intent = new Intent(AddSalidaAdminActivity.this, AdminMainActivity.class );
        startActivity(intent);  
   }


	private void showDialog() {
	if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hideDialog() {
		if (pDialog.isShowing())
				pDialog.dismiss();
	}
}
