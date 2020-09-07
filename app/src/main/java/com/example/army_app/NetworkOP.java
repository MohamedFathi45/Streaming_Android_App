package com.example.army_app;


import android.util.Log;

import com.example.army_app.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class NetworkOP {


    public static synchronized Object executeNetworkOperation(ArrayList<String> params) throws UnsupportedEncodingException, JSONException {
        Object ret = null;
        int id = Integer.parseInt(params.get(0));
        if(id == MainActivity.LOADER_FOR_LOAD_MAIN_SCREEN_MOVIES) {
            String link = params.get(1);
            String cat = params.get(2);
            ret = NetworkUtils.LoadMainScreenShows(link ,cat );
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_MAIN_SCREEN_TV_SHOWS) {
            String link = params.get(1);
            String cat = params.get(2);
            ret = NetworkUtils.LoadMainScreenShows(link ,cat );
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_MAIN_SCREEN_DVDS) {
            String link = params.get(1);
            String cat = params.get(2);
            ret = NetworkUtils.LoadMainScreenShows(link ,cat );
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_MAIN_SCREEN_POSTER ) {
            String link = params.get(1);
            ret = NetworkUtils.LoadeLatestShow(link);
        }
        else if( id == MainActivity.LOADER_FOR_LOAD_MAIN_SCREEN_BANGTANS) {
            String link = params.get(1);
            String cat = params.get(2);
            ret = NetworkUtils.LoadMainScreenShows(link ,cat );
        }
        else if( id == MainActivity.LOADER_FOR_LOAD_MAIN_SCREEN_VLIVE) {
            String link = params.get(1);
            String cat = params.get(2);
            ret = NetworkUtils.LoadMainScreenShows(link ,cat );
        }
        else if( id == MainActivity.LOADER_FOR_LOAD_MAIN_SCREEN_OTHERS) {
            String link = params.get(1);
            String cat = params.get(2);
            ret = NetworkUtils.LoadMainScreenShows(link ,cat );
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_MOVIE_LINKS){
            String link = params.get(1);
            String movieId = params.get(2);
            ret = NetworkUtils.LoadMovieLinks(link , movieId);
        }

        else if(id == MainActivity.LOADER_FOR_LOAD_SHOW_TAGS){
            String link = params.get(1);
            String movieId = params.get(2);
            ret = NetworkUtils.LoadeTags(link , movieId);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_MAIN_ACTORS){
            String link = params.get(1);
            String movieId = params.get(2);
            ret = NetworkUtils.LoadeActors(link , movieId);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_ALL_SHOW_CAT) {
            String link = params.get(1);
            String cat = params.get(2);
            ret = NetworkUtils.LoadMainScreenShows(link ,cat );
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_SESSIONS){
            String link = params.get(1);
            String seriesId = params.get(2);
            ret = NetworkUtils.LoadeSessions(link , seriesId);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_EPISODES){
            String link = params.get(1);
            String seriesId = params.get(2);
            String sessionId = params.get(3);
            ret = NetworkUtils.LoadeEpisodes(link , seriesId , sessionId);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_EPISODE_LINKS){
            String link = params.get(1);
            String seriesId = params.get(2);
            String sessionId = params.get(3);
            String episodeId = params.get(4);
            ret = NetworkUtils.LoadeEpisodeLinks(link , seriesId , sessionId , episodeId);
        }
        /*
        else if(id == MainActivity.LOADER_FOR_LOAD_SERIES_LINK){
            String link = params.get(1);
            String seriesId = params.get(2);
            String sessionId = params.get(3);
            String episodeId = params.get(4);
            String server_id = params.get(5);
            String cat = params.get(6);
            ret = NetworkUtils.LoadeSeriesLink(link , seriesId , sessionId , episodeId , server_id , cat);
        }
         */
        else if(id == MainActivity.LOADER_FOR_LOAD_NON_SERIES_LINK){
            String link = params.get(1);
            String non_series_show_id=params.get(2);
            String server_id = params.get(3);
            String cat = params.get(4);
            ret = NetworkUtils.LoadeNonSeriesLink(link,non_series_show_id , server_id , cat);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_ALL_ACTORS){
            String link = params.get(1);
            ret = NetworkUtils.LoadeActors(link);
        }
        else if (id == MainActivity.LOADER_FOR_LOAD_TV_SHOWS_BY_TYPE){
            String link = params.get(1);
            String cat = params.get(2);
            ret = NetworkUtils.LoadMainScreenShows(link ,cat );
        }
        else if (id == MainActivity.LOADER_FOR_LOAD_ACTOR_WORKS){
            String link = params.get(1);
            String actorId = params.get(2);
            ret = NetworkUtils.LoadeActorWorks(link ,actorId );
        }
        else if(id == MainActivity.LOADER_FOR_SEARCH){
            String link = params.get(1);
            String name = params.get(2);
            ret = NetworkUtils.LoadSearchResults(link , name);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_OUR_WORKS){
            String link = params.get(1);
            ret = NetworkUtils.LoadOurWorks(link);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_MY_LIST){
            String link = params.get(1);
            String userid = params.get(2);
            ret = NetworkUtils.LoadeUserList(link ,userid);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_HISTORY){
            String link = params.get(1);
            String userid = params.get(2);
            ret = NetworkUtils.LoadeUserList(link ,userid);
        }
        else if(id == MainActivity.LOADER_FOR_CHECK_LIST){
            String link = params.get(1);
            String userid = params.get(2);
            String movieId = params.get(3);
            ret = NetworkUtils.CheckGeneralShowInListOrNot(link , movieId , userid);
        }
        else if(id == MainActivity.LOADER_FOR_INVERSE_LIST_STATUS){
            String link = params.get(1);
            String userid = params.get(2);
            String movieId = params.get(3);
            ret = NetworkUtils.InvrseListStatus(link , movieId , userid);
        }

        else if(id == MainActivity.LOADER_FOR_RIGISTER_USER){
            String link = params.get(1);
            String email = params.get(2);
            String user_name = params.get(3);
            String user_photo_url = params.get(4);
            NetworkUtils.InsertUser(link , email , user_name , user_photo_url);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_SOCIAL_MEDIA_LINKS){
            String link = params.get(1);
            ret = NetworkUtils.getKeyValueLinks(link);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_APPLICATION_VERSION){
            String link = params.get(1);
            ret = NetworkUtils.getApplicationVersion(link);
        }
        else if(id == MainActivity.LOADER_FOR_REGISTER_IN_USER_HISTORY){
            String link = params.get(1);
            String user_id = params.get(2);
            String general_show_id = params.get(3);
            ret = NetworkUtils.RegisterShowInUserHistory(link , user_id , general_show_id);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_REVIEWS){
            String link = params.get(1);
            String general_show_id = params.get(2);
            ret = NetworkUtils.LoadReviews(link , general_show_id);
        }
        else if(id == MainActivity.LOADER_FOR_INSERT_REVIEW){
            String link = params.get(1);
            String user_id = params.get(2);
            String show_id = params.get(3);
            String comment = params.get(4);
            ret = NetworkUtils.InsertReview(link , user_id , show_id , comment);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_EPISODE_COMMENTS){
            String link = params.get(1);
            String show_id = params.get(2);
            String session_id = params.get(3);
            String episode_id = params.get(4);
            ret = NetworkUtils.LoadEpisodeComments(link , show_id , session_id , episode_id);
        }
        else if(id == MainActivity.LOADER_FOR_INSERT_EPISODE_COMMENTS){
            String link = params.get(1);
            String user_id = params.get(2);
            String show_id = params.get(3);
            String session_id = params.get(4);
            String episode_id = params.get(5);
            String comment = params.get(6);
            ret = NetworkUtils.InsertEpisodeComment(link , user_id , show_id , session_id , episode_id , comment);
        }
        else if(id== MainActivity.LOADER_FOR_LOAD_USER_RATING){
            String link = params.get(1);
            String user_id = params.get(2);
            String show_id = params.get(3);
            ret = NetworkUtils.GetUserRating(link , user_id , show_id);
        }
        return ret;
    }

}
