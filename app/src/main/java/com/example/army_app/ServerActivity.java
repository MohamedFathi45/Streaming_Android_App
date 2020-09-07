package com.example.army_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.army_app.Loader_Tasks.EpisodeLinksLoader;

import java.util.ArrayList;
import java.util.Objects;

public class ServerActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object> , ServerShowAdapter.ListItemClickListener , AvilableQualityAdapter.ListItemClickListener{
    private static final String SERVER_LOAD_EPISODE_LINKS_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_episode_links.php";
    ArrayList<Server> streamingServers = null;
    LinearLayout results_layout;
    LinearLayout loading_layout;
    LinearLayout no_results_layout;
    RecyclerView serverShowRecyclerView;
    Dialog qualitiesDialog;
    RecyclerView avilable_qualitiesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        results_layout = findViewById(R.id.id_layout_watch);
        loading_layout = findViewById(R.id.id_progress_bar_linear_layout);
        serverShowRecyclerView = findViewById(R.id.id_recyclerView_servers);
        no_results_layout = findViewById(R.id.id_no_results_layout);
        qualitiesDialog = new Dialog(this);
        Objects.requireNonNull(qualitiesDialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        setTitle(getString(R.string.servers));
        Intent intent = getIntent();
        String show_id = intent.getStringExtra(MainActivity.ID);
        String session_id = intent.getStringExtra(SeriesDetailsActivity.IDTWO);
        String episode_id = intent.getStringExtra(SeriesDetailsActivity.IDTHREE);
        getEpisodeLinksFromServer(show_id , session_id , episode_id);
    }






    private void getEpisodeLinksFromServer(String show_id , String session_id , String episode_id) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_LOAD_EPISODE_LINKS_API);
        queryBundle.putString(MainActivity.ID , show_id);
        queryBundle.putString(SeriesDetailsActivity.IDTWO , session_id);
        queryBundle.putString(SeriesDetailsActivity.IDTHREE , episode_id);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_EPISODE_LINKS, queryBundle, this);
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader res = null;
        if(id == MainActivity.LOADER_FOR_LOAD_EPISODE_LINKS){
            results_layout.setVisibility(View.GONE);
            no_results_layout.setVisibility(View.GONE);
            loading_layout.setVisibility(View.VISIBLE);
            res = new EpisodeLinksLoader(this , args.getString(MainActivity.SERVER_LINK) , args.getString(MainActivity.ID) , args.getString(SeriesDetailsActivity.IDTWO) , args.getString(SeriesDetailsActivity.IDTHREE));
        }
        return res;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == MainActivity.LOADER_FOR_LOAD_EPISODE_LINKS) {
            if (data != null) {
                no_results_layout.setVisibility(View.GONE);
                results_layout.setVisibility(View.VISIBLE);
                loading_layout.setVisibility(View.GONE);
                streamingServers = (ArrayList<Server>) data;
                ServerShowAdapter serversAdapter = new ServerShowAdapter( this, streamingServers );
                serverShowRecyclerView.setAdapter(serversAdapter);
                serverShowRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            }
            else{
                no_results_layout.setVisibility(View.VISIBLE);
                results_layout.setVisibility(View.GONE);
                loading_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }

    @Override
    public void onListItemClick(int pos) {
        ArrayList<Server> choosendServers = new ArrayList<>();
        for( int i = 0 ; i < streamingServers.size() ; i++ ){
            if(streamingServers.get(i).getServerName().equals(streamingServers.get(pos).getServerName()))
                choosendServers.add(streamingServers.get(i));
        }
        Log.d("AppDebugSpace" , String.valueOf(choosendServers.size()));
        showPopUpServersDialog(choosendServers);
    }

    public void showPopUpServersDialog(ArrayList<Server> choosendServers){
        qualitiesDialog.setContentView(R.layout.qualities_pop_up);
        //View view;
        //LayoutInflater inflater = (LayoutInflater)   this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        //view = inflater.inflate(R.layout.qualities_pop_up, null);
        avilable_qualitiesRecyclerView = qualitiesDialog.findViewById(R.id.id_rv_qualities_pop_up);
        AvilableQualityAdapter avilableQualityAdapter = new AvilableQualityAdapter( this , choosendServers , this );

        avilable_qualitiesRecyclerView.setAdapter(avilableQualityAdapter);

        avilable_qualitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        qualitiesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        qualitiesDialog.show();
    }

    @Override
    public void onListItemClick(Server clicked, String order) {
        if(order.equals("watch")){
            try {

                String link = clicked.getStreaming_link();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }catch (Exception e){
                Toast.makeText(this , "Error" , Toast.LENGTH_SHORT).show();
            }
        }
        else if (order.equals("download")){
            try {

                String link = clicked.getDownloading_link();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }catch (Exception e){
                Toast.makeText(this , "Error" , Toast.LENGTH_SHORT).show();
            }
        }
    }
}
