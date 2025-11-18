package com.example.prueba_pohlalopez;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;

public class VerActivity extends AppCompatActivity {

    TextView txtNombre, txtDesc;
    int codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre = findViewById(R.id.verNombre);
        txtDesc = findViewById(R.id.verDesc);
        Button btnEditar = findViewById(R.id.btnEditar);
        Button btnEliminar = findViewById(R.id.btnEliminar);

        codigo = getIntent().getIntExtra("CODIGO", 0);
        cargarDatos();

        btnEditar.setOnClickListener(v -> {
            Intent i = new Intent(this, EditarActivity.class);
            i.putExtra("CODIGO", codigo);
            startActivity(i);
        });

        btnEliminar.setOnClickListener(v -> {
            DBHelper db = new DBHelper(this);
            SQLiteDatabase sql = db.getWritableDatabase();
            sql.execSQL("DELETE FROM ESTUDIANTES WHERE CODIGO = " + codigo);
            finish();
        });

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

    private void eliminar() {
        DBHelper db = new DBHelper(this);
        SQLiteDatabase sql = db.getWritableDatabase();

        sql.execSQL("DELETE FROM ESTUDIANTES WHERE CODIGO=" + codigo);

        Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ListarActivity.class));
        finish();
    }
}
