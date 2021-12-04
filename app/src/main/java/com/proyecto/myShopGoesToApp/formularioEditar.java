package com.proyecto.myShopGoesToApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyectoapp1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class formularioEditar extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private EditText et1, et2, et3, et4, et5;
    private Spinner sp1;
    private ImageView imgView;

    private Bitmap bmp1, bmp2;

    private ImageButton btnCamara;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_editar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et1 = findViewById(R.id.TxtnomproducEditar);
        et2 = findViewById(R.id.TxtCantidadEditar);
        et3 = findViewById(R.id.TextPrecioEditar);
        et4 = findViewById(R.id.txtDescripcionEditar);
        et5 = findViewById(R.id.txtgarantiasPrdEditarr);

        sp1 = findViewById(R.id.spinnerEditar);

        btnCamara = findViewById(R.id.btnCamaraEditar);
        imgView = findViewById(R.id.imageViewEditar);
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });


    }


    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }
    }

    public void abrirGaleriaedit(View view){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    //navegación a actividad de confirmacion editado
    public void EditarProducto(View view) {
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.getDatabase().getReference().child("Productos").orderByChild("nomProduct").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nomproduc, cantproduc, precio, catproduc, descproduc, garanproduc, imagen;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Productos producto = dataSnapshot.getValue(Productos.class);
                    nomproduc = producto.getNomproduc();
                    cantproduc = producto.getCantidad();
                    precio = producto.getPrecio();
                    catproduc = producto.getCatproducto();
                    descproduc = producto.getDescproduc();
                    garanproduc = producto.getGaranproduc();
                    imagen = producto.getImagen();

                    et1.setText(nomproduc);
                    et2.setText(cantproduc);
                    et3.setText(precio);
                    sp1.getSelectedItem().equals(catproduc);
                    et4.setText(descproduc);
                    et5.setText(garanproduc);
                    Bitmap decodedByte = null;
                    byte[] decodeString;
                    decodeString = Base64.decode(imagen, Base64.DEFAULT);
                    decodedByte = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
                    imgView.setImageBitmap(decodedByte);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        String nomproduct = et1.getText().toString();
        String cantidadproduct = et2.getText().toString();
        String precioproduct = et3.getText().toString();
        String catproduct = sp1.getSelectedItem().toString();
        String descriproduct = et4.getText().toString();
        String garantiaproduct = et5.getText().toString();

        String imgCodificada="";

        imgView.buildDrawingCache(true);
        bmp1=imgView.getDrawingCache(true);
        bmp2= Bitmap.createScaledBitmap(bmp1,imgView.getWidth(), imgView.getHeight(),true);
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        bmp2.compress(Bitmap.CompressFormat.PNG,25, baos);
        byte[] imagen = baos.toByteArray();
        imgCodificada= Base64.encodeToString(imagen, Base64.DEFAULT);

        if (!nomproduct.equals("") && !cantidadproduct.equals("") && !precioproduct.equals("") && !catproduct.equals("Seleccione...") && !descriproduct.equals("") && !garantiaproduct.equals("") && imgView.getDrawable()!= null) {
            myRef=FirebaseDatabase.getInstance().getReference();
            myRef.child("Productos").orderByChild("nomproduc").equalTo(nomproduct).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            et1.setText("");
            et2.setText("");
            et3.setText("");
            sp1.setSelection(0);
            et4.setText("");
            et5.setText("");

            imgView.setImageBitmap(null);
            imgView.destroyDrawingCache();
            imgView.setImageDrawable(null);

            Toast.makeText(this, "Registro editado", Toast.LENGTH_SHORT).show();
            Intent newIntent=new Intent(this, Cargar_activity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(newIntent);

        } else {
            Toast.makeText(this, "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show();
        }*/
    }


    //se dice que finalize la actividad anterior mientras esta se esté ejecutando
    public void onBackPressed(){
        finish();
    }
    //Metodo que permite volver a la actividad anterior desde el menú
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id= menuItem.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }
}