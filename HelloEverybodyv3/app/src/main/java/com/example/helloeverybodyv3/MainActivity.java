package com.example.helloeverybodyv3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    Button b1;
    TextView tv1;
    EditText et1, et2, et3, et4;
    TextInputEditText ti1;
    SeekBar sb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button) findViewById(R.id.button2);
        sb1=findViewById(R.id.seekBar);
        tv1=findViewById(R.id.textView8);
        et1 = findViewById(R.id.editTextTextPersonName);
        et2 = findViewById(R.id.editTextDate);
        et3 = findViewById(R.id.editTextPhone);
        et4 = findViewById(R.id.editTextTextEmailAddress);
        ti1 = findViewById(R.id.textInputEditText);
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                tv1.setText(""+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        b1.setText(R.string.agregar);
    }
    public void guardarDatos(View view){
        String nom = "Nombre: " + et1.getText();
        String fnac = "Fecha de nacimiento: " + et2.getText();
        String tel = "Teléfono: " + et3.getText();
        String email = "Corrreo electrónico: " + et4.getText();
        String dir = "Dirección: " + ti1.getText();

        new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Resumen")
                .setMessage(nom + "\n" + fnac + "\n" + tel + "\n" + email + "\n" + dir)
                .setPositiveButton("Aceptar", null).show();

    }
}