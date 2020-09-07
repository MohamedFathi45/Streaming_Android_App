package com.example.army_app.Loader_Tasks;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.army_app.MainActivity;
import com.example.army_app.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;

public class  CheckTheListLoader extends AsyncTaskLoader<BigInteger> {

    BigInteger loadedResult =  null;
    String link;            // this have to go inside bundel but is made for example
    Context context;
    String movie_id;
    String user_id;


    public CheckTheListLoader(Context context, String link , String user_id , String movie_id ){
        super(context);
        this.context = context;
        this.link = link;
        this.user_id = user_id;
        this.movie_id = movie_id;
    }

    @Nullable
    @Override
    public BigInteger loadInBackground() {
        BigInteger ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_CHECK_LIST));
            params.add(link);
            params.add(user_id);
            params.add(movie_id);
            ret = (BigInteger) NetworkOP.executeNetworkOperation(params);
            loadedResult = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
    @Override
    public void deliverResult(@Nullable BigInteger data) {
        this.loadedResult = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(loadedResult != null)
            deliverResult(loadedResult);
        if(takeContentChanged() || loadedResult == null)
            forceLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedResult = null;
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
