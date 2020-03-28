package com.example.tourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    int userID;
    EditText cvvET, cardNameET, cardNumberET;
    public BookingData bData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);

        Bundle bundle = getIntent().getExtras();
        origin = bundle.getString("fromLocation");
        destination = bundle.getString("toLocation");
        year = bundle.getString("Year");
        month = bundle.getString("Month");
        day = bundle.getString("Day");
        noOfPassengers = bundle.getString("noOfPassengers");
        String temp = bundle.getString("userID");
        userID = Integer.parseInt(temp);
        userID = 1;

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
                    SimpleDateFormat simpleformat = new SimpleDateFormat("MM/dd/yyyy");
                    String bookingDate = simpleformat.format(cal.getTime());


                    int noOfSeats = Integer.parseInt(noOfPassengers);
                    int busID = 17;

                    bData = new BookingData(userID,origin, destination, travelDate.toString(), bookingDate, noOfSeats, busID);
                    saveTicketToDB();

                    //Toast.makeText(getApplicationContext(),"Ticket is booked", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("flag","1");
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveTicketToDB(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.15:61188/") // replace your local ip address here (but not localhost/127.0.0.1)
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
