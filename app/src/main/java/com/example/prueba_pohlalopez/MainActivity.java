package com.example.prueba_pohlalopez;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    Button btnAgregar;

    FirebaseFirestore db;
    ArrayList<Alumno> alumnos;
    ArrayAdapter<String> adapter;
    ArrayList<String> nombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.listaAlumnos);
        btnAgregar = findViewById(R.id.btnAgregar);

        db = FirebaseFirestore.getInstance();
        alumnos = new ArrayList<>();
        nombres = new ArrayList<>();

        cargarDatos();

        btnAgregar.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AgregarActivity.class);
            startActivity(i);
        });

        lista.setOnItemClickListener((adapterView, view, position, id) -> {
            Alumno a = alumnos.get(position);

            Intent i = new Intent(MainActivity.this, EditarActivity.class);
            i.putExtra("id", a.getId());
            i.putExtra("nombre", a.getNombre());
            i.putExtra("apellido", a.getApellido());
            i.putExtra("edad", a.getEdad());
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
    }

    private void cargarDatos() {
        alumnos.clear();
        nombres.clear();

        db.collection("alumnos").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Alumno a = new Alumno(
                            doc.getId(),
                            doc.getString("nombre"),
                            doc.getString("apellido"),
                            doc.getLong("edad").intValue()
                    );
                    alumnos.add(a);
                    nombres.add(a.getNombre() + " " + a.getApellido());
                }

                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres);
                lista.setAdapter(adapter);
            }
        });
    }
}
