package com.gayuu.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DetailView extends AppCompatActivity {
    TextView detailtitle,detaildescription,detailstarttime;
    String usertitle,userdescription,userstarttime;
    FloatingActionButton editbutton;
    String key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        detailtitle=findViewById(R.id.detailtitle);
        detaildescription=findViewById(R.id.detaildescription);
        detailstarttime=findViewById(R.id.detailstarttime);
        editbutton=findViewById(R.id.editbutton);
        showUserData();
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            detailtitle.setText(bundle.getString("Title"));
            detaildescription.setText(bundle.getString("Description"));
            detailstarttime.setText(bundle.getString("StartTime"));
            key=bundle.getString("key");
        }
    }
    public void showUserData(){
        Intent intent=getIntent();
        usertitle=intent.getStringExtra("Title");
        userdescription=intent.getStringExtra("Description");
        userstarttime=intent.getStringExtra("StartTime");
        detailtitle.setText(usertitle);
        detaildescription.setText(userdescription);
        detailstarttime.setText(userstarttime);

    }
    public void passUserData() {
        String usertitle = detailtitle.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Task");
        Query check = reference.orderByChild("title").equalTo(usertitle);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String titlefromDB = snapshot.child(usertitle).child("title").getValue(String.class);
                    String descriptionfromDB = snapshot.child(usertitle).child("description").getValue(String.class);
                    String starttimefromDB = snapshot.child(usertitle).child("startTime").getValue(String.class);
                    Intent intent = new Intent(DetailView.this, Update.class);
                    intent.putExtra("title", titlefromDB);
                    intent.putExtra("description", descriptionfromDB);
                    intent.putExtra("startTime", starttimefromDB);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}