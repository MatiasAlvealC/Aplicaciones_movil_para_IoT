package com.example.proyecto_firebase;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditSeriesActivity extends AppCompatActivity {

    private EditText titleInput, seasonsInput, totalSeasonsInput, dateInput;
    private Spinner genreSpinner;
    private CheckBox completedCheckbox;
    private Button updateSeriesButton;
    private DatabaseReference reference;
    private Calendar calendar;
    private String seriesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_series);

        // Referenciar los elementos de la vista
        titleInput = findViewById(R.id.titleInput);
        genreSpinner = findViewById(R.id.genreSpinner);
        seasonsInput = findViewById(R.id.seasonsInput);
        totalSeasonsInput = findViewById(R.id.totalSeasonsInput);
        dateInput = findViewById(R.id.dateInput);
        completedCheckbox = findViewById(R.id.completedCheckbox);
        updateSeriesButton = findViewById(R.id.updateSeriesButton);
        updateSeriesButton.setText("Actualizar Serie");

        // Configurar Firebase y Calendar
        calendar = Calendar.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("series");

        seriesId = getIntent().getStringExtra("seriesId");
        loadSeriesData();

        // Configurar el DatePickerDialog para la fecha
        dateInput.setOnClickListener(view -> showDatePickerDialog());

        // Manejar la lógica de actualización
        updateSeriesButton.setOnClickListener(view -> {
            String title = titleInput.getText().toString();
            String genre = genreSpinner.getSelectedItem().toString();
            String seasonsText = seasonsInput.getText().toString();
            String totalSeasonsText = totalSeasonsInput.getText().toString();
            String dateWatchedString = dateInput.getText().toString();

            // Validar campos vacíos
            if (title.isEmpty() || seasonsText.isEmpty() || totalSeasonsText.isEmpty() || dateWatchedString.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int seasonsWatched, totalSeasons;
            Date dateWatched;
            boolean completed;

            try {
                // Convertir temporadas vistas y total
                seasonsWatched = Integer.parseInt(seasonsText);
                totalSeasons = Integer.parseInt(totalSeasonsText);

                if (seasonsWatched > totalSeasons) {
                    Toast.makeText(this, "Las temporadas vistas no pueden ser mayores al total", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar si la serie está completada automáticamente
                completed = seasonsWatched == totalSeasons || completedCheckbox.isChecked();

                // Convertir la fecha
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dateWatched = sdf.parse(dateWatchedString);

            } catch (NumberFormatException | ParseException e) {
                Toast.makeText(this, "Introduce datos válidos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear objeto actualizado
            SeriesHelperClass updatedSeries = new SeriesHelperClass(seriesId, title, genre, seasonsWatched, totalSeasons, completed, dateWatched);

            // Guardar en Firebase
            reference.child(seriesId).setValue(updatedSeries).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Serie actualizada con éxito", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadSeriesData() {
        // Cargar los datos actuales desde Firebase
        reference.child(seriesId).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SeriesHelperClass series = snapshot.getValue(SeriesHelperClass.class);
                if (series != null) {
                    titleInput.setText(series.getTitle());
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            EditSeriesActivity.this, R.array.genres_array, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    genreSpinner.setAdapter(adapter);
                    genreSpinner.setSelection(adapter.getPosition(series.getGenre()));
                    seasonsInput.setText(String.valueOf(series.getSeasonsWatched()));
                    totalSeasonsInput.setText(String.valueOf(series.getTotalSeasons()));
                    completedCheckbox.setChecked(series.isCompleted());
                    dateInput.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(series.getDateWatched()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditSeriesActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            }
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
