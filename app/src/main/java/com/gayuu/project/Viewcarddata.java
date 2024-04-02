package com.gayuu.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import android.widget.SearchView;
import androidx.appcompat.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Viewcarddata extends AppCompatActivity {
    RecyclerView recyclerview;
    ArrayList<UserClass> list;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    SearchView searchview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcarddata);
        recyclerview=findViewById(R.id.recyclerview);
        searchview=findViewById(R.id.search);
        searchview.clearFocus();;
        databaseReference= FirebaseDatabase.getInstance().getReference("Task");
        list=new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(this,list);
        recyclerview.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    UserClass user=dataSnapshot.getValue(UserClass.class);
                    user.setKey(dataSnapshot.getKey());
                    list.add(user);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchlist(s);
                return true;
            }
        });

    }
    public void searchlist(String Text){
        ArrayList<UserClass> searchlist=new ArrayList<>();
        for(UserClass userclass:list){
            if(userclass.getTitle().toLowerCase().contains(Text.toLowerCase())){
                searchlist.add(userclass);
            }
        }
        adapter.searchdatalist(searchlist);
    }

}