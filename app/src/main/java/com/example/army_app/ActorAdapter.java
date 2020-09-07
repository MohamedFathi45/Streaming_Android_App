package com.example.army_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.util.ArrayList;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.AdapterViewHolder> {
    ArrayList<Actor> mData;
    private ActorAdapter.ListItemClickListener mOnClickListener;


    public interface ListItemClickListener {
        void onListItemClick(Actor ac);
    }

    public ActorAdapter(ListItemClickListener listener , ArrayList<Actor> mData){
        this.mData = mData;
        mOnClickListener = listener;
    }


    @NonNull
    @Override
    public ActorAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_general_show,parent,false);
        final ActorAdapter.AdapterViewHolder holder = new AdapterViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                mOnClickListener.onListItemClick(mData.get(pos));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActorAdapter.AdapterViewHolder holder, int position) {
        holder.tvTitle.setText(mData.get(position).getName());
        //holder.imgPoster.setImageBitmap(mData.get(position).getPoster_image());
        Picasso.get().load( MainActivity.SERVER_ROOT_LINK_URL+"/"+mData.get(position).getImage_path()).fit().into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvTitle;
        private ImageView imgPoster;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.id_rv_movie_name);
            imgPoster = itemView.findViewById(R.id.id_rv_movie_image);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Actor ret =  mData.get(clickedPosition);

        }
    }
}
