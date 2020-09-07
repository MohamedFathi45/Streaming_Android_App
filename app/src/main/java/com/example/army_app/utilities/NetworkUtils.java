package com.example.army_app.utilities;

import android.content.Context;
import android.util.Log;

import com.example.army_app.Actor;
import com.example.army_app.Comment;
import com.example.army_app.Episode;
import com.example.army_app.GeneralShow;
import com.example.army_app.GeneralShowSpec;

import com.example.army_app.MainActivity;
import com.example.army_app.Server;
import com.example.army_app.Session;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkUtils {

    //public static final String NUMBER_OF_LINES = "number_of_lines";
    public static final String USERNAME = "user";
    public static final String PASSWORD = "pass";
    public static final String ID = "id";
    //public static final String TYPE = "type";
    static Context context;
    public NetworkUtils(Context context){
        this.context = context;
    }

    private static synchronized JSONArray useHttpRequest(String link , String post_string){
        String ret = "";
        JSONArray ret_array = null;
        try {
            URL url = new URL(link);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream outputStream = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream , "UTF-8"));
            writer.write(post_string);
            writer.flush();
            writer.close();
            outputStream.close();
            InputStream inputStream = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream , "ISO-8859-1"));
            String line = "";
            while((line = reader.readLine())!=null ){           // line = one movie
                ret += line;
            }
            Log.d("AppDebugSpace" , ret);
            JSONObject jObj = new JSONObject(ret);

            ret_array = jObj.optJSONArray("data");

            reader.close();
            inputStream.close();
            http.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
        }
        return ret_array;
    }

    private static boolean is_null(JSONArray arr){
        if(arr == null || arr.length() == 0){
            return true;
        }
        return false;
    }

    private static synchronized ArrayList<GeneralShow>  getMainScreenShowsObjectsFromJasonArray(JSONArray arr ) throws JSONException {
        ArrayList<GeneralShow> r = new ArrayList<>();
        for( int i = 0 ; i < arr.length() ; i ++ ){
            JSONObject obj = (JSONObject) arr.get(i);
            String id =(String) obj.get("id");
            int cat_id = Integer.parseInt((String)obj.get("cat_id"));
            String name = (String) obj.get("general_show_name");

            String poster_path = (String) obj.get("poster_path");
            String description = (String) obj.get("description");
            String country = (String) obj.get("country");
            int yearOfProduction = Integer.parseInt((String)obj.get("year_of_production"));
            Map properties = new HashMap();

            properties.put("description" , description);
            properties.put("country" , country);
            properties.put("year_of_production" , yearOfProduction);
            properties.put("num_of_voters" ,new BigInteger( String.valueOf(obj.get("num_of_voters"))));
            properties.put("rating" ,new BigInteger( String.valueOf(obj.get("ratting"))));
            GeneralShowSpec spec = new GeneralShowSpec(properties);
            r.add(new GeneralShow(new BigInteger(id) ,cat_id, name,poster_path , country , spec));
        }

        return r;
    }

    /*
    private static ArrayList<Movie> getMovieObjectsFromJasonArray(JSONArray arr) throws JSONException {
        ArrayList<Movie> ret = new ArrayList<>();
        for( int i = 0 ; i < arr.length() ; i ++ ){
            JSONObject obj = (JSONObject) arr.get(i);
            String id =(String) obj.get("id");
            String name = (String) obj.get("name");
            String poster_path = (String) obj.get("poster_path");
            String description = (String) obj.get("description");
            String tags = getTagsFromJassonArray(arr);
            ret.add(new Movie(new BigInteger(id) , name , poster_path , description , null));

        }
        return ret;
    }

     */


    private static ArrayList<String> getTagsFromJasonArray(JSONArray arr) throws JSONException {
        ArrayList<String> ret = new ArrayList<>();
        for( int i = 0 ; i <arr.length() ; i ++){
            JSONObject obj = (JSONObject) arr.get(i);
            String tag = obj.get("tag").toString();
            ret.add(tag);
        }
        return ret;
    }

    private static String getLinkJasonArray(JSONArray arr,String cat) throws  JSONException{
        String ret = "";
        for( int i = 0 ; i <arr.length() ; i ++){
            JSONObject obj = (JSONObject) arr.get(i);
            String r = obj.get("link").toString();
            ret = r;
        }
        return ret;
    }

    private static ArrayList<Session> getSessionsFromJasonArray(JSONArray arr) throws JSONException {
        ArrayList<Session> ret = new ArrayList<>();
        for( int i = 0 ; i <arr.length() ; i ++){
            JSONObject obj = (JSONObject) arr.get(i);
            String id = obj.get("id").toString();
            String session_number = obj.get("session_number").toString();
            ret.add(new Session(new BigInteger(id) , Integer.parseInt(session_number)));
        }
        return ret;
    }
    private static ArrayList<Episode> getEpisodesFromJasonArray(JSONArray arr) throws JSONException {
        ArrayList<Episode> ret = new ArrayList<>();
        for( int i = 0 ; i <arr.length() ; i ++){
            JSONObject obj = (JSONObject) arr.get(i);
            String id = obj.get("id").toString();
            String episode_number = obj.get("episode_number").toString();
            ret.add(new Episode(new BigInteger(id), Integer.parseInt(episode_number)));
        }
        return ret;
    }

    private static Map<String,String> getKeyValueFromJason(JSONArray arr) throws JSONException {
        Map<String , String> ret = new HashMap<>();
        for( int i = 0 ; i < arr.length() ; i ++ ){
            JSONObject obj = (JSONObject) arr.get(i);
            String key = obj.get("key").toString();
            String value = obj.get("value").toString();
            ret.put(key,value);
        }
        return ret;
    }

    private static ArrayList<Actor> getActorsFromJasonArray(JSONArray arr) throws JSONException {
        ArrayList<Actor> ret = new ArrayList<>();
        for( int i = 0 ; i <arr.length() ; i ++){
            JSONObject obj = (JSONObject) arr.get(i);
            String id = obj.get("id").toString();
            String name = obj.get("name").toString();
            String image_path = obj.get("member_image_path").toString();
            ret.add(new Actor(new BigInteger(id) , name , image_path));
        }
        return ret;
    }

    private static ArrayList<Comment> getCommentsFromJasonArray(JSONArray arr) throws JSONException {
        ArrayList<Comment> ret = new ArrayList<>();
        for( int i = 0 ; i  < arr.length() ; i ++ ){
            JSONObject obj = (JSONObject) arr.get(i);
            String user_name =obj.get("user_name").toString();
            String comment = obj.get("comment").toString();
            String usr_photo_url = obj.get("usr_photo_url").toString();
            String date = obj.get("date").toString();
            ret.add(new Comment(user_name, usr_photo_url , date , comment));
        }
        return ret;
    }

    private static ArrayList<Server> getShowLinksFromJasonArray(JSONArray arr) throws JSONException {
        ArrayList<Server> ret = new ArrayList<>();
        for( int i = 0 ; i <arr.length() ; i ++){
            JSONObject obj = (JSONObject) arr.get(i);
            String server_name = (String) obj.get("server_name");
            String streaming_link = (String) obj.get("streaming_link");
            String downloding_lnik = (String) obj.get("downloading_link");
            String quality = (String)obj.get("quality");
            ret.add(new Server(server_name , streaming_link , downloding_lnik,quality));
        }
        return ret;
    }




    /*
    private static ArrayList<Movie> getLatestMovieObjectsFromJasonArray(JSONArray arr) throws JSONException {
        ArrayList<Movie> ret = new ArrayList<>();
        for( int i = 0 ; i < arr.length() ; i ++ ){
            JSONObject obj = (JSONObject) arr.get(i);
            String id =(String) obj.get("id");
            String name = (String) obj.get("name");
            String poster_path = (String) obj.get("poster_path");
            String description = (String) obj.get("description");
            ret.add(new Movie(new BigInteger(id) , name , poster_path , description , null));

        }
        return ret;
    }

     */

    /*
    private static ArrayList<BangTonTv> getBangtonShowsObjectsFromJasonArray(JSONArray arr) throws JSONException {
        ArrayList<BangTonTv> ret = new ArrayList<>();
        for( int i = 0 ; i < arr.length() ; i ++ ){
            JSONObject obj = (JSONObject) arr.get(i);
            String id =(String) obj.get("id");
            String name = (String) obj.get("name");
            String poster_path = (String) obj.get("poster_path");
            String description = (String) obj.get("description");
            ret.add(new BangTonTv(new BigInteger(id) , name , poster_path , description , null));

        }
        return ret;
    }

     */


    /*
    private static ArrayList<Dvd> getDvdsObjectsFromJasonArray(JSONArray arr) throws JSONException {
        ArrayList<Dvd> ret = new ArrayList<>();
        for( int i = 0 ; i < arr.length() ; i ++ ){
            JSONObject obj = (JSONObject) arr.get(i);
            String id =(String) obj.get("id");
            String name = (String) obj.get("name");
            String poster_path = (String) obj.get("poster_path");
            String description = (String) obj.get("description");
            ret.add(new Dvd(new BigInteger(id) , name , poster_path , description , null));

        }
        return ret;
    }

     */


