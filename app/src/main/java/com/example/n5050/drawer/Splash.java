package com.example.n5050.drawer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class Splash extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView=(TextView) findViewById(R.id.txtview);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/ds.ttf");
        textView.setTypeface(typeface);
        Thread timer=new Thread()
        {
            public void run(){
                try{
                    sleep(6000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(Splash.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }
    protected void onPause(){
        super.onPause();
        finish();

    }
}
