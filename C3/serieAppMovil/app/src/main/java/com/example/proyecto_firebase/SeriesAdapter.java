package com.example.proyecto_firebase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {

    private final Context context;
    private final List<SeriesHelperClass> seriesList; // Lista de series
    private final DatabaseReference reference; // Referencia a Firebase

    // Constructor
    public SeriesAdapter(Context context, List<SeriesHelperClass> seriesList) {
        this.context = context;
        this.seriesList = seriesList;
        this.reference = FirebaseDatabase.getInstance().getReference("series");
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista XML
        View view = LayoutInflater.from(context).inflate(R.layout.activity_series_adapter, parent, false);
        return new SeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        // Obtener la serie en la posición actual
        SeriesHelperClass series = seriesList.get(position);

        // Formatear la fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = series.getDateWatched() != null ? sdf.format(series.getDateWatched()) : "No registrada";

        // Asignar los datos a las vistas
        holder.titleText.setText(series.getTitle());
        holder.genreText.setText(series.getGenre());
        holder.seasonsText.setText("Temporadas vistas: " + series.getSeasonsWatched());
        holder.totalSeasonsText.setText("Total de temporadas: " + series.getTotalSeasons());
        holder.completedText.setText(series.isCompleted() ? "Estado: Completada" : "Estado: En progreso");
        holder.dateWatchedText.setText("Fecha: " + formattedDate);


        // Configurar el botón de edición
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditSeriesActivity.class);
            intent.putExtra("seriesId", series.getId()); // Pasar el ID de la serie a la actividad de edición
            context.startActivity(intent);
        });

        // Configurar el botón de eliminación
        holder.deleteButton.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(context)
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Estás seguro de que deseas eliminar esta serie?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        reference.child(series.getId()).removeValue().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Serie eliminada", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return seriesList.size(); // Número total de elementos en la lista
    }

    // Clase ViewHolder para las vistas
    public static class SeriesViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, genreText, seasonsText, totalSeasonsText, completedText, dateWatchedText;
        ImageButton editButton, deleteButton;

        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            genreText = itemView.findViewById(R.id.genreText);
            seasonsText = itemView.findViewById(R.id.seasonsText);
            totalSeasonsText = itemView.findViewById(R.id.totalSeasonsText);
            completedText = itemView.findViewById(R.id.completedText);
            dateWatchedText = itemView.findViewById(R.id.dateWatchedText);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
