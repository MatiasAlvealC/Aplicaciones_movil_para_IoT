package com.example.proyecto_firebase;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ListSeriesActivity extends AppCompatActivity {

    private RecyclerView seriesRecyclerView;
    private EditText filterDate;
    private Spinner filterGenre;
    private CheckBox filterCompleted;
    private Button applyFilterButton;
    private TextView clearFilterButton; // Botón o enlace de texto para limpiar filtros
    private FloatingActionButton addSeriesButton;

    private List<SeriesHelperClass> seriesList; // Lista principal de series
    private List<SeriesHelperClass> filteredList; // Lista filtrada

    private DatabaseReference reference; // Referencia a Firebase
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_series);

        // Inicializar vistas
        seriesRecyclerView = findViewById(R.id.seriesRecyclerView);
        filterDate = findViewById(R.id.filterDate);
        filterGenre = findViewById(R.id.filterGenre);
        filterCompleted = findViewById(R.id.filterCompleted);
        applyFilterButton = findViewById(R.id.applyFilterButton);
        clearFilterButton = findViewById(R.id.clearFilterButton); // Nueva referencia
        addSeriesButton = findViewById(R.id.addSeriesButton);

        // Configurar RecyclerView
        seriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        seriesRecyclerView.setHasFixedSize(true);

        // Inicializar lista y Firebase
        seriesList = new ArrayList<>();
        filteredList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("series");
        calendar = Calendar.getInstance();

        // Cargar las series desde Firebase
        loadSeries();

        // Configurar el DatePicker para seleccionar la fecha
        filterDate.setOnClickListener(v -> showDatePickerDialog());

        // Aplicar filtros al presionar el botón
        applyFilterButton.setOnClickListener(v -> applyFilters());

        // Limpiar filtros al presionar el enlace de texto
        clearFilterButton.setOnClickListener(v -> clearFilters());

        // Acción del botón flotante para añadir serie
        addSeriesButton.setOnClickListener(v -> {
            Intent intent = new Intent(ListSeriesActivity.this, AddSeriesActivity.class);
            startActivity(intent);
        });
    }

    private void loadSeries() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                seriesList.clear(); // Limpiar la lista para evitar duplicados
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SeriesHelperClass series = dataSnapshot.getValue(SeriesHelperClass.class);
                    if (series != null) {
                        seriesList.add(series);
                    }
                }
                filteredList.clear();
                filteredList.addAll(seriesList); // Inicialmente, mostrar todas las series
                updateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListSeriesActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    filterDate.setText(sdf.format(calendar.getTime())); // Mostrar la fecha seleccionada
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void applyFilters() {
        String date = filterDate.getText().toString(); // Fecha seleccionada
        String genre = filterGenre.getSelectedItem().toString(); // Género seleccionado
        boolean isCompleted = filterCompleted.isChecked(); // Estado completado

        filteredList.clear();
        for (SeriesHelperClass series : seriesList) {
            boolean matches = true;

            // Filtrar por fecha
            if (!date.isEmpty() && series.getDateWatched() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String seriesDate = sdf.format(series.getDateWatched());
                if (!seriesDate.equals(date)) {
                    matches = false;
                }
            }

            // Filtrar por género
            if (!genre.equals("Todos") && !series.getGenre().equals(genre)) {
                matches = false;
            }

            // Filtrar por estado completado
            if (isCompleted && !series.isCompleted()) {
                matches = false;
            }

            if (matches) {
                filteredList.add(series);
            }
        }

        updateRecyclerView(); // Actualizar la lista visual

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No se encontraron series con esos filtros", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFilters() {
        // Limpiar los valores de los filtros
        filterDate.setText("");
        filterGenre.setSelection(0); // Seleccionar el primer valor del Spinner (ej. "Todos")
        filterCompleted.setChecked(false);

        // Restaurar la lista completa
        filteredList.clear();
        filteredList.addAll(seriesList);
        updateRecyclerView();

        Toast.makeText(this, "Filtros limpiados", Toast.LENGTH_SHORT).show();
    }

    private void updateRecyclerView() {
        SeriesAdapter adapter = new SeriesAdapter(this, filteredList); // Mostrar la lista filtrada
        seriesRecyclerView.setAdapter(adapter);
    }
}
