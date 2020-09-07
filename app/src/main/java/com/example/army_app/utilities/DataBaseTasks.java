package com.example.army_app.utilities;

import android.content.Context;

import com.example.army_app.MainActivity;
import com.example.army_app.SignInActivity;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class DataBaseTasks {

    public static final String SET_SHOW_RATTING_IN_DATABASE = "set ratting in database";
    public static final String SET_HISTORY_DATABASE = "set history in database";
    public static final String GET_USER_FROM_DATABASE = "get user from database";

    public static void executeTask(Context context , String action) throws UnsupportedEncodingException, JSONException {
        String[] parts = action.split(",");
        action = parts[0];
            if(SET_SHOW_RATTING_IN_DATABASE.equals(action)){
                NetworkUtils.WriteShowRatting(MainActivity.WRITE_SHOW_RATTING_API  , parts[1] ,parts[2] , parts[3]);
            }
            else if(SET_HISTORY_DATABASE.equals(action)){
                NetworkUtils.RegisterShowInUserHistory(MainActivity.SERVER_REGISTER_SHOW_IN_HISTORY_API,parts[1] , parts[2]);
            }
            else if(GET_USER_FROM_DATABASE.equals(action)){
                NetworkUtils.InsertUser(SignInActivity.SERVER_WRITE_USER_API ,parts[1] , parts[2] , parts[3]);
            }
    }
}
