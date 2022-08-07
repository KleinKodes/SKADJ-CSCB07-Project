package com.example.b07_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

public class LoadingScreenDeprecated {
    int zero = 0;
    public void makeLoadingScreen(View view, Context context, Class next){
        Intent i = new Intent(context, next);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ProgressBar p = (ProgressBar) view.findViewById(R.id.progressBar);
        Handler h = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(zero < 100){
                    zero++;
                    android.os.SystemClock.sleep(10);
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            p.setProgress(zero);
                        }
                    });
                }
                context.startActivity(i);
                ((Activity)context).finish();
            }
        }).start();
    }
/*    View view = findViewById(android.R.id.content);
    Context context = getBaseContext();
    LoadingScreen loadingScreen = new LoadingScreen();
    loadingScreen.makeLoadingScreen(view, context, WrapperActivity.class);*/
    // pass this code if you want to use this class (how to call without flags?)

}
