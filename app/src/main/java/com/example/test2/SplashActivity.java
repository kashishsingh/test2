package com.example.test2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window window = SplashActivity.this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color of status bar
        window.setStatusBarColor(SplashActivity.this.getResources().getColor(R.color.my_statusbar_color));
        window.setNavigationBarColor(SplashActivity.this.getResources().getColor(R.color.dark_blue));

        //SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        //boolean firstStart = prefs.getBoolean("firstStart",true);
        //if(firstStart)
        //{
            //SharedPreferences.Editor editor = prefs.edit();
            //editor.putBoolean("firstStart",false);
            //editor.apply();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Intent intent = new Intent(SplashActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    //finish();
                }
            },1000);

        //}
       // else
        //{
            //Intent intent = new Intent(SplashActivity.this,RegisterActivity.class);
           // startActivity(intent);
        //}



    }

}
