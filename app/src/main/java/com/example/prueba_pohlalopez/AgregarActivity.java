package com.example.prueba_pohlalopez;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AgregarActivity extends AppCompatActivity {

    EditText txtNombre, txtApellido, txtEdad;
    Button btnGuardar;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtEdad = findViewById(R.id.txtEdad);
        btnGuardar = findViewById(R.id.btnGuardar);

        db = FirebaseFirestore.getInstance();

        btnGuardar.setOnClickListener(v -> guardar());
    }

    private void guardar() {
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        int edad = Integer.parseInt(txtEdad.getText().toString());

        Map<String, Object> alumno = new HashMap<>();
        alumno.put("nombre", nombre);
        alumno.put("apellido", apellido);
        alumno.put("edad", edad);

        db.collection("alumnos")
                .add(alumno)
                .addOnSuccessListener(doc -> {
                    Toast.makeText(this, "Alumno agregado", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
