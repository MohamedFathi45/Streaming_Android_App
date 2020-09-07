package com.example.army_app;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.army_app.utilities.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class  CommentAdapter extends RecyclerView.Adapter<CommentAdapter.AdapterViewHolder>{

    ArrayList<Comment> mData;

    public CommentAdapter(ArrayList<Comment> comments){
        this.mData= comments;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false);
        final CommentAdapter.AdapterViewHolder holder = new AdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.tvUserName.setText(mData.get(position).getUser_name());
        holder.tvCommentBody.setText(mData.get(position).getComment_body());

        holder.tvDate.setText(mData.get(position).getDate());
        //holder.imgPoster.setImageBitmap(mData.get(position).getPoster_image());
        if(!mData.get(position).getUser_photo_url().equals( "non"))
            Picasso.get().load( mData.get(position).getUser_photo_url()).transform(new CircleTransform()).into(holder.userPhoto);
        else
            holder.userPhoto.setImageResource(R.drawable.profile);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserName;
        private TextView tvDate;
        private TextView tvCommentBody;
        private ImageView userPhoto;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.id_comment_user_name);
            tvDate = itemView.findViewById(R.id.id_comment_date);
            tvCommentBody = itemView.findViewById(R.id.id_comment_body);
            userPhoto = itemView.findViewById(R.id.id_comment_photo);
        }
    }
}
