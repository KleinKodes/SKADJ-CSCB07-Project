package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private int pStatus=0;
    private UserServices userServices;
    Activity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        activity = this;
        userServices = new UserServices();

    }

    public void updateUserProfile(String name){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ////    System.out.println(user.getDisplayName());
                        }
                    }
                });
    }


    public void signUp(View view){
        EditText last_name = (EditText) findViewById(R.id.LastName_EditText);
        EditText name = (EditText) findViewById(R.id.Name_EditText);
        EditText email = (EditText) findViewById(R.id.Email_SignUp_EditText);
        EditText password = (EditText) findViewById(R.id.Password_SignUp_EditText);
        if(!(validateData(view, last_name.getText().toString(),
                name.getText().toString(), email.getText().toString(), password.getText().toString()))){return;}



            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUserProfile(name.getText().toString());
                                User newUser = new User();
                                newUser.id = user.getUid();
                                newUser.auth = 0;
                                newUser.firstName = name.getText().toString();
                                newUser.lastName = last_name.getText().toString();
                                newUser.email = email.getText().toString();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users");
                                myRef.child(newUser.id).setValue(newUser);
                                userServices.logInUser(newUser.email,password.getText().toString(), view, activity);
                                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference myRef = database.getReference("Users").child(user.getUid());

                                                myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>(){
                                                        @Override
                                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                            User logUser = ((User)task.getResult().getValue(User.class));


                                                        }
                                                    }
                                                );
                                            }
                                        }
                                    });
                            } else {
                                Snackbar mySnackbar = Snackbar.make(view, task.getException().getMessage(), BaseTransientBottomBar.LENGTH_SHORT);
                                mySnackbar.show();
                            }
                        }
                    });
    }


    private boolean validateData(View view, String lastName, String name, String email, String password){
        if(name.trim().isEmpty() || lastName.trim().isEmpty()){
            Snackbar mySnackbar = Snackbar.make(view, "Please enter a first name or last name", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        if(email.trim().isEmpty()){
            Snackbar mySnackbar = Snackbar.make(view, "Please enter an email", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        if(password.trim().isEmpty()){
            Snackbar mySnackbar = Snackbar.make(view, "Please enter a password", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        return true;
    }

    public void transitionToSignIn(View view){
        Intent intent = new Intent(this, LoginActivity.class);
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
                startActivity(intent);
                finish();
            }
        }).start();
    }

}