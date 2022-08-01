package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.nio.file.StandardWatchEventKinds;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //if(currentUser != null){
        //    System.out.println("CREATE NEW INTENT OF MAIN ACTIVE");
        //}
    }


    public void login(View view){
        EditText email = (EditText) findViewById(R.id.Email_EditText);
        EditText password = (EditText) findViewById(R.id.Password_EditText);
        System.out.println(email.getText());
        System.out.println(password.getText());

        if (email.getText().toString().equals("")){
            TextView loginFail = (TextView) findViewById(R.id.Login_Failed_TextView);
            loginFail.setText("EMAIL is MISSING");
            return;
        }
        if (password.getText().toString().equals("")){
            TextView loginFail = (TextView) findViewById(R.id.Login_Failed_TextView);
            loginFail.setText("PASSWORD is MISSING");
            return;
        }

        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent mainAct = new Intent(LoginActivity.this, MainActivity.class);
                            Intent adminAct = new Intent(LoginActivity.this, AdminActivity.class);

                            FirebaseUser user = mAuth.getCurrentUser();
                            //user.getEmail();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users").child(user.getUid());

                            myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>(){

                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        User logUser = ((User)task.getResult().getValue(User.class));
                                        mainAct.putExtra("auth", logUser.auth);
                                        mainAct.putExtra("id", logUser.id);

                                        if (logUser.auth == 1){
                                            startActivity(adminAct);
                                        }
                                        else{
                                            startActivity(mainAct);
                                        }
                                        
                                        finish();
                                    }
                                }
                            );

                        } else {
                            System.out.println("LOGIN FAILED NOOO");
                            TextView loginFail = (TextView) findViewById(R.id.Login_Failed_TextView);
                            loginFail.setText(task.getException().getMessage());
                        }
                    }
                });
    }
}