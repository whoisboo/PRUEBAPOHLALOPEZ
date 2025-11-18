package com.example.prueba_pohlalopez;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtDesc;
    int codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        txtNombre = findViewById(R.id.editNombre);
        txtDesc = findViewById(R.id.editDesc);
        Button btnActualizar = findViewById(R.id.btnActualizar);

        codigo = getIntent().getIntExtra("CODIGO", 0);

        cargarDatos();

        btnActualizar.setOnClickListener(v -> actualizar());
    }

    private void cargarDatos() {
        DBHelper db = new DBHelper(this);
        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor c = sql.rawQuery("SELECT * FROM ESTUDIANTES WHERE CODIGO=" + codigo, null);

        if (c.moveToFirst()) {
            txtNombre.setText(c.getString(1));
            txtDesc.setText(c.getString(2));
        }

        c.close();
    }

    private void actualizar() {
        DBHelper db = new DBHelper(this);
        SQLiteDatabase sql = db.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("NOMBRE", txtNombre.getText().toString());
        valores.put("DESCRIPCION", txtDesc.getText().toString());

        sql.update("ESTUDIANTES", valores, "CODIGO=" + codigo, null);

        Toast.makeText(this, "Actualizado!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
