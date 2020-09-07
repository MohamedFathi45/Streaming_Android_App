package com.example.army_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchResultAdapterGridView extends BaseAdapter {
    Context context;
    ArrayList<GeneralShow> shows;
    LayoutInflater inflter;
    public SearchResultAdapterGridView(Context applicationContext, ArrayList<GeneralShow> shows) {
        this.context = applicationContext;
        this.shows= shows;
        inflter = (LayoutInflater.from(applicationContext));
    }



    @Override

    public int getCount() {
        return shows.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.search_result_item, null); // inflate the layout
        ImageView poster = (ImageView) view.findViewById(R.id.id_rv_movie_image); // get the reference of ImageView
        TextView name = (TextView) view.findViewById(R.id.id_rv_movie_name);
        TextView searchCat= view.findViewById(R.id.id_search_cat);
        GeneralShow.assignCat(shows.get(i).getCat_id() , context , searchCat);
        name.setText(shows.get(i).getName());
        Picasso.get().load( MainActivity.SERVER_ROOT_LINK_URL+"/"+shows.get(i).getPosterImagePath()).fit().into(poster);
        return view;
    }

}
