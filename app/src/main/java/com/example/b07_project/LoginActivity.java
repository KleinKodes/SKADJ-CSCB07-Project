package com.example.b07_project;

import static java.lang.System.exit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;


public class LoginActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public int auth;
    private UserServices userServices;
    private int pStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        userServices = new UserServices();
    }


    public void login(View view){

        EditText email = (EditText) findViewById(R.id.Email_EditText);
        EditText password = (EditText) findViewById(R.id.Password_EditText);
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        TextView loginFail = findViewById(R.id.Login_Failed_TextView);

        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if(!(validateData(view, email, password))){return;}
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
            }
        }).start();
        userServices.logInUser(emailString, passwordString, view, this);


    }

    private boolean validateData(View view, EditText email, EditText password){
        if(email.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()){
            Snackbar mySnackbar = Snackbar.make(view, "Provide credentials to login", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        return true;
    }

public void trasitionToSignUp(View view)
{
    setContentView(R.layout.loading_activity);
    Intent intent = new Intent(this, SignUpActivity.class);
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