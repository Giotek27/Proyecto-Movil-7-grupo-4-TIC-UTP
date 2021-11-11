package com.example.proyectoapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class formularioCrear extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_formulario_crear);
        //Se llama al botn devolver
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //navegación a actividad de confirmacion creado
    public void goToActivityCargar(View view){
        Intent newIntent=new Intent(this, Cargar_activity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(newIntent);
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