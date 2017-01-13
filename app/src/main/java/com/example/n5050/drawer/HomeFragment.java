package com.example.n5050.drawer;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    LinearLayout linearLayout;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        linearLayout=(LinearLayout) getActivity().findViewById(R.id.fraghome);
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView=new TextView(getContext());
        textView.setText(sharedPreferences.getString("name",""));
        linearLayout.addView(textView);
        return  view;
    }

}
