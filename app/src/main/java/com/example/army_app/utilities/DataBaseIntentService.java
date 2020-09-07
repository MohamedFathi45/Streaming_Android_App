package com.example.army_app.utilities;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class DataBaseIntentService extends IntentService {


    public DataBaseIntentService() {
        super("DataBaseIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        try {
            DataBaseTasks.executeTask(this , action);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
