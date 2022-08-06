package com.example.b07_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingScreen extends AppCompatActivity {
    private int pStatus=0;

    public void onCreateLoading(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);
        ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
        Handler h = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(pStatus < 100){
                    pStatus++;
                    android.os.SystemClock.sleep(10);
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            p.setProgress(pStatus);
                        }
                    });
                }

                finish();
            }
        }).start();

    }
/*    View view = findViewById(android.R.id.content);
    Context context = getBaseContext();
    LoadingScreen loadingScreen = new LoadingScreen();
    loadingScreen.makeLoadingScreen(view, context, WrapperActivity.class);*/
    // pass this code if you want to use this class (how to call without flags?)

}
