package com.example.gestoralmacen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gestoralmacen.databinding.ActivityMainBinding;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button boton;
    private EditText email, password;
  	private ProgressDialog pDialog;

    public static final String id = "idKey";
    public static final String nombre = "nombreKey";
    public static final String apellido = "apellidoKey";
    public static final String rol = "rolKey";

    public static final String MY_PREFS_NAME = "MySession";
    SharedPreferences.Editor editor;
    SharedPreferences openeditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

		pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);
		email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        boton = findViewById(R.id.submit);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser(email.getText().toString(), password.getText().toString());
			}
        });
    }







	private void LoginUser(final String suser, final String scontrasena) {
		String tag_string_req = "req_register";
		pDialog.setMessage("Validando usuario ...");
		showDialog();
		StringRequest strReq = new StringRequest(Request.Method.POST,
						"https://concursogtd.online/api/login.php", new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					hideDialog();
				//	Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_LONG).show();
					try {
					 JSONArray respObj = new JSONArray(response);
					 for(int i = 0; i < respObj.length(); i++){
							JSONObject c = respObj.getJSONObject(i);
							String resultado = c.getString("resultado");
							String sid = c.getString("id");
							String sidentificacion = c.getString("identificacion");
							String snombre = c.getString("nombre");
							String sapellido = c.getString("apellido");
							String scorreo = c.getString("correo");
							String spassword = c.getString("password");
							String stelefono = c.getString("telefonod");
							String srol = c.getString("rol");

							editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
							editor.putString(id, sid);
							editor.putString(nombre, snombre);
							editor.putString(apellido, sapellido);
              editor.putString(rol, srol);
        			editor.commit();

						  if(srol.equals("admin")){
							  Intent intent = new Intent( LoginActivity.this, AdminMainActivity.class );
							  startActivity(intent);
						  }
						 if(srol.equals("personal")){
							 Intent intent = new Intent( LoginActivity.this, MainActivity.class );
							 startActivity(intent);
						 }
					   }
					 } catch (Exception e){
							 e.printStackTrace();
							 Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
					 }
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					hideDialog();
					Toast.makeText(LoginActivity.this, "Error: " +error.getMessage(), Toast.LENGTH_LONG).show();
				}
			}) {
				@Override
				protected Map<String, String> getParams() {
						Map<String, String> params = new HashMap<String, String>();
						params.put("correo", suser);
						params.put("password",scontrasena);
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
}
