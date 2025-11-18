package com.example.prueba_pohlalopez;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;

import java.util.ArrayList;

public class ListarActivity extends AppCompatActivity {

    ListView lista;
    ArrayList<String> datos;
    ArrayList<Integer> codigos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        lista = findViewById(R.id.listaEstudiantes);
        Button btnAgregar = findViewById(R.id.btnIrAgregar);

        cargarDatos();

        btnAgregar.setOnClickListener(v ->
                startActivity(new Intent(this, AgregarActivity.class))
        );

        lista.setOnItemClickListener((a, v, pos, id) -> {
            Intent i = new Intent(this, VerActivity.class);
            i.putExtra("CODIGO", codigos.get(pos));
            startActivity(i);
        });
    }

    private void cargarDatos() {
        DBHelper db = new DBHelper(this);
        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor c = sql.rawQuery("SELECT * FROM ESTUDIANTES", null);

        datos = new ArrayList<>();
        codigos = new ArrayList<>();

        while (c.moveToNext()) {
            codigos.add(c.getInt(0));
            datos.add(c.getString(1) + " - " + c.getString(2));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, datos);
        lista.setAdapter(adapter);

        c.close();
    }
    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
    }

}
