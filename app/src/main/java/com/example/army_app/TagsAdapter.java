package com.example.army_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.AdapterViewHolder>{


    private ArrayList<String> mData;
    private TagsAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(String tag);
    }


    public TagsAdapter(ListItemClickListener listener , ArrayList<String> mData){
        this.mData = mData;
        mOnClickListener = listener;
    }



    @NonNull
    @Override
    public TagsAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.tag_item,parent,false);
        final TagsAdapter.AdapterViewHolder holder = new TagsAdapter.AdapterViewHolder(view);
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
    public void onBindViewHolder(@NonNull TagsAdapter.AdapterViewHolder holder, int position) {
        String tag = mData.get(position);
        if(tag.charAt(0) != '#');
            tag = "#" + tag;
        holder.tagTextView.setText(tag);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tagTextView;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tagTextView = itemView.findViewById(R.id.tag_item_textView);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            String ret =  mData.get(clickedPosition);
        }
    }
}
