package com.example.prueba_pohlalopez;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper db = new DBHelper(this);
        SQLiteDatabase sql = db.getWritableDatabase();

        // hilo de 5 segundos
        new Handler().postDelayed(() -> {
            Intent i = new Intent(MainActivity.this, ListarActivity.class);
            startActivity(i);
            finish();
        }, 5000); // 5 segundos
    }
}
