package com.example.army_app.Loader_Tasks;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.army_app.Actor;
import com.example.army_app.MainActivity;
import com.example.army_app.NetworkOP;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class UserRigisterLoader extends AsyncTaskLoader<Void> {
    String link;
    String email;
    String user_name;
    String user_photo_url;
    public UserRigisterLoader(@NonNull Context context ,String link ,String email , String usr_name , String user_photo_url) {
        super(context);
        this.link = link;
        this.email = email;
        this.user_name = usr_name;
        this.user_photo_url = user_photo_url;
    }

    @Nullable
    @Override
    public Void loadInBackground() {

        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_RIGISTER_USER));
            params.add(link);
            params.add(email);
            params.add(user_name);
            params.add(user_photo_url);
            NetworkOP.executeNetworkOperation(params);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
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
