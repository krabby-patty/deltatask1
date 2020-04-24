package com.example.factorizer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Locale;

public class Options extends AppCompatActivity {
    public static final String SHARED_PREFS = "com.example.factorizer.sharedPrefs";
    boolean incomplete;
    public Button btn3, btn4, btn5;
    public int x, y, count = 0;
    public int a[] = {0, 0, 0};
    public TextView N;
    Random random = new Random();
    int X, Y, Z, temp, choice, wr;

    boolean timeRunning;

    TextView timer;
    int curpage = 0;

    long timeInMilli = 10000;
    private CountDownTimer countDownTimer;
    int orientation;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        incomplete = sharedPreferences.getBoolean("INCOMPLETE", false);
        if (!incomplete) {
            btn3 = (Button) findViewById(R.id.btn3);
            btn4 = (Button) findViewById(R.id.btn4);
            btn5 = (Button) findViewById(R.id.btn5);
            N = (TextView) findViewById(R.id.prnt);
            x = sharedPreferences.getInt("CURRQN", x);
            N.setText(String.valueOf(x));
            a[0] = x + 1;

            y = x - 1;
            if (x == 1)
                y = 1;
            while (x % a[0] != 0) {
                a[0] = 1 + random.nextInt(y);
            }
            a[1] = a[0];
            count = 0;
            while (a[1] == a[0] || x % a[1] == 0) {
                count++;
                a[1] = 1 + random.nextInt(y);
                if (count > 10) {
                    a[1] = x + 1 + random.nextInt(10);
                }
            }
            count = 0;
            a[2] = a[1];
            while (a[2] == a[0] || a[2] == a[1] || x % a[2] == 0) {
                count++;
                a[2] = 1 + random.nextInt(y);
                if (count > 10) {
                    a[2] = x + 1 + random.nextInt(10);
                }
            }

            choice = random.nextInt(3);
            if (choice == 0) {
                X = a[0];
                temp = random.nextInt(2);
                if (temp == 0) {
                    Y = a[1];
                    Z = a[2];
                } else {
                    Y = a[2];
                    Z = a[1];
                }
            } else if (choice == 1) {
                Y = a[0];
                temp = random.nextInt(2);
                if (temp == 0) {
                    X = a[1];
                    Z = a[2];
                } else {
                    X = a[2];
                    Z = a[1];
                }
            } else {
                Z = a[0];
                temp = random.nextInt(2);
                if (temp == 0) {
                    Y = a[1];
                    X = a[2];
                } else {
                    Y = a[2];
                    X = a[1];
                }
            }
            btn3.setText(String.valueOf(X));
            btn4.setText(String.valueOf(Y));
            btn5.setText(String.valueOf(Z));
            curpage = 1;
            sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("CURPAGE", curpage);
            editor.putInt("CURRQN", x);
            editor.putInt("OPTIONA", X);
            editor.putInt("OPTIONB", Y);
            editor.putInt("OPTIONC", Z);
            editor.putBoolean("INCOMPLETE", true);
            editor.putBoolean("TIME", true);
            editor.apply();

            timer = (TextView) findViewById(R.id.timer);
            timeRunning = true;
            timeInMilli = 10000;

            final SharedPreferences finalSharedPreferences = sharedPreferences;
            starter();
            timeRunning = true;
            btn3.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    timeRunning = false;
                    editor.putBoolean("TIME", false);

                    if (x % X == 0)
                        correct();
                    else {
                        wr = 1;
                        wrong();
                    }
                }
            });
            btn4.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    timeRunning = false;
                    editor.putBoolean("TIME", false);

                    if (x % Y == 0)
                        correct();
                    else {
                        wr = 2;
                        wrong();
                    }
                }
            });
            btn5.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    timeRunning = false;
                    editor.putBoolean("TIME", false);

                    if (x % Z == 0)
                        correct();
                    else {
                        wr = 3;
                        wrong();
                    }
                }
            });
        } else {

            btn3 = (Button) findViewById(R.id.btn3);
            btn4 = (Button) findViewById(R.id.btn4);
            btn5 = (Button) findViewById(R.id.btn5);
            N = (TextView) findViewById(R.id.prnt);


            curpage = 1;
            sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("CURPAGE", curpage);

            editor.apply();
            X = sharedPreferences.getInt("OPTIONA", 0);
            Y = sharedPreferences.getInt("OPTIONB", 0);
            Z = sharedPreferences.getInt("OPTIONC", 0);
            x = sharedPreferences.getInt("CURRQN", 1);
            N.setText(String.valueOf(x));

            btn3.setText(String.valueOf(X));
            btn4.setText(String.valueOf(Y));
            btn5.setText(String.valueOf(Z));
            timer = (TextView) findViewById(R.id.timer);
            timeRunning = true;
            timeInMilli = sharedPreferences.getLong("TIMELEFT", 10000);
            starter();
            timeRunning = true;
            btn3.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    timeRunning = false;
                    editor.putBoolean("TIME", false);

                    if (x % X == 0)
                        correct();
                    else {
                        wr = 1;
                        wrong();
                    }
                }
            });
            btn4.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    timeRunning = false;
                    editor.putBoolean("TIME", false);

                    if (x % Y == 0)
                        correct();
                    else {
                        wr = 2;
                        wrong();
                    }
                }
            });
            btn5.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    timeRunning = false;
                    editor.putBoolean("TIME", false);

                    if (x % Z == 0)
                        correct();
                    else {
                        wr = 3;
                        wrong();
                    }
                }
            });
        }
    }
    private void correct ()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("RESULT", 1);
        editor.putBoolean("RESCOMP",false);
        editor.apply();
        countDownTimer.cancel();
        Intent intent = new Intent(this, result.class);
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
    public void wrong ()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("RESULT", 2);
        editor.putInt("WRONG", wr);
        editor.apply();
        countDownTimer.cancel();
        Intent intent = new Intent(this, result.class);
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
    public void timeup ()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("RESULT", 3);
        editor.putBoolean("RESCOMP",false);
        editor.apply();
        Intent intent = new Intent(this, result.class);
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




    public void starter () {
        countDownTimer = new CountDownTimer(timeInMilli, 1000) {
           @Override
            public void onTick(long millisUntilFinished) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                timeInMilli = millisUntilFinished;
                editor.putLong("TIMELEFT", timeInMilli);
                timeRunning = true;
                editor.putBoolean("TIME", true);
                editor.apply();
                timer.setText(String.valueOf((timeInMilli / 1000) + 1));
                timeRunning = true;
            }

           @RequiresApi(api = Build.VERSION_CODES.M)
           @Override
            public void onFinish() {
                timeRunning = false;
                SharedPreferences shared = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                boolean timer = shared.getBoolean("TIME", false);
                if (timer) {
                    editor.putBoolean("TIME", false);
                    timeup();
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
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