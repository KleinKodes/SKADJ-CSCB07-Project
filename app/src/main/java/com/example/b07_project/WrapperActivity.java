package com.example.b07_project;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WrapperActivity extends AppCompatActivity {


    private int pStatus=0;

    UserServices userServices;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef;



    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        userServices = new UserServices();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference("Users");

        ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
        Handler h = new Handler();


        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Intent loginIntent = new Intent(getBaseContext(), LoginActivity.class);
                if (firebaseAuth.getCurrentUser() == null){

                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                            startActivity(loginIntent);
                            finish();
                        }
                    }).start();
                    return;
                }

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
                    }
                }).start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        userServices.routeUser(getBaseContext());
                    }
                }, 1150);

                return;
           }
        });



    }
}
