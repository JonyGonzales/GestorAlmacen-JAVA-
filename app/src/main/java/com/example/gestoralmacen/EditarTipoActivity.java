package com.example.gestoralmacen;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class EditarTipoActivity extends AppCompatActivity {

    String bid, btipo;
    private EditText idtipo, tipo;
    private Button submit;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tipo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        idtipo = (EditText) findViewById(R.id.idtipo);
        tipo = (EditText) findViewById(R.id.tipo);

        Bundle param = this.getIntent().getExtras();
        if(param != null){
            bid = String.valueOf(param.getString("id"));
            btipo = String.valueOf(param.getString("tipo"));

            idtipo.setText(""+bid);
            tipo.setText(btipo);
        }


        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            EditTipo(idtipo.getText().toString(), tipo.getText().toString());
          }
        });
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
                    EliminarTipo(idtipo.getText().toString());
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




    private void EditTipo(final String sidtipo, final String stipo) {
      String tag_string_req = "req_register";
      pDialog.setMessage("Editando Tipo ...");
      showDialog();
      StringRequest strReq = new StringRequest(Request.Method.POST,
              "https://concursogtd.online/api/editarTipo.php", new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            hideDialog();

            Toast.makeText(EditarTipoActivity.this, ""+response, Toast.LENGTH_LONG).show();
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            hideDialog();
            Toast.makeText(EditarTipoActivity.this, "Error: " +error.getMessage(), Toast.LENGTH_LONG).show();
          }
        }) {
          @Override
          protected Map<String, String> getParams() {
              Map<String, String> params = new HashMap<String, String>();
              params.put("idtipo", sidtipo);
              params.put("tipo", stipo);
              return params;
          }
      };
      AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }




      private void EliminarTipo(final String sidtipo) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Eliminando Tipo ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://concursogtd.online/api/eliminarTipo.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              hideDialog();

            }
          }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              hideDialog();
              Toast.makeText(EditarTipoActivity.this, "Error: " +error.getMessage(), Toast.LENGTH_LONG).show();
            }
          }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idtipo", sidtipo);
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
     Intent intent = new Intent(EditarTipoActivity.this, AdminMainActivity.class);
     startActivity(intent);
    }


}
