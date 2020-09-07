package com.example.army_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.army_app.Loader_Tasks.TvShowsByTypeLoader;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MoreShowsActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object>{

    ArrayList<GeneralShow> ret=null;
    GridView more_shows_grid_view;
    LinearLayout mLoadingLayout;
    LinearLayout mResultLayout;
    LinearLayout mNoResultLayout;
    public static final String SERVER_LOAD_TV_SHOWS_BY_TYPE_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_tv_shows_by_type.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_shows);
        more_shows_grid_view = findViewById(R.id.id_more_shows_grid);
        mResultLayout = findViewById(R.id.id_results_layout);
        mLoadingLayout = findViewById(R.id.id_progress_bar_linear_layout);
        mNoResultLayout = findViewById(R.id.id_no_results_layout);
        Intent intent = getIntent();
        String type = intent.getStringExtra(MainActivity.TYPE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mResultLayout.setVisibility(View.GONE);
        mNoResultLayout.setVisibility(View.GONE);
        getShowsFromServer(type);
        more_shows_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                Intent intent = new Intent(MoreShowsActivity.this , SeriesDetailsActivity.class);
                Inventory.getInstance().add(ret.get(i));
                intent.putExtra(MainActivity.TV_SHOW_OBJECT_ID , String.valueOf(ret.get(i).getId()));
                startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader res = null;
        switch (id){
            case MainActivity.LOADER_FOR_LOAD_TV_SHOWS_BY_TYPE:
                res = new TvShowsByTypeLoader(this , args.getString(MainActivity.SERVER_LINK) ,args.getString(MainActivity.CAT) );
                break;
        }
        return res;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == MainActivity.LOADER_FOR_LOAD_TV_SHOWS_BY_TYPE) {
            if(data != null) {
                mNoResultLayout.setVisibility(View.GONE);
                mLoadingLayout.setVisibility(View.GONE);
                mResultLayout.setVisibility(View.VISIBLE);
                ret = (ArrayList<GeneralShow>) data;
                GeneralShowAdapterGridView movieAdapter = new GeneralShowAdapterGridView(this, (ArrayList<GeneralShow>) data);
                more_shows_grid_view.setAdapter(movieAdapter);
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
    private void getShowsFromServer(String type){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_LOAD_TV_SHOWS_BY_TYPE_API);
        queryBundle.putString(MainActivity.CAT , type);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_TV_SHOWS_BY_TYPE, queryBundle, this);
    }
}
