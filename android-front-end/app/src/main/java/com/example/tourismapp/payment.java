package com.example.tourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tourismapp.Helpers.GlobalStorage;
import com.example.tourismapp.Interface.RetrofitApiInterface;
import com.example.tourismapp.Models.BookingData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class payment extends AppCompatActivity {
    String origin, destination, year, month, day, noOfPassengers, CVV, cardName, cardNumber;
    String startTime, endTime;
    int userID, fare, busId, busNo;
    EditText cvvET, cardNameET, cardNumberET;
    public BookingData bData = null;

    int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);

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
        year = bundle.getString("Year");
        month = bundle.getString("Month");
        day = bundle.getString("Day");
        noOfPassengers = bundle.getString("noOfPassengers");
        String temp = bundle.getString("userID");
        startTime = bundle.getString("startTime");
        endTime = bundle.getString("endTime");
        fare = Integer.parseInt(bundle.getString("fare"));
        busId = Integer.parseInt(bundle.getString("busId"));
        busNo = Integer.parseInt(bundle.getString("busNo"));
        userID = Integer.parseInt(temp);
        //userID = 11;
        System.out.println(userID+"------------");
        System.out.println(busId);
        cvvET = (EditText) findViewById(R.id.etCVV);
        cardNameET = (EditText) findViewById(R.id.cardName_et);
        cardNumberET = (EditText) findViewById(R.id.cardNumber_et);

        Button proceedBtn = (Button) findViewById(R.id.paymentButton);
        try {
            proceedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CVV = cvvET.getText().toString();
                    cardName = cardNameET.getText().toString();
                    cardNumber = cardNumberET.getText().toString();

                    try{
                        if(CVV.length() == 0 || (Integer.parseInt(CVV)<101 || Integer.parseInt(CVV)>999)){
                            Toast.makeText(getApplicationContext(),"Invalid CVV", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }catch (NumberFormatException numberFormatException){
                        Toast.makeText(getApplicationContext(),"CVV should be 3 digit numbers", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(cardName.length()==0){
                        Toast.makeText(getApplicationContext(),"Invalid Card Holder Name", Toast.LENGTH_LONG).show();
                        return;
                    }
                    cardNumber = cardNumber.replaceAll("-","");
                    long cardNO;
                    try {
                        cardNO = Long.parseLong(cardNumber);
                    } catch(NumberFormatException ne){
                        Toast.makeText(getApplicationContext(),"Invalid Card Number", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (cardNumber.length()!=16){
                        Toast.makeText(getApplicationContext(),"Invalid Card Number (Hint:1111-1111-1111-1111)", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (userID == 0){
                        Toast.makeText(getApplicationContext(),"Login first to Book Ticket", Toast.LENGTH_LONG).show();
                        return;
                    }

                    String tempD = month+"/"+day+"/"+year;
                    Date travelDate = null;
                    try {
                        travelDate = new SimpleDateFormat("MM/DD/YYYY").parse(tempD);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar cal = Calendar.getInstance();
                    Date date = cal.getTime();
                    SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String bookingDate = simpleformat.format(date);

                    String travelDate_Temp = simpleformat.format(date);

                    int noOfSeats = Integer.parseInt(noOfPassengers);

                    bData = new BookingData(userID,origin, destination, travelDate_Temp, bookingDate, noOfSeats, busId);

                    saveTicketToDB();

                    Bundle bundle1 = new Bundle();
                    bundle1.putString("fromLocation",origin);
                    bundle1.putString("toLocation",destination);
                    bundle1.putString("travelDate",tempD);
                    bundle1.putString("noOfPassengers",String.valueOf(noOfSeats));
                    bundle1.putString("userID",String.valueOf(userID));
                    bundle1.putString("startTime", startTime);
                    bundle1.putString("endTime", endTime);
                    bundle1.putString("fare",String.valueOf(fare));
                    bundle1.putString("busNo", String.valueOf(busNo));
                    //Toast.makeText(getApplicationContext(),"Ticket is booked", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),generatedTicketLayout.class);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveTicketToDB(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fv2z97pt9c.execute-api.us-east-1.amazonaws.com/dev/booking/") // replace your local ip address here (but not localhost/127.0.0.1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
        Call<BookingData> call = retrofitApiInterface.createBookingDetails(bData);
        call.enqueue(new Callback<BookingData>() {
            @Override
            public void onResponse(Call<BookingData> call, Response<BookingData> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                BookingData bookingResponse = response.body();
                System.out.println("API response: " + bookingResponse);
            }

            @Override
            public void onFailure(Call<BookingData> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
