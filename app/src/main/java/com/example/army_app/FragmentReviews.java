package com.example.army_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.army_app.Loader_Tasks.CommentLoader;
import com.example.army_app.Loader_Tasks.InsertReviewLoader;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class FragmentReviews extends Fragment implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object>{
    View view;
    ImageView sendButtonImage;
    RecyclerView commentsRecyclerView;
    int operationCount = 0;
    int numOfOperations = 1;
    String show_id;
    LinearLayout loading_layout;
    LinearLayout results_layout;
    TextInputLayout textInputLayout;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        commentsRecyclerView = getView().findViewById(R.id.id_recyclerView_comments);
        textInputLayout = getView().findViewById(R.id.id_review_input_text);
        sendButtonImage = getView().findViewById(R.id.id_send_review);
        loading_layout = getView().findViewById(R.id.id_progress_bar_linear_layout);
        results_layout = getView().findViewById(R.id.reviews_layout);
        operationCount = 0;
        loading_layout.setVisibility(View.VISIBLE);
        results_layout.setVisibility(View.GONE);
        getReviewsFromServer(show_id);

        sendButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.account == null || String.valueOf(MainActivity.userId).equals("0")){
                    Toast.makeText(getActivity(), R.string.must_sign_in, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(textInputLayout.getEditText().getText().toString().trim().equals("") || textInputLayout.getEditText().getText().toString().trim().length() > 100){
                    Toast.makeText(getActivity() , R.string.comment_size_problem , Toast.LENGTH_SHORT).show();
                }
                else{
                    insertReview(String.valueOf(MainActivity.userId) , String.valueOf(show_id) , textInputLayout.getEditText().getText().toString().trim());
                    textInputLayout.getEditText().setText("");
                }
            }
        });
    }

    public FragmentReviews(String id) {
        show_id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reviews_fragment,container , false);
        return view;
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader res = null;
        switch (id){
            case MainActivity.LOADER_FOR_LOAD_REVIEWS:
                res = new CommentLoader(getActivity() , args.getString(MainActivity.SERVER_LINK),args.getString(MainActivity.ID) );
                break;
            case MainActivity.LOADER_FOR_INSERT_REVIEW:
                res = new InsertReviewLoader(getActivity() , args.getString(MainActivity.SERVER_LINK),args.getString(MainActivity.ID) ,args.getString(SeriesDetailsActivity.IDTWO) , args.getString(MainActivity.VALUE));
                break;
        }
        return res;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
         if(id == MainActivity.LOADER_FOR_INSERT_REVIEW){
            if(data != null){
                String res = (String) data;
                if((res.equals("0"))){
                    Toast.makeText(getActivity() , R.string.cannot_insert_more_reviews , Toast.LENGTH_SHORT).show();
                }
                else if(res.equals("1")){
                    getReviewsFromServer(show_id);
                }
                stopLoader(id);
            }
        }
         else if(id == MainActivity.LOADER_FOR_LOAD_REVIEWS){
             if (data != null) {
                 ArrayList<Comment> comments = (ArrayList<Comment>)data;
                 CommentAdapter commentAdapter  = new CommentAdapter( comments);
                 commentsRecyclerView.setAdapter(commentAdapter);
                 commentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
             }
             incrementOperationCount();
         }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }

    private void insertReview(String user_id , String show_id , String comment){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , MainActivity.SERVER_INSERT_REVIEW_API);
        queryBundle.putString(MainActivity.ID , user_id);
        queryBundle.putString(SeriesDetailsActivity.IDTWO , show_id);
        queryBundle.putString(MainActivity.VALUE , comment);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        //  Get our Loader by calling getLoader and passing the ID we specified
        Loader<Object> insertReviewLoader = loaderManager.getLoader(MainActivity.LOADER_FOR_INSERT_REVIEW);
        //  If the Loader was null, initialize it. Else, restart it.
        if (insertReviewLoader == null) {
            loaderManager.initLoader(MainActivity.LOADER_FOR_INSERT_REVIEW, queryBundle, this);
        } else {
            loaderManager.restartLoader(MainActivity.LOADER_FOR_INSERT_REVIEW, queryBundle, this);
        }
    }
    private void getReviewsFromServer(String movieId) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , MainActivity.SERVER_GET_REVIEWS_API);
        queryBundle.putString(MainActivity.ID , movieId);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        //  Get our Loader by calling getLoader and passing the ID we specified
        Loader<Object> loadReviewsLoader = loaderManager.getLoader(MainActivity.LOADER_FOR_LOAD_REVIEWS);
        //  If the Loader was null, initialize it. Else, restart it.
        if (loadReviewsLoader == null) {
            loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_REVIEWS, queryBundle, this);
        } else {
            loaderManager.restartLoader(MainActivity.LOADER_FOR_LOAD_REVIEWS, queryBundle, this);
        }
    }
    void incrementOperationCount() {
        synchronized (this) {
            operationCount = operationCount + 1;
            if(operationCount == numOfOperations){
                loading_layout.setVisibility(View.GONE);
                results_layout.setVisibility(View.VISIBLE);
            }
        }
    }
    void stopLoader(int id) {
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.destroyLoader(id);
    }
}
