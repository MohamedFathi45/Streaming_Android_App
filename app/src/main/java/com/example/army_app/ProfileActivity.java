package com.example.army_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.army_app.utilities.CircleTransform;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;

public class ProfileActivity extends AppCompatActivity {

    TextView userName;
    ImageView userPhoto;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName = findViewById(R.id.id_user_name);
        userPhoto = findViewById(R.id.id_userPhoto);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("162836358215-jm067gc3plp7k8njb7cq2vk3858tkfoo.apps.googleusercontent.com").requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this , gso);
        setUserInfo();
    }

    private void setUserInfo() {
        if(MainActivity.account != null){
            userName.setText(MainActivity.account.getDisplayName());
            if(MainActivity.account.getPhotoUrl() != null){
                Picasso.get().load(MainActivity.account.getPhotoUrl()).transform(new CircleTransform()).into(userPhoto);
                //Picasso.get().load(MainActivity.account.getPhotoUrl()).fit().into(userPhoto);
            }
            else{
                    userPhoto.setImageResource(R.drawable.profile);
            }
        }
    }

    public void logoutClick(View view) {
        signOut();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(MainActivity.account != null){
            setUserInfo();
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        MainActivity.account = null;
                        MainActivity.userId = new BigInteger("0");
                        finish();
                    }
                });
    }

}
