package com.example.n5050.drawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {
    public String courses[]=new String[6];
    String name;
    String teachers[]=new String[6];
    String questions[]=new String[10];
    String loginurl="http://52aef4e1.ngrok.io/instafeed/conf.php";
    String suburl="http://52aef4e1.ngrok.io/instafeed/subjects.php";
    String quesurl="http://52aef4e1.ngrok.io/instafeed/quesurl.php";
    RequestQueue requestQueue;
    String enroll,course1_res,course2_res,course3_res,course4_res,course5_res,course6_res;
    SharedPreferences sharedPreferences;
    protected boolean isLogged=false;
    String phone;
    EditText otp;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        otp=(EditText) findViewById(R.id.otp);

        Button button=(Button) findViewById(R.id.loginbtn);
        sharedPreferences= getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        TextView textView=(TextView) findViewById(R.id.msg);
        Bundle extras=getIntent().getExtras();
        if(extras !=null){
            phone=extras.getString("phone");
            phone=phone.substring(9);
            enroll=extras.getString("enrollment");
            name=extras.getString("name");
            course1_res=extras.getString("course1_res");
            course2_res=extras.getString("course2_res");
            course3_res=extras.getString("course3_res");
            course4_res=extras.getString("course4_res");
            course5_res=extras.getString("course5_res");
            course6_res=extras.getString("course6_res");
        }
        textView.setText("An OTP has been sent to your registered mobile number ending with "+ phone);

    }
    protected  void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences=getSharedPreferences("myprefs",Context.MODE_PRIVATE);
        isLogged=sharedPreferences.getBoolean("isLogged",false);
        if(isLogged){
            startActivity(new Intent(StartActivity.this,MainActivity.class));
        }
    }
    public  void login(View view){
        final ProgressDialog progressDialog=new ProgressDialog(StartActivity.this);
        progressDialog.setMessage("Authenticating...Please Wait");
        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, loginurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("name",response);
                if(response.equalsIgnoreCase("failure")){
                    AlertDialog alertDialog = new AlertDialog.Builder(StartActivity.this).create();
                    alertDialog.setTitle("Authentication Failed!");
                    alertDialog.setMessage("Entered OTP is incorrect.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Dismiss",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    progressDialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else
                if(response.equalsIgnoreCase("Success"))
                {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        getSubjects();
                        getQuestions();
                        editor.putString("name", name);
                        editor.putString("enrollment",enroll);
                        editor.putBoolean("isLogged", true);
                        editor.putString("course1_res",course1_res);
                        editor.putString("course2_res",course2_res);
                        editor.putString("course3_res",course3_res);
                        editor.putString("course4_res",course4_res);
                        editor.putString("course5_res",course5_res);
                        editor.putString("course6_res",course6_res);
                        editor.commit();
                        Log.e("Shared pref data",getSharedPreferences("myprefs",0).getString("course1_res","1"));
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                        progressDialog.dismiss();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("enrollment",enroll);
                params.put("otp",otp.getText().toString().trim());
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
    public void getSubjects(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, suburl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Subjects",response);
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("result");
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        String teacher;
                        String coursename;
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        coursename = jsonObject.getString("coursename");
                        teacher=jsonObject.getString("teacher");
                        editor.putString("course"+i,coursename);
                        editor.putString("teacher"+i,teacher);
                        editor.apply();
                    }



                }
                catch (JSONException e){
                    e.printStackTrace();

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    public void getQuestions(){
        StringRequest req=new StringRequest(Request.Method.POST, quesurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Questions",response);
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("ques");
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    for (int i = 0; i < jsonArray.length(); i++) {


                        String question;
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        question=jsonObject.getString("question");
                        questions[i]=question;
                        editor.putString("question"+i,question);
                        editor.commit();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(req);
    }

}
