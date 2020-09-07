package com.example.army_app.Loader_Tasks;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.army_app.GeneralShow;
import com.example.army_app.MainActivity;
import com.example.army_app.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainScreenMoviesLoader extends AsyncTaskLoader<ArrayList<GeneralShow>> {
    ArrayList<GeneralShow> loadedMovies = null;
    String link;            // this have to go inside bundel but is made for example
    String cat;
    Context context;

    public MainScreenMoviesLoader(Context context, String link,String cat ){
        super(context);
        this.context = context;
        this.link = link;
        this.cat = cat;
    }

    @Nullable
    @Override
    public ArrayList<GeneralShow> loadInBackground() {
        ArrayList<GeneralShow> ret = null;
        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_MAIN_SCREEN_MOVIES));
            params.add(link);
            params.add(cat);
            ret = (ArrayList<GeneralShow>) NetworkOP.executeNetworkOperation(params);
            loadedMovies = ret;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<GeneralShow> data) {
        this.loadedMovies = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedMovies == null)
            forceLoad();
        else
            deliverResult(loadedMovies);

    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedMovies = null;
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
