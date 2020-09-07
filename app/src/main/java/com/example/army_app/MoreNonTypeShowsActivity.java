package com.example.army_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.army_app.Loader_Tasks.AllShowCatLoader;

import java.util.ArrayList;

public class MoreNonTypeShowsActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object> {

    LinearLayout mLoadingLayout;
    LinearLayout mResultLayout;
    LinearLayout mNoResultLayout;
    GridView more_movies_grid_view;
    ArrayList<GeneralShow> ret = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_movies);
        mResultLayout = findViewById(R.id.id_results_layout);
        mLoadingLayout = findViewById(R.id.id_progress_bar_linear_layout);
        mNoResultLayout = findViewById(R.id.id_no_results_layout);
        more_movies_grid_view = findViewById(R.id.id_more_movies_grid);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mResultLayout.setVisibility(View.GONE);
        mNoResultLayout.setVisibility(View.GONE);
        Intent intent = getIntent();
        String type = intent.getStringExtra(MainActivity.TYPE);
        setTitle(type);
        getAllShowCatFromServer(type);
        more_movies_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MoreNonTypeShowsActivity.this , MovieDetailsActivity.class);
                Inventory.getInstance().add(ret.get(i));
                intent.putExtra(MainActivity.MOVIE_OBJECT_ID , String.valueOf(ret.get(i).getId()));
                startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader res = null;
        switch (id){
            case MainActivity.LOADER_FOR_LOAD_ALL_SHOW_CAT:
                res= new AllShowCatLoader(this , args.getString(MainActivity.SERVER_LINK) ,args.getString(MainActivity.ID) );
                break;
        }
        return res;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {

        int id = loader.getId();
        if(id == MainActivity.LOADER_FOR_LOAD_ALL_SHOW_CAT) {
            if(data != null) {
                mNoResultLayout.setVisibility(View.GONE);
                mLoadingLayout.setVisibility(View.GONE);
                mResultLayout.setVisibility(View.VISIBLE);
                ret = (ArrayList<GeneralShow>) data;
                GeneralShowAdapterGridView movieAdapter = new GeneralShowAdapterGridView(this, (ArrayList<GeneralShow>) data);
                more_movies_grid_view.setAdapter(movieAdapter);
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

    private void getAllShowCatFromServer(String cat_name){

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(MainActivity.SERVER_LINK, MainActivity.SERVER_LOAD_ALL_SHOW_CAT_API);
        queryBundle.putString(MainActivity.ID , cat_name);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_ALL_SHOW_CAT, queryBundle, this);
    }
}
