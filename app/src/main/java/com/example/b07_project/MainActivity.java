package com.example.b07_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);

        Intent intent = new Intent(this, WrapperActivity.class);

        startActivity(intent);
        finish();

/*        View view = findViewById(android.R.id.content);
        Context context = getBaseContext();
        LoadingScreen loadingScreen = new LoadingScreen();
        loadingScreen.makeLoadingScreen(view, context, WrapperActivity.class);*/

/*        ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
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
                startActivity(intent);
                finish();
            }
        }).start();*/
    }
}
