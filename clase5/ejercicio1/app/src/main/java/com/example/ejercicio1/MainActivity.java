package com.example.ejercicio1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private RadioButton rb_20;
    private RadioButton rb_30;
    private CheckBox cb_pago20;
    private CheckBox cb_complemento;
    private Button bt_datos;


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

        spinner = findViewById(R.id.spinner);
        rb_20 = findViewById(R.id.rb_20);
        rb_30 =  findViewById(R.id.rb_30);
        cb_pago20 = findViewById(R.id.cb_pago20);
        cb_complemento = findViewById(R.id.cb_complemento);
        bt_datos = findViewById(R.id.bt_datos);

        bt_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String datosSpinner = spinner.getSelectedItem().toString();

                Intent intent = new Intent(MainActivity.this,Datos.class);
                intent.putExtra("spinnerOption", datosSpinner);

                startActivities(new Intent[]{intent});
            }
        });

    }
}