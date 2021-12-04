package com.proyecto.myShopGoesToApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class formularioCrear extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;


    private EditText et1, et2, et3, et4, et5;
    private Spinner sp1;
    private ImageView imgView;

    private Bitmap bmp1, bmp2;

    private ImageButton btnCamara;

    private Productos productos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_formulario_crear);

        et1 = findViewById(R.id.TxtnomproducCrear);
        et2 = findViewById(R.id.TxtCantidadCrear);
        et3 = findViewById(R.id.TextPrecioCrear);
        et4 = findViewById(R.id.txtDescripcionCrear);
        et5 = findViewById(R.id.txtgarantiasPrdcrear);

        sp1 = findViewById(R.id.spinnerCrear);

        btnCamara = findViewById(R.id.btnCamara);
        imgView = findViewById(R.id.imageViewCrear);
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });

    }

    private void abrirCamara(){
        final CharSequence[]opciones={"Tomar foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(formularioCrear.this);
        alertOpciones.setTitle("Seleccione una Opción");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }
    }

    public void abrirGaleria(View view){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);
        }
        //galeria
        if(requestCode == 2 && resultCode==RESULT_OK) {
            Uri selectedImage = data.getData();
            InputStream InputStream;
            try {
                InputStream = getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(InputStream);
                Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                imgView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    //Se crea el Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //Metodo que permite volver a la actividad anterior desde el menú
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.crearProducto){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String nomproduct = et1.getText().toString();
            String cantidadproduct = et2.getText().toString();
            String precioproduct = et3.getText().toString();
            String catproduct = sp1.getSelectedItem().toString();
            String descriproduct = et4.getText().toString();
            String garantiaproduct = et5.getText().toString();

            if (!nomproduct.equals("") && !cantidadproduct.equals("") && !precioproduct.equals("") && !catproduct.equals("Seleccione...") && !descriproduct.equals("") && !garantiaproduct.equals("") && imgView.getDrawable()!= null) {
                String imgCodificada="";
                imgView.buildDrawingCache(true);
                bmp1=imgView.getDrawingCache(true);
                bmp2=Bitmap.createScaledBitmap(bmp1,imgView.getWidth(), imgView.getHeight(),true);
                ByteArrayOutputStream baos= new ByteArrayOutputStream();
                bmp2.compress(Bitmap.CompressFormat.PNG,25, baos);
                byte[] imagen = baos.toByteArray();
                imgCodificada= Base64.encodeToString(imagen, Base64.DEFAULT);

                productos=new Productos(nomproduct,cantidadproduct,precioproduct,catproduct,descriproduct,garantiaproduct,imgCodificada);
                myRef=database.getReference().child("Productos").push();
                myRef.setValue(productos);

                et1.setText("");
                et2.setText("");
                et3.setText("");
                sp1.setSelection(0);
                et4.setText("");
                et5.setText("");

                imgView.setImageBitmap(null);
                imgView.destroyDrawingCache();
                imgView.setImageDrawable(null);

                Toast.makeText(this, "Registro almacenado", Toast.LENGTH_SHORT).show();
                Intent newIntent=new Intent(this, Cargar_activity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(newIntent);

            } else {
                Toast.makeText(this, "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show();
            }
        }else if(id==R.id.buscar_producto) {
            String nomProducBuscar=et1.getText().toString();
            if (!nomProducBuscar.equals("")){
                myRef=FirebaseDatabase.getInstance().getReference();
                myRef.child("Productos").orderByChild("nomproduc").equalTo(nomProducBuscar).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getChildrenCount()>0) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Bitmap decodedByte=null;
                                byte[] decodeString;
                                Productos productos=dataSnapshot.getValue(Productos.class);
                                String cantidad=productos.getCantidad();
                                String precio=productos.getPrecio();
                                String categoria=productos.getCatproducto();
                                String desproduc=productos.getDescproduc();
                                String garanproducs=productos.getGaranproduc();
                                decodeString= Base64.decode(productos.getImagen(),Base64.DEFAULT);
                                decodedByte= BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);

                                et2.setText(cantidad);
                                et3.setText(precio);
                                Adapter adapter=sp1.getAdapter();
                                for (int i=0; i< adapter.getCount();i++){
                                    if (categoria.equals(adapter.getItem(i))){
                                        sp1.setSelection(i);
                                    }
                                }
                                et4.setText(desproduc);
                                et5.setText(garanproducs);
                                imgView.setImageBitmap(decodedByte);

                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "El registro no existe", Toast.LENGTH_SHORT).show();
                            et1.setText("");
                            et2.setText("");
                            et3.setText("");
                            sp1.setSelection(0);
                            et4.setText("");
                            et5.setText("");

                            imgView.setImageBitmap(null);
                            imgView.destroyDrawingCache();
                            imgView.setImageDrawable(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }else if (id==R.id.edit_product){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String nomproduct = et1.getText().toString();
            String cantidadproduct = et2.getText().toString();
            String precioproduct = et3.getText().toString();
            String catproduct = sp1.getSelectedItem().toString();
            String descriproduct = et4.getText().toString();
            String garantiaproduct = et5.getText().toString();

            if (!nomproduct.equals("") && !cantidadproduct.equals("") && !precioproduct.equals("") && !catproduct.equals("Seleccione...") && !descriproduct.equals("") && !garantiaproduct.equals("") && imgView.getDrawable()!= null) {
                String imgCodificada="";
                imgView.buildDrawingCache(true);
                bmp1=imgView.getDrawingCache(true);
                bmp2=Bitmap.createScaledBitmap(bmp1,imgView.getWidth(), imgView.getHeight(),true);
                ByteArrayOutputStream baos= new ByteArrayOutputStream();
                bmp2.compress(Bitmap.CompressFormat.PNG,25, baos);
                byte[] imagen = baos.toByteArray();
                imgCodificada= Base64.encodeToString(imagen, Base64.DEFAULT);

                productos=new Productos(nomproduct,cantidadproduct,precioproduct,catproduct,descriproduct,garantiaproduct,imgCodificada);
                myRef=database.getReference();
                myRef.child("productos").orderByChild("nomproduc").equalTo(nomproduct).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                myRef.setValue(productos);

                et1.setText("");
                et2.setText("");
                et3.setText("");
                sp1.setSelection(0);
                et4.setText("");
                et5.setText("");

                imgView.setImageBitmap(null);
                imgView.destroyDrawingCache();
                imgView.setImageDrawable(null);

                Toast.makeText(this, "Producto editado", Toast.LENGTH_SHORT).show();
            }else {
            Toast.makeText(this, "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show();
        }
        }else if(id==R.id.delete_product){
            String nomProducBuscar=et1.getText().toString();
            if (!nomProducBuscar.equals("")){
                myRef=FirebaseDatabase.getInstance().getReference();
                myRef.child("Productos").orderByChild("nomproduc").equalTo(nomProducBuscar).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getChildrenCount()>0) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                dataSnapshot.getRef().removeValue();
                            }
                            Toast.makeText(getApplicationContext(), "Producto Eliminado", Toast.LENGTH_SHORT).show();
                            et1.setText("");
                            et2.setText("");
                            et3.setText("");
                            sp1.setSelection(0);
                            et4.setText("");
                            et5.setText("");

                            imgView.setImageBitmap(null);
                            imgView.destroyDrawingCache();
                            imgView.setImageDrawable(null);
                        } else
                            Toast.makeText(getApplicationContext(), "El registro no existe", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }else {
                Toast.makeText(this, "Ingrese el nombre del producto que desea borrar", Toast.LENGTH_SHORT).show();
            }
        } else if (id==R.id.verProducts){
            Intent newIntent=new Intent(this, InicioCrud.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(newIntent);
        }

        return super.onOptionsItemSelected(menuItem);
    }

    public void limpiarcampos(View view){
        et1.setText("");
        et2.setText("");
        et3.setText("");
        sp1.setSelection(0);
        et4.setText("");
        et5.setText("");

        imgView.setImageBitmap(null);
        imgView.destroyDrawingCache();
        imgView.setImageDrawable(null);
    }

    //----------------------------------------------------
    //Se crea mensaje de confirmación para salir o no de la app
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Información")
                    .setMessage("¿Desea Salir?")
                    .setNegativeButton("No",null)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            formularioCrear.this.finish();
                        }
                    }).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //se dice que finalize la actividad anterior mientras esta se esté ejecutando
    public void onBackPressed(){
        finish();
    }

}