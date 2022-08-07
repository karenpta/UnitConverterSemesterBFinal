package com.example.unitconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unitconverter.models.RegisteredUser;
import com.example.unitconverter.models.ScreenShot;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nmd.screenshot.Screenshot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class con_energy extends AppCompatActivity {
    CardView cv_fromUnit, cv_toUnit, cv_convert;
    RelativeLayout mCLayout;
    String fromUnit = "Celcius";
    String toUnit = "Farenheit";
    TextView tv_fromUnit, tv_toUnit;
    EditText et_fromUnit, et_toUnit;
    final String[] values = new String[]{
            "Joule",
            "Calorie",
            "Watt hour",
            "Electron Volt",
            "Megajoule", "Gigajoule"
    };

    Screenshot screenshot;
    UtillsUpload utillsUpload;
    CardView screenShotBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_energy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.temp_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        tv_fromUnit.setText(values[0]);
        tv_toUnit.setText(values[0]);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        screenShotBtn=findViewById(R.id.screen);
        screenshot=new Screenshot(con_energy.this);
        utillsUpload=new UtillsUpload(this);

        screenShotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenshot.notificationTitle("Screenshot");
                screenshot.setCallback(new Screenshot.OnResultListener() {
                    @Override
                    public void result(boolean success, String filePath, Bitmap bitmap) {
                        if(success){
                            Toast.makeText(con_energy.this, "Uploading Screenshot...", Toast.LENGTH_SHORT).show();
                            utillsUpload.uploadScreenShot(bitmap);
                        }
                    }
                });
                //After you have done your settings let's take the screenshot
                screenshot.takeScreenshot();
            }
        });


        cv_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempInput = et_fromUnit.getText().toString();
                if (tempInput.equals("") || tempInput == null) {
                    et_fromUnit.setError("Please enter some value");
                } else {
                    if (tv_fromUnit.getText().toString().equals(values[0])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(tempInput);
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(celciusToFarenheit(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(celciusToKelvin(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(celciusToRankine(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(celciusToNewton(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(celciusToDelisle(Double.parseDouble(tempInput)));
                        }
                    } else if (tv_fromUnit.getText().toString().equals(values[1])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(fahrenheitToCelcius(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(tempInput);
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(fahrenheitToKelvin(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(fahrenheitToRankine(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(fahrenheitToNewton(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(fahrenheitToDelisle(Double.parseDouble(tempInput)));
                        }
                    } else if (tv_fromUnit.getText().toString().equals(values[2])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(kelvinToCelcius(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(kelvinToFahrenheit(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(tempInput);
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(kelvinToRankine(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(kelvinToNewton(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(kelvinToDelisle(Double.parseDouble(tempInput)));
                        }
                    } else if (tv_fromUnit.getText().toString().equals(values[3])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(rankineToCelcius(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(rankineToFahrenheit(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(rankineToKelvin(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(tempInput);
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(rankineToNewton(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(rankineToDelisle(Double.parseDouble(tempInput)));
                        }
                    } else if (tv_fromUnit.getText().toString().equals(values[4])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(newtonToCelcius(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(newtonToFahrenheit(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(newtonToKelvin(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(newtonToRankine(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(tempInput);
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(newtonToDelisle(Double.parseDouble(tempInput)));
                        }
                    } else if (tv_fromUnit.getText().toString().equals(values[5])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(delisleToCelcius(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(delisleToFahrenheit(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(delisleToKelvin(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(delisleToRankine(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(delisleToNewton(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(tempInput);
                        }
                    }
                }
            }
        });
        cv_toUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(con_energy.this);
                builder.setTitle("choose Unit");
                final String[] flowers = new String[]{
                        "Joule",
                        "Calorie",
                        "Watt hour",
                        "Electron Volt",
                        "Megajoule", "Gigajoule"
                };
                builder.setSingleChoiceItems(
                        flowers,
                        -1,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String selectedItem = Arrays.asList(flowers).get(i);
                                toUnit = selectedItem;
                                tv_toUnit.setText(toUnit);
                            }
                        });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        cv_fromUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(con_energy.this);
                builder.setTitle("choose Unit");
                final String[] flowers = new String[]{
                        "Joule",
                        "Calorie",
                        "Watt hour",
                        "Electron Volt",
                        "Megajoule", "Gigajoule"
                };
                builder.setSingleChoiceItems(
                        flowers,
                        -1,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String selectedItem = Arrays.asList(flowers).get(i);
                                fromUnit = selectedItem;
                                tv_fromUnit.setText(fromUnit);
                            }
                        });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }




    private String celciusToKelvin(double celsius) {
        double kelvin = celsius/1e+6;
        return String.valueOf(kelvin);
    }
    private String celciusToRankine(double celsius) {
        double rankine = celsius /10000;
        return String.valueOf(rankine);
    }
    private String celciusToNewton(double celsius) {
        double newton = celsius /100;
        return String.valueOf(newton);
    }
    private String celciusToDelisle(double celsius) {
        double delisle = celsius*100;
        return String.valueOf(delisle);
    }
    private String celciusToFarenheit(double celsius) {
        double fahrenheit = celsius* 10000;
        return String.valueOf(fahrenheit);
    }
    private String fahrenheitToKelvin(double fahrenheit) {
        double kelvin = fahrenheit*100;
        return String.valueOf(kelvin);
    }
    private String fahrenheitToRankine(double fahrenheit) {
        double rankine = fahrenheit* 10000;
        return String.valueOf(rankine);
    }
    private String fahrenheitToNewton(double fahrenheit) {
        double newton = fahrenheit*1e+8;
        return String.valueOf(newton);
    }
    private String fahrenheitToDelisle(double fahrenheit) {
        double delisle = fahrenheit*1e+10;
        return String.valueOf(delisle);
    }
    private String fahrenheitToCelcius(double fahrenheit) {
        double celcius = fahrenheit*1e+6;
        return String.valueOf(celcius);
    }
    private String kelvinToRankine(double kelvin) {
        double rankine = kelvin * 100;
        return String.valueOf(rankine);
    }
    private String kelvinToNewton(double kelvin) {
        double newton = kelvin*1e+6;
        return String.valueOf(newton);
    }
    private String kelvinToDelisle(double kelvin) {
        double delisle = kelvin*1e+8;
        return String.valueOf(delisle);
    }
    private String kelvinToCelcius(double kelvin) {
        double celcius = kelvin* 10000;
        return String.valueOf(celcius);
    }
    private String kelvinToFahrenheit(double kelvin) {
        double fahrenheit = kelvin/100;
        return String.valueOf(fahrenheit);
    }
    private String rankineToNewton(double rankine) {
        double newton = rankine*10000;
        return String.valueOf(newton);
    }
    private String rankineToDelisle(double rankine) {
        double delisle = rankine*1e+6;
        return String.valueOf(delisle);
    }
    private String rankineToCelcius(double rankine) {
        double celcius = rankine*100;
        return String.valueOf(celcius);
    }
    private String rankineToFahrenheit(double rankine) {
        double fahrenheit = rankine /10000;
        return String.valueOf(fahrenheit);
    }
    private String rankineToKelvin(double rankine) {
        double kelvin = rankine /100;
        return String.valueOf(kelvin);
    }
    private String newtonToDelisle(double newton) {
        double delisle = newton*100;
        return String.valueOf(delisle);
    }
    private String newtonToCelcius(double newton) {
        double celcius = newton / 100 ;
        return String.valueOf(celcius);
    }
    private String newtonToFahrenheit(double newton) {
        double fahrenheit = newton /1e+8;
        return String.valueOf(fahrenheit);
    }
    private String newtonToKelvin(double newton) {
        double kelvin = newton/1e+6;
        return String.valueOf(kelvin);
    }
    private String newtonToRankine(double newton) {
        double rankine = newton /10000;
        return String.valueOf(rankine);
    }
    private String delisleToCelcius(double delisle) {
        double celcius = delisle/10000;
        return String.valueOf(celcius);
    }
    private String delisleToFahrenheit(double delisle) {
        double fahrenheit = delisle/1e+10;
        return String.valueOf(fahrenheit);
    }
    private String delisleToKelvin(double delisle) {
        double kelvin = delisle/1e+8;
        return String.valueOf(kelvin);
    }
    private String delisleToRankine(double delisle) {
        double rankine = delisle/1e+6;
        return String.valueOf(rankine);
    }
    private String delisleToNewton(double delisle) {
        double newton = delisle/100;
        return String.valueOf(newton);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return  true;
    }
}