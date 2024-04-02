package com.gayuu.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class Update extends AppCompatActivity {

    EditText updateTitle,updateDescription;
    Button updatesavetodatabase;
    String usertitle,userdescription,userstarttime;
    DatabaseReference databaseReference;
    TextView choosetime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        updateTitle=findViewById(R.id.updateTitle);
        updateDescription=findViewById(R.id.updateDescription);
        choosetime=findViewById(R.id.Cchoosetime);
        updatesavetodatabase=findViewById(R.id.updatesavedata);
        showdata();
        databaseReference= FirebaseDatabase.getInstance().getReference("Task");
        updatesavetodatabase.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(istitlechange() || isdescriptionchange() ){

                  Toast.makeText(Update.this,"Saved",Toast.LENGTH_SHORT).show();
              }
              else{
                  Toast.makeText(Update.this,"No Changes found",Toast.LENGTH_SHORT).show();

              }
              Intent intent=new Intent(Update.this, Viewcarddata.class);
              startActivity(intent);

          }
      });

        createnotificationChannel();
        choosetime.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View view) {

                                              Toast.makeText(Update.this,"No changes in time ",Toast.LENGTH_SHORT).show();
                                          }
        });


    }

      private void createnotificationChannel(){
        if(Build.VERSION.SDK_INT >= 0){
            CharSequence name="channel";
            String  desc="Channel for Alarm Manager";
            int imp= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel("Task",name,imp);
            channel.setDescription(desc);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

    }

    public boolean istitlechange(){
        if(!usertitle.equals(updateTitle.getText().toString())){
            databaseReference.child(usertitle).child("title").setValue(updateTitle.getText().toString());
            usertitle=updateTitle.getText().toString();
            return true;
        }else{
            return false;
        }

    }
    public boolean isdescriptionchange(){
        if(!userdescription.equals(updateDescription.getText().toString())){
            databaseReference.child(usertitle).child("description").setValue(updateDescription.getText().toString());
            userdescription=updateDescription.getText().toString();
            return true;
        }else{
            return false;
        }

    }

    public void showdata(){
        Intent intent=getIntent();
        usertitle=intent.getStringExtra("title");
        userdescription=intent.getStringExtra("description");
        userstarttime=intent.getStringExtra("startTime");
        updateTitle.setText(usertitle);
        updateDescription.setText(userdescription);
        choosetime.setText(userstarttime);

    }

}