package com.example.b07_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class WrapperActivity extends AppCompatActivity {

    int auth;
    User currentUser;
    UserServices userServices;
    FirebaseAuth firebaseAuth;



    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        userServices = new UserServices();
        currentUser = new User();


        if (firebaseAuth.getCurrentUser() == null){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                if (firebaseAuth.getCurrentUser() == null){
                    Intent loginIntent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
                currentUser = userServices.findUserByUserId(firebaseAuth.getUid());



                if (currentUser.getAuth() == 1){
                    Intent adminIntent = new Intent(getBaseContext(), AdminActivity.class);
                    adminIntent.putExtra("auth", currentUser.getAuth());
                    adminIntent.putExtra("firstName", currentUser.getFirstName());
                    startActivity(adminIntent);

                }else if (currentUser.getAuth() == 0){
                    Intent customerIntent = new Intent(getBaseContext(), VenuePageDennt.class);
                    customerIntent.putExtra("auth", currentUser.getAuth());
                    customerIntent.putExtra("firstName", currentUser.getFirstName());
                    startActivity(customerIntent);
                } else{
                    Log.e("LoginIssue", "user does not have correct auth value");
                }

            }
        });



    }
}
