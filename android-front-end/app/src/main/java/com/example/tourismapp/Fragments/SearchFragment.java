package com.example.tourismapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tourismapp.Helpers.GlobalStorage;
import com.example.tourismapp.Models.Location;
import com.example.tourismapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements RViewAdapter.onClickListener {

    EditText etLocation;
    ImageButton ibSearch;
    RecyclerView rvLocations;
    RViewAdapter rviewAdapter;
    private Location locationObj;
    private RequestQueue queue;
    ArrayList<com.example.tourismapp.Models.Location> alLocations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        alLocations = new ArrayList<Location>();

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etLocation = (EditText)view.findViewById(R.id.destination);
        ibSearch = (ImageButton)view.findViewById(R.id.search);

        queue = Volley.newRequestQueue(getActivity());

        rvLocations = (RecyclerView)view.findViewById(R.id.rv_destination);
        rvLocations.setLayoutManager(new LinearLayoutManager(rvLocations.getContext()));
        rvLocations.setItemAnimator(new DefaultItemAnimator());
        if(rviewAdapter == null)
        {
            rviewAdapter = new RViewAdapter(alLocations,getContext(),SearchFragment.this);
        }

        rvLocations.setAdapter(rviewAdapter);
        rviewAdapter.notifyDataSetChanged();

        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"response: ",Toast.LENGTH_LONG).show();
                getLocations(etLocation.getText().toString());
            }
        });
    }

    private void getLocations(String city) {
        final String url = "http://search-load-balancer-825170455.us-east-1.elb.amazonaws.com/search/locations/" + city;

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    alLocations.clear();
                    Toast.makeText(getActivity(),"response: "+response.length(),Toast.LENGTH_LONG).show();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jo = response.getJSONObject(i);
                        Location dest = new Location();

                        dest.setAttraction(jo.getString("attraction"));
                        dest.setCity(jo.getString("city"));
                        dest.setDesc(jo.getString("description"));
                        alLocations.add(dest);
                    }
                    rviewAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getContext(),"Error Retriving Data",Toast.LENGTH_LONG).show();
                    }
                }

        );
        queue.add(request);
    }


    @Override
    public void onItemClickListener(int position, com.example.tourismapp.Models.Location destination) {
        locationObj = destination;
        Toast.makeText(getContext(),"Booking button clicked from position" + (position+1), Toast.LENGTH_LONG).show();
        // check if user is logged in
        String userEmail = ((GlobalStorage) getActivity().getApplication()).getUserEmail();
        if(userEmail == null) {
            // user not logged in
        }
        else {
            // user logged in
            // fetch user id
            int userId = ((GlobalStorage) getActivity().getApplication()).getUserId();
//          BookingFragment fragment = new BookingFragment();
//          FragmentTransaction ft = getFragmentManager().beginTransaction();
//          ft.replace(((ViewGroup) (getView().getParent())).getId(), fragment);
//          ft.addToBackStack(null);
//          ft.commit();
        }
    }
}
