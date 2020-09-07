package com.example.army_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.army_app.Loader_Tasks.ActorsLoader;
import com.example.army_app.Loader_Tasks.CheckTheListLoader;
import com.example.army_app.Loader_Tasks.InverseListStatusLoader;
import com.example.army_app.Loader_Tasks.MainScreenBangtanLoader;
import com.example.army_app.Loader_Tasks.MainScreenDvdLoader;
import com.example.army_app.Loader_Tasks.MainScreenMoviesLoader;
import com.example.army_app.Loader_Tasks.MainScreenOthersLoader;
import com.example.army_app.Loader_Tasks.MainScreenPosterLoader;
import com.example.army_app.Loader_Tasks.MainScreenTvShowsLoader;
import com.example.army_app.Loader_Tasks.MainScreenVliveLoader;
import com.example.army_app.Loader_Tasks.ShowTagsLoader;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object> , GeneralShowAdapter.ListItemClickListener , ActorAdapter.ListItemClickListener {

    public static BigInteger userId =new BigInteger("0");
    public static GoogleSignInAccount account = null;
    ImageView mMainPoster;
    LinearLayout mPosterImageLayout;
    GeneralShow latestShow = null;
    ImageView listImage ;
    RecyclerView mFirstRecyclerView;
    RecyclerView mSecondRecyclerView;
    RecyclerView mThirdRecyclerView;
    RecyclerView mFourthRecyclerView;
    RecyclerView mSeventhRecyclerView;
    LinearLayout progressBarLinearLayout;


    public static final String MOVIE_OBJECT_ID = "movie_object";
    public static final String TV_SHOW_OBJECT_ID = "tv_show_object";
    public static final String CAT = "category";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String NAMETOW = "name2";
    public static final String VALUE = "value";


    public static final int LOADER_FOR_LOAD_MAIN_SCREEN_TV_SHOWS = 26;
    public static final int LOADER_FOR_LOAD_MAIN_SCREEN_MOVIES = 27;
    public static final int LOADER_FOR_LOAD_MAIN_SCREEN_DVDS = 28;
    public static final int LOADER_FOR_LOAD_MAIN_SCREEN_BANGTANS = 29;
    public static final int LOADER_FOR_LOAD_MAIN_SCREEN_VLIVE = 30;
    public static final int LOADER_FOR_LOAD_MAIN_SCREEN_OTHERS = 31;
    public static final int LOADER_FOR_LOAD_TV_SHOWS_BY_TYPE = 34;
    public static final int LOADER_FOR_LOAD_MAIN_SCREEN_POSTER = 35;
    public static final int LOADER_FOR_LOAD_ALL_SHOW_CAT = 36;
    public static final int LOADER_FOR_LOAD_MOVIE_LINKS =37;
    public static final int LOADER_FOR_LOAD_SHOW_TAGS = 38;
    public static final int LOADER_FOR_LOAD_MAIN_ACTORS = 39;
    public static final int LOADER_FOR_LOAD_EPISODES = 41;
    public static final int LOADER_FOR_LOAD_SESSIONS = 42;
    public static final int LOADER_FOR_LOAD_EPISODE_LINKS = 43;
    public static final int LOADER_FOR_LOAD_SERIES_LINK = 44;
    public static final int LOADER_FOR_LOAD_NON_SERIES_LINK = 45;
    public static final int LOADER_FOR_LOAD_ALL_ACTORS = 46;
    public static final int LOADER_FOR_LOAD_ACTOR_WORKS = 47;
    public static final int LOADER_FOR_SEARCH = 48;
    public static final int LOADER_FOR_LOAD_OUR_WORKS = 49;
    public static final int LOADER_FOR_LOAD_MY_LIST = 50;
    public static final int LOADER_FOR_LOAD_HISTORY = 51;
    public static final int LOADER_FOR_CHECK_LIST = 52;
    public static final int LOADER_FOR_INVERSE_LIST_STATUS = 53;
    public static final int LOADER_FOR_RIGISTER_USER = 55;
    public static final int LOADER_FOR_LOAD_SOCIAL_MEDIA_LINKS = 56;
    public static final int LOADER_FOR_LOAD_APPLICATION_VERSION = 57;
    public static final int LOADER_FOR_REGISTER_IN_USER_HISTORY =58;
    public static final int LOADER_FOR_LOAD_REVIEWS =59;
    public static final int LOADER_FOR_INSERT_REVIEW =61;
    public static final int LOADER_FOR_LOAD_EPISODE_COMMENTS = 62;
    public static final int LOADER_FOR_INSERT_EPISODE_COMMENTS = 63;
    public static final int LOADER_FOR_LOAD_USER_RATING = 64;


    public static final String URL = "url";
    public static final String TYPE = "type";
    public static final String SERVER_LINK = "link";
    private static final String RECYLER_VIEW_FIRST="first";
    private static final String RECYLER_VIEW_SECOND="second";
    private static final String RECYLER_VIEW_THIRD="third";
    private static final String RECYLER_VIEW_FORURTH="fourth";
    private static final String RECYLER_VIEW_FIFTH="fifth";
    private static final String RECYLER_VIEW_SIXTH="sixth";
    public static final String PROJECT_ROOT = "army_app";
    public static final String SERVER_ROOT_LINK_URL = "http://d4569dda3b77.ngrok.io"+"/"+PROJECT_ROOT;
    private static final String SERVER_IMAGES_URL = SERVER_ROOT_LINK_URL+"/army_app_rest/army_app_file_system/images";
    public static final String SERVER_LOAD_MAIN_SCREEN_SHOWS_API = SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_main_screen.php";
    public static final String SERVER_LOAD_MAIN_SCREEN_POSTER_SHOW_API=SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_latest_show.php";
    public static final String SERVER_LOAD_ALL_SHOW_CAT_API = SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_all_show_cat.php";
    public static final String SERVER_LOAD_ALL_ACTORS_API = SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_all_members.php";
    public static final String SERVER_CHECK_LIST_API = SERVER_ROOT_LINK_URL+"/army_app_rest/api/check_general_show_in_list.php";
    public static final String SERVER_INVERSE_LIST_STATUS_API = SERVER_ROOT_LINK_URL+"/army_app_rest/api/inverse_the_list_status.php";
    public static final String SERVER_GET_REVIEWS_API = SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_reviews.php";
    public static final String WRITE_SHOW_RATTING_API = SERVER_ROOT_LINK_URL+"/army_app_rest/api/write_usr_rating.php";
    public static final String SERVER_GET_USER_NUMBER_OF_REVIEWS_API = SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_user_number_of_reviews.php";
    public static final String SERVER_INSERT_REVIEW_API = SERVER_ROOT_LINK_URL+"/army_app_rest/api/write_review.php";
    public static final String SERVER_REGISTER_SHOW_IN_HISTORY_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/write_general_show_into_history.php";
    private int operationCount = 0;
    private int numOfOperations = 9;
    BottomNavigationView mBottonNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
        Log.d("AppDebugSpace" , "Called");
        operationCount = 0;
        if(account!= null){
            numOfOperations = numOfOperations +1;
        }
        mSecondRecyclerView = (RecyclerView)findViewById(R.id.id_recyclerView_second);
        mFirstRecyclerView = (RecyclerView)findViewById(R.id.id_recyclerView_first);
        mThirdRecyclerView = (RecyclerView)findViewById(R.id.id_recyclerView_third);
        mFourthRecyclerView = (RecyclerView)findViewById(R.id.id_recyclerView_fourth);
        mSeventhRecyclerView = findViewById(R.id.id_recyclerView_seven);
        mMainPoster = (ImageView)findViewById(R.id.id_posterImage);
        progressBarLinearLayout = findViewById(R.id.id_progress_bar_linear_layout);
        listImage = findViewById(R.id.id_is_list_added);
        mBottonNavigationView = (BottomNavigationView) findViewById(R.id.id_bottom_navigation);
        //if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
        //mBottonNavigationView.setItemIconTintList(null);
        mBottonNavigationView.setSelectedItemId(R.id.id_nav_home);

        mBottonNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_nav_search:
                        Intent intent = new Intent(MainActivity.this, Search.class);
                        startActivity(intent);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;
                    case R.id.id_nav_our_works:
                        Intent intent1 = new Intent(MainActivity.this, OurWorks.class);
                        startActivity(intent1);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;
                    case R.id.id_nav_more:
                        Intent intent2 = new Intent(MainActivity.this, More.class);
                        startActivity(intent2);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;
                    case R.id.id_nav_home:
                        return true;

                }
                return false;
            }
        });
        getMainScreenShows();
        getAllActors();
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(final int id, @Nullable  Bundle args) {
        Loader res = null;
        switch (id){
            case LOADER_FOR_LOAD_MAIN_SCREEN_POSTER:
                res = new MainScreenPosterLoader(MainActivity.this , args.getString(SERVER_LINK) );
                //res.startLoading();
                break;
            case LOADER_FOR_LOAD_MAIN_SCREEN_MOVIES:
                res = new MainScreenMoviesLoader(this , args.getString(SERVER_LINK),args.getString(CAT) );
                //res.startLoading();
                break;
            case LOADER_FOR_LOAD_MAIN_SCREEN_TV_SHOWS:
                res = new MainScreenTvShowsLoader(this , args.getString(SERVER_LINK),args.getString(CAT));
                //res.startLoading();
                break;
            case LOADER_FOR_LOAD_MAIN_SCREEN_DVDS:
                res = new MainScreenDvdLoader(this , args.getString(SERVER_LINK) ,args.getString(CAT));
                //res.startLoading();
                break;
            case LOADER_FOR_LOAD_MAIN_SCREEN_BANGTANS:
                res = new MainScreenBangtanLoader(this , args.getString(SERVER_LINK),args.getString(CAT) );
                //res.startLoading();
                break;
            case LOADER_FOR_LOAD_MAIN_SCREEN_VLIVE:
                res = new MainScreenVliveLoader(this , args.getString(SERVER_LINK),args.getString(CAT) );
                //res.startLoading();
                break;
            case LOADER_FOR_LOAD_MAIN_SCREEN_OTHERS:
                res = new MainScreenOthersLoader(this , args.getString(SERVER_LINK) ,args.getString(CAT));
                //res.startLoading();
                break;
            case LOADER_FOR_LOAD_SHOW_TAGS:
                res = new ShowTagsLoader(this , args.getString(SERVER_LINK),args.getString(ID) );
                //res.startLoading();
                break;
            case LOADER_FOR_INVERSE_LIST_STATUS:
                res = new InverseListStatusLoader(this , args.getString(SERVER_LINK),args.getString(ID),args.getString(SeriesDetailsActivity.IDTWO) );
                //res.startLoading();
                break;
            case LOADER_FOR_CHECK_LIST:
                res = new CheckTheListLoader(this , args.getString(SERVER_LINK),args.getString(ID),args.getString(SeriesDetailsActivity.IDTWO) );
               // res.startLoading();
                break;
            case LOADER_FOR_LOAD_ALL_ACTORS:
                res = new ActorsLoader(this , args.getString(SERVER_LINK) );
                //res.startLoading();
                break;
        }
        return res;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == LOADER_FOR_LOAD_MAIN_SCREEN_MOVIES) {
            if(data != null) {
                GeneralShowAdapter movieAdapter = new GeneralShowAdapter(this, (ArrayList<GeneralShow>) data, RECYLER_VIEW_FIRST);
                mFirstRecyclerView.setAdapter(movieAdapter);
                mFirstRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            }
            incrementOperationCount();
        }
        else if(id == LOADER_FOR_LOAD_MAIN_SCREEN_POSTER){
            if(data != null) {
                latestShow = (GeneralShow) data;
                Picasso.get().load(SERVER_ROOT_LINK_URL + "/" + latestShow.getPosterImagePath()).fit().into(mMainPoster);
                getShowTagsFromServer(String.valueOf(((GeneralShow) data).getId()));
                if(!String.valueOf(userId).equals("0"))
                    getIfMainShowInList(String.valueOf(((GeneralShow) data).getId()) , String.valueOf(userId));
            }
            incrementOperationCount();

        }
        else if(id == LOADER_FOR_LOAD_MAIN_SCREEN_TV_SHOWS){
            if(data != null){
                GeneralShowAdapter showsAdapter = new GeneralShowAdapter(this, (ArrayList<GeneralShow>) data, RECYLER_VIEW_SECOND);
                mSecondRecyclerView.setAdapter(showsAdapter);
                mSecondRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            }
            incrementOperationCount();
        }
        else if(id == LOADER_FOR_LOAD_MAIN_SCREEN_DVDS){
            if(data != null) {
                GeneralShowAdapter dvdAdapter = new GeneralShowAdapter(this, (ArrayList<GeneralShow>) data, RECYLER_VIEW_THIRD);
                mThirdRecyclerView.setAdapter(dvdAdapter);
                mThirdRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            }
            incrementOperationCount();
        }
        else if( id == LOADER_FOR_LOAD_MAIN_SCREEN_BANGTANS){
            if(data != null) {
                GeneralShowAdapter bangtonTvAdapter = new GeneralShowAdapter(this, (ArrayList<GeneralShow>) data, RECYLER_VIEW_FORURTH);
                mFourthRecyclerView.setAdapter(bangtonTvAdapter);
                mFourthRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            }
            incrementOperationCount();
        }
        else if( id == LOADER_FOR_LOAD_MAIN_SCREEN_VLIVE){
            if(data != null) {
                GeneralShowAdapter bangtonTvAdapter = new GeneralShowAdapter(this, (ArrayList<GeneralShow>) data, RECYLER_VIEW_FIFTH);
                mFourthRecyclerView.setAdapter(bangtonTvAdapter);
                mFourthRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            }
            incrementOperationCount();
        }
        else if( id == LOADER_FOR_LOAD_MAIN_SCREEN_OTHERS){
            if(data != null) {
                GeneralShowAdapter bangtonTvAdapter = new GeneralShowAdapter(this, (ArrayList<GeneralShow>) data, RECYLER_VIEW_SIXTH);
                mFourthRecyclerView.setAdapter(bangtonTvAdapter);
                mFourthRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            }
            incrementOperationCount();
        }
        else if(id == LOADER_FOR_LOAD_ALL_ACTORS){
            if (data != null) {
                ArrayList<Actor> actors = (ArrayList<Actor>)data;
                ActorAdapter actorAdapter = new ActorAdapter(this , actors);
                mSeventhRecyclerView.setAdapter(actorAdapter);
                mSeventhRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            }
            incrementOperationCount();
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_SHOW_TAGS){
            if (data != null) {
                ArrayList<String> tags = (ArrayList<String>)data;
                String collectedTags ="";
                for( int i = 0 ; i < tags.size() ; i ++ ) {
                    if (i == tags.size() - 1)
                        collectedTags += tags.get(i);
                    else
                        collectedTags += tags.get(i) + "-";
                }
                TextView showDesc = findViewById(R.id.id_showDescriptionBar);
                showDesc.setText(collectedTags);
            }
            incrementOperationCount();
        }
        else if(id == MainActivity.LOADER_FOR_CHECK_LIST){
            if (!String.valueOf(data).equals("0")) {
                listImage.setImageResource(R.drawable.ic_check_gray_24dp);
            }
            else{
                listImage.setImageResource(R.drawable.ic_playlist_add_gray_24dp);
            }
            incrementOperationCount();
        }
        else if(id == MainActivity.LOADER_FOR_INVERSE_LIST_STATUS){
            if(!String.valueOf(data).equals("0")){          // 0 means removed
                listImage.setImageResource(R.drawable.ic_check_gray_24dp);
                Toast.makeText(this, R.string.added_to_list, Toast.LENGTH_SHORT).show();
            }
            else {
                listImage.setImageResource(R.drawable.ic_playlist_add_gray_24dp);
                Toast.makeText(this,R.string.removed_from_list , Toast.LENGTH_SHORT).show();
            }
            stopLoader(id);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }

    private void getMainScreenMovies(String cat){

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(SERVER_LINK, SERVER_LOAD_MAIN_SCREEN_SHOWS_API);
        queryBundle.putString(CAT , cat);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_FOR_LOAD_MAIN_SCREEN_MOVIES, queryBundle, this);
    }

    private void InverseTheListStatusInServer(String userId , String moveId ){    // if add then add it , if rem then remove it

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(SERVER_LINK, SERVER_INVERSE_LIST_STATUS_API);
        queryBundle.putString(ID , userId);
        queryBundle.putString(SeriesDetailsActivity.IDTWO , moveId);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        //  Get our Loader by calling getLoader and passing the ID we specified
        Loader<Object> loadInverseListStatus = loaderManager.getLoader(MainActivity.LOADER_FOR_INVERSE_LIST_STATUS);
        //  If the Loader was null, initialize it. Else, restart it.
        if (loadInverseListStatus == null) {
            loaderManager.initLoader(MainActivity.LOADER_FOR_INVERSE_LIST_STATUS, queryBundle, this);
        } else {
            loaderManager.restartLoader(MainActivity.LOADER_FOR_INVERSE_LIST_STATUS, queryBundle, this);
        }
    }

    private void getMainScreenVlive(String cat){

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(SERVER_LINK, SERVER_LOAD_MAIN_SCREEN_SHOWS_API);
        queryBundle.putString(CAT , cat);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_FOR_LOAD_MAIN_SCREEN_VLIVE, queryBundle, this);
    }

    private void getMainScreenOthers(String cat){

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(SERVER_LINK, SERVER_LOAD_MAIN_SCREEN_SHOWS_API);
        queryBundle.putString(CAT , cat);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_FOR_LOAD_MAIN_SCREEN_OTHERS, queryBundle, this);
    }


    private void getLatestMovie(){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(SERVER_LINK , SERVER_LOAD_MAIN_SCREEN_POSTER_SHOW_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_FOR_LOAD_MAIN_SCREEN_POSTER, queryBundle, this);
    }

    private void getAllActors(){

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(SERVER_LINK, SERVER_LOAD_ALL_ACTORS_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_FOR_LOAD_ALL_ACTORS, queryBundle, this);
    }

    private void getMainScreenTvShows(String cat){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(CAT , cat);
        queryBundle.putString(SERVER_LINK , SERVER_LOAD_MAIN_SCREEN_SHOWS_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_FOR_LOAD_MAIN_SCREEN_TV_SHOWS, queryBundle, this);
    }

    private void getMainScreenDvds(String cat){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(CAT , cat);
        queryBundle.putString(SERVER_LINK , SERVER_LOAD_MAIN_SCREEN_SHOWS_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_FOR_LOAD_MAIN_SCREEN_DVDS, queryBundle, this);
    }
    private void getIfMainShowInList(String movieId ,String userId){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(ID , userId);
        queryBundle.putString(SeriesDetailsActivity.IDTWO ,  movieId);
        queryBundle.putString(SERVER_LINK , SERVER_CHECK_LIST_API);


        LoaderManager loaderManager = LoaderManager.getInstance(this);
        //  Get our Loader by calling getLoader and passing the ID we specified
        Loader<Object> loadcheck = loaderManager.getLoader(LOADER_FOR_CHECK_LIST);
        //  If the Loader was null, initialize it. Else, restart it.
        if (loadcheck == null) {
            loaderManager.initLoader(LOADER_FOR_CHECK_LIST, queryBundle, this);
        } else {
            loaderManager.restartLoader(LOADER_FOR_CHECK_LIST, queryBundle, this);
        }
    }

    private void getMainScreenBangTan(String cat){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(CAT , cat);
        queryBundle.putString(SERVER_LINK , SERVER_LOAD_MAIN_SCREEN_SHOWS_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_FOR_LOAD_MAIN_SCREEN_BANGTANS, queryBundle, this);
    }

    @Override
    public void onListItemClick(GeneralShow ret , String recyclerViewDefiningString) {
        if(ret.getCat_id() == 2) {
            Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
            Inventory.getInstance().add(ret);
            intent.putExtra(MainActivity.MOVIE_OBJECT_ID , String.valueOf(ret.getId()));
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(MainActivity.this , SeriesDetailsActivity.class);
            Inventory.getInstance().add(ret);
            intent.putExtra(MainActivity.TV_SHOW_OBJECT_ID , String.valueOf(ret.getId()));
            startActivity(intent);
        }
    }

    public void moreNonTypeMoviesClickListner(View view) {
        openMoreMovies();
    }

    public void moreShowsClickListner(View view) {
        openTvShowsCategories();
    }

    public void openTvShowsCategories(){
        Intent intent = new Intent(this , ShowsCategoriesActivity.class);
        startActivity(intent);
    }
    public void openMoreMovies(){
        Intent intent = new Intent(this , MoreNonTypeShowsActivity.class);
        intent.putExtra(TYPE , GeneralShowType.MOVIES.toString());
        startActivity(intent);
    }

    private void getShowTagsFromServer(String id) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , MovieDetailsActivity.SERVER_LOAD_TAGS_API);
        queryBundle.putString(ID , id);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_SHOW_TAGS, queryBundle, this);

    }

    public void getMainScreenShows(){
        getLatestMovie();
        getMainScreenMovies(GeneralShowType.MOVIES.toString());
        getMainScreenTvShows(GeneralShowType.TV_SHOWS.toString());
        getMainScreenVlive(GeneralShowType.VLIVE.toString());
        getMainScreenOthers(GeneralShowType.OTHERS.toString());
        getMainScreenDvds(GeneralShowType.DVD.toString());
        getMainScreenBangTan(GeneralShowType.BANGTAN_TV.toString());
    }

    @Override
    public void onListItemClick(Actor ac) {
        String id = String.valueOf(ac.getId());
        Intent intent = new Intent(this , ActorWorks.class);
        intent.putExtra(MainActivity.ID , id);
        intent.putExtra(NAME , ac.getName());
        startActivity(intent);
    }

    void incrementOperationCount() {
        synchronized (this) {
            operationCount = operationCount + 1;
        }
        if(operationCount == numOfOperations)
            progressBarLinearLayout.setVisibility(View.GONE);
    }



    public void inverseTheListStatus(View view) {
        // if true then erase it , if false then add it
        if(account != null && latestShow != null) {
            InverseTheListStatusInServer(String.valueOf(userId) ,String.valueOf(latestShow.getId()) );
        }
        else{
            Toast.makeText(this , getString(R.string.must_sign_in) , Toast.LENGTH_SHORT).show();
        }
    }

    public void upperBarTvShowsClick(View view) {
        openTvShowsCategories();
    }

    public void upperBarMoviesClick(View view) {
        openMoreMovies();
    }

    public void upperBarMyListClick(View view) {
        if(account != null){
            Intent intent = new Intent(this , MyListActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this , getString(R.string.must_sign_in) , Toast.LENGTH_SHORT).show();
        }
    }

    public void latestShowPlayClick(View view) {
        if(latestShow != null){
            if(latestShow.getCat_id() == 2){       // movie
                Intent intent = new Intent(this , MovieDetailsActivity.class);
                Inventory.getInstance().add(latestShow);
                intent.putExtra(MOVIE_OBJECT_ID , String.valueOf(latestShow.getId()));
                startActivity(intent);
            }
            else{     // show
                Intent intent = new Intent(this , SeriesDetailsActivity.class);
                Inventory.getInstance().add(latestShow);
                intent.putExtra(TV_SHOW_OBJECT_ID , String.valueOf(latestShow.getId()));
                startActivity(intent);
            }
        }
    }

    public void moreNonTypeDvdClickListner(View view) {
        Intent intent = new Intent(this , MoreNonTypeShowsActivity.class);
        intent.putExtra(TYPE , GeneralShowType.DVD.toString());
        startActivity(intent);
    }

    public void moreNonTypeBangTanClickListner(View view) {
        Intent intent = new Intent(this , MoreNonTypeShowsActivity.class);
        intent.putExtra(TYPE , GeneralShowType.BANGTAN_TV.toString());
        startActivity(intent);
    }

    public void moreNonTypeVLiveClickListner(View view) {
        Intent intent = new Intent(this , MoreNonTypeShowsActivity.class);
        intent.putExtra(TYPE , GeneralShowType.VLIVE.toString());
        startActivity(intent);
    }

    public void moreNonTypeOthersClickListner(View view) {
        Intent intent = new Intent(this , MoreNonTypeShowsActivity.class);
        intent.putExtra(TYPE , GeneralShowType.OTHERS.toString());
        startActivity(intent);
    }
    void stopLoader(int id) {
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.destroyLoader(id);
    }
}