package com.example.army_app.Loader_Tasks;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.army_app.GeneralShow;
import com.example.army_app.MainActivity;
import com.example.army_app.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ActorWorksLoader extends AsyncTaskLoader<ArrayList<GeneralShow>> {

    ArrayList<GeneralShow> loadedShows = null;
    String link;// this have to go inside bundel but is made for example
    Context context;
    String actor_id;
    LinearLayout resultsLayout;
    LinearLayout noResultsLayout;
    LinearLayout progress_bar_linear_layout;

    public ActorWorksLoader(Context context, String link, String actor_id, LinearLayout resultsLayout, LinearLayout noResultsLayout, LinearLayout progress_bar_linear_layout){
        super(context);
        this.context = context;
        this.link = link;
        this.actor_id = actor_id;
        this.resultsLayout = resultsLayout;
        this.noResultsLayout = noResultsLayout;
        this.progress_bar_linear_layout = progress_bar_linear_layout;
    }

    @Nullable
    @Override
    public ArrayList<GeneralShow> loadInBackground() {
        ArrayList<GeneralShow> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_ACTOR_WORKS));
            params.add(link);
            params.add(actor_id);
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
        progress_bar_linear_layout.setVisibility(View.VISIBLE);
        resultsLayout.setVisibility(View.GONE);
        noResultsLayout.setVisibility(View.GONE);
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
