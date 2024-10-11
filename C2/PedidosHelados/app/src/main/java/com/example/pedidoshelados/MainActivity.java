package com.example.pedidoshelados;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;

    // Credenciales correctas
    private final String correctUsername = "m";
    private final String correctPassword = "123";

    // Contador de intentos fallidos
    private int loginAttempts = 0;
    private final int maxAttempts = 3;

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

        // Referencias a los elementos de la UI (nuevas líneas)
        usernameField = findViewById(R.id.user);
        passwordField = findViewById(R.id.password);
        loginButton = findViewById(R.id.bt_login);

        // Añadir listener al botón de inicio de sesión (nueva lógica)
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });
    }
    // Función para validar el inicio de sesión (nueva función)
    private void validateLogin() {
        String enteredUsername = usernameField.getText().toString();
        String enteredPassword = passwordField.getText().toString();

        // Verificar si los campos están vacíos
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(MainActivity.this, "Por favor ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
            return; // Detener la ejecución si los campos están vacíos
        }

        // Verificar si las credenciales son correctas
        if (enteredUsername.equals(correctUsername) && enteredPassword.equals(correctPassword)) {
            Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, PedidosHelados.class);
            startActivity(intent); // Inicia la nueva actividad
            finish();
        } else {
            // Incrementar el contador de intentos fallidos
            loginAttempts++;
            if (loginAttempts >= maxAttempts) {
                // Deshabilitar el botón si se excede el número de intentos
                loginButton.setEnabled(false);
                Toast.makeText(MainActivity.this, "Has alcanzado el número máximo de intentos", Toast.LENGTH_SHORT).show();
            } else {
                // Informar cuántos intentos quedan
                int remainingAttempts = maxAttempts - loginAttempts;
                Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos. Intentos restantes: " + remainingAttempts, Toast.LENGTH_SHORT).show();
            }
        }
    }
}