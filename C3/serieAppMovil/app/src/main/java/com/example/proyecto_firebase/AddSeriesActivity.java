package com.example.proyecto_firebase;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddSeriesActivity extends AppCompatActivity {

    private EditText titleInput, seasonsInput, totalSeasonsInput, dateInput;
    private Spinner genreSpinner;
    private CheckBox completedCheckbox;
    private Button addSeriesButton;
    private DatabaseReference reference;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series);

        // Referenciar los elementos de la vista
        titleInput = findViewById(R.id.titleInput);
        genreSpinner = findViewById(R.id.genreSpinner);
        seasonsInput = findViewById(R.id.seasonsInput);
        totalSeasonsInput = findViewById(R.id.totalSeasonsInput);
        dateInput = findViewById(R.id.dateInput);
        completedCheckbox = findViewById(R.id.completedCheckbox);
        addSeriesButton = findViewById(R.id.addSeriesButton);

        // Configurar Firebase y Calendar
        calendar = Calendar.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("series");

        // Configurar el Spinner de géneros
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.genress_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        // Configurar el DatePickerDialog para el campo de fecha
        dateInput.setOnClickListener(view -> showDatePickerDialog());

        addSeriesButton.setOnClickListener(view -> {
            // Obtener datos de los campos
            String title = titleInput.getText().toString().trim();
            String genre = genreSpinner.getSelectedItem().toString();
            String seasonsText = seasonsInput.getText().toString().trim();
            String totalSeasonsText = totalSeasonsInput.getText().toString().trim();
            String dateWatchedString = dateInput.getText().toString().trim();

            // Validación básica
            if (title.isEmpty() || seasonsText.isEmpty() || totalSeasonsText.isEmpty() || dateWatchedString.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convertir temporadas y fecha
            int seasonsWatched, totalSeasons;
            Date dateWatched;
            boolean completed;

            try {
                seasonsWatched = Integer.parseInt(seasonsText);
                totalSeasons = Integer.parseInt(totalSeasonsText);

                if (seasonsWatched > totalSeasons) {
                    Toast.makeText(this, "Las temporadas vistas no pueden ser mayores al total", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar si debe marcarse como completado automáticamente
                completed = seasonsWatched == totalSeasons || completedCheckbox.isChecked();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dateWatched = sdf.parse(dateWatchedString);

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Introduce números válidos para las temporadas", Toast.LENGTH_SHORT).show();
                System.out.println("Error al convertir temporadas: " + e.getMessage());
                return;
            } catch (ParseException e) {
                Toast.makeText(this, "Formato de fecha inválido", Toast.LENGTH_SHORT).show();
                System.out.println("Error al convertir la fecha: " + e.getMessage());
                return;
            }

            // Crear ID único para la serie
            String id = reference.push().getKey();
            if (id == null) {
                Toast.makeText(this, "Error al generar el ID para la serie", Toast.LENGTH_SHORT).show();
                System.out.println("No se pudo generar un ID para la serie");
                return;
            }

            // Crear y guardar la serie
            SeriesHelperClass series = new SeriesHelperClass(id, title, genre, seasonsWatched, totalSeasons, completed, dateWatched);
            System.out.println("Datos de la serie a guardar: " + series);

            reference.child(id).setValue(series).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Serie añadida con éxito", Toast.LENGTH_SHORT).show();
                    System.out.println("Serie añadida exitosamente: " + title);
                    finish(); // Regresar a la pantalla anterior
                } else {
                    Toast.makeText(this, "Error al añadir la serie", Toast.LENGTH_SHORT).show();
                    System.out.println("Error al guardar la serie: " + task.getException());
                }
            });
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateInput();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void updateDateInput() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateInput.setText(sdf.format(calendar.getTime()));
    }
}
