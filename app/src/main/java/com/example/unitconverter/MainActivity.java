package com.example.unitconverter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    GoogleSignInAccount account;
    private ImageAdapter adapter;

    CardView temperature;
    CardView mass;
    CardView length;
    CardView speed;
    CardView volume;
    CardView time;
    CardView fuel;
    CardView area;
    CardView pressure;
    CardView frequency;
    CardView energy;
    CardView storage;
    TextView timer;
    CardView past;
    ImageButton logoutBtn;
    Button pastConvBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences preferences=getSharedPreferences("userPrefs",MODE_PRIVATE);

        SharedPreferences.Editor editor=preferences.edit();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            account = (GoogleSignInAccount) extras.get("user");
        }
        TextView welcome = findViewById(R.id.welcomeTV);
        welcome.setText("Welcome, " + preferences.getString("name",""));

        ImageView userImage = findViewById(R.id.mainuserimage);
        Glide.with(this).load(preferences.getString("pic","")).into(userImage);


        pastConvBtn=findViewById(R.id.pastConversationsBtn);
        logoutBtn=findViewById(R.id.logoutBtn);
        temperature = findViewById(R.id.temperature);
        mass = findViewById(R.id.mass);
        length = findViewById(R.id.length);
        speed = findViewById(R.id.speed);
        volume = findViewById(R.id.volume);
        time = findViewById(R.id.time);
        fuel = findViewById(R.id.fuel);
        area = findViewById(R.id.area);
        pressure = findViewById(R.id.pressure);
        frequency = findViewById(R.id.frequency);
        energy = findViewById(R.id.energy);
        storage = findViewById(R.id.storage);
        timer = findViewById(R.id.timer);
        past = findViewById(R.id.past);
        Calendar c = Calendar.getInstance();
        System.out.println("Current dateTime => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c.getTime());
        timer.setText("Current Time : "+formattedDate);

        pastConvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PastConversationActivity.class));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.clear();
                editor.commit();
                editor.apply();

                startActivity(new Intent(MainActivity.this,Conversions.class));
                finish();
            }
        });

        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_temperature.class));
            }
        });
        mass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_mass.class));
            }
        });
        length.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_length.class));
            }
        });
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_speed.class));
            }
        });
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_volume.class));
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_time.class));
            }
        });
        fuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_fuel.class));
            }
        });
        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_area.class));
            }
        });
        pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_pressure.class));
            }
        });
        frequency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_frequency.class));
            }
        });
        energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_energy.class));
            }
        });
        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, con_storage.class));
            }
        });
        // Obtain the FirebaseAnalytics instance.
        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, screenshot.class));
            }
        });
    }

}