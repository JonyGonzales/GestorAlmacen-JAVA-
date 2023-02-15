package com.example.gestoralmacen;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditarProductActivity extends AppCompatActivity {

    String bid, bnombre, bprecio, bstock, bdescripcion, bestado, bfkcategoria, bcategoria, bfoto;
    private EditText idsalida,idproducto, producto, descripcion , precio, stock, idcategoria, categoria;
    private ImageView foto;
    private Button submit;
    ProgressDialog pDialog;

    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 1;
    String KEY_IMAGE = "foto";
    String KEY_NOMBRE = "nombre";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        idproducto = (EditText) findViewById(R.id.idProducto);
        producto = (EditText) findViewById(R.id.producto);
        descripcion = (EditText) findViewById(R.id.descripcion);
        precio = (EditText) findViewById(R.id.precio);
        stock = (EditText) findViewById(R.id.stock);
        categoria = (EditText) findViewById(R.id.categoria);
        idcategoria = (EditText) findViewById(R.id.idCategoria);
        foto = (ImageView) findViewById(R.id.foto);

        Bundle param = this.getIntent().getExtras();
        if(param != null){
            bid = String.valueOf(param.getString("id"));
            bnombre  = String.valueOf(param.getString("nombre"));
            bprecio = String.valueOf(param.getString("precio"));
            bstock = String.valueOf(param.getString("stock"));
            bdescripcion = String.valueOf(param.getString("descripcion"));
            bestado = String.valueOf(param.getString("estado"));
            bcategoria = String.valueOf(param.getString("categoria"));
            bfkcategoria = String.valueOf(param.getString("fkcategoria"));
            bfoto = String.valueOf(param.getString("foto"));

            idproducto.setText(bid);
            producto.setText(bnombre);
            precio.setText(bprecio);
            stock.setText(bstock);
            descripcion.setText(bdescripcion);
            idcategoria.setText(bfkcategoria);
            categoria.setText(bcategoria);
            Glide.with(this).load("https://concursogtd.online/pages/producto/upload/"+bfoto).into(foto);

        }

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        foto = findViewById(R.id.foto);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
           }
        });


        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            EditProducto(idproducto.getText().toString(),"",producto.getText().toString(), descripcion.getText().toString() , precio.getText().toString(), stock.getText().toString(), categoria.getText().toString(), idcategoria.getText().toString());
          }
        });
    }



        public String getStringImagen(Bitmap bmp) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }

        private void showFileChooser() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Seleciona imagen"), PICK_IMAGE_REQUEST);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri filePath = data.getData();
                try {
                    //Cómo obtener el mapa de bits de la Galería
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    //Configuración del mapa de bits en ImageView
                    foto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
                    EliminarProducto(idproducto.getText().toString());
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



  private void EditProducto(final String sid, final String simagen, final String sproducto, final String sdescripcion , final String sprecio, final String sstock, final String scateegoria, final String sidcategoria) {
      String tag_string_req = "req_register";
      pDialog.setMessage("Editando Producto ...");
      showDialog();
      StringRequest strReq = new StringRequest(Request.Method.POST,
              "https://concursogtd.online/api/editarProducto.php", new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
              hideDialog();
              producto.setText("");
              precio.setText("");
              stock.setText("");
              idcategoria.setText("");
              categoria.setText("");
              Toast.makeText(EditarProductActivity.this, ""+response, Toast.LENGTH_LONG).show();
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              hideDialog();
              Toast.makeText(EditarProductActivity.this, "Error: " +error.getMessage(), Toast.LENGTH_LONG).show();
          }
      }) {
          @Override
          protected Map<String, String> getParams() {
              String simagen = getStringImagen(bitmap);
              Map<String, String> params = new HashMap<String, String>();
              params.put("id", sid);
              params.put("foto", simagen);
              params.put("descripcion", sdescripcion.toString());
              params.put("producto", sproducto.toString());
              params.put("precio", sprecio.toString());
              params.put("stock",sstock.toString());
              params.put("categoria",scateegoria);
              params.put("idcategoria",sidcategoria);
              return params;
          }
      };
      AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
  }






    private void EliminarProducto(final String sidproducto) {
      String tag_string_req = "req_register";
      pDialog.setMessage("Eliminando Producto ...");
      showDialog();
      StringRequest strReq = new StringRequest(Request.Method.POST,
              "https://concursogtd.online/api/eliminarProducto.php", new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            hideDialog();

          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            hideDialog();
            Toast.makeText(EditarProductActivity.this, "Error: " +error.getMessage(), Toast.LENGTH_LONG).show();
          }
        }) {
          @Override
          protected Map<String, String> getParams() {
              Map<String, String> params = new HashMap<String, String>();
              params.put("idproducto", sidproducto);
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
   Intent intent = new Intent(EditarProductActivity.this, AdminMainActivity.class);
   startActivity(intent);
  }
}
