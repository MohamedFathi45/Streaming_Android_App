package com.example.army_app.Loader_Tasks;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.army_app.MainActivity;
import com.example.army_app.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class VersionLoader extends AsyncTaskLoader<ArrayList<String>> {

    ArrayList<String> loadedVersion = null;
    String link;            // this have to go inside bundel but is made for example
    Context context;


    public VersionLoader(Context context, String link){
        super(context);
        this.context = context;
        this.link = link;
    }

    @Nullable
    @Override
    public ArrayList<String> loadInBackground() {
        ArrayList<String> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_APPLICATION_VERSION));
            params.add(link);
            ret = (ArrayList<String>) NetworkOP.executeNetworkOperation(params);
            loadedVersion = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
    @Override
    public void deliverResult(@Nullable ArrayList<String> data) {
        this.loadedVersion = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedVersion == null)
            forceLoad();
        else
            deliverResult(loadedVersion);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedVersion = null;
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
