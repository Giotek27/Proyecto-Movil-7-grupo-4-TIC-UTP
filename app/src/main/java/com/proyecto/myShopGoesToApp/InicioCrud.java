package com.proyecto.myShopGoesToApp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.proyectoapp1.R;

public class InicioCrud extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_inicio_crud);

    }
    //Se crea el Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //--------------------------------------------------------------------
    //Se crea la utilidad del menú, es decir, al presionar el boton o la opcion
    //que se puso me redirije hacia otra activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.crearProducto){
            Intent newIntent=new Intent(this, formularioCrear.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(newIntent);
        }

        return super.onOptionsItemSelected(menuItem);
    }
//---------------------Desplazamiento entre ventanas(actividades)---------------------------------
    //Se crea metodo para dirigirme a la actividad de formularioEditar
    public void goToActivityformularioEditar(View view){
        Intent newIntent=new Intent(this, formularioEditar.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(newIntent);
    }
    public void goToActivityconfirmacionEliminado(View view){
        Intent newIntent=new Intent(this, confirmacionEliminado.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(newIntent);
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
                            InicioCrud.this.finish();
                        }
                    }).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    }