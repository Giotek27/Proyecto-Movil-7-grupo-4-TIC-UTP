package com.proyecto.myShopGoesToApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoapp1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InicioCrud extends AppCompatActivity {
    private final int cols = 2;

    FirebaseDatabase database;
    DatabaseReference myRef;


    private TextView showdata1;

    private EditText et1, et2, et3, et4, et5;
    private Spinner sp1;
    private ImageView imgViewEdit;
    private ImageView imgView;
    private ImageButton btnCamara;

    private String cad_productos;

    private CustomAdapter ca;
    private RecyclerView rv;
    private GridLayoutManager glm;
    private LinearLayoutManager llm;
    private ArrayList<Productos> dataset = new ArrayList<Productos>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_inicio_crud);
        //Se llama al botn devolver
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgView = findViewById(R.id.imgView);
        try {
            rv = findViewById(R.id.rv_list);
            dataset = initDataSet();
            ca = new CustomAdapter(dataset);
            rv.setAdapter(ca);


            setRecylerviewLayoutManager();
        }catch (Exception e){
            Toast.makeText(this, "No tienes productos registrados", Toast.LENGTH_SHORT).show();
        }




    }

    public ArrayList<Productos> initDataSet() {
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.getDatabase().getReference().child("Productos").orderByChild("nomProduct").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Productos producto = dataSnapshot.getValue(Productos.class);
                    dataset.add(producto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return dataset;
    }


    public void setRecylerviewLayoutManager() {
        int scrollPosition = 0;

        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        glm = new GridLayoutManager(this, cols);
        rv.setLayoutManager(llm);
        //rv.setLayoutManager(glm);
        rv.scrollToPosition(scrollPosition);
    }

    //Se crea el Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_verproducts, menu);
        return true;
    }

    //Metodo que permite volver a la actividad anterior desde el menú
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }





}

