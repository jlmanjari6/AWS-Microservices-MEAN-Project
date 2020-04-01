package com.example.tourismapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Random;

import com.example.tourismapp.Fragments.SearchFragment;
import com.example.tourismapp.Helpers.GlobalStorage;
import com.example.tourismapp.Helpers.Screenshot;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

public class generatedTicketLayout extends AppCompatActivity {
    String origin, destination, noOfPassengers, travelDate;
    String startTime, endTime;
    int fare, busNo;

    int userId;

    TextView originTV, destinationTV, startTimeTV, noOfSeatsTV, endTimeTV, fareTV, busNoTV, travelDateTV;

    RelativeLayout relativeLayout;

    Button home,pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_ticket_layout);

        String userEmail = ((GlobalStorage) this.getApplication()).getUserEmail();
        if(userEmail == null) {
            // user not logged in
        }
        else {
            // user logged in
            // fetch user id
            userId = ((GlobalStorage) this.getApplication()).getUserId();
            //int userId = ((GlobalStorage) this.getApplication()).getUserId();
        }

        Bundle bundle = getIntent().getExtras();
        origin = bundle.getString("fromLocation");
        destination = bundle.getString("toLocation");
        noOfPassengers = bundle.getString("noOfPassengers");
        startTime = bundle.getString("startTime");
        endTime = bundle.getString("endTime");
        fare = Integer.parseInt(bundle.getString("fare"));
        busNo = Integer.parseInt(bundle.getString("busNo"));
        travelDate = bundle.getString("travelDate");

        System.out.println(travelDate);

        originTV = (TextView) findViewById(R.id.fromLocation_value);
        destinationTV = (TextView) findViewById(R.id.toLocation_value);
        startTimeTV = (TextView) findViewById(R.id.startTime_value);
        endTimeTV = (TextView) findViewById(R.id.endTime_value);
        noOfSeatsTV = (TextView) findViewById(R.id.seats_value);
        fareTV = (TextView) findViewById(R.id.fare_value);
        busNoTV = (TextView) findViewById(R.id.busNO_value);
        travelDateTV = (TextView) findViewById(R.id.bookedDate);

        originTV.setText(origin);
        destinationTV.setText(destination);
        startTimeTV.setText(startTime);
        endTimeTV.setText(endTime);
        noOfSeatsTV.setText(noOfPassengers);
        fareTV.setText(String.valueOf(fare));
        busNoTV.setText(String.valueOf(busNo));
        travelDateTV.setText(travelDate);

        home = (Button) findViewById(R.id.homeButton);
        pdf = (Button) findViewById(R.id.pdfDownload);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home.setVisibility(View.GONE);
                pdf.setVisibility(View.GONE);
                relativeLayout = (RelativeLayout) findViewById(R.id.ssLayout);

                View rootView = relativeLayout;
                rootView.setDrawingCacheEnabled(true);
                //Bitmap bitmap =  rootView.getDrawingCache();

                if(shouldAskPermissions()) {
                    askPermissions();
                }

                Bitmap bitmap1 = Screenshot.takescreenshotOfRootView(relativeLayout);

                saveBitMap(bitmap1);
                home.setVisibility(View.VISIBLE);
            }
        });
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    public void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
    }

    private void saveBitMap(Bitmap bitmap) {
        Random random = new Random();
        int rand1 = random.nextInt(1000);
        String fileName = Environment.getExternalStorageDirectory().toString() + "/"+System.currentTimeMillis()+"ticket_"+busNo+"_"+String.valueOf(rand1)+".jpg";
        //ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        //File imagePath = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);//new File(Environment.getExternalStorageDirectory() + "/ticket.png");
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("Error", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("Error", e.getMessage(), e);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
