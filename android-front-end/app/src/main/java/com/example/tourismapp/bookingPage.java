package com.example.tourismapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismapp.Helpers.DatePickerFragment;
import com.example.tourismapp.Helpers.GlobalStorage;
import com.example.tourismapp.Interface.RetrofitApiInterface;
import com.example.tourismapp.Models.BusData;
import com.example.tourismapp.Models.LocationModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.widget.AdapterView.OnItemSelectedListener;



public class bookingPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, OnItemSelectedListener {
    String fromLocation;
    String toLocation, startTime1, endTime1;
    int noOfPassenger;
    String fare1, busId1;
    TextView toLocation_et, fromTime, endTime, fareTV, busID_tv;
    Spinner spinner, busNoSpinner;

    int Y, M, D;
    int userId;
    int locationID;

    String busNO;

    ArrayList<String> location = new ArrayList<String>();
    ArrayList<Integer> locationId = new ArrayList<Integer>();
    public ArrayList<Integer> busNo = new ArrayList<Integer>();
    public ArrayList<Integer> busId = new ArrayList<Integer>();
    public ArrayList<String> busFromTime = new ArrayList<String>();
    public ArrayList<String> busEndTime = new ArrayList<String>();
    public ArrayList<Integer> fare = new ArrayList<Integer>();

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
            //int userId = ((GlobalStorage) this.getApplication()).getUserId();
        }

        final Spinner fromLocation_sp = (Spinner) findViewById(R.id.fromLocation_sp);
        locationDataFromDB(fromLocation_sp);
        busNoSpinner = (Spinner) findViewById(R.id.selectBusNo);
        toLocation_et = (TextView) findViewById(R.id.toLocation);

        Bundle bndl = getIntent().getExtras();

        locationID = Integer.parseInt(bndl.getString("locationId"));
        toLocation_et.setText(bndl.getString("name"));
        fromTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        fareTV = (TextView) findViewById(R.id.fare);
        busID_tv = (TextView) findViewById(R.id.busId);
        spinner = (Spinner) findViewById(R.id.spinner1);
        getBusNo(locationID, busNoSpinner, fromTime, endTime, fareTV, busID_tv, spinner);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int busNO_temp;
                if(fareTV.getText().toString() == ""){
                    busNO_temp = 1;
                }
                else{
                    busNO_temp = Integer.parseInt(fareTV.getText().toString());
                }
                int noOfSeats_temp = Integer.parseInt(spinner.getSelectedItem().toString());
                int temp = busNO_temp * noOfSeats_temp;
                fareTV.setText(String.valueOf(temp));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                fromLocation = fromLocation_sp.getSelectedItem().toString();
                toLocation = toLocation_et.getText().toString();
                startTime1 = fromTime.getText().toString();
                endTime1 = endTime.getText().toString();
                fare1 = (fareTV.getText().toString());
                busId1 = (busID_tv.getText().toString());
                busNO = busNoSpinner.getSelectedItem().toString();

                if (fromLocation.length()==0) {
                    Toast.makeText(getApplicationContext(), "Error: Enter Source Location", Toast.LENGTH_LONG).show();
                    return;
                }
                if (toLocation.length()==0){
                    Toast.makeText(getApplicationContext(), "Error: Enter Destination Name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (fromLocation.equalsIgnoreCase(toLocation)){
                    Toast.makeText(getApplicationContext(), "Error: Source and Destination can not be same", Toast.LENGTH_LONG).show();
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
                bundle.putString("startTime", startTime1);
                bundle.putString("endTime", endTime1);
                bundle.putString("fare",fare1);
                bundle.putString("busNo", busNO);
                bundle.putString("busId",busId1);
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

    public void locationDataFromDB(Spinner fromLocation_sp){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fv2z97pt9c.execute-api.us-east-1.amazonaws.com/dev/") // replace your local ip address here (but not localhost/127.0.0.1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
        Call<List<LocationModel>> call = retrofitApiInterface.locationName();
        call.enqueue(new Callback<List<LocationModel>>() {
            @Override
            public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                try {
                    List<LocationModel> locationModels = response.body();

                    for (LocationModel b: locationModels){
                        locationId.add(b.getId());
                        location.add(b.getLocation());
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, location);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    fromLocation_sp.setAdapter(dataAdapter);
                    
                    fromLocation_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                catch (Exception e){
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<LocationModel>> call, Throwable t) {
                System.out.println("error");
            }
        });
    }

    public void getBusNo(int locationID, Spinner spinner, TextView fromTime, TextView endTime, TextView fareTV, TextView busID_tv, Spinner spinner1){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fv2z97pt9c.execute-api.us-east-1.amazonaws.com/dev/") // replace your local ip address here (but not localhost/127.0.0.1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);

        Call<List<BusData>> call = retrofitApiInterface.busData(locationID);
        call.enqueue(new Callback<List<BusData>>() {
            @Override
            public void onResponse(Call<List<BusData>> call, Response<List<BusData>> response) {
                List<BusData> busData = response.body();
                for(BusData b: busData){
                    busNo.add(b.getBusNo());
                    busId.add(b.getBusId());
                    busFromTime.add(b.getStartTime());
                    busEndTime.add(b.getEndTime());
                    fare.add(b.getFare());
                }
                //b1 = busData;
                ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, busNo);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
                spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        //Toast.makeText(getApplicationContext(),"You have selected",Toast.LENGTH_LONG).show();
                        fromTime.setText(busFromTime.get(position));
                        endTime.setText(busEndTime.get(position));
                        int temp = Integer.parseInt(fare.get(position).toString());
                        int temp2 = Integer.parseInt(spinner1.getSelectedItem().toString());
                        temp = temp * temp2;
                        fareTV.setText(String.valueOf(temp));
                        busID_tv.setText(busId.get(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<BusData>> call, Throwable t) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String label = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), "You selected: " + label, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
