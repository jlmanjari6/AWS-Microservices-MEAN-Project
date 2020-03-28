package com.example.tourismapp.Fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.tourismapp.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {
    @NonNull
    ArrayList<String> bookedDate;
    ArrayList<String> ticketId;
    ArrayList<String> origin;
    ArrayList<String> destination;
    ArrayList<String> travelDate;
    ArrayList<String> fromTime;
    ArrayList<String> toTime;
    ArrayList<String> noSeats;
    ArrayList<String> busNo;
    ArrayList<String> fare;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> bookedDate, ArrayList<String> ticketId, ArrayList<String> origin, ArrayList<String> destination, ArrayList<String> travelDate, ArrayList<String> fromTime, ArrayList<String> toTime, ArrayList<String> noSeats, ArrayList<String> busNo, ArrayList<String> fare) {
        this.context = context;
        this.bookedDate = bookedDate;
        this.ticketId = ticketId;
        this.origin = origin;
        this.destination = destination;
        this.travelDate = travelDate;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.noSeats = noSeats;
        this.busNo = busNo;
        this.fare = fare;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.bookedDate.setText("Booked on "+bookedDate.get(position));
        holder.ticketId.setText(ticketId.get(position));
        holder.origin.setText(origin.get(position));
        holder.destination.setText(destination.get(position));
        holder.travelDate.setText(travelDate.get(position));
        holder.fromTime.setText(fromTime.get(position));
        holder.toTime.setText(toTime.get(position));
        holder.noSeats.setText(noSeats.get(position));
        holder.busNo.setText(busNo.get(position));
        holder.fare.setText("Total fare:  CAD "+fare.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookedDate.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bookedDate, ticketId, origin, destination, travelDate, fromTime, toTime, noSeats, busNo, fare;

        public MyViewHolder(View itemView) {
            super(itemView);
            bookedDate =(TextView)itemView.findViewById(R.id.bookedDate);
            ticketId = (TextView)itemView.findViewById(R.id.ticketId);
            origin = (TextView)itemView.findViewById(R.id.origin);
            destination=(TextView)itemView.findViewById(R.id.destination);
            travelDate = (TextView)itemView.findViewById(R.id.travelDate);
            fromTime = (TextView)itemView.findViewById(R.id.fromTime);
            toTime =(TextView)itemView.findViewById(R.id.toTime);
            noSeats = (TextView)itemView.findViewById(R.id.noSeats);
            busNo = (TextView)itemView.findViewById(R.id.busNo);
            fare =(TextView)itemView.findViewById(R.id.fare);

        }
    }
}
