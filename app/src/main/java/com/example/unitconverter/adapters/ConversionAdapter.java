package com.example.unitconverter.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unitconverter.R;
import com.example.unitconverter.models.ScreenShot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConversionAdapter extends RecyclerView.Adapter<ConversionAdapter.MyViewHolder> {

    Context context;
    List<ScreenShot> screenShots;


    public ConversionAdapter(Context context, List<ScreenShot> screenShots) {
        this.context = context;
        this.screenShots = screenShots;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.screenshot_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ScreenShot screenShot=screenShots.get(position);

        Glide.with(context).load(screenShot.getPic()).into(holder.imageView);
        holder.textView.setText(milisToTime(screenShot.getTime()));
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStoragePic(screenShot ,position);
            }
        });

    }

    private String milisToTime(String time){
        long timeInMilliseconds=Long.parseLong(time);
        Date currentDate = new Date(timeInMilliseconds);
        return currentDate.toString();
     }

    private void deleteStoragePic(ScreenShot screenShot ,int pos) {
        StorageReference reference= FirebaseStorage.getInstance().getReferenceFromUrl(screenShot.getPic());

        screenShots.remove(pos);
        notifyDataSetChanged();

        reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference("screenShots").child(screenShot.getId());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snapshot1: snapshot.getChildren()){
                                ScreenShot s=snapshot1.getValue(ScreenShot.class);
                                if(s.getTime().equals(screenShot.getTime())){
                                    snapshot1.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return screenShots.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        ImageButton deleteBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item_image);
            textView=itemView.findViewById(R.id.time_text_item);
            deleteBtn=itemView.findViewById(R.id.deleteBtn_item);
        }
    }
}