/*
    private static ArrayList<Show> getShowObjectsFromJasonArray(JSONArray arr) throws JSONException {
        ArrayList<Show> ret = new ArrayList<>();
        for( int i = 0 ; i < arr.length() ; i ++ ){
            JSONObject obj = (JSONObject) arr.get(i);
            String id =(String) obj.get("id");
            String name = (String) obj.get("name");
            String poster_path = (String) obj.get("poster_path");
            String description = (String) obj.get("description");
            String type = (String) obj.get("type");
            ret.add(new Show(new BigInteger(id) , name , poster_path , description , null , type));

        }
        return ret;
    }


 */



    public static Object LoadeLatestShow(String link ) throws UnsupportedEncodingException, JSONException {
        ArrayList<GeneralShow> loadedMovies;
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(! is_null(result)) {
            loadedMovies = getMainScreenShowsObjectsFromJasonArray(result);
            if (loadedMovies.size() == 1)
                return loadedMovies.get(0);
            return loadedMovies;
        }
        return null;
    }

/*
    public static Object LoadeMovies(String link) throws UnsupportedEncodingException, JSONException {
        ArrayList<Movie> loadedMovies;
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            loadedMovies = getMovieObjectsFromJasonArray(result);
            if (loadedMovies.size() == 1)
                return loadedMovies.get(0);
            return loadedMovies;
        }
        return null;
    }

 */

    /*
    public static Object LoadShows(String link ) throws UnsupportedEncodingException, JSONException {
        ArrayList<Show> loadedShows;

        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(! is_null(result) ) {
            loadedShows = getShowObjectsFromJasonArray(result);
            return loadedShows;
        }
        return null;
    }


     */
    /*
    public static Object LoadSpicificShows(String link , String type) throws UnsupportedEncodingException, JSONException {
        ArrayList<Show> loadedShows;

        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeType(type).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(! is_null(result) ) {
            loadedShows = getShowObjectsFromJasonArray(result);
            return loadedShows;
        }
        return null;
    }

     */

    /*
    public static Object LoadDvds(String link ) throws UnsupportedEncodingException, JSONException {

        ArrayList<Dvd> loadedDvds;
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root", "").getQueryString();
        JSONArray result = useHttpRequest(link, post_string);
        if(!is_null(result)) {
            loadedDvds = getDvdsObjectsFromJasonArray(result);
            return loadedDvds;
        }
        return null;
    }

    public static Object LoadBangtonTv(String link ) throws UnsupportedEncodingException, JSONException {
        ArrayList<BangTonTv> loadedShows ;

        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            loadedShows = getBangtonShowsObjectsFromJasonArray(result);
            return loadedShows;
        }
        return null;
    }

     */

    public static Object LoadMainScreenShows(String link , String cat) throws UnsupportedEncodingException, JSONException {
        ArrayList<GeneralShow> loadedShows = null ;
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeCat(cat).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            loadedShows = getMainScreenShowsObjectsFromJasonArray(result );
            return loadedShows;
        }
        return null;
    }



    /*
    public static Object LoadeShowStreamingLinks(String link , String movieId) throws UnsupportedEncodingException, JSONException {
        ArrayList<VideoStreamingLink> loadesShowStreamingLinks;
        QueryBuilder builder = new QueryBuilder();
        String post_String = builder.EncodePermissions("root" , "").EncodeId(movieId).getQueryString();
        JSONArray result = useHttpRequest(link ,post_String);
        if(!is_null(result)) {
            loadesShowStreamingLinks = getShowStreamingLinksFromJasonArray(result);
            return loadesShowStreamingLinks;
        }
        return null;
    }
     */


    public static Object LoadMovieLinks(String link, String movieId) throws UnsupportedEncodingException, JSONException {
        ArrayList<Server> loadesShowServers ;
        QueryBuilder builder = new QueryBuilder();
        String post_String = builder.EncodePermissions("root" , "").EncodeId(movieId).getQueryString();
        JSONArray result = useHttpRequest(link ,post_String);
        if(!is_null(result)) {
            loadesShowServers = getShowLinksFromJasonArray(result);
            return loadesShowServers;
        }
        return null;
    }
    public static Object LoadeEpisodeLinks(String link, String seriesId,String sessionId , String episodeId) throws UnsupportedEncodingException, JSONException {
        ArrayList<Server> loadesShowServers ;
        QueryBuilder builder = new QueryBuilder();
        String post_String = builder.EncodePermissions("root" , "").EncodeId(seriesId).EncodeIdTwo(sessionId).EncodeIdThree(episodeId).getQueryString();
        JSONArray result = useHttpRequest(link ,post_String);
        if(!is_null(result)) {
            loadesShowServers = getShowLinksFromJasonArray(result);
            return loadesShowServers;
        }
        return null;
    }

    public static Object LoadeTags(String link, String movieId) throws UnsupportedEncodingException, JSONException {
        ArrayList<String> loadedTags ;
        QueryBuilder builder = new QueryBuilder();
        String post_String = builder.EncodePermissions("root" , "").EncodeId(movieId).getQueryString();
        JSONArray result = useHttpRequest(link ,post_String);
        if(!is_null(result)) {
            loadedTags = getTagsFromJasonArray(result);
            return loadedTags;
        }
        return null;
    }

    public static Object LoadeActors(String link, String movieId) throws UnsupportedEncodingException, JSONException {
        ArrayList<Actor> loadedActors ;
        QueryBuilder builder = new QueryBuilder();
        String post_String = builder.EncodePermissions("root" , "").EncodeId(movieId).getQueryString();
        JSONArray result = useHttpRequest(link ,post_String);
        if(!is_null(result)) {
            loadedActors = getActorsFromJasonArray(result);
            return loadedActors;
        }
        return null;
    }


    public static Object LoadeSessions(String link, String seriesId) throws UnsupportedEncodingException, JSONException {
        ArrayList<Session> loadedSessions ;
        QueryBuilder builder = new QueryBuilder();
        String post_String = builder.EncodePermissions("root" , "").EncodeId(seriesId).getQueryString();
        JSONArray result = useHttpRequest(link ,post_String);
        if(!is_null(result)) {
            loadedSessions = getSessionsFromJasonArray(result);
            return loadedSessions;
        }
        return null;
    }

    public static Object LoadeEpisodes(String link, String seriesId, String sessionId) throws JSONException, UnsupportedEncodingException {
        ArrayList<Episode> loadedEpisodes ;
        QueryBuilder builder = new QueryBuilder();
        String post_String = builder.EncodePermissions("root" , "").EncodeId(seriesId).EncodeIdTwo(sessionId).getQueryString();
        JSONArray result = useHttpRequest(link ,post_String);
        if(!is_null(result)) {
            loadedEpisodes = getEpisodesFromJasonArray(result);
            return loadedEpisodes;
        }
        return null;
    }

    public static Object LoadeNonSeriesLink(String link, String non_series_show_id,String server_id, String cat) throws UnsupportedEncodingException, JSONException {
        String  loadedLink ;
        QueryBuilder builder = new QueryBuilder();
        String post_String = builder.EncodePermissions("root" , "").EncodeId(non_series_show_id).EncodeIdFour(server_id).EncodeCat(cat).getQueryString();
        JSONArray result = useHttpRequest(link ,post_String);
        if(!is_null(result)) {
            loadedLink = getLinkJasonArray(result , cat);
            return loadedLink;
        }
        return null;
    }

    public static Object LoadeActors(String link) throws UnsupportedEncodingException, JSONException {
        ArrayList<Actor> loadedActors ;
        QueryBuilder builder = new QueryBuilder();
        String post_String = builder.EncodePermissions("root" , "").getQueryString();
        JSONArray result = useHttpRequest(link ,post_String);
        if(!is_null(result)) {
            loadedActors = getActorsFromJasonArray(result);
            return loadedActors;
        }
        return null;
    }

    public static Object LoadeActorWorks(String link, String actorId) throws UnsupportedEncodingException, JSONException {
        ArrayList<GeneralShow> loadedShows = null ;
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeId(actorId).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            loadedShows = getMainScreenShowsObjectsFromJasonArray(result );
            return loadedShows;
        }
        return null;
    }

    public static Object LoadSearchResults(String link, String name) throws UnsupportedEncodingException, JSONException {
        ArrayList<GeneralShow> loadedShows = null ;
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeName(name).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            loadedShows = getMainScreenShowsObjectsFromJasonArray(result );
            return loadedShows;
        }
        return null;
    }

    public static Object LoadOurWorks(String link) throws UnsupportedEncodingException, JSONException {
        ArrayList<GeneralShow> loadedShows = null ;
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            loadedShows = getMainScreenShowsObjectsFromJasonArray(result );
            return loadedShows;
        }
        return null;
    }

    public static Object LoadeUserList(String link , String userid) throws UnsupportedEncodingException, JSONException {
        ArrayList<GeneralShow> loadedShows = null ;
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeId(userid).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            loadedShows = getMainScreenShowsObjectsFromJasonArray(result );
            return loadedShows;
        }
        return null;
    }

    public static void InsertUser(String link , String email , String userName , String user_photo_url) throws UnsupportedEncodingException, JSONException {
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeName(email).EncodeNameTow(userName).EncodeUrl(user_photo_url).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)){
            JSONObject obj = (JSONObject) result.get(0);
            MainActivity.userId = new BigInteger(String.valueOf(obj.get("id")));
        }
    }



    public static Object CheckGeneralShowInListOrNot(String link, String showId, String userid) throws UnsupportedEncodingException, JSONException {
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeId(userid).EncodeIdTwo(showId).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            JSONObject obj = (JSONObject) result.get(0);
            BigInteger ret = new BigInteger(String.valueOf(obj.get("id")));
            return ret;
        }
        return new BigInteger("0");
    }

    public static Object InvrseListStatus(String link, String showId, String userid) throws UnsupportedEncodingException, JSONException {
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeId(userid).EncodeIdTwo(showId).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            JSONObject obj = (JSONObject) result.get(0);
            BigInteger ret = new BigInteger(String.valueOf(obj.get("id")));
            return ret;
        }
        return new BigInteger("0");
    }


    public static Object getKeyValueLinks(String link) throws UnsupportedEncodingException, JSONException {
        QueryBuilder builder = new QueryBuilder();
        Map<String , String> loadedLinks ;
        String post_string = builder.EncodePermissions("root" , "").getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            loadedLinks = getKeyValueFromJason(result);
            return loadedLinks;
        }
        return null;
    }

    public static Object getApplicationVersion(String link) throws UnsupportedEncodingException, JSONException {
        QueryBuilder builder = new QueryBuilder();
        ArrayList<String>loadedVersion ;
        String post_string = builder.EncodePermissions("root" , "").getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            loadedVersion = new ArrayList<>();
            JSONObject obj = (JSONObject) result.get(0);
            String version = String.valueOf(obj.get("key"));
            String applink = String.valueOf(obj.get("value"));
            loadedVersion.add(version);     loadedVersion.add(applink);
            return loadedVersion;
        }
        return null;
    }

    public static String RegisterShowInUserHistory(String link, String user_id, String general_show_id) throws UnsupportedEncodingException, JSONException {
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeId(user_id).EncodeIdTwo(general_show_id).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            JSONObject obj = (JSONObject) result.get(0);
            return String.valueOf(obj.get("id"));
        }
        return null;
    }

    public static Object LoadReviews(String link, String general_show_id) throws UnsupportedEncodingException, JSONException {
        ArrayList<Comment> loadedComments = null ;
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeId(general_show_id).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            loadedComments = getCommentsFromJasonArray(result );
            return loadedComments;
        }
        return null;
    }

    public static void WriteShowRatting(String link, String user_id, String show_id , String ratting) throws UnsupportedEncodingException {
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeId(user_id).EncodeIdTwo(show_id).EncodeValue(ratting).getQueryString();
        JSONArray result= useHttpRequest(link,post_string);
    }


    public static Object InsertReview(String link, String user_id, String show_id, String comment) throws UnsupportedEncodingException, JSONException {
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeId(user_id).EncodeIdTwo(show_id).EncodeValue(comment).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            JSONObject obj = (JSONObject) result.get(0);
            return String.valueOf(obj.get("id"));
        }
        return null;
    }

    public static Object LoadEpisodeComments(String link, String show_id, String session_id, String episode_id) throws UnsupportedEncodingException, JSONException {
        ArrayList<Comment> loadedComments = null ;
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeId(show_id).EncodeIdTwo(session_id).EncodeIdThree(episode_id).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            loadedComments = getCommentsFromJasonArray(result );
            return loadedComments;
        }
        return null;
    }

    public static Object InsertEpisodeComment(String link, String user_id, String show_id, String session_id, String episode_id, String comment) throws UnsupportedEncodingException, JSONException {
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeId(user_id).EncodeIdTwo(show_id).EncodeIdThree(session_id).EncodeIdFour(episode_id).EncodeValue(comment).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            JSONObject obj = (JSONObject) result.get(0);
            return String.valueOf(obj.get("id"));
        }
        return null;
    }

    public static Object GetUserRating(String link, String user_id, String show_id) throws UnsupportedEncodingException, JSONException {
        QueryBuilder builder = new QueryBuilder();
        String post_string = builder.EncodePermissions("root" , "").EncodeId(user_id).EncodeIdTwo(show_id).getQueryString();
        JSONArray result = useHttpRequest(link,post_string);
        if(!is_null(result)) {
            JSONObject obj = (JSONObject) result.get(0);
            return String.valueOf(obj.get("id"));
        }
        return null;
    }
}
