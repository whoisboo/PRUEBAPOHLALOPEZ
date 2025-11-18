package com.example.prueba_pohlalopez;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;

public class AgregarActivity extends AppCompatActivity {

    EditText txtNombre, txtDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        txtNombre = findViewById(R.id.txtNombre);
        txtDesc = findViewById(R.id.txtDescripcion);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> guardar());
    }

    private void guardar() {
        DBHelper db = new DBHelper(this);
        SQLiteDatabase sql = db.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("NOMBRE", txtNombre.getText().toString());
        valores.put("DESCRIPCION", txtDesc.getText().toString());

        sql.insert("ESTUDIANTES", null, valores);

        Toast.makeText(this, "Guardado!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
