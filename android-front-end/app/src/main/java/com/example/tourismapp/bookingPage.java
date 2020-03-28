package com.example.tourismapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismapp.Helpers.DatePickerFragment;
import com.example.tourismapp.Helpers.GlobalStorage;

import java.text.DateFormat;
import java.util.Calendar;


public class bookingPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String fromLocation;
    String toLocation;
    int noOfPassenger;
    EditText fromLocation_et, toLocation_et;
    Spinner spinner;
    int Y, M, D;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_layout);

        // check if user is logged in

        String userEmail = ((GlobalStorage) this.getApplication()).getUserEmail();
        if(userEmail == null) {
            // user not logged in
        }
        else {
            // user logged in
            // fetch user id
            userId = ((GlobalStorage) this.getApplication()).getUserId();
            int userId = ((GlobalStorage) this.getApplication()).getUserId();
        }

        fromLocation_et = (EditText) findViewById(R.id.fromEditText);
        toLocation_et = (EditText) findViewById(R.id.toEditText);
        spinner = (Spinner) findViewById(R.id.spinner1);

        Button dateButton1 = (Button) findViewById(R.id.selectDate);

        dateButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new datePickerFragement();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        Button proccedBtn = (Button) findViewById(R.id.paymentButton);
        proccedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromLocation = fromLocation_et.getText().toString();
                toLocation = toLocation_et.getText().toString();
                if (fromLocation.length()==0) {
                    Toast.makeText(getApplicationContext(), "Error: Enter Source Location", Toast.LENGTH_LONG).show();
                    return;
                }
                if (toLocation.length()==0){
                    Toast.makeText(getApplicationContext(), "Error: Enter Destination Name", Toast.LENGTH_LONG).show();
                    return;
                }
                String tempS = spinner.getSelectedItem().toString();
                noOfPassenger = Integer.parseInt(tempS);
                if(Y == 0 || M == 0 || D == 0){
                    Toast.makeText(getApplicationContext(), "Error: Date is Incorrect", Toast.LENGTH_LONG).show();
                    return;
                }
                //System.out.println(Y+" "+M+" "+D);

                Bundle bundle = new Bundle();
                bundle.putString("fromLocation",fromLocation);
                bundle.putString("toLocation",toLocation);
                bundle.putString("Year",String.valueOf(Y));
                bundle.putString("Month",String.valueOf(M));
                bundle.putString("Day",String.valueOf(D));
                bundle.putString("noOfPassengers",String.valueOf(noOfPassenger));
                bundle.putString("userID",String.valueOf(userId));
                Intent intent = new Intent(getApplicationContext(), payment.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Year);
        calendar.set(Calendar.MONTH, Month);
        calendar.set(Calendar.DAY_OF_MONTH, Day);

        TextView textView = (TextView) findViewById(R.id.dateEdit);
        textView.setText(Month+"/"+Day+"/"+Year);

        Y = Year;
        M = Month;
        D = Day;
    }
}
