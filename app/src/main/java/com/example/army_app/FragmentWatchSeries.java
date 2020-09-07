package com.example.army_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.army_app.Loader_Tasks.SessionsLoader;

import java.math.BigInteger;
import java.util.ArrayList;

public class FragmentWatchSeries extends Fragment implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object> , GeneralShowAdapter.ListItemClickListener{

    View view;
    RecyclerView sessionsRecyclerView;
    LinearLayout results_layout;
    LinearLayout no_results_layout;
    LinearLayout progress_bar_layout;
    GeneralShow show = null;

    private static final String SERVER_LOAD_SESSIONS_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_sessions.php";

    public FragmentWatchSeries(String id) {
        show = Inventory.getInstance().getShow(new BigInteger(id));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.watch_series_fragment,container , false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sessionsRecyclerView = getView().findViewById(R.id.id_recyclerView_sessions);
        results_layout = getView().findViewById(R.id.id_layout_watch);
        progress_bar_layout = getView().findViewById(R.id.id_progress_bar_linear_layout);
        no_results_layout = getView().findViewById(R.id.id_no_results_layout);
        getSessionsFromServer(String.valueOf(show.getId()));
    }

    private void getSessionsFromServer(String tv_showId) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_LOAD_SESSIONS_API);
        queryBundle.putString(MainActivity.ID , tv_showId);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_SESSIONS, queryBundle, this);

    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader res =  null;
        if(id == MainActivity.LOADER_FOR_LOAD_SESSIONS){
            no_results_layout.setVisibility(View.GONE);
            results_layout.setVisibility(View.GONE);
            progress_bar_layout.setVisibility(View.VISIBLE);
            res = new SessionsLoader(getActivity() , args.getString(MainActivity.SERVER_LINK), args.getString(MainActivity.ID) );
        }
        return res;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == MainActivity.LOADER_FOR_LOAD_SESSIONS){
            if (data != null) {
                no_results_layout.setVisibility(View.GONE);
                results_layout.setVisibility(View.VISIBLE);
                progress_bar_layout.setVisibility(View.GONE);
                ArrayList<Session> ret = (ArrayList<Session>)data;
                ArrayList<GeneralShow> sessions = new ArrayList<>();
                if(ret.size() ==1) {
                    sessions.add(new GeneralShow(new BigInteger(String.valueOf(ret.get(0).getId())), 2, getString(R.string.episodes), show.getPosterImagePath(), "non", null)); }
                else{
                    for (int i = 0; i < ret.size(); i++)
                        sessions.add(new GeneralShow(new BigInteger(String.valueOf(ret.get(i).getId())), 2, getString(R.string.session)+": " +String.valueOf(ret.get(i).getSession_number()), show.getPosterImagePath(), "non", null));
                }
                GeneralShowAdapter adapter = new GeneralShowAdapter(this , sessions , "");
                sessionsRecyclerView.setAdapter(adapter);
                sessionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            }
            else{
                no_results_layout.setVisibility(View.VISIBLE);
                results_layout.setVisibility(View.GONE);
                progress_bar_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }

    @Override
    public void onListItemClick(GeneralShow pressed_show, String rvDefiningString) {
        String sessionId = String.valueOf(pressed_show.getId());
        String show_id = String.valueOf(show.getId());
        Intent intent = new Intent(getActivity() , EpisodesActivity.class);
        intent.putExtra(MainActivity.ID , show_id);
        intent.putExtra(SeriesDetailsActivity.IDTWO , sessionId);
        startActivity(intent);
    }
}
