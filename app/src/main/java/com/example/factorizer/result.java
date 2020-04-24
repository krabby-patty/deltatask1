package com.example.factorizer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class result extends AppCompatActivity {

    Button btn32,btn42,btn52;
    int x,X,Y,Z;
    int str,his;
    TextView streak;
    public static final String HIGHSCORE = "HIGHSCORE";
    public static final String STREAK = "STREAK";
    Vibrator vib;
    int orientation;


    public static final String SHARED_PREFS = "com.example.factorizer.sharedPrefs";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        int res = sharedPreferences.getInt("RESULT",3);

        if(res == 1)
        {
            int orientation  = getResources().getConfiguration().orientation;
            if(orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.correct);
            else
                setContentView(R.layout.correct_land);

            btn32 = (Button) findViewById(R.id.btn32);
            btn42 = (Button) findViewById(R.id.btn42);
            btn52 = (Button) findViewById(R.id.btn52);
            TextView prnt2 = (TextView) findViewById(R.id.prnt2);
            x = sharedPreferences.getInt("CURRQN",1);
            X = sharedPreferences.getInt("OPTIONA",1);
            Y = sharedPreferences.getInt("OPTIONB",1);
            Z = sharedPreferences.getInt("OPTIONC",1);
            prnt2.setText(String.valueOf(x));
            btn32.setText(String.valueOf(X));
            btn42.setText(String.valueOf(Y));
            btn52.setText(String.valueOf(Z));
            if (x % X == 0) {
                btn32.setBackgroundColor(getColor(R.color.colorPrimaryDark));
                btn32.setTextColor(getColor(R.color.correctColor));
            }
            if (x % Y == 0) {
                btn42.setBackgroundColor(getColor(R.color.colorPrimaryDark));
                btn42.setTextColor(getColor(R.color.correctColor));
            }
            if (x % Z == 0) {
                btn52.setBackgroundColor(getColor(R.color.colorPrimaryDark));
                btn52.setTextColor(getColor(R.color.correctColor));
            }


            str = sharedPreferences.getInt(STREAK, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(!sharedPreferences.getBoolean("RESCOMP", true))
            {
                str = str + 1;
                editor.putBoolean("RESCOMP",true);
            }
            streak = (TextView) findViewById(R.id.streakprint);
            streak.setText("CURRENT WIN STREAK :" + String.valueOf(str));
            Button btn6, btn7;
            btn6 = (Button) findViewById(R.id.btn6);
            btn7 = (Button) findViewById(R.id.btn7);
            editor.putInt(STREAK, str);
            editor.putBoolean("INCOMPLETE", false);
            editor.apply();
            TextView hi = (TextView) findViewById(R.id.hiskore);

            his = sharedPreferences.getInt(HIGHSCORE,0);
            if(str>his)
            {
                hi.setVisibility(View.VISIBLE);
                his = str;
                editor.putInt(HIGHSCORE,his);
                editor.apply();
            }
            else
            {
                hi.setVisibility(View.INVISIBLE);
            }

            editor.putInt(STREAK, str);
            editor.putBoolean("INCOMPLETE", false);
            editor.apply();
            btn6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playAgain();
                }
            });
            btn7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goHome();
                }
            });
        }
        else if(res == 2)
        {

            int orientation  = getResources().getConfiguration().orientation;
            if(orientation == Configuration.ORIENTATION_PORTRAIT)
                setContentView(R.layout.wrong);
            else
                setContentView(R.layout.wrong2);
            sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            str = sharedPreferences.getInt(STREAK, 0);
            his = sharedPreferences.getInt(HIGHSCORE, 0);
            if (str > his) {
                his = str;
            }
            x = sharedPreferences.getInt("CURRQN",1);
            X = sharedPreferences.getInt("OPTIONA",1);
            Y = sharedPreferences.getInt("OPTIONB",1);
            Z = sharedPreferences.getInt("OPTIONC",1);
            str = 0;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(STREAK, str);
            editor.putInt(HIGHSCORE, his);
            editor.putBoolean("INCOMPLETE", false);
            editor.apply();
            Button btn8, btn33, btn43, btn53;
            TextView qn;
            qn = (TextView) findViewById(R.id.prnt3);
            qn.setText(String.valueOf(x));
            btn33 = (Button) findViewById(R.id.btn33);
            btn43 = (Button) findViewById(R.id.btn43);
            btn53 = (Button) findViewById(R.id.btn53);
            btn8 = (Button) findViewById(R.id.btn83);
            int wr = sharedPreferences.getInt("WRONG",1);
            if (wr == 1) {
                btn33.setBackgroundColor(getColor(R.color.colorPrimaryDark));
                btn33.setTextColor(getColor(R.color.wrongColor));
            } else if (wr == 2) {
                btn43.setBackgroundColor(getColor(R.color.colorPrimaryDark));
                btn43.setTextColor(getColor(R.color.wrongColor));
            } else {
                btn53.setBackgroundColor(getColor(R.color.colorPrimaryDark));
                btn53.setTextColor(getColor(R.color.wrongColor));
            }
            if (x % X == 0) {
                btn33.setBackgroundColor(getColor(R.color.correctColor));
                btn33.setTextColor(getColor(R.color.white));
            }
            if (x % Y == 0) {
                btn43.setBackgroundColor(getColor(R.color.correctColor));
                btn43.setTextColor(getColor(R.color.white));
            }
            if (x % Z == 0) {
                btn53.setBackgroundColor(getColor(R.color.correctColor));
                btn53.setTextColor(getColor(R.color.white));
            }
            btn33.setText(String.valueOf(X));
            btn43.setText(String.valueOf(Y));
            btn53.setText(String.valueOf(Z));
            vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            assert vib != null;
            vib.vibrate(500);
            btn8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goHome();
                }
            });
        }
        else
        {
            int orientation  = getResources().getConfiguration().orientation;
            if(orientation == Configuration.ORIENTATION_PORTRAIT)
                setContentView(R.layout.time_up);
            else
                setContentView(R.layout.time_up2);
            sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
            str = sharedPreferences.getInt(STREAK,0);
            his = sharedPreferences.getInt(HIGHSCORE,0);
            if (str>his)
            {
                his = str;
            }
            str = 0;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(STREAK, str);
            editor.putInt(HIGHSCORE, his);
            editor.putBoolean("INCOMPLETE", false);
            editor.apply();
            Button btn84,btn34,btn44,btn54;
            TextView prnt4;
            prnt4 = (TextView) findViewById(R.id.prnt4);
            prnt4.setText(String.valueOf(x));
            btn34 = (Button) findViewById(R.id.btn34);
            btn44 = (Button) findViewById(R.id.btn44);
            btn54 = (Button) findViewById(R.id.btn54);
            btn84 = (Button) findViewById(R.id.btn84);
            x = sharedPreferences.getInt("CURRQN",1);
            X = sharedPreferences.getInt("OPTIONA",1);
            Y = sharedPreferences.getInt("OPTIONB",1);
            Z = sharedPreferences.getInt("OPTIONC",1);
            if(x%X==0)
            {
                btn34.setBackgroundColor(getColor(R.color.correctColor));
                btn34.setTextColor(getColor(R.color.white));
            }
            if(x%Y==0)
            {
                btn44.setBackgroundColor(getColor(R.color.correctColor));
                btn44.setTextColor(getColor(R.color.white));
            }
            if(x%Z==0)
            {
                btn54.setBackgroundColor(getColor(R.color.correctColor));
                btn54.setTextColor(getColor(R.color.white));
            }
            prnt4.setText(String.valueOf(x));
            btn34.setText(String.valueOf(X));
            btn44.setText(String.valueOf(Y));
            btn54.setText(String.valueOf(Z));
            if(!sharedPreferences.getBoolean("RESCOMP", false))
            {
                editor.putBoolean("RESCOMP",true);
                editor.apply();
                vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                assert vib != null;
                vib.vibrate(500);
            }
            btn84.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goHome();
                }
            });
        }
    }



    public void playAgain(){
        Intent intent = new Intent(this, GamePlay.class);
        orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
        else{
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

        }
    }
    public void goHome(){
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
