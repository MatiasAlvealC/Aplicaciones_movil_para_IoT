package com.example.ejerciciocalculadora;

import android.os.Bundle;

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
        EditText txt_num1;
        EditText txt_num2;

        ImageButton bt_mas;
        ImageButton bt_menos;
        ImageButton bt_mult;
        ImageButton bt_div;


        // se definen las variables a usar, llamndolas
        txt_num1 = (EditText)  findViewById(R.id.txt_num1);
        txt_num2 = (EditText)  findViewById(R.id.txt_num2);

        bt_mas = (ImageButton) findViewById(R.id.bt_mas);
        bt_menos = (ImageButton) findViewById(R.id.bt_menos);
        bt_mult = (ImageButton) findViewById(R.id.bt_mult);
        bt_div = (ImageButton) findViewById(R.id.bt_div);

        txt_resultado = (TextView) findViewById(R.id.txt_resultado);

        // definimos el evento click
        bt_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculo(txt_num1.getText().toString(),txt_num2.getText().toString(),1);
            }
        });
        // definimos el evento click
        bt_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculo(txt_num1.getText().toString(),txt_num2.getText().toString(),2);
            }
        });
        // definimos el evento click
        bt_mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculo(txt_num1.getText().toString(),txt_num2.getText().toString(),3);
            }
        });
        // definimos el evento click
        bt_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculo(txt_num1.getText().toString(),txt_num2.getText().toString(),4);
            }
        });
    }

    public void calculo(String valor1,String valor2,int operacion){
        int numero1;
        int numero2;
        int resultado;
        try {
            numero1 = Integer.parseInt(valor1);
            numero2 = Integer.parseInt(valor2);

            if (operacion == 1){
                resultado= numero1 + numero2;
                txt_resultado= "El resultado es:"+resultado;
            } else if (operacion == 2) {
                resultado= numero1 - numero2;
                txt_resultado+=  numero1 - numero2;
            } else if (operacion == 3) {
                resultado= numero1 - numero2;
                txt_resultado+=  numero1 * numero2;

            } else if (operacion == 4) {
                resultado= numero1 - numero2;
                txt_resultado+=  numero1 / numero2;
            }
        } catch (Exception e){
            Toast.makeText(MainActivity.this, "Error",Toast.LENGTH_SHORT).show();

        }

    }
}