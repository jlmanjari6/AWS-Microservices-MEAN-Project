package com.example.tourismapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

        etLocation = (EditText)view.findViewById(R.id.etLocation);
        ibSearch = (ImageButton)view.findViewById(R.id.ibSearch);

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

        // add search button onclick listener to make search API call
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"response: ",Toast.LENGTH_LONG).show();
                InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                getLocations(etLocation.getText().toString());
            }
        });

        // add editText onActionListener to perform search button click on Enter key press
        etLocation.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    ibSearch.performClick();
                    return true;
                }
              return false;
            };
        });
    }

    private void getLocations(String city) {
        final String url = "http://search-load-balancer-825170455.us-east-1.elb.amazonaws.com/search/locations/" + city;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        alLocations.clear();
//                        Toast.makeText(getActivity(),"response: "+response.length(),Toast.LENGTH_LONG).show();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jo = response.getJSONObject(i);
                            Location locationItem = new Location();

                            locationItem.setId(jo.getInt("id"));
                            locationItem.setAttraction(jo.getString("attraction"));
                            locationItem.setCity(jo.getString("city"));
                            locationItem.setDesc(jo.getString("description"));
                            locationItem.setImageURL(jo.getString("imageURL"));
                            alLocations.add(locationItem);
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
        Toast.makeText(getContext(),"Book ticket for " + (destination.getId()) + ' ' +  (destination.getAttraction()), Toast.LENGTH_LONG).show();
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
