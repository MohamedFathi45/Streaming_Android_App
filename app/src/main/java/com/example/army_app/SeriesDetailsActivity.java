package com.example.army_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.army_app.Loader_Tasks.CheckTheListLoader;
import com.example.army_app.Loader_Tasks.CommentLoader;
import com.example.army_app.Loader_Tasks.EpisodesLoader;
import com.example.army_app.Loader_Tasks.InsertReviewLoader;
import com.example.army_app.Loader_Tasks.InverseListStatusLoader;
import com.example.army_app.Loader_Tasks.MainActorsLoader;
import com.example.army_app.Loader_Tasks.SessionsLoader;
import com.example.army_app.Loader_Tasks.ShowTagsLoader;
import com.example.army_app.Loader_Tasks.EpisodeLinksLoader;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.util.ArrayList;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class SeriesDetailsActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    public static final String IDTWO ="id2";
    public static final String IDTHREE ="id3";
    public static final String IDFOUR ="id4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_details);

        tabLayout = findViewById(R.id.id_movie_details_tab_layout);
        viewPager = findViewById(R.id.movie_details_view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        Intent intent = getIntent();
        String id = intent.getStringExtra(MainActivity.TV_SHOW_OBJECT_ID);
        adapter.addFragment(new FragmentDetails(id) , getString(R.string.details));
        adapter.addFragment(new FragmentWatchSeries(id) , getString(R.string.watch));
        adapter.addFragment(new FragmentReviews(id) , getString(R.string.reviews));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
