package com.example.unitconverter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unitconverter.models.RegisteredUser;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Conversions extends AppCompatActivity {
    private static final int RC_SIGN_IN = 777;
    private FirebaseAnalytics mFirebaseAnalytics;
    private GoogleSignInClient mGoogleSignInClient;

    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversions);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        preferences=getSharedPreferences("userPrefs",MODE_PRIVATE);
        databaseReference=FirebaseDatabase.getInstance().getReference("users");
        editor=preferences.edit();

        String email=preferences.getString("email","");
        if(!TextUtils.isEmpty(email)){
            startActivity(new Intent(Conversions.this,MainActivity.class));
            finish();
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("711083773690-c4omvkrju6tc4r0jo71k5329mrj7rgts.apps.googleusercontent.com")
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Button signIn = findViewById(R.id.button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            Log.d("TAG", "onActivityResult: before");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d("TAG", "onActivityResult: after"+task.isSuccessful());
            Log.d("TAG", "onActivityResult: after"+task.getException());

            handleSignInResult(task);
        }



    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent i = new Intent(Conversions.this, MainActivity.class);
            i.putExtra("user", account);


            List<String> sc=new ArrayList<>();

            RegisteredUser user=new RegisteredUser(account.getId(),account.getEmail(),String.valueOf(account.getPhotoUrl()),account.getDisplayName(),sc);
            databaseReference.child(account.getId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    editor.putString("email",account.getEmail());
                    editor.putString("id",account.getId());
                    editor.putString("pic", String.valueOf(account.getPhotoUrl()));
                    editor.putString("name",account.getDisplayName());
                    editor.commit();
                    editor.apply();

                    startActivity(i);
                }
            });



        } catch (ApiException e) {
        }
    }
}