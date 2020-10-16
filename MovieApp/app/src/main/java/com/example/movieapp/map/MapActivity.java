package com.example.movieapp.map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.movieapp.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Button mapButton =findViewById(R.id.btnMap);
        mapButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText editText=findViewById(R.id.etAddress);
                Intent intent = new Intent(MapActivity.this, GMAPActivity.class);
                intent.putExtra("location", editText.getText().toString());
                Log.i(" location ", editText.getText().toString());
                startActivity(intent);
            }
        });

    }
}
