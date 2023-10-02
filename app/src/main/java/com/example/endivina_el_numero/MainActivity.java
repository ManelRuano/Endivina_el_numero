package com.example.endivina_el_numero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText numeroEditText;
    private TextView historicoTextView;
    private TextView intentosTextView;
    private ScrollView historicoScrollView;
    private int numeroAleatorio;
    private int intentos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numeroEditText = findViewById(R.id.numeroEditText);
        Button validarButton = findViewById(R.id.validarButton);
        historicoTextView = findViewById(R.id.historicoTextView);
        intentosTextView = findViewById(R.id.intentosTextView);
        historicoScrollView = findViewById(R.id.historicoScrollView);

        // Generar un número aleatorio entre 1 y 100
        Random random = new Random();
        numeroAleatorio = random.nextInt(100) + 1;

        validarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarNumero();
            }
        });
    }

    private void validarNumero() {
        String numeroStr = numeroEditText.getText().toString();
        if (numeroStr.isEmpty()) {
            Toast.makeText(this, "Por favor, introduce un número.", Toast.LENGTH_SHORT).show();
            return;
        }

        int numeroUsuario = Integer.parseInt(numeroStr);
        intentos++;

        if (numeroUsuario < numeroAleatorio) {
            mostrarMensaje("El número que buscas es mayor.");
        } else if (numeroUsuario > numeroAleatorio) {
            mostrarMensaje("El número que buscas es menor.");
        } else {
            mostrarMensaje("¡Felicidades! Has adivinado el número en " + intentos + " intentos.");
            reiniciarJuego();
        }

        actualizarHistorico(numeroUsuario);
        actualizarIntentos();
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void actualizarHistorico(int numeroUsuario) {
        String historicoActual = historicoTextView.getText().toString();
        historicoActual += "Intento #" + intentos + ": " + numeroUsuario + "\n";
        historicoTextView.setText(historicoActual);

        historicoScrollView.post(new Runnable() {
            @Override
            public void run() {
                historicoScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void actualizarIntentos() {
        intentosTextView.setText("Intentos: " + intentos);
    }

    private void reiniciarJuego() {
        Random random = new Random();
        numeroAleatorio = random.nextInt(100) + 1;
        intentos = 0;
        historicoTextView.setText("");
        actualizarIntentos();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¡Felicidades! ¿Quieres jugar de nuevo?")
                .setPositiveButton("Sí", (dialog, id) -> {})
                .setNegativeButton("No", (dialog, id) -> finish());
        builder.create().show();
    }
}
