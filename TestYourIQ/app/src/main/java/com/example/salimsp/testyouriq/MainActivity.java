package com.example.salimsp.testyouriq;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    Button btnMath, btnIQ, btnGK, btnEnglish, btnPhysics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialing Id's
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        btnMath = findViewById(R.id.btnMath);
        btnIQ = findViewById(R.id.btnIQ);
        btnGK = findViewById(R.id.btnGK);
        btnPhysics = findViewById(R.id.btnPhysics);
        btnEnglish = findViewById(R.id.btnEnglish);

        btn();

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        if (id == R.id.action_exit) {

            finish();

            return true;
        }

        if (id == R.id.action_scoreBoard){

            startActivity(new Intent(MainActivity.this,ScoreBoard.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_score) {

            startActivity(new Intent(MainActivity.this,ScoreBoard.class));

        } else if (id == R.id.nav_about) {

            aboutAlert();

        } else if (id == R.id.nav_exit) {

            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void btn(){

    btnMath.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            calling(MainActivity.this, Start.class,Database.TBL_MathQtAns,  btnMath.getText().toString());

        }
    });

    btnEnglish.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            calling(MainActivity.this, Start.class,Database.TBL_EnglishQtAns,  btnEnglish.getText().toString());

        }
    });

    btnIQ.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            calling(MainActivity.this, Start.class,Database.TBL_IQQtAns,  "IQ");

        }
    });

    btnGK.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            calling(MainActivity.this, Start.class,Database.TBL_GKQtAns,  "GK");

        }
    });

    btnPhysics.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            calling(MainActivity.this, Start.class,Database.TBL_PhysicsQtAns,  btnPhysics.getText().toString());

        }
    });

}

    public void calling(Context c, Class cl, String TBL_NAME, String subject){

        startActivity(new Intent(c,cl));
        new Database(c).getQtAns(TBL_NAME);
        ScoreBoard.subject = subject;
        ScoreBoard.username = "";
        ScoreBoard.score = 0;
        ScoreBoard.time = "";

    }


    private void aboutAlert() {

        String msg = "Total subject: 5\n\nQuestion per subject: 10\n\nTime: 2 minutes";

        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        alert.setTitle("About");

        alert.setMessage(msg);

        alert.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alert.setCancelable(true);

            }
        });

        AlertDialog dialog = alert.create();

        dialog.show();


        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        positiveButton.setTextColor(getResources().getColor(R.color.BLACK));

    }

}
