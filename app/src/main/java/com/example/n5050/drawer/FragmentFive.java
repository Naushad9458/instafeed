package com.example.n5050.drawer;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFive extends Fragment {
    LinearLayout linearLayout;


    public FragmentFive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_five, container, false);
        linearLayout=(LinearLayout) view.findViewById(R.id.frame5);



        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("myprefs",0);
        TextView teacher=new TextView(getContext());
        teacher.setTextColor(Color.BLUE);
        teacher.setTextSize(15);

        teacher.setText("Taught By: "+sharedPreferences.getString("teacher4",""));
        linearLayout.addView(teacher);


        for(int i=0;i<6;i++){
            String ques="question"+i;
            TextView textView=new TextView(getContext());
            textView.setText(sharedPreferences.getString(ques,"q"));
            textView.setId((i+1));
            textView.setTextSize(18);
            textView.setPadding(5,5,5,5);
            textView.setTextColor(Color.BLACK);
            linearLayout.addView(textView);
            RadioGroup radioGroup = new RadioGroup(getContext());
            radioGroup.setId((i + 1) * 1000);
            radioGroup.setOrientation(RadioGroup.HORIZONTAL);
            radioGroup.setPadding(10,15,10,10);
            final RadioButton radioButton[]=new RadioButton[5];
            for(int j=0;j<5;j++){
                radioButton[j] = new RadioButton(getContext());
                radioGroup.addView(radioButton[j]);
                radioButton[j].setText("" + (j + 1));
                radioButton[j].setId((j+1)*100);
                radioButton[j].setHighlightColor(Color.GREEN);
                radioButton[j].setPadding(0,0,5,0);
            }
            linearLayout.addView(radioGroup);




        }
        Button submit=new Button(getContext());
        submit.setText("SUBMIT");
        submit.setPadding(5,5,5,5);
        submit.setTextColor(Color.WHITE);
        submit.setBackgroundColor(Color.BLACK);
        linearLayout.addView(submit);
        return view;
    }

}
