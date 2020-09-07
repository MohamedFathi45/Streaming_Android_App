package com.example.army_app.Loader_Tasks;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.army_app.Comment;
import com.example.army_app.MainActivity;
import com.example.army_app.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class EpisodeCommentLoader extends AsyncTaskLoader<ArrayList<Comment>> {
    ArrayList<Comment> loadedComments;
    Context context;
    String link;
    String general_show_id;
    String session_id;
    String episode_id;
    public EpisodeCommentLoader(Context context, String link , String show_id , String session_id , String episode_id){
        super(context);
        this.context = context;
        this.link = link;
        this.general_show_id = show_id;
        this.session_id  = session_id;
        this.episode_id = episode_id;
    }

    @Nullable
    @Override
    public ArrayList<Comment> loadInBackground() {
        ArrayList<Comment> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_EPISODE_COMMENTS));
            params.add(link);
            params.add(general_show_id);
            params.add(session_id);
            params.add(episode_id);
            ret = (ArrayList<Comment>) NetworkOP.executeNetworkOperation(params);
            loadedComments = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
    @Override
    public void deliverResult(@Nullable ArrayList<Comment> data) {
        this.loadedComments = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedComments == null)
            forceLoad();
        else
            deliverResult(loadedComments);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedComments = null;
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
