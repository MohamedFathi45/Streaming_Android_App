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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.army_app.Loader_Tasks.EpisodeCommentInsertionLoader;
import com.example.army_app.Loader_Tasks.EpisodeCommentLoader;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigInteger;
import java.util.ArrayList;


public class CommentActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<Object>{


    TextInputLayout textInputLayout;
    RecyclerView commentsRecyclerView;
    LinearLayout results_layout;
    LinearLayout loading_layout;
    ImageView sendImage;
    GeneralShow show;
    String session_id;
    String episode_id;
    int operationCount = 0;
    int numOfOperations = 1;
    public static final String SERVER_GET_EPISODE_COMMENTS_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/read_comments.php";
    public static final String SERVER_INSERT_EPISODE_COMMENT_API = MainActivity.SERVER_ROOT_LINK_URL+"/army_app_rest/api/write_episode_comment.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        results_layout = findViewById(R.id.comments_layout);
        textInputLayout = findViewById(R.id.id_comment_input_text);
        commentsRecyclerView = findViewById(R.id.id_recyclerView_comments);
        loading_layout = findViewById(R.id.id_progress_bar_linear_layout);
        sendImage = findViewById(R.id.id_send_review);
        Intent intent = getIntent();
        String episode_number = intent.getStringExtra(MainActivity.NAME);
        String show_id = intent.getStringExtra(MainActivity.ID);
        show = Inventory.getInstance().getShow(new BigInteger(show_id));
        setTitle(show.getName()+"("+ getString(R.string.episode)+" "+ episode_number+")");
        session_id = intent.getStringExtra(SeriesDetailsActivity.IDTWO);
        episode_id = intent.getStringExtra(SeriesDetailsActivity.IDTHREE);
        operationCount = 0;
        loading_layout.setVisibility(View.VISIBLE);
        results_layout.setVisibility(View.GONE);
        getEpisodeCommentsFromServer(show_id , session_id , episode_id);
        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.account == null || String.valueOf(MainActivity.userId).equals("0")){
                    Toast.makeText(CommentActivity.this, R.string.must_sign_in, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(textInputLayout.getEditText().getText().toString().trim().equals("") || textInputLayout.getEditText().getText().toString().trim().length() > 100){
                    Toast.makeText(CommentActivity.this , R.string.comment_size_problem , Toast.LENGTH_SHORT).show();
                }
                else{
                    insertEpisodeComment(String.valueOf(MainActivity.userId) , String.valueOf(show_id) , session_id , episode_id, textInputLayout.getEditText().getText().toString().trim());
                    textInputLayout.getEditText().setText("");
                }
            }
        });
    }

    private void getEpisodeCommentsFromServer(String show_id , String session_id , String episode_id) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_GET_EPISODE_COMMENTS_API);
        queryBundle.putString(MainActivity.ID , show_id);
        queryBundle.putString(SeriesDetailsActivity.IDTWO , session_id);
        queryBundle.putString(SeriesDetailsActivity.IDTHREE , episode_id);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        //  Get our Loader by calling getLoader and passing the ID we specified
        Loader<Object> loadReviewsLoader = loaderManager.getLoader(MainActivity.LOADER_FOR_LOAD_EPISODE_COMMENTS);
        //  If the Loader was null, initialize it. Else, restart it.
        if (loadReviewsLoader == null) {
            loaderManager.initLoader(MainActivity.LOADER_FOR_LOAD_EPISODE_COMMENTS, queryBundle, this);
        } else {
            loaderManager.restartLoader(MainActivity.LOADER_FOR_LOAD_EPISODE_COMMENTS, queryBundle, this);
        }
    }

    private void insertEpisodeComment(String user_id , String show_id, String session_id , String episode_id , String comment){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(MainActivity.SERVER_LINK , SERVER_INSERT_EPISODE_COMMENT_API);
        queryBundle.putString(MainActivity.ID , user_id);
        queryBundle.putString(SeriesDetailsActivity.IDTWO , show_id);
        queryBundle.putString(SeriesDetailsActivity.IDTHREE , session_id);
        queryBundle.putString(SeriesDetailsActivity.IDFOUR , episode_id);
        queryBundle.putString(MainActivity.VALUE , comment);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        //  Get our Loader by calling getLoader and passing the ID we specified
        Loader<Object> insertReviewLoader = loaderManager.getLoader(MainActivity.LOADER_FOR_INSERT_EPISODE_COMMENTS);
        //  If the Loader was null, initialize it. Else, restart it.
        if (insertReviewLoader == null) {
            loaderManager.initLoader(MainActivity.LOADER_FOR_INSERT_EPISODE_COMMENTS, queryBundle, this);
        } else {
            loaderManager.restartLoader(MainActivity.LOADER_FOR_INSERT_EPISODE_COMMENTS, queryBundle, this);
        }
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader res = null;
        if(id == MainActivity.LOADER_FOR_LOAD_EPISODE_COMMENTS){
            res = new EpisodeCommentLoader(this , args.getString(MainActivity.SERVER_LINK) ,args.getString(MainActivity.ID) , args.getString(SeriesDetailsActivity.IDTWO) , args.getString(SeriesDetailsActivity.IDTHREE) );
        }
        else if(id == MainActivity.LOADER_FOR_INSERT_EPISODE_COMMENTS){
            res = new EpisodeCommentInsertionLoader(this , args.getString(MainActivity.SERVER_LINK) , args.getString(MainActivity.ID) , args.getString(SeriesDetailsActivity.IDTWO) , args.getString(SeriesDetailsActivity.IDTHREE) , args.getString(SeriesDetailsActivity.IDFOUR) , args.getString(MainActivity.VALUE));
        }
        return res;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == MainActivity.LOADER_FOR_LOAD_EPISODE_COMMENTS){
            if (data != null) {
                ArrayList<Comment> comments = (ArrayList<Comment>)data;
                CommentAdapter commentAdapter  = new CommentAdapter( comments);
                commentsRecyclerView.setAdapter(commentAdapter);
                commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            }
            incrementOperationCount();
        }
        else if(id == MainActivity.LOADER_FOR_INSERT_EPISODE_COMMENTS){
            if(data != null){
                String res = (String) data;
                if((res.equals("0"))){
                    Toast.makeText(this , R.string.cannot_insert_more_comments , Toast.LENGTH_SHORT).show();
                }
                else if(res.equals("1")){
                    getEpisodeCommentsFromServer(String.valueOf(show.getId()) , String.valueOf(session_id) , String.valueOf(episode_id));
                }
                stopLoader(id);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

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
