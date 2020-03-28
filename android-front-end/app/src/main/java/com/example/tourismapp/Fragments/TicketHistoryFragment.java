package com.example.tourismapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismapp.Helpers.GlobalStorage;
import com.example.tourismapp.Interface.RetrofitApiInterface;
import com.example.tourismapp.Models.TicketDetails;
import com.example.tourismapp.Models.Topplaces;
import com.example.tourismapp.R;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketHistoryFragment extends Fragment {
    @Nullable
    RecyclerView recyclerView;
    int userId;
    ArrayList<String> bookedDate = new ArrayList<>();
    ArrayList<String> ticketId = new ArrayList<>();
    ArrayList<String> origin = new ArrayList<>();
    ArrayList<String> destination = new ArrayList<>();
    ArrayList<String> travelDate = new ArrayList<>();
    ArrayList<String> fromTime = new ArrayList<>();
    ArrayList<String> toTime = new ArrayList<>();
    ArrayList<String> noSeats = new ArrayList<>();
    ArrayList<String> busNo = new ArrayList<>();
    ArrayList<String> fare = new ArrayList<>();


    Context c;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_activity_history, container, false);

        userId = ((GlobalStorage) getActivity().getApplication()).getUserId();
        Log.e("userrId","is"+userId);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ticketHistory(c);

        return view;
    }

    public void ticketHistory(Context c) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:192.168.56.1:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiInterface service = retrofit.create(RetrofitApiInterface.class);
        Call<List<TicketDetails>> call = service.ticketHistory(userId);
        call.enqueue(new Callback<List<TicketDetails>>() {

            @Override
            public void onResponse(Call<List<TicketDetails>> call, Response<List<TicketDetails>> response) {
                try {
                    int i=0;
                    List<TicketDetails> listtickets = response.body();
                    Log.d("hi", " "+listtickets);
                    for (TicketDetails c : listtickets) {
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.ENGLISH);
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMMM", Locale.ENGLISH);
                        Date date = null;
                        try {
                            date = (Date) sdf1.parse(c.getBookingDate());
                            String newDate = sdf2.format(date);
                            System.out.println(newDate);
                            Log.e("Date", newDate);
                            bookedDate.add(newDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d("hey", " "+c.getBookingDate());
                        ticketId.add(c.getTicketId());
                        origin.add(c.getOrigin());
                        destination.add(c.getDestination());
                        Date date1 = null;
                        try {
                            date1 = (Date) sdf1.parse(c.getTravelDate());
                            String newDate1 = sdf2.format(date1);
                            Log.e("Date", newDate1);
                            travelDate.add(newDate1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        fromTime.add(c.getStartTime());
                        toTime.add(c.getEndTime());
                        noSeats.add(c.getNoOfSeats());
                        busNo.add(c.getBusNo());
                        fare.add(c.getFare());
                    }
                    CustomAdapter customAdapter = new CustomAdapter(c, bookedDate, ticketId, origin, destination, travelDate, fromTime, toTime, noSeats, busNo, fare);
                    recyclerView.setAdapter(customAdapter);
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<TicketDetails>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
//https://abhiandroid.com/programming/json