package com.example.gestoralmacen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.gestoralmacen.databinding.ActivityAddTipoBinding;

import java.util.HashMap;
import java.util.Map;

public class AddTipoActivity extends AppCompatActivity {

    private EditText tipo;
    private Button submit;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tipo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        tipo = (EditText) findViewById(R.id.tipo);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            SaveTipo(tipo.getText().toString());
          }
        });
    }



        private void SaveTipo(final String stipo) {
          String tag_string_req = "req_register";
          pDialog.setMessage("Guardando Tipo ...");
          showDialog();
          StringRequest strReq = new StringRequest(Request.Method.POST,
                  "https://concursogtd.online/api/registrarTipo.php", new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                hideDialog();
                tipo.setText("");
                Toast.makeText(AddTipoActivity.this, ""+response, Toast.LENGTH_LONG).show();
              }
            }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(AddTipoActivity.this, "Error: " +error.getMessage(), Toast.LENGTH_LONG).show();
              }
            }) {
              @Override
              protected Map<String, String> getParams() {
                  Map<String, String> params = new HashMap<String, String>();
                  params.put("tipo", stipo);
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
       public void onBackPressed() {
           Intent intent = new Intent(AddTipoActivity.this, AdminMainActivity.class );
           startActivity(intent);
       }

}
