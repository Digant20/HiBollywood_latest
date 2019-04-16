package com.example.hibollywood;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    public FrameLayout frame;
    public static  int tracker;
//    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide(); // hide the title bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        frame=findViewById(R.id.frame);
//        img=(ImageView) findViewById(R.id.img);
        if (tracker==0){
            frame.setBackgroundResource(R.drawable.logo1);
        }
        else {
            frame.setBackgroundResource(tracker);
        }
        Thread display= new Thread(){
            public void run(){
                try{
                    sleep(1 * 1000);
                    Intent i= new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    finish();
                }
            }
        };
        display.start();
    }
}
