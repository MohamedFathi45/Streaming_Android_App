package com.example.army_app.Loader_Tasks;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.army_app.Episode;
import com.example.army_app.MainActivity;
import com.example.army_app.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class EpisodesLoader extends AsyncTaskLoader<ArrayList<Episode>> {

    ArrayList<Episode> loadedEpisodes = null;
    String link;// this have to go inside bundel but is made for example
    Context context;
    String show_id;
    String session_id;

    public EpisodesLoader(Context context, String link,String show_id ,String session_id){
        super(context);
        this.context = context;
        this.link = link;
        this.show_id = show_id;
        this.session_id = session_id;
    }

    @Nullable
    @Override
    public ArrayList<Episode> loadInBackground() {
        ArrayList<Episode> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_EPISODES));
            params.add(link);
            params.add(show_id);
            params.add(session_id);
            ret = (ArrayList<Episode>) NetworkOP.executeNetworkOperation(params);
            loadedEpisodes = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Episode> data) {
        this.loadedEpisodes = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedEpisodes == null)
            forceLoad();
        else
            deliverResult(loadedEpisodes);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedEpisodes = null;
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
