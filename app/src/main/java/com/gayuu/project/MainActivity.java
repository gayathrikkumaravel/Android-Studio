package com.gayuu.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button gotoschedule;
    CardView scheduletime,learn,master,chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduletime=findViewById(R.id.scheduletime);
        learn=findViewById(R.id.learn);
        master=findViewById(R.id.master);
        chat=findViewById(R.id.chat);
        gotoschedule=findViewById(R.id.gotoschedule);
        gotoschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,Schedule.class);
                startActivity(i);
            }
        });
        scheduletime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,Schedule.class);
                startActivity(i);
            }
        });
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();

            }
        });
        master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();

            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();

            }
        });
    }
}