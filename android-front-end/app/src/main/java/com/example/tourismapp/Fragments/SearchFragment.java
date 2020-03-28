package com.example.tourismapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tourismapp.Helpers.GlobalStorage;
import com.example.tourismapp.R;

public class SearchFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // check if user is logged in
        String userEmail = ((GlobalStorage) getActivity().getApplication()).getUserEmail();
        if(userEmail == null) {
            // user not logged in
        }
        else {
            // user logged in
            // fetch user id
            int userId = ((GlobalStorage) getActivity().getApplication()).getUserId();
        }
         return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
