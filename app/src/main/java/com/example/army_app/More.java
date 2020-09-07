package com.example.army_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.army_app.Loader_Tasks.SocialMediaLinksLoader;
import com.example.army_app.Loader_Tasks.VersionLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Map;


public class More extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object> {
    public static final String SERVER_DOWNLOAD_SCOIA_MEDIA_LINKS_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_key_value_social_media_links.php";

    TextView appLinkTv;
    TextView signInOrName;
    LinearLayout progress_bar_layout;
    LinearLayout results_layout;
    LinearLayout noResultsLayout;
    private static Map<String,String>socialMediaLinks;
    private static final int numOfOperations = 2;
    private static int operationCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        /*
        signInLayout = findViewById(R.id.id_sign_in_layout);
        profile_pic = findViewById(R.id.id_profile_picture);
        profile_name = findViewById(R.id.id_profile_name);

         */
        noResultsLayout = findViewById(R.id.id_no_results_layout);
        progress_bar_layout = findViewById(R.id.id_progress_bar_linear_layout);
        results_layout = findViewById(R.id.id_main_linear_layout);
        signInOrName = findViewById(R.id.id_signinOrName);
        operationCount = 0;
        if(MainActivity.account != null){
            setUserInfo();
        }
        else{
            setDefalutInfo();
        }
        appLinkTv = findViewById(R.id.id_app_link_tv);
        //initProfileInfo();
        BottomNavigationView mBottonNavigationView = (BottomNavigationView) findViewById(R.id.id_bottom_navigation);
        //if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
        //mBottonNavigationView.setItemIconTintList(null);
        mBottonNavigationView.setSelectedItemId(R.id.id_nav_more);
        mBottonNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_nav_search:
                        Intent intent = new Intent(More.this, Search.class);
                        startActivity(intent);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;
                    case R.id.id_nav_our_works:
                        Intent intent1 = new Intent(More.this, OurWorks.class);
                        startActivity(intent1);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;
                    case R.id.id_nav_more:
                        return true;
                    case R.id.id_nav_home:
                        Intent intent2 = new Intent(More.this, MainActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        progress_bar_layout.setVisibility(View.VISIBLE);
        results_layout.setVisibility(View.GONE);
        noResultsLayout.setVisibility(View.GONE);

        getCurrentApplicationVersion();
        getSocialMediaLinksFromServer();
    }

    private void setDefalutInfo() {
        signInOrName.setText(getString(R.string.sign_in));
    }

    private void setUserInfo() {
        signInOrName.setText(MainActivity.account.getGivenName());
    }

    private void getCurrentApplicationVersion() {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , ServerVersionValidationActivity.SERVER_DOWNLOAD_APP_VERSION_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_APPLICATION_VERSION, queryBundle, this);

    }
    private void getSocialMediaLinksFromServer(){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_DOWNLOAD_SCOIA_MEDIA_LINKS_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_SOCIAL_MEDIA_LINKS, queryBundle, this);
    }
    public void myListOnClick(View view) {
        if(MainActivity.account != null && !MainActivity.userId.equals("0")){
            Intent intent = new Intent(this , MyListActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this , getString(R.string.must_sign_in) , Toast.LENGTH_SHORT).show();
        }
    }

    public void historyOnClick(View view) {
        if(MainActivity.account != null &&!MainActivity.userId.equals("0")){
            Intent intent = new Intent(this , HistoryActicity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this , getString(R.string.must_sign_in) , Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader loader = null;
        if(id == MainActivity.LOADER_FOR_LOAD_APPLICATION_VERSION){

            loader = new VersionLoader(this  , args.getString(MainActivity.SERVER_LINK));
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_SOCIAL_MEDIA_LINKS){
            loader = new SocialMediaLinksLoader(this , args.getString(MainActivity.SERVER_LINK));
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if( id == MainActivity.LOADER_FOR_LOAD_APPLICATION_VERSION){
            if(data != null) {
                ArrayList<String> ret = (ArrayList<String>) data;
                appLinkTv.setText(ret.get(1));
                incrementOperationCount();
            }
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_SOCIAL_MEDIA_LINKS){
            if(data != null){
                socialMediaLinks = (Map<String,String>)data;
                incrementOperationCount();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }

    public void copyLinkClick(View view) {
        ClipboardManager clipboard = (ClipboardManager) this.getSystemService(this.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("link", appLinkTv.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this , getString(R.string.copied) , Toast.LENGTH_SHORT).show();
    }

    public void SignInClick(View view) {
        if(MainActivity.account == null) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this , ProfileActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(MainActivity.account != null){
            setUserInfo();
        }
        else{
            setDefalutInfo();
        }
    }

    void incrementOperationCount() {
        synchronized (this) {
            operationCount = operationCount + 1;
        }
        if(operationCount == numOfOperations){
            noResultsLayout.setVisibility(View.GONE);
            progress_bar_layout.setVisibility(View.GONE);
            results_layout.setVisibility(View.VISIBLE);
        }
    }

    public void faceBookClick(View view) {
        if(socialMediaLinks.get("FaceBook") != null) {
            try {

                String link = socialMediaLinks.get("FaceBook");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }catch (Exception e){
                Toast.makeText(this , "Error" , Toast.LENGTH_SHORT).show();
            }
        }
        else{
            errorToast();
        }
    }

    public void twitterClick(View view) {
        if(socialMediaLinks.get("Twitter") != null) {
            try {

                String link = socialMediaLinks.get("Twitter");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }catch (Exception e){
                Toast.makeText(this , "Error" , Toast.LENGTH_SHORT).show();
            }
        }
        else{
            errorToast();
        }
    }

    public void youtubeClick(View view) {

        if(socialMediaLinks.get("Youtube") != null) {
            try {
                String link = socialMediaLinks.get("Youtube");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }catch (Exception e){
                Toast.makeText(this , "Error" , Toast.LENGTH_SHORT).show();
            }
        }
        else{
            errorToast();
        }
    }

    public void InstagramClick(View view) {
        if(socialMediaLinks.get("Instagram") != null) {
            try {
                String link = socialMediaLinks.get("Instagram");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }catch (Exception e){
                Toast.makeText(this , "Error" , Toast.LENGTH_SHORT).show();
            }
        }
        else{
            errorToast();
        }
    }
    public void errorToast(){
        Toast.makeText(this , "network error, or server error" , Toast.LENGTH_SHORT).show();
    }
}
