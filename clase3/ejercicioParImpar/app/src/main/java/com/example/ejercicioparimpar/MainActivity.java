package com.example.ejercicioparimpar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView txt_resultado;
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
        // se nombran las variables que se usaran
        EditText txt_numero;
        Button txt_boton;

        // se definen las variables a usar, llamndolas
        txt_numero = (EditText)  findViewById(R.id.txt_numero);
        txt_boton= (Button) findViewById(R.id.txt_boton);
        txt_resultado = (TextView) findViewById(R.id.txt_resultado);

        // definimos el evento click
        txt_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esParImpar(txt_numero.getText().toString());
            }
        });
    }

    public void esParImpar(String valor){
        int numero;
        try {
            numero = Integer.parseInt(valor);

            if (numero % 2 == 0){
                //Toast.makeText(MainActivity.this, "Numero Par",Toast.LENGTH_SHORT).show();
                txt_resultado.setText("Es par");
            } else {
                //Toast.makeText(MainActivity.this, "Numero impar",Toast.LENGTH_SHORT).show();
                txt_resultado.setText("Es impar");
            }
        } catch ( NumberFormatException e ) {
            Toast.makeText(MainActivity.this, "Debe ingresar un numero",Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(MainActivity.this, "Error",Toast.LENGTH_SHORT).show();

        }
    }
}