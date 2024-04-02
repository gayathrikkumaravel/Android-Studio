package com.gayuu.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Notification extends AppCompatActivity {
    TextView viewtitle,viewdescription;
    Button closebutton;
    ImageView notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        viewtitle=findViewById(R.id.viewtitle);
        viewdescription=findViewById(R.id.viewdescription);

        closebutton=findViewById(R.id.closebutton);
        notification=findViewById(R.id.notification);
        Animation shake= AnimationUtils.loadAnimation(this,R.anim.anim);
        notification.startAnimation(shake);
        if(getIntent().getExtras()!=null){
            viewtitle.setText(getIntent().getStringExtra("Title"));
            viewdescription.setText(getIntent().getStringExtra("Description"));

        }
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}