package com.example.pedidoshelados;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PedidosHelados extends AppCompatActivity {

    private Spinner spinnerSize;
    private CheckBox checkBoxChocolate, checkBoxVainilla, checkBoxFrutilla, checkBoxMenta, checkBoxDulceDeLeche;
    private RadioGroup radioGroupAccompaniment, radioGroupEat, radioGroupConsumption;
    private Button calculateButton, summaryButton;
    private int cost = 0;  // Usar esta variable de instancia para almacenar el costo total
    private boolean isCostCalculated = false;  // Variable para controlar si ya se calculó el costo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pedidos_helados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Referencias a los elementos de la UI
        spinnerSize = findViewById(R.id.spinner_size);
        checkBoxChocolate = findViewById(R.id.checkbox_chocolate);
        checkBoxVainilla = findViewById(R.id.checkbox_vainilla);
        checkBoxFrutilla = findViewById(R.id.checkbox_frutilla);
        checkBoxMenta = findViewById(R.id.checkbox_menta);
        checkBoxDulceDeLeche = findViewById(R.id.checkbox_dulce_de_leche);
        radioGroupAccompaniment = findViewById(R.id.radioGroup_accompaniment);
        radioGroupEat = findViewById(R.id.radioGroup_eat);
        radioGroupConsumption = findViewById(R.id.radioGroup_consumption);
        calculateButton = findViewById(R.id.calculateButton);
        summaryButton = findViewById(R.id.summary_button);

        // Desactivar el botón de Resumen al inicio
        summaryButton.setEnabled(false);

        // Configurar el botón de calcular
        calculateButton.setOnClickListener(v -> {
            if (validateFields()) {
                calculateCost();
                isCostCalculated = true;  // Marcar que el costo ha sido calculado
                summaryButton.setEnabled(true);  // Activar el botón de Resumen
            }
        });

        // Configurar el botón de "A Resumen"
        summaryButton.setOnClickListener(v -> {
            if (isCostCalculated) {  // Solo permitir si el costo ya fue calculado
                goToSummary();
            } else {
                Toast.makeText(this, "Primero debes calcular el pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Función para validar si todos los campos están completos
    private boolean validateFields() {
        /*
        // Verificar si se seleccionó un tamaño de torta
        if (spinnerSize.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Por favor selecciona un tamaño del helado", Toast.LENGTH_SHORT).show();
            return false;
        }
        */


        // Verificar si se seleccionó al menos un sabor
        if (getSelectedFlavorsCount() < 1) {
            Toast.makeText(this, "Por favor selecciona al menos un sabor", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Verificar si se seleccionó mas de dos sabores
        if (getSelectedFlavorsCount() > 2) {
            Toast.makeText(this, "Por favor selecciona maximo dos sabores", Toast.LENGTH_SHORT).show();
            return false;
        }
        /*
        // Verificar si se seleccionó un acompañamiento
        if (radioGroupAccompaniment.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Por favor selecciona una cobertura", Toast.LENGTH_SHORT).show();
            return false;
        }
        */
        // Verificar si se seleccionó el tipo de consumo
        if (radioGroupConsumption.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Por favor selecciona el tipo de consumo", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar si se seleccionó como se servira
        if (radioGroupEat.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Por favor selecciona como se servira", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;  // Si todo está completo, retornar true
    }

    private void calculateCost() {
        cost = 0;  // Reiniciar el costo total

        // Costo basado en el tamaño de la torta
        String selectedSize = spinnerSize.getSelectedItem().toString();
        switch (selectedSize) {
            case "Pequeño":
                cost += 2000;
                break;
            case "Mediano":
                cost += 3000;
                break;
            case "Grande":
                cost += 4000;
                break;
        }

        // Costo adicional por sabores
        int selectedFlavorsCount = getSelectedFlavorsCount();
        if (selectedFlavorsCount >= 1) {
            cost += (selectedFlavorsCount ) * 300;
        }

        // Costo por Acompañamiento
        String selectedAccompaniment = getSelectedRadioButtonText(radioGroupAccompaniment);
        if (!selectedAccompaniment.isEmpty()) {
            cost += 500;
        }

        // Mostrar el costo total
        String consumptionType = getSelectedRadioButtonText(radioGroupConsumption);
        String eatType = getSelectedRadioButtonText(radioGroupEat);
        String finalMessage = "Costo total: $" + cost + "\nHelado en: " + eatType;
        Toast.makeText(this, finalMessage, Toast.LENGTH_LONG).show();
    }

    private void goToSummary() {
        // Obtener el resumen del pedido
        String selectedSize = spinnerSize.getSelectedItem().toString();
        String selectedFlavors = getSelectedFlavors();
        String selectedAccompaniment = getSelectedRadioButtonText(radioGroupAccompaniment);
        String selectedEat = getSelectedRadioButtonText(radioGroupEat);
        String consumptionType = getSelectedRadioButtonText(radioGroupConsumption);

        if (!selectedAccompaniment.isEmpty()){
            String summary = "Tamaño: " + selectedSize +
                    "\nSabores: " + selectedFlavors +
                    "\nAconpañamiento: " + selectedAccompaniment +
                    "\nConsumo: " + consumptionType +
                    "\nComo se servira: " + selectedEat;
            // Enviar los datos a la actividad Resumen
            Intent intent = new Intent(PedidosHelados.this, Resumen.class);
            intent.putExtra("summary", summary);
            intent.putExtra("totalPrice", cost);  // Pasar el costo total
            startActivity(intent);
        } else {
            String summary = "Tamaño: " + selectedSize +
                    "\nSabores: " + selectedFlavors +
                    "\nConsumo: " + consumptionType +
                    "\nComo se servira: " + selectedEat;
            // Enviar los datos a la actividad Resumen
            Intent intent = new Intent(PedidosHelados.this, Resumen.class);
            intent.putExtra("summary", summary);
            intent.putExtra("totalPrice", cost);  // Pasar el costo total
            startActivity(intent);
        }



    }

    private int getSelectedFlavorsCount() {
        int count = 0;

        if (checkBoxChocolate.isChecked()) count++;
        if (checkBoxVainilla.isChecked()) count++;
        if (checkBoxFrutilla.isChecked()) count++;
        if (checkBoxMenta.isChecked()) count++;
        if (checkBoxDulceDeLeche.isChecked()) count++;

        return count;
    }

    private String getSelectedFlavors() {
        StringBuilder selectedFlavors = new StringBuilder();

        if (checkBoxChocolate.isChecked()) selectedFlavors.append("Chocolate ");
        if (checkBoxVainilla.isChecked()) selectedFlavors.append("Vainilla ");
        if (checkBoxFrutilla.isChecked()) selectedFlavors.append("Frutilla ");
        if (checkBoxMenta.isChecked()) selectedFlavors.append("Menta ");
        if (checkBoxDulceDeLeche.isChecked()) selectedFlavors.append("Dulce de leche ");

        return selectedFlavors.toString().trim();
    }

    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            return radioButton.getText().toString();
        }
        return "";
    }
}