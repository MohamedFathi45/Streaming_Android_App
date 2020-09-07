package com.example.army_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.army_app.Loader_Tasks.UserRigisterLoader;
import com.example.army_app.utilities.NetworkUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class SignInActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object> {

    int RC_SIGN_IN = 0 ;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    ProgressBar progressBar;
    public static final String SERVER_WRITE_USER_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/write_usr.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        setTitle(getString(R.string.sing_in));
        progressBar = findViewById(R.id.id_progress_bar);
        signInButton = findViewById(R.id.id_sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("162836358215-jm067gc3plp7k8njb7cq2vk3858tkfoo.apps.googleusercontent.com").requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this , gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                signInWithGoogle();
            }
        });
    }

    public void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            MainActivity.account = completedTask.getResult(ApiException.class);
            registerUserOnServer(MainActivity.account.getEmail());
        }catch (ApiException e){
            Toast.makeText(SignInActivity.this , getString(R.string.sing_in_failed) , Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader loader = null;
        if(id == MainActivity.LOADER_FOR_RIGISTER_USER){
            loader =new UserRigisterLoader(this ,args.getString(MainActivity.SERVER_LINK) , args.getString(MainActivity.NAME) , args.getString(MainActivity.NAMETOW) , args.getString(MainActivity.URL));
            loader.forceLoad();
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == MainActivity.LOADER_FOR_RIGISTER_USER){
            progressBar.setVisibility(View.INVISIBLE);
            finish();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }


    private void registerUserOnServer(String email){

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(MainActivity.SERVER_LINK, SERVER_WRITE_USER_API);
        queryBundle.putString(MainActivity.NAME , email);          // is every google account has unique id
        queryBundle.putString(MainActivity.NAMETOW , MainActivity.account.getDisplayName());

        if(MainActivity.account.getPhotoUrl() != null){
            queryBundle.putString(MainActivity.URL , String.valueOf(MainActivity.account.getPhotoUrl()));
        }
        else{
            queryBundle.putString(MainActivity.URL , "non");
        }

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_RIGISTER_USER, queryBundle, this);
    }
}
