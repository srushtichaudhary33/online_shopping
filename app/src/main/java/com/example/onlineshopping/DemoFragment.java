package com.example.onlineshopping;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DemoFragment extends Fragment {

    public DemoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        //TextView name = view.findViewById(R.id.activity_fragment_open);
        //Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
        return view;
    }
}