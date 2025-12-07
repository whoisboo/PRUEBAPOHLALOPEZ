package com.example.prueba_pohlalopez;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtApellido, txtEdad;
    Button btnActualizar, btnEliminar;

    FirebaseFirestore db;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtEdad = findViewById(R.id.txtEdad);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        db = FirebaseFirestore.getInstance();

        id = getIntent().getStringExtra("id");
        txtNombre.setText(getIntent().getStringExtra("nombre"));
        txtApellido.setText(getIntent().getStringExtra("apellido"));
        txtEdad.setText(String.valueOf(getIntent().getIntExtra("edad", 0)));

        btnActualizar.setOnClickListener(v -> actualizar());
        btnEliminar.setOnClickListener(v -> eliminar());
    }

    private void actualizar() {
        Map<String, Object> alumno = new HashMap<>();
        alumno.put("nombre", txtNombre.getText().toString());
        alumno.put("apellido", txtApellido.getText().toString());
        alumno.put("edad", Integer.parseInt(txtEdad.getText().toString()));

        db.collection("alumnos").document(id)
                .set(alumno)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    private void eliminar() {
        db.collection("alumnos").document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
