package com.example.b07_project;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class Navigation extends AppCompatActivity implements View.OnClickListener{
    private int pStatus = 0;
    String userId;
    @Override
    public void onClick(View view)
    {
        UserServices userServices = new UserServices();
        ////    System.out.println(view.getId());
        switch (view.getId())
        {
            case R.id.homeButton:
                Intent intent1 = new Intent(view.getContext(), VenuePageDennt.class);
                if (userId != null)intent1.putExtra("userId", userId);
                ////    System.out.println("BRUH");
                view.getContext().startActivity(intent1);
                finish();

                break;

            case R.id.profileButton:
                Intent intent2 = new Intent(view.getContext(), profile.class);
                ////    System.out.println("lmao");
                view.getContext().startActivity(intent2);
                finish();
                break;
            case R.id.logOutButton:
                userServices.logOutCurrentUser();
                finish();
                break;


        }
    }
}
