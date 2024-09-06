package com.example.habitos;

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
    TextView txt_total;
    TextView txt_recomendacion;

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
        EditText txt_ejercicio;
        EditText min_lunes;
        EditText min_martes;
        EditText min_miercoles;
        EditText min_jueves;
        EditText min_viernes;
        EditText min_sabado;
        EditText min_domingo;

        Button bt_calcular;

        // se definen las variables a usar, llamndolas
        txt_ejercicio = (EditText)  findViewById(R.id.txt_ejercicio);
        min_lunes = (EditText)  findViewById(R.id.min_lunes);
        min_martes = (EditText)  findViewById(R.id.min_martes);
        min_miercoles = (EditText)  findViewById(R.id.min_miercoles);
        min_jueves= (EditText)  findViewById(R.id.min_jueves);
        min_viernes = (EditText)  findViewById(R.id.min_viernes);
        min_sabado = (EditText)  findViewById(R.id.min_sabado);
        min_domingo = (EditText)  findViewById(R.id.min_domingo);

        bt_calcular= (Button) findViewById(R.id.bt_calcular);
        txt_total = (TextView) findViewById(R.id.txt_total);
        txt_recomendacion = (TextView) findViewById(R.id.txt_recomendacion);



        // definimos el evento click
        bt_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ejer = txt_ejercicio.getText().toString();
                if (ejer.isEmpty()){
                    Toast.makeText(MainActivity.this, "Debe ingresar un ejercicio",Toast.LENGTH_SHORT).show();
                } else{
                    nivelActividad(min_lunes.getText().toString(),min_martes.getText().toString(), min_miercoles.getText().toString(),min_jueves.getText().toString(), min_viernes.getText().toString(),min_sabado.getText().toString(),min_domingo.getText().toString());

                }
            }
        });
    }

    public void nivelActividad(String valor1, String valor2, String valor3, String valor4, String valor5, String valor6, String valor7){
        int num1,num2,num3,num4,num5,num6,num7,suma;
        try {
            num1 = Integer.parseInt(valor1);
            num2 = Integer.parseInt(valor2);
            num3 = Integer.parseInt(valor3);
            num4 = Integer.parseInt(valor4);
            num5 = Integer.parseInt(valor5);
            num6 = Integer.parseInt(valor6);
            num7 = Integer.parseInt(valor7);

            if (valor1 != null || valor2 != null  || valor3 != null || valor4 != null || valor5 != null || valor6 != null || valor7 != null || num1 > 0 || num2 > 0 || num3 > 0 || num4 > 0 || num5 > 0 || num6 > 0 || num7 > 0) {
                suma = num1 + num2 + num3 +num4 + num5 + num6 + num7;
                if (suma<60){
                    //Toast.makeText(MainActivity.this, "Numero Par",Toast.LENGTH_SHORT).show();
                    txt_total.setText("Tiempo total: "+suma+" minutos, SEDENTARIO");
                    txt_recomendacion.setText("Se le recomienda ejercitar más");
                } else if (suma>=60 && suma<=149) {
                    txt_total.setText("Tiempo total: "+suma+" minutos, MODERADAMENTE ACTIVO");
                    txt_recomendacion.setText("Va por buen camino, si puede aumente los dias de entrenamiento o incremente el tiempo");
                } else if (suma>=150 && suma <=300) {
                    txt_total.setText("Tiempo total: "+suma+" minutos, ACTIVO");
                    txt_recomendacion.setText("Esta perfecto, siga así. Recuerde comer balaceado");

                } else {
                    //Toast.makeText(MainActivity.this, "Numero impar",Toast.LENGTH_SHORT).show();
                    txt_total.setText("Tiempo total: "+suma+" minutos, MUY ACTIVO");
                    txt_recomendacion.setText("Es un atleta de alto rendimiento? o no sabe descanzar?");
                }
            }
        } catch ( NumberFormatException e ) {
            Toast.makeText(MainActivity.this, "Debe ingresar un numero",Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

        }
    }
}