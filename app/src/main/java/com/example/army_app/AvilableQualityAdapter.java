package com.example.army_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AvilableQualityAdapter extends RecyclerView.Adapter<AvilableQualityAdapter.AdapterViewHolder> {
    private ArrayList<Server> mData;
    private AvilableQualityAdapter.ListItemClickListener mOnClickListener;
    Context context;


    public interface ListItemClickListener {
        void onListItemClick(Server clicked , String order);
    }

    public AvilableQualityAdapter(ListItemClickListener listener , ArrayList<Server> mData , Context context){
        this.mData = mData;
        mOnClickListener = listener;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_avilable_quality,parent,false);
        AvilableQualityAdapter.AdapterViewHolder holder = new AvilableQualityAdapter.AdapterViewHolder(view);

        holder.imageWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                mOnClickListener.onListItemClick( mData.get(pos), "watch");
            }
        });
        holder.imageDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                mOnClickListener.onListItemClick(mData.get(pos) , "download");
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        String description = getQualityDescription(mData.get(position).getQuality());
        holder.qualit_description.setText(description);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView qualit_description;
        public LinearLayout imageWatch;
        public LinearLayout imageDownload;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            qualit_description = itemView.findViewById(R.id.id_quality_description_text);
            imageWatch = itemView.findViewById(R.id.id_watch_image);
            imageDownload = itemView.findViewById(R.id.id_download_image);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Server ret =  mData.get(clickedPosition);
        }
    }
    String getQualityDescription(String quality){
        String ret = "multiple";
        if(quality.equals("144") || quality.equals("244") || quality.equals(360))
            ret = context.getString(R.string.low_quality);
        else if(quality.equals("480"))
            ret = context.getString(R.string.medium_quality);
        else if(quality.equals("720") || quality.equals("1080"))
            ret = context.getString(R.string.high_quality);
        else if(quality.equals("multiple"))
            ret = context.getString(R.string.multiple_quality);
        return ret;
    }
}
