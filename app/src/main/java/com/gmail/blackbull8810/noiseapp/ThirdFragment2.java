package com.gmail.blackbull8810.noiseapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * 사용하지 않는 Fragment
 */

public class ThirdFragment2 extends Fragment {

    private View view;

    public ThirdFragment2() {
        // Required empty public constructor
    }

    public static ThirdFragment2 newInstance() {
        
        Bundle args = new Bundle();
        
        ThirdFragment2 fragment = new ThirdFragment2();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_third, container, false);



        return view;
    }

}
