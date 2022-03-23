package com.company.dnotz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashhScreen extends AppCompatActivity {

    TextView tv_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashh_screen);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        tv_description = findViewById(R.id.tv_description);
        Animation tv_description_anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        tv_description.startAnimation(tv_description_anim);

        new CountDownTimer(9000, 1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent i = new Intent(SplashhScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }.start();
    }

}