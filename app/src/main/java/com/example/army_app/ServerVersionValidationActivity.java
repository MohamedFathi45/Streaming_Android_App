package com.example.army_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.army_app.Loader_Tasks.UserRigisterLoader;
import com.example.army_app.Loader_Tasks.VersionLoader;
import com.example.army_app.utilities.DataBaseIntentService;
import com.example.army_app.utilities.DataBaseTasks;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

import java.util.ArrayList;

public class ServerVersionValidationActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object>{

    ArrayList<String> ret;
    private int operationCount = 0;
    private int numOfOperations = 1;
    private static final String  APP_VERSION = "1";
    public static final String SERVER_DOWNLOAD_APP_VERSION_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_app_version.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_version_validation);
        MainActivity.account = GoogleSignIn.getLastSignedInAccount(this);
        if(MainActivity.account != null){
            numOfOperations = 2;
            registerUserOnServer(MainActivity.account.getEmail().toString());
        }
        getCurrentApplicationVersion();
    }



    private void getCurrentApplicationVersion() {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_DOWNLOAD_APP_VERSION_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_APPLICATION_VERSION, queryBundle, this);

    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader loader = null;
        if(id == MainActivity.LOADER_FOR_LOAD_APPLICATION_VERSION){
            loader = new VersionLoader(this  , args.getString(MainActivity.SERVER_LINK));
        }
        else if(id == MainActivity.LOADER_FOR_RIGISTER_USER){
            loader =new UserRigisterLoader(this ,args.getString(MainActivity.SERVER_LINK) , args.getString(MainActivity.NAME) , args.getString(MainActivity.NAMETOW) , args.getString(MainActivity.URL));
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == MainActivity.LOADER_FOR_LOAD_APPLICATION_VERSION){
            if(data != null) {
                ret = (ArrayList<String>) data;
                incrementOperationCount();
            }
        }
        else if(id == MainActivity.LOADER_FOR_RIGISTER_USER){
                incrementOperationCount();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }

    private void registerUserOnServer(String email){

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(MainActivity.SERVER_LINK, SignInActivity.SERVER_WRITE_USER_API);
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


    void incrementOperationCount() {
        synchronized (this) {
            operationCount = operationCount + 1;
            if (operationCount == numOfOperations) {
                if (ret.get(0).equals("0")) {             // server is down for while    allert then exit
                    Toast.makeText(this, "server is down please try again later", Toast.LENGTH_SHORT).show();
                    finish();
                } else {                                   // online
                    if (ret.get(0).equals(APP_VERSION)) {           // open main Activity
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {                       // allert with the new version to download and exit
                        DownloadLatestVersionDialog dialog = new DownloadLatestVersionDialog(ret.get(1));
                        dialog.show(getSupportFragmentManager(), "dialog");
                    }
                }
            }
        }
    }
}
