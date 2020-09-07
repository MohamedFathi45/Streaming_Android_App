package com.example.army_app.Loader_Tasks;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.army_app.GeneralShow;
import com.example.army_app.MainActivity;
import com.example.army_app.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MyListLoader extends AsyncTaskLoader<ArrayList<GeneralShow>> {
    ArrayList<GeneralShow> loadedShows = null;
    String link;// this have to go inside bundel but is made for example
    Context context;
    String user_id;


    public MyListLoader(Context context, String link ,String user_id){
        super(context);
        this.context = context;
        this.link = link;
        this.user_id = user_id;
    }

    @Nullable
    @Override
    public ArrayList<GeneralShow> loadInBackground() {
        ArrayList<GeneralShow> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_MY_LIST));
            params.add(link);
            params.add(user_id);
            ret = (ArrayList<GeneralShow>) NetworkOP.executeNetworkOperation(params);
            loadedShows = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<GeneralShow> data) {
        this.loadedShows = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedShows == null)
            forceLoad();
        else
            deliverResult(loadedShows);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedShows = null;
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
