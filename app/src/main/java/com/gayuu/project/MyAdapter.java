package com.gayuu.project;





import static androidx.core.content.ContextCompat.getDrawable;
import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
     ArrayList<UserClass> list;
    DatabaseReference databaseReference;




    public MyAdapter(Context context, ArrayList<UserClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vi= LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        return new MyViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        databaseReference= FirebaseDatabase.getInstance().getReference("Task");
        UserClass user=list.get(position);
        holder.Title.setText(user.getTitle());
        holder.Description.setText(user.getDescription());
        holder.Starttime.setText(user.getStartTime());

        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder completedialog =new AlertDialog.Builder(context);
                completedialog.setTitle("Complete the task!")
                        .setMessage("Are you sure you want to mark this task as complete.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Dialog dialog=new Dialog(context);
                                dialog.setContentView(R.layout.dialog);
                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                Button done=dialog.findViewById(R.id.done);
                              done.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               databaseReference.child(String.valueOf(user.getKey())).removeValue();
              dialog.dismiss();



           }


       });
                                dialog.show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }


        });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,DetailView.class);
                intent.putExtra("Title",list.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("Description",list.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("StartTime",list.get(holder.getAdapterPosition()).getStartTime());
                intent.putExtra("key",list.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public  int getItemCount() {
        return list.size();
    }

    public void searchdatalist(ArrayList<UserClass> searchlist){
        list=searchlist;
        notifyDataSetChanged();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
      TextView Title,Description,Starttime;
     CardView card;
      ImageView complete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.texttitle);
            Description=itemView.findViewById(R.id.textdescription);
            Starttime=itemView.findViewById(R.id.textstarttime);
            card=itemView.findViewById(R.id.cardview);
            complete=itemView.findViewById(R.id.completeimage);
        }
    }
}



