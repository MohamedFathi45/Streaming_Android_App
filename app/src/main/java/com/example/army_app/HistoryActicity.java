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

import com.example.army_app.Loader_Tasks.HistoryLoader;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HistoryActicity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object> , FavouritesAdapter.ListItemClickListener{

    private static final String SERVER_LOAD_HISTORY_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_history.php";
    RecyclerView myHistoryRecyclerView;
    LinearLayout mLoadingLayout;
    LinearLayout mResultLayout;
    LinearLayout mNoResultLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_acticity);
        setTitle(getString(R.string.my_history));
        myHistoryRecyclerView = findViewById(R.id.id_history_recycler_view);
        mResultLayout = findViewById(R.id.id_results_layout);
        mLoadingLayout = findViewById(R.id.id_progress_bar_linear_layout);
        mNoResultLayout = findViewById(R.id.id_no_result_layout);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mResultLayout.setVisibility(View.GONE);
        mNoResultLayout.setVisibility(View.GONE);
        getUserHistoryFromServer(String.valueOf(MainActivity.userId));
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader loader = null;
       if(id == MainActivity.LOADER_FOR_LOAD_HISTORY){
           loader = new HistoryLoader(this , args.getString(MainActivity.SERVER_LINK) , args.getString(MainActivity.ID));
       }
       return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == MainActivity.LOADER_FOR_LOAD_HISTORY) {
            if(data != null) {
                mNoResultLayout.setVisibility(View.GONE);
                mLoadingLayout.setVisibility(View.GONE);
                mResultLayout.setVisibility(View.VISIBLE);
                FavouritesAdapter historyAdapter = new FavouritesAdapter(this, (ArrayList<GeneralShow>) data , this);
                myHistoryRecyclerView.setAdapter(historyAdapter);
                myHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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

    @Override
    public void onListItemClick(GeneralShow generalShow) {
        if(generalShow.getCat_id() == 2) {
            Intent intent = new Intent(HistoryActicity.this, MovieDetailsActivity.class);
            Inventory.getInstance().add(generalShow);
            intent.putExtra(MainActivity.MOVIE_OBJECT_ID , String.valueOf(generalShow.getId()));
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(HistoryActicity.this , SeriesDetailsActivity.class);
            Inventory.getInstance().add(generalShow);
            intent.putExtra(MainActivity.TV_SHOW_OBJECT_ID , String.valueOf(generalShow.getId()));
            startActivity(intent);
        }

    }

    private void getUserHistoryFromServer(String id){

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(MainActivity.SERVER_LINK, SERVER_LOAD_HISTORY_API);
        queryBundle.putString(MainActivity.ID , id);          // is every google account has unique id

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_HISTORY, queryBundle, this);
    }
}
