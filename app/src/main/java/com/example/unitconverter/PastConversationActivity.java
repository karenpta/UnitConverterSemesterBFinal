package com.example.unitconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.unitconverter.adapters.ConversionAdapter;
import com.example.unitconverter.models.ScreenShot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PastConversationActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ConversionAdapter adapter;
    List<ScreenShot> screenShotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_conversation);

        recyclerView=findViewById(R.id.passConvRecycler);

        ImageButton back=findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        screenShotList=new ArrayList<>();
        loadScreenShots();


    }

    private void loadScreenShots() {
        SharedPreferences preferences=getSharedPreferences("userPrefs",MODE_PRIVATE);
        String id=preferences.getString("id","");

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("screenShots").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    ScreenShot screenShot=snapshot1.getValue(ScreenShot.class);
                    screenShotList.add(screenShot);
                }

                adapter=new ConversionAdapter(PastConversationActivity.this,screenShotList);
                recyclerView.setLayoutManager(new GridLayoutManager(PastConversationActivity.this,2));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}