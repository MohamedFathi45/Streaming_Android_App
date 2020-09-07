package com.example.army_app.Loader_Tasks;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.army_app.MainActivity;
import com.example.army_app.NetworkOP;
import com.example.army_app.Server;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class EpisodeLinksLoader extends AsyncTaskLoader<ArrayList<Server>> {
    ArrayList<Server> loadedServers = null;
    String link;// this have to go inside bundel but is made for example
    Context context;
    String show_id;
    String session_id;
    String episode_id;


    public EpisodeLinksLoader(Context context, String link , String show_id , String session_id , String episode_id){
        super(context);
        this.context = context;
        this.link = link;
        this.show_id = show_id;
        this.session_id =session_id;
        this.episode_id = episode_id;
    }

    @Nullable
    @Override
    public ArrayList<Server> loadInBackground() {
        ArrayList<Server> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_EPISODE_LINKS));
            params.add(link);
            params.add(show_id);
            params.add(session_id);
            params.add(episode_id);
            ret = (ArrayList<Server>) NetworkOP.executeNetworkOperation(params);
            loadedServers = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Server> data) {
        this.loadedServers = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedServers == null)
            forceLoad();
        else
            deliverResult(loadedServers);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedServers = null;
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
