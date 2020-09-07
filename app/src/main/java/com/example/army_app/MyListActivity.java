package com.example.army_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.army_app.Loader_Tasks.MyListLoader;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MyListActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object> , FavouritesAdapter.ListItemClickListener {

    private static final String SERVER_LOAD_MY_LIST_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_my_list.php";
    RecyclerView myListRecyclerView;
    LinearLayout mLoadingLayout;
    LinearLayout mResultLayout;
    LinearLayout mNoResultLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        setTitle(getString(R.string.my_list));
        myListRecyclerView = findViewById(R.id.id_favourites_recycler_view);
        mResultLayout = findViewById(R.id.id_results_layout);
        mLoadingLayout = findViewById(R.id.id_progress_bar_linear_layout);
        mNoResultLayout = findViewById(R.id.id_no_result_layout);
        mNoResultLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mResultLayout.setVisibility(View.GONE);
        getUserListFromServer(String.valueOf(MainActivity.userId));
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader loader = null;
        if(id == MainActivity.LOADER_FOR_LOAD_MY_LIST){
            loader = new MyListLoader(this , args.getString(MainActivity.SERVER_LINK) , args.getString(MainActivity.ID));
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == MainActivity.LOADER_FOR_LOAD_MY_LIST) {
            if(data != null) {
                mNoResultLayout.setVisibility(View.GONE);
                mLoadingLayout.setVisibility(View.GONE);
                mResultLayout.setVisibility(View.VISIBLE);
                FavouritesAdapter favouritesAdapter = new FavouritesAdapter(this, (ArrayList<GeneralShow>) data , this);
                myListRecyclerView.setAdapter(favouritesAdapter);
                myListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            }
            else{
                mLoadingLayout.setVisibility(View.GONE);
                mResultLayout.setVisibility(View.GONE);
                mNoResultLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }
    private void getUserListFromServer(String id){

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(MainActivity.SERVER_LINK, SERVER_LOAD_MY_LIST_API);
        queryBundle.putString(MainActivity.ID , id);          // is every google account has unique id

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_MY_LIST, queryBundle, this);
    }

    @Override
    public void onListItemClick(GeneralShow generalShow) {
        if(generalShow.getCat_id() == 2) {
            Intent intent = new Intent(MyListActivity.this, MovieDetailsActivity.class);
            Inventory.getInstance().add(generalShow);
            intent.putExtra(MainActivity.MOVIE_OBJECT_ID , String.valueOf(generalShow.getId()));
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(MyListActivity.this , SeriesDetailsActivity.class);
            Inventory.getInstance().add(generalShow);
            intent.putExtra(MainActivity.TV_SHOW_OBJECT_ID , String.valueOf(generalShow.getId()));
            startActivity(intent);
        }
    }
}
