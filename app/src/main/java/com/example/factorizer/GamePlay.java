package com.example.factorizer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


public class GamePlay extends AppCompatActivity {

    public Button btn2,btn3,btn4,btn5;
    public EditText num;
    public TextView N,streak;
    Random random = new Random();
    public int x,y,count=0;
    public int a[] = {0,0,0};
    public static final String SHARED_PREFS = "com.example.factorizer.sharedPrefs";
    public static final String HIGHSCORE = "HIGHSCORE";
    public static final String STREAK = "STREAK";
    int his,str;
    int X,Y,Z,temp,choice,wr;
    Vibrator vib;
    long timeInMilli = 10000;
    boolean timeRunning;
    CountDownTimer countdownTimer;
    TextView timer;
    int curpage;
    int orientation;
    boolean incomplete;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orientation = this.getResources().getConfiguration().orientation;
        final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("INCOMPLETE", false);
        editor.putLong("TIMELEFT", 10000);
        editor.apply();

        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_game_play);
        else
            setContentView(R.layout.activity_game_land);

        num = (EditText) findViewById(R.id.NUM);
        btn2 = (Button) findViewById(R.id.btn2);
        int curstatus = sharedPreferences.getInt("VALIDITY",1);
        if(curstatus == 1)
        {
            num.setHint("NUMBER");
        }
        else
            num.setHint("INVALID");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long xo= Long.parseLong(num.getText().toString());
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(xo<1||xo>2147483646)
                {
                    editor.putInt("VALIDITY",0);
                    editor.apply();
                    goAgain();
                }
                else
                {
                    editor.putInt("VALIDITY",1);
                    editor.apply();

                x = (int) xo;
                editor.putInt("CURRQN",x);
                editor.apply();
                count = 0;
                curpage = 1;
                ask();}
            }
        });
    }

    public void ask()
        {
            Intent intent = new Intent(this, Options.class);
            orientation = getResources().getConfiguration().orientation;
            if(orientation == Configuration.ORIENTATION_PORTRAIT) {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else{
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

            }
        }

    public void goAgain()
    {
        Intent intent1 = new Intent(this, GamePlay.class);
        startActivity(intent1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else{
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);

        }
    }
}
