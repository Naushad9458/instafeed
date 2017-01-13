package com.example.n5050.drawer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
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

public class LoginActivity extends AppCompatActivity {
    EditText enroll;
    Button proceed;
    String url="http://52aef4e1.ngrok.io/instafeed/sms.php";
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        enroll=(EditText) findViewById(R.id.ennum);
        proceed=(Button) findViewById(R.id.proceedbtn);
    }
    public void proceed(View view){
        if(enroll.getText().toString().trim().equals("")){
            enroll.setError("This Field is required");
        }
        else {
            enroll.setError(null);

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.e("respnse", response);
                    if (response.equalsIgnoreCase("fail")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setTitle("Invalid Enrollment");
                        alertDialog.setMessage("Entered Enrollment Number is not Valid");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Dismiss",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        progressDialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    } else {

                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("res");
                            JSONObject object = jsonArray.getJSONObject(0);
                            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                            intent.putExtra("phone", object.getString("phone"));
                            intent.putExtra("enrollment", object.getString("enrollment"));
                            intent.putExtra("name", object.getString("name"));
                            intent.putExtra("course1_res", object.getString("course1_res"));
                            intent.putExtra("course2_res", object.getString("course2_res"));
                            intent.putExtra("course3_res", object.getString("course3_res"));
                            intent.putExtra("course4_res", object.getString("course4_res"));
                            intent.putExtra("course5_res", object.getString("course5_res"));
                            intent.putExtra("course6_res", object.getString("course6_res"));
                            Log.e("name", object.getString("name"));
                            startActivity(intent);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    String inp=enroll.getText().toString().trim().toLowerCase();
                    inp=inp.replace(" ","");
                    params.put("enrollment",inp);
                    return params;
                }

            };
            request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);


        }
    }
}
