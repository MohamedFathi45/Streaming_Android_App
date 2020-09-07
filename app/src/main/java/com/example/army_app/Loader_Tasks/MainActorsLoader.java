package com.example.army_app.Loader_Tasks;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.army_app.Actor;
import com.example.army_app.MainActivity;
import com.example.army_app.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActorsLoader extends AsyncTaskLoader<ArrayList<Actor>> {
    ArrayList<Actor> loadedActors = null;
    String link;            // this have to go inside bundel but is made for example
    Context context;
    String movie_id;

    public MainActorsLoader(Context context, String link , String movie_id ){
        super(context);
        this.context = context;
        this.link = link;
        this.movie_id = movie_id;
    }

    @Nullable
    @Override
    public ArrayList<Actor> loadInBackground() {
        ArrayList<Actor> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_MAIN_ACTORS));
            params.add(link);
            params.add(movie_id);
            ret = (ArrayList<Actor>) NetworkOP.executeNetworkOperation(params);
            loadedActors = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
    @Override
    public void deliverResult(@Nullable ArrayList<Actor> data) {
        this.loadedActors = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedActors == null)
            forceLoad();
        else
            deliverResult(loadedActors);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedActors = null;
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
