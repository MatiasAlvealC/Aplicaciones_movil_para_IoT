package com.example.pedidoshelados;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Resumen extends AppCompatActivity {
    private TextView summaryTextView;
    private TextView totalPriceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resumen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        summaryTextView = findViewById(R.id.summary_text_view);
        totalPriceView = findViewById(R.id.total_price_view);

        // Obtener los datos del Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String summary = extras.getString("summary");
            int totalPrice = extras.getInt("totalPrice");

            summaryTextView.setText(summary);
            totalPriceView.setText("Precio total: $" + totalPrice);
        }
    }
}