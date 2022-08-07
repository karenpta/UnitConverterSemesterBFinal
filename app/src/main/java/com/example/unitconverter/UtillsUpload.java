package com.example.unitconverter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.unitconverter.models.ScreenShot;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class UtillsUpload {

    Context context;

    public UtillsUpload(Context context) {
        this.context = context;
    }

    public void uploadScreenShot(Bitmap bitmap) {
        SharedPreferences preferences=context.getSharedPreferences("userPrefs",MODE_PRIVATE);
        String id=preferences.getString("id","");
        String time=String.valueOf(System.currentTimeMillis());

        StorageReference storageRef = FirebaseStorage.getInstance().getReference("screenshots").child(time);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return storageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    saveDownloadUriToDatabase(downloadUri,id);
                } else {
                    Toast.makeText(context, "Uploading failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveDownloadUriToDatabase(Uri downloadUri, String id) {
        DatabaseReference screenShotsRef= FirebaseDatabase.getInstance().getReference("screenShots");

        String time=String.valueOf(System.currentTimeMillis());
        ScreenShot screenshot=new ScreenShot(id,String.valueOf(downloadUri),time);
        screenShotsRef.child(id).child(time).setValue(screenshot).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "ScreenShot Uploaded Successfully", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, "Failed "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
