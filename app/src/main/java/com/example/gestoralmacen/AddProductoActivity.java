package com.example.gestoralmacen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.gestoralmacen.databinding.ActivityAddProductoBinding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductoActivity extends AppCompatActivity {

    private EditText producto, descripcion, stock, precio, categoria, idcategoria;
    private Button submit;
    private ProgressDialog pDialog;
    private ImageView btnCategoria, foto;
    String bproducto, bprecio, bstock, bidcategoria, bcategoria;

    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 1;
    String KEY_IMAGE = "foto";
    String KEY_NOMBRE = "nombre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_producto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        descripcion = findViewById(R.id.descripcion);
        producto = findViewById(R.id.producto);
        precio = findViewById(R.id.precio);
        stock = findViewById(R.id.stock);
        categoria = findViewById(R.id.categoria);
        idcategoria = findViewById(R.id.idCategoria);


        Bundle param = this.getIntent().getExtras();
        if(param != null){
            bproducto = String.valueOf(param.getString("producto"));
            bprecio = String.valueOf(param.getString("precio"));
            bstock = String.valueOf(param.getString("stock"));
            bidcategoria = String.valueOf(param.getString("idcategoria"));
            bcategoria = String.valueOf(param.getString("categoria"));

            //user.setText(bid):
            producto.setText(""+bproducto);
            precio.setText(bprecio);
            stock.setText(bstock);
            idcategoria.setText(""+bidcategoria);
            categoria.setText(""+bcategoria);
        }

        foto = findViewById(R.id.foto);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
           }
        });


        btnCategoria = findViewById(R.id.btn_categoria);
        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent( AddProductoActivity.this, ListaCategoriaActivity.class );
              intent.putExtra("producto",  producto.getText().toString());
              intent.putExtra("precio", precio.getText().toString());
              intent.putExtra("stock", stock.getText().toString());
              intent.putExtra("idcategoria", idcategoria.getText().toString());
              intent.putExtra("categoria", categoria.getText().toString());
              startActivity(intent);
            }
        });

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                SaveProducto(producto.getText().toString(), descripcion.getText().toString() , precio.getText().toString(), stock.getText().toString(), categoria.getText().toString(), idcategoria.getText().toString());
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


    private void SaveProducto(final String sproducto, final String sdescripcion , final String sprecio, final String sstock, final String scateegoria, final String sidcategoria) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Guardando Producto ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://concursogtd.online/api/registrarProducto.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                producto.setText("");
                precio.setText("");
                stock.setText("");
                idcategoria.setText("");
                categoria.setText("");
                Toast.makeText(AddProductoActivity.this, ""+response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(AddProductoActivity.this, "Error: " +error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                String simagen = getStringImagen(bitmap);
                Map<String, String> params = new HashMap<String, String>();
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


    @Override
     public void onBackPressed() {
         Intent intent = new Intent(AddProductoActivity.this, AdminMainActivity.class );
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
