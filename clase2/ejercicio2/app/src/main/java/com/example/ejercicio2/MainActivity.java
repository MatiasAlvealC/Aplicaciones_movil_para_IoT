package com.example.ejercicio2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        // se nombran las variables que se definieron en la interfaz
        EditText txt_edad;
        Button bt_calcular;

        // defino las variables
        txt_edad = (EditText) findViewById(R.id.txt_edad);
        bt_calcular = (Button) findViewById(R.id.bd_calcular);

        // hacemos que el boton haga algo cuando ocurra el evento de click
        bt_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int edad;
               try{
                   edad = Integer.parseInt(txt_edad.getText().toString());
                   if (edad >= 18 && edad <=105) {
                       // print: (donde se abre, que dice, cuanto tiempo se muestra)
                       Toast.makeText(getApplicationContext(), "Mayor de edad, viejito", Toast.LENGTH_LONG).show();
                   } else if (edad>105) {
                       Toast.makeText(getApplicationContext(), "estas seguro que estas vivo?", Toast.LENGTH_LONG).show();
                   } else {
                       Toast.makeText(getApplicationContext(), "Menor de edad, es un bebé", Toast.LENGTH_LONG).show();
                   }
               } catch (Exception e){
                   Toast.makeText(getApplicationContext(), "Error ingrese solo números",Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

}