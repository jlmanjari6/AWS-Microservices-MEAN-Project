package com.example.tourismapp.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismapp.Models.Location;
import com.example.tourismapp.R;

import java.util.ArrayList;

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.ViewHolder> {
    ArrayList<Location> alLocation;
    Context context;
    onClickListener listener;

    public RViewAdapter(ArrayList<Location> alLocation, Context context, onClickListener listener) {
        this.alLocation = alLocation;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RViewAdapter.ViewHolder holder, int position) {
        if(alLocation.size() > 0)
        {
            final Location destination = alLocation.get(position);

            holder.locationImage.setImageResource(R.drawable.ic_booking);
            holder.locationName.setText(destination.getAttraction());
            holder.locationCity.setText(destination.getCity());
            holder.locationDescription.setText(destination.getDesc());

            holder.book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                    {
                        listener.onItemClickListener(holder.getAdapterPosition(),alLocation.get(holder.getAdapterPosition()));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return alLocation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView locationImage;
        TextView locationName, locationCity, locationDescription;
        Button book;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            locationImage = (ImageView)itemView.findViewById(R.id.photo);
            locationName = (TextView)itemView.findViewById(R.id.attraction);
            locationCity = (TextView)itemView.findViewById(R.id.city);
            locationDescription = (TextView)itemView.findViewById(R.id.description);
            book = (Button)itemView.findViewById(R.id.book);
        }
    }

    public void doRefresh(ArrayList<Location> alLocation)
    {
        this.alLocation = alLocation;
        notifyDataSetChanged();
    }

    public interface onClickListener
    {
        void onItemClickListener(int position,Location destination);
    }
}
