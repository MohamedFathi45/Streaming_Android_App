package com.example.army_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.army_app.Loader_Tasks.EpisodesLoader;

import java.math.BigInteger;
import java.util.ArrayList;

public class EpisodesActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object> , EpisodeAdapter.ListItemClickListener , EpisodeAdapter.CommentItemClickListener{

    RecyclerView episodes_rv;
    LinearLayout results_layout;
    LinearLayout loading_layout;
    LinearLayout no_results_layout;
    GeneralShow show= null;
    String session_id;
    private static final String SERVER_LOAD_EPISODS_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_episodes.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);
        episodes_rv = findViewById(R.id.id_recyclerView_episodes);
        results_layout = findViewById(R.id.id_results_layout);
        loading_layout = findViewById(R.id.id_progress_bar_linear_layout);
        no_results_layout = findViewById(R.id.id_no_results_layout);
        Intent intent = getIntent();
        String show_id = intent.getStringExtra(MainActivity.ID);
        session_id = intent.getStringExtra(SeriesDetailsActivity.IDTWO);
        show = Inventory.getInstance().getShow(new BigInteger(show_id));
        setTitle(show.getName()+" : " + getString(R.string.episodes));
        getEpisodesFromServer(show_id , session_id);
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader res =  null;
        if(id == MainActivity.LOADER_FOR_LOAD_EPISODES){
            loading_layout.setVisibility(View.VISIBLE);
            results_layout.setVisibility(View.GONE);
            no_results_layout.setVisibility(View.GONE);
            res = new EpisodesLoader(this , args.getString(MainActivity.SERVER_LINK), args.getString(MainActivity.ID) , args.getString(SeriesDetailsActivity.IDTWO) );
        }
        return res;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id= loader.getId();
        if(id == MainActivity.LOADER_FOR_LOAD_EPISODES){
            if (data != null) {
                no_results_layout.setVisibility(View.GONE);
                loading_layout.setVisibility(View.GONE);
                results_layout.setVisibility(View.VISIBLE);
                ArrayList<Episode> episodes = (ArrayList<Episode>)data;
                EpisodeAdapter episodeAdapter = new EpisodeAdapter(this, this , episodes);
                episodes_rv.setAdapter(episodeAdapter);
                episodes_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            }
            else{
                no_results_layout.setVisibility(View.VISIBLE);
                loading_layout.setVisibility(View.GONE);
                results_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }

    private void getEpisodesFromServer(String tv_showId , String session_id) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_LOAD_EPISODS_API);
        queryBundle.putString(MainActivity.ID , tv_showId);
        queryBundle.putString(SeriesDetailsActivity.IDTWO , session_id);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        //  Get our Loader by calling getLoader and passing the ID we specified
        Loader<Object> loadEpisodes = loaderManager.getLoader(MainActivity.LOADER_FOR_LOAD_EPISODES);
        //  If the Loader was null, initialize it. Else, restart it.
        if (loadEpisodes == null) {
            loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_EPISODES, queryBundle, this);
        } else {
            loaderManager.restartLoader(MainActivity.LOADER_FOR_LOAD_EPISODES, queryBundle, this);
        }
    }

    @Override
    public void onListItemClick(Episode episode) {
        Intent intent = new Intent(this , ServerActivity.class);
        intent.putExtra(MainActivity.ID , String.valueOf(show.getId()));
        intent.putExtra(SeriesDetailsActivity.IDTWO , String.valueOf(session_id));
        intent.putExtra(SeriesDetailsActivity.IDTHREE , String.valueOf(episode.getId()));
        startActivity(intent);
    }

    @Override
    public void onCommentItemClick(Episode episode) {
        Intent intent = new Intent(this , CommentActivity.class);
        intent.putExtra(MainActivity.ID , String.valueOf(show.getId()));
        intent.putExtra(SeriesDetailsActivity.IDTWO , String.valueOf(session_id));
        intent.putExtra(MainActivity.NAME , String.valueOf(episode.getEpisode_number()));
        intent.putExtra(SeriesDetailsActivity.IDTHREE , String.valueOf(episode.getEpisode_number()));
        startActivity(intent);
    }
}
