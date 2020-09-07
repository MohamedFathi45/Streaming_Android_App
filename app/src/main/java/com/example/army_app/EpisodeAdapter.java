package com.example.army_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.AdapterViewHolder>{

    ArrayList<Episode> mData;
    Context con;
    private EpisodeAdapter.ListItemClickListener mOnClickListener;
    private EpisodeAdapter.CommentItemClickListener mCommentOnClickListener;
    public interface ListItemClickListener {
        void onListItemClick(Episode episode);
    }

    public interface CommentItemClickListener {
        void onCommentItemClick(Episode episode);
    }
    public EpisodeAdapter(ListItemClickListener listener ,CommentItemClickListener listener2 , ArrayList<Episode> mData){
        this.mData = mData;
        mOnClickListener = listener;
        mCommentOnClickListener = listener2;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        con = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.episode_item,parent,false);
        final EpisodeAdapter.AdapterViewHolder holder = new EpisodeAdapter.AdapterViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                mOnClickListener.onListItemClick(mData.get(pos));
            }
        });
        holder.commentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                mCommentOnClickListener.onCommentItemClick(mData.get(pos));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        String episode_def =  con.getString(R.string.episode)+" :" + mData.get(position).getEpisode_number();
        holder.sessionName.setText( episode_def);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView sessionName;
        private LinearLayout commentImage;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            sessionName = itemView.findViewById(R.id.id_item_episode_textView);
            commentImage = itemView.findViewById(R.id.id_episode_comment_image);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Episode ret =  mData.get(clickedPosition);
        }
    }
}
