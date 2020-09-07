package com.example.army_app;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.army_app.Loader_Tasks.MovieLinksLoader;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentWatch extends Fragment implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object>  , ServerShowAdapter.ListItemClickListener , AvilableQualityAdapter.ListItemClickListener{
    View view;
    LinearLayout loading_layout;
    LinearLayout results_layout;
    LinearLayout no_results_layout;
    ArrayList<Server> streamingServers;
    Dialog qualitiesDialog;
    RecyclerView avilable_qualitiesRecyclerView;
    private static final String SERVER_LOAD_MOVIE_LINKS_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_movie_links.php";
    RecyclerView serverShowRecyclerView;
    String movie_id;
    public FragmentWatch(String id) {
        movie_id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.watch_fragment,container , false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        serverShowRecyclerView = getView().findViewById(R.id.id_recyclerView_servers);
        loading_layout = getView().findViewById(R.id.id_progress_bar_linear_layout);
        results_layout = getView().findViewById(R.id.id_layout_watch);
        no_results_layout = getView().findViewById(R.id.id_no_results_layout);
        qualitiesDialog = new Dialog(getActivity());
        Objects.requireNonNull(qualitiesDialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        getMovieLinks(movie_id);
    }

    private void getMovieLinks(String id) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_LOAD_MOVIE_LINKS_API);
        queryBundle.putString(MainActivity.ID , id);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_MOVIE_LINKS, queryBundle, this);
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader res = null;
        if(id == MainActivity.LOADER_FOR_LOAD_MOVIE_LINKS) {
            loading_layout.setVisibility(View.VISIBLE);
            results_layout.setVisibility(View.GONE);
            no_results_layout.setVisibility(View.GONE);
            res = new MovieLinksLoader(getActivity(), args.getString(MainActivity.SERVER_LINK), args.getString(MainActivity.ID));
        }

       return res;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == MainActivity.LOADER_FOR_LOAD_MOVIE_LINKS) {
            if (data != null) {
                loading_layout.setVisibility(View.GONE);
                results_layout.setVisibility(View.VISIBLE);
                no_results_layout.setVisibility(View.GONE);
                streamingServers = (ArrayList<Server>) data;
                ServerShowAdapter serversAdapter = new ServerShowAdapter(this, streamingServers);
                serverShowRecyclerView.setAdapter(serversAdapter);
                serverShowRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            }
            else{
                loading_layout.setVisibility(View.GONE);
                results_layout.setVisibility(View.GONE);
                no_results_layout.setVisibility(View.VISIBLE);
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
        AvilableQualityAdapter avilableQualityAdapter = new AvilableQualityAdapter( this , choosendServers , getActivity() );

        avilable_qualitiesRecyclerView.setAdapter(avilableQualityAdapter);

        avilable_qualitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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
                Toast.makeText(getActivity() , "Error" , Toast.LENGTH_SHORT).show();
            }
        }
        else if (order.equals("download")){
            try {

                String link = clicked.getDownloading_link();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }catch (Exception e){
                Toast.makeText(getActivity() , "Error" , Toast.LENGTH_SHORT).show();
            }
        }
    }
}
