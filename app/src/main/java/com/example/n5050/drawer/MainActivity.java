package com.example.n5050.drawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    NavigationView navigationView;
    String names[]=new String[6];
    SharedPreferences sharedPreferences;
    String name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        sharedPreferences=getSharedPreferences("myprefs",Context.MODE_PRIVATE);
        final Menu menu=navigationView.getMenu();


        name=sharedPreferences.getString("name","Not Available");
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);
        TextView text = (TextView) header.findViewById(R.id.texthead);
        text.setText(name);
        menu.findItem(R.id.item1).setTitle(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course0","1"));
        menu.findItem(R.id.item2).setTitle(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course1","2"));
        menu.findItem(R.id.item3).setTitle(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course2","3"));
        menu.findItem(R.id.item4).setTitle(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course3","4"));
        menu.findItem(R.id.item5).setTitle(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course4","5"));
        menu.findItem(R.id.item6).setTitle(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course5","6"));

        Toast.makeText(getApplicationContext(),sharedPreferences.getString("course1_res","1"),Toast.LENGTH_LONG).show();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else
        if(id==R.id.logout){
            SharedPreferences sharedPreferences=getSharedPreferences("myprefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("isLogged",false);
            editor.commit();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.item1) {
            // Handle the camera action
            if(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course1_res","").equalsIgnoreCase("true")){
                BlankFragment blankFragment=new BlankFragment();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.conmain,blankFragment).commit();
            }
            else{
            FragmentOne fragmentOne=new FragmentOne();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.conmain,fragmentOne).commit();
            }
        } else if (id == R.id.item2) {
            if(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course2_res","").equalsIgnoreCase("true")){
                BlankFragment blankFragment=new BlankFragment();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.conmain,blankFragment).commit();
            }
            else{
            FragmentTwo fragmentTwo=new FragmentTwo();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.conmain,fragmentTwo).commit();}

        } else if (id == R.id.item3) {
            if(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course3_res","").equalsIgnoreCase("true")){
                BlankFragment blankFragment=new BlankFragment();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.conmain,blankFragment).commit();
            }
            else{
            FragmentThree fragmentThree=new FragmentThree();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.conmain,fragmentThree).commit();}

        } else if (id == R.id.item4) {
            if(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course4_res","").equalsIgnoreCase("true")){
                BlankFragment blankFragment=new BlankFragment();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.conmain,blankFragment).commit();
            }
            else {
            FragmentFour fragmentFour=new FragmentFour();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.conmain,fragmentFour).commit();}

        } else if (id == R.id.item5) {
            if(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course5_res","").equalsIgnoreCase("true")){
                BlankFragment blankFragment=new BlankFragment();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.conmain,blankFragment).commit();
            }
            else {
            FragmentFive fragmentFive=new FragmentFive();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.conmain,fragmentFive).commit();}

        } else if (id == R.id.item6) {
            if(getSharedPreferences("myprefs",Context.MODE_PRIVATE).getString("course1_res","").equalsIgnoreCase("true")){
                BlankFragment blankFragment=new BlankFragment();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.conmain,blankFragment).commit();
            }
            else {
            FragmentSix fragmentSix=new FragmentSix();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.conmain,fragmentSix).commit();}

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
