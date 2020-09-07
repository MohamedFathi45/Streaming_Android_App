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

public class ShowTagsLoader extends AsyncTaskLoader<ArrayList<String>> {
    ArrayList<String> loadedTags = null;
    String link;            // this have to go inside bundel but is made for example
    Context context;
    String movie_id;



    public ShowTagsLoader(Context context, String link, String movie_id ){
        super(context);
        this.context = context;
        this.link = link;
        this.movie_id = movie_id;
    }

    @Nullable
    @Override
    public ArrayList<String> loadInBackground() {
        ArrayList<String> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_SHOW_TAGS));
            params.add(link);
            params.add(movie_id);
            ret = (ArrayList<String>) NetworkOP.executeNetworkOperation(params);
            loadedTags = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
    @Override
    public void deliverResult(@Nullable ArrayList<String> data) {
        this.loadedTags = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(loadedTags == null)
            forceLoad();
        else
            deliverResult(loadedTags);;
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedTags = null;
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
