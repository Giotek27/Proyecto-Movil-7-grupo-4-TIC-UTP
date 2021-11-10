package com.example.myshoopgoestoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et1,et2;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1=(EditText) findViewById(R.id.editTextTextPersonName);
        et2=(EditText) findViewById(R.id.editTextTextPassword);
        tv1=(TextView) findViewById(R.id.textView5);
        tv1.setClickable(true);
        String texto="<a href=''>Recordar contraseña</a>";
        tv1.setText(Html.fromHtml(texto));
    }
    public void login(View view){
        String user=et1.getText().toString();
        String pass=et2.getText().toString();
        if (!user.equals("")&&!pass.equals("")){
            if(user.equals("ShoopAdmin")&&pass.equals("Admin1")){
                Intent newIntent=new Intent(this,Registro_Activity.class);
                startActivity(newIntent);
                finish();
            }
            else{
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                et1.requestFocus();
            }

        }
        else {
            if(user.equals("")){
                et1.requestFocus();
                Toast.makeText(this, "Por favor ingrese el usuario", Toast.LENGTH_SHORT).show();
            }
            else if(pass.equals("")){
                et2.requestFocus();
                Toast.makeText(this, "Por favor ingrese el contraseña", Toast.LENGTH_SHORT).show();
            }

        }
    }
}