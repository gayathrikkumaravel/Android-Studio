package com.gayuu.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.lang.CharSequence;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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



public class GetData extends AppCompatActivity {



    EditText Title,Description;
    Button savetodatabase;
    DatabaseReference databaseReference;
    String tasktitle, taskDescription, taskstarttime;


    private MaterialTimePicker timePicker;
    private Calendar calender;
    private AlarmManager alarmManager;
    private PendingIntent pendingintent;
    TextView choosetime;
    FloatingActionButton set,cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        savetodatabase=findViewById(R.id.savedata);
        Title=findViewById(R.id.Title);
        Description=findViewById(R.id.Description);
        databaseReference= FirebaseDatabase.getInstance().getReference("Task");
        savetodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validatefields()){
                InsertData();}
            }
        });
        createnotificationChannel();
        choosetime=findViewById(R.id.Cchoosetime);
        cancel=findViewById(R.id.canceltime);
        set=findViewById(R.id.settime);
        choosetime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                displayclock(choosetime);
            }

        });

        set.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                createalarm();
            }

        });
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                 Intent intent=new Intent(GetData.this,AlarmReceiver.class);
                pendingintent=PendingIntent.getBroadcast(GetData.this,0,intent, PendingIntent.FLAG_IMMUTABLE);
                if(alarmManager==null){
                    alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                }
                alarmManager.cancel(pendingintent);
                Toast.makeText(GetData.this,"Cancel",Toast.LENGTH_SHORT).show();
                choosetime.setText((CharSequence) choosetime);

            }
        });
    }

   public void InsertData(){
        tasktitle=Title.getText().toString();
        taskDescription=Description.getText().toString();
        taskstarttime=choosetime.getText().toString();
       UserClass user=new UserClass(tasktitle,taskDescription,taskstarttime);
        databaseReference.child(tasktitle).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(GetData.this,"Saved Task ",Toast.LENGTH_SHORT).show();
                             Intent intent=new Intent(GetData.this,Schedule.class);
                                startActivity(intent);
                        }}
                });

    }
    private void createnotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            CharSequence name="channel";
            String  desc="Channel for Alarm Manager";
            int imp= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel= null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = new NotificationChannel("Task",name,imp);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel.setDescription(desc);
            }
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(channel);
            }


        }}
    @SuppressLint("ScheduleExactAlarm")
    public void createalarm() {
        try{
            int requestId=(int) System.currentTimeMillis();
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(GetData.this, AlarmReceiver.class);
        intent.putExtra("Title", Title.getText().toString());
        intent.putExtra("Description", Description.getText().toString());


            pendingintent = PendingIntent.getBroadcast(GetData.this, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingintent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingintent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingintent);
            }}
            pendingintent = PendingIntent.getBroadcast(GetData.this, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis() , pendingintent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingintent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingintent);
                }
            }


    }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void displayclock(TextView Time){
        timePicker=new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Choose  Alarm Time")
                .build();
        timePicker.show(getSupportFragmentManager(),"Task");
        timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timePicker.getHour()>12){
                    Time.setText(String.format("%02d",(timePicker.getHour()-12))+":"+String.format("%02d",timePicker.getMinute())+"PM");

                }
                else{
                    Time.setText(timePicker.getHour()+":"+timePicker.getMinute()+"AM");
                }
                calender=Calendar.getInstance();
                calender.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                calender.set(Calendar.MINUTE,timePicker.getMinute());
                calender.set(Calendar.SECOND,0);
                calender.set(Calendar.MILLISECOND,0);
            }
        });
    }
    public boolean validatefields(){
        if(Title.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this,"Please enter a valid title",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(Description.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this,"Please enter a valid Description",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
}


