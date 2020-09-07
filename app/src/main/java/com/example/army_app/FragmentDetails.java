package com.example.army_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.army_app.Loader_Tasks.CheckTheListLoader;
import com.example.army_app.Loader_Tasks.InverseListStatusLoader;
import com.example.army_app.Loader_Tasks.MainActorsLoader;
import com.example.army_app.Loader_Tasks.ShowTagsLoader;
import com.example.army_app.Loader_Tasks.UserRatingLoader;
import com.example.army_app.utilities.DataBaseIntentService;
import com.example.army_app.utilities.DataBaseTasks;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class FragmentDetails extends Fragment implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object> , ActorAdapter.ListItemClickListener , TagsAdapter.ListItemClickListener{
    View view;
    ScrollView results_layout;
    RatingBar ratingBar;
    LinearLayout progress_bar;
    LinearLayout rating_layout;
    int operationCount = 0;
    int numOfOperations = 0;
    RecyclerView mainActorsRecyclerView;
    RecyclerView tagsRecyclerView;
    ImageView listImage;
    ImageView details_image;
    GeneralShow show;

    TextView show_type;
    TextView details_descritpion;
    TextView show_name;
    TextView rattingTv;
    TextView num_of_voters_tv;
    TextView year_of_production;
    TextView country;
    public static final String SERVER_LOAD_TAGS_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_show_tags.php";
    public static final String SERVER_LOAD_MAIN_ACTORS_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_main_role_members.php";
    public static final String SERVER_LOAD_USER_RATING_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_usr_ratting.php";
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        mainActorsRecyclerView = getView().findViewById(R.id.id_rv_main_role_actors);
        tagsRecyclerView = getView().findViewById(R.id.id_recyclerView_tags);
        show_type = getView().findViewById(R.id.id_details_type);
        listImage = getView().findViewById(R.id.id_is_list_added);
        rating_layout = getView().findViewById(R.id.details_ratting_linear_layout);
        ratingBar=getView().findViewById(R.id.details_rating_bar);
        details_descritpion = getView().findViewById(R.id.id_details_description);
        show_name = getView().findViewById(R.id.id_details_name);
        rattingTv = getView().findViewById(R.id.id_details_show_rating);
        num_of_voters_tv = getView().findViewById(R.id.id_number_of_voters);
        year_of_production = getView().findViewById(R.id.id_details_year_of_production);
        country = getView().findViewById(R.id.id_details_country);
        details_image = getView().findViewById(R.id.id_details_image_poster);
        progress_bar = getView().findViewById(R.id.id_progress_bar_linear_layout);
        results_layout = getView().findViewById(R.id.id_results_layout);
        operationCount = 0;
        numOfOperations = 2;
        getActivity().setTitle(show.getName());
        activateDetailsLayout();


        listImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.account != null && !String.valueOf(MainActivity.userId).equals("0"))
                    InverseTheListStatusInServer(String.valueOf(MainActivity.userId) ,String.valueOf(show.getId()));
                else{
                    Toast.makeText(getActivity() , getString(R.string.must_sign_in) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int ratting = (int) v;
                Intent writeRattingIntent = new Intent(getActivity() , DataBaseIntentService.class);
                String orderString = DataBaseTasks.SET_SHOW_RATTING_IN_DATABASE+","+String.valueOf(MainActivity.userId)+","+String.valueOf(show.getId())+","+String.valueOf(ratting);
                writeRattingIntent.setAction(orderString);
                getActivity().startService(writeRattingIntent);
            }
        });
    }

    public FragmentDetails(String movie_id) {
        show = Inventory.getInstance().getShow(new BigInteger(movie_id));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.details_fragment,container , false);
        return view;
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader res = null;
        switch (id){
            case MainActivity.LOADER_FOR_LOAD_SHOW_TAGS:
                res = new ShowTagsLoader(getActivity(),args.getString(MainActivity.SERVER_LINK) , args.getString(MainActivity.ID) );
                break;
            case MainActivity.LOADER_FOR_LOAD_MAIN_ACTORS:
                res = new MainActorsLoader(getActivity(),args.getString(MainActivity.SERVER_LINK) , args.getString(MainActivity.ID) );
                break;
            case MainActivity.LOADER_FOR_INVERSE_LIST_STATUS:
                res = new InverseListStatusLoader(getActivity() , args.getString(MainActivity.SERVER_LINK),args.getString(MainActivity.ID),args.getString(SeriesDetailsActivity.IDTWO) );
                break;
            case MainActivity.LOADER_FOR_CHECK_LIST:
                res = new CheckTheListLoader(getActivity() , args.getString(MainActivity.SERVER_LINK),args.getString(MainActivity.ID),args.getString(SeriesDetailsActivity.IDTWO) );
                break;
            case MainActivity.LOADER_FOR_LOAD_USER_RATING:
                res = new UserRatingLoader(getActivity(),args.getString(MainActivity.SERVER_LINK) , args.getString(MainActivity.ID) , args.getString(SeriesDetailsActivity.IDTWO));
        }
        return res;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == MainActivity.LOADER_FOR_LOAD_SHOW_TAGS){
            if (data != null) {
                ArrayList<String> tags = (ArrayList<String>)data;
                TagsAdapter tagsAdapter = new TagsAdapter(this::onListItemClick, tags);     //if problme this::onListItemClick
                tagsRecyclerView.setAdapter(tagsAdapter);
                tagsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                //movie.getSpec().getProperties().put("tags" , tags);
            }
            incrementOperationCount();
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_MAIN_ACTORS){
            if (data != null) {
                ArrayList<Actor> actors = (ArrayList<Actor>)data;
                ActorAdapter actorAdapter = new ActorAdapter(this::onListItemClick , actors);
                mainActorsRecyclerView.setAdapter(actorAdapter);
                mainActorsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            }
            incrementOperationCount();
        }
        else if(id == MainActivity.LOADER_FOR_CHECK_LIST){
            if (!String.valueOf(data).equals("0")) {
                listImage.setImageResource(R.drawable.ic_check_gray_24dp);
            }
            else{
                listImage.setImageResource(R.drawable.ic_playlist_add_gray_24dp);
            }
            incrementOperationCount();
        }
        else if(id == MainActivity.LOADER_FOR_INVERSE_LIST_STATUS){
            if(!String.valueOf(data).equals("0")){          // non zero means added
                listImage.setImageResource(R.drawable.ic_check_gray_24dp);
                Toast.makeText(getActivity(), R.string.added_to_list, Toast.LENGTH_SHORT).show();
            }
            else {
                listImage.setImageResource(R.drawable.ic_playlist_add_gray_24dp);
                Toast.makeText(getActivity(),R.string.removed_from_list , Toast.LENGTH_SHORT).show();
            }
            stopLoader(id);
        }
        else if(id == MainActivity.LOADER_FOR_LOAD_USER_RATING){
            Log.d("AppDebugSpace" , (String) data);
            if (data != null) {
                String ret = (String) data;
                ratingBar.setRating(Integer.parseInt(ret));
            }
            else
                ratingBar.setRating(0);
            incrementOperationCount();

            stopLoader(id);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }
    public void activateDetailsLayout() {
        progress_bar.setVisibility(View.VISIBLE);
        results_layout.setVisibility(View.GONE);
        if(MainActivity.account != null) {
            rating_layout.setVisibility(View.VISIBLE);
            numOfOperations = numOfOperations +2;   // checkList and rating bar
            getIfShowInList(String.valueOf(show.getId()), String.valueOf(MainActivity.userId));
            getUserRating(String.valueOf(MainActivity.userId) ,String.valueOf(show.getId()));
            Intent writeIntoHistory = new Intent(getActivity()  , DataBaseIntentService.class);
            String orderString = DataBaseTasks.SET_HISTORY_DATABASE+","+String.valueOf(MainActivity.userId)+","+String.valueOf(show.getId());
            writeIntoHistory.setAction(orderString);
            getActivity().startService(writeIntoHistory);
        }
        else{
            rating_layout.setVisibility(View.GONE);
        }
        getShowTagsFromServer(String.valueOf(show.getId()));
        getMainActorsFromServer(String.valueOf(show.getId()));
        setMovieDetails();


    }
    private void setMovieDetails() {
        BigDecimal totalRating = new BigDecimal(String.valueOf(show.getSpec().getProperties().get("rating")));
        BigDecimal numberOfVoters = new BigDecimal(String.valueOf(show.getSpec().getProperties().get("num_of_voters")));
        //BigDecimal mul = totalRating.multiply(numberOfVoters,new MathContext(4, RoundingMode.HALF_UP));
        BigDecimal res;
        if(!String.valueOf(numberOfVoters).equals("0")) {
            res = totalRating.divide(numberOfVoters, new MathContext(4, RoundingMode.HALF_UP)).setScale(1, RoundingMode.HALF_UP);
        }
        else {
            res = numberOfVoters;
        }
        GeneralShow.assignCat(show.getCat_id() , getActivity() , show_type);
        String votes = "( "+ String.valueOf(show.getSpec().getProperties().get("num_of_voters")) +"  votes)";

        String ratingString = String.valueOf(res);
        if(ratingString.charAt(ratingString.length()-1) =='0'  ){
            int ind = ratingString.indexOf(".");
            if (ind != -1)
            {
                ratingString= ratingString.substring(0 , ind);
            }
        }
        ratingString+="/5";
        Picasso.get().load(MainActivity.SERVER_ROOT_LINK_URL+"/"+show.getPosterImagePath()).fit().into(details_image);

        details_descritpion.setText(String.valueOf(show.getSpec().getProperty("description")));
        show_name.setText(show.getName());
        rattingTv.setText(ratingString);
        num_of_voters_tv.setText(votes);
        year_of_production.setText(String.valueOf(show.getSpec().getProperty("year_of_production")));
        country.setText(show.getCountry());
    }
    private void getMainActorsFromServer(String movieId) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_LOAD_MAIN_ACTORS_API);
        queryBundle.putString(MainActivity.ID , movieId);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_MAIN_ACTORS, queryBundle, this);
    }

    private void getShowTagsFromServer(String id) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_LOAD_TAGS_API);
        queryBundle.putString(MainActivity.ID , id);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_SHOW_TAGS, queryBundle, this);
    }
    private void getIfShowInList(String movieId ,String userId){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.ID , userId);
        queryBundle.putString(SeriesDetailsActivity.IDTWO ,  movieId);
        queryBundle.putString(MainActivity.SERVER_LINK , MainActivity.SERVER_CHECK_LIST_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        //  Get our Loader by calling getLoader and passing the ID we specified
        Loader<Object> loadcheck = loaderManager.getLoader(MainActivity.LOADER_FOR_CHECK_LIST);
        //  If the Loader was null, initialize it. Else, restart it.
        if (loadcheck == null) {
            loaderManager.initLoader(MainActivity.LOADER_FOR_CHECK_LIST, queryBundle, this);
        } else {
            loaderManager.restartLoader(MainActivity.LOADER_FOR_CHECK_LIST, queryBundle, this);
        }
    }

    private void getUserRating(String user_id ,String show_id){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.ID , user_id);
        queryBundle.putString(SeriesDetailsActivity.IDTWO ,  show_id);
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_LOAD_USER_RATING_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        //  Get our Loader by calling getLoader and passing the ID we specified
        Loader<Object> loadRating = loaderManager.getLoader(MainActivity.LOADER_FOR_LOAD_USER_RATING);
        //  If the Loader was null, initialize it. Else, restart it.
        if (loadRating == null) {
            loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_USER_RATING, queryBundle, this);
        } else {
            loaderManager.restartLoader(MainActivity.LOADER_FOR_LOAD_USER_RATING, queryBundle, this);
        }
    }

    private void InverseTheListStatusInServer(String userId , String moveiId ){    // if add then add it , if rem then remove it

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(MainActivity.SERVER_LINK, MainActivity.SERVER_INVERSE_LIST_STATUS_API);
        queryBundle.putString(MainActivity.ID , userId);
        queryBundle.putString(SeriesDetailsActivity.IDTWO , moveiId);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        //  Get our Loader by calling getLoader and passing the ID we specified
        Loader<Object> loadInverseListStatus = loaderManager.getLoader(MainActivity.LOADER_FOR_INVERSE_LIST_STATUS);
        //  If the Loader was null, initialize it. Else, restart it.
        if (loadInverseListStatus == null) {
            loaderManager.initLoader(MainActivity.LOADER_FOR_INVERSE_LIST_STATUS, queryBundle, this);
        } else {
            loaderManager.restartLoader(MainActivity.LOADER_FOR_INVERSE_LIST_STATUS, queryBundle, this);
        }
    }
    void incrementOperationCount() {
        synchronized (this) {
            operationCount = operationCount + 1;
            if(operationCount >= numOfOperations){
                progress_bar.setVisibility(View.GONE);
                results_layout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onListItemClick(Actor ac) {
        String id = String.valueOf(ac.getId());
        Intent intent = new Intent(getActivity() , ActorWorks.class);
        intent.putExtra(MainActivity.ID , id);
        intent.putExtra(MainActivity.NAME , ac.getName());
        startActivity(intent);
    }

    @Override
    public void onListItemClick(String tag) {

    }
    void stopLoader(int id) {
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.destroyLoader(id);
    }
}
