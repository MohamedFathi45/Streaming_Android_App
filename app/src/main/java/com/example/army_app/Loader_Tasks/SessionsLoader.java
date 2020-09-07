package com.example.army_app.Loader_Tasks;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.army_app.MainActivity;
import com.example.army_app.NetworkOP;
import com.example.army_app.Session;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SessionsLoader extends AsyncTaskLoader<ArrayList<Session>> {

    ArrayList<Session> loadedSessions = null;
    String link;// this have to go inside bundel but is made for example
    Context context;
    String show_id;



    public SessionsLoader(Context context, String link ,String show_id){
        super(context);
        this.context = context;
        this.link = link;
        this.show_id = show_id;
    }

    @Nullable
    @Override
    public ArrayList<Session> loadInBackground() {
        ArrayList<Session> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_SESSIONS));
            params.add(link);
            params.add(show_id);
            ret = (ArrayList<Session>) NetworkOP.executeNetworkOperation(params);
            loadedSessions = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Session> data) {
        this.loadedSessions = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedSessions == null)
            forceLoad();
        else
            deliverResult(loadedSessions);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedSessions = null;
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
