package com.example.n5050.drawer;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {
    LinearLayout linearLayout;
    String submiturl="http://52aef4e1.ngrok.io/instafeed/submitResponse.php";
    String updateurl="http://52aef4e1.ngrok.io/instafeed/updatedb.php";
    String enroll;


    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_two, container, false);
        linearLayout=(LinearLayout) view.findViewById(R.id.frame2);



        final SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("myprefs",0);
        TextView teacher=new TextView(getContext());
        teacher.setTextColor(Color.BLUE);
        teacher.setTextSize(15);
        enroll=sharedPreferences.getString("enrollment","");

        teacher.setText("Taught By: "+sharedPreferences.getString("teacher1",""));
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
            radioGroup.setId((i + 1) + 1000);
            radioGroup.setOrientation(RadioGroup.HORIZONTAL);
            radioGroup.setPadding(10,15,10,10);
            final RadioButton radioButton[]=new RadioButton[5];
            for(int j=0;j<5;j++){
                radioButton[j] = new RadioButton(getContext());
                radioGroup.addView(radioButton[j]);
                radioButton[j].setText("" + (j + 1));
                radioButton[j].setId((j+1)+100);
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog prog=new ProgressDialog(getContext());
                prog.setMessage("Submitting");
                prog.show();
                StringRequest request=new StringRequest(Request.Method.POST, submiturl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Submit response",response);
                        if(response.equalsIgnoreCase("Success")){
                            prog.dismiss();
                            updateDb();
                            SharedPreferences.Editor edit=sharedPreferences.edit();
                            edit.putString("course2_res","true");
                            edit.commit();

                            BlankFragment blankFragment=new BlankFragment();
                            FragmentManager manager=getFragmentManager();
                            manager.beginTransaction().replace(R.id.conmain,blankFragment).commit();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        String inp=sharedPreferences.getString("name","");
                        inp=inp.replace(" ","");
                        params.put("enrollment",enroll);
                        params.put("course",sharedPreferences.getString("course1","0"));
                        String[] responses={"res_one","res_two","res_three","res_four","res_five","res_six"};
                        for(int i=0;i<6;i++){

                            RadioGroup rg=(RadioGroup) linearLayout.findViewById((i+1)+1000);
                            RadioButton button=(RadioButton) linearLayout.findViewById(rg.getCheckedRadioButtonId());
                            params.put(responses[i],button.getText().toString());
                        }
                        return params;
                    }

                };
                request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MySingleton.getInstance(getContext()).addToRequestQueue(request);
            }
        });
        return view;

    }
    public void updateDb(){
        StringRequest request=new StringRequest(Request.Method.POST, updateurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("enrollment",enroll);
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }


}
