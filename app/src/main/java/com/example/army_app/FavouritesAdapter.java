package com.example.army_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.AdapterViewHolder> {

    Context context;
    ArrayList<GeneralShow>mData;
    private FavouritesAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(GeneralShow generalShow);
    }

    public FavouritesAdapter(FavouritesAdapter.ListItemClickListener listener , ArrayList<GeneralShow> mData , Context applicationContext){
        this.mData = mData;
        mOnClickListener = listener;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.favourites_item,parent,false);
        final FavouritesAdapter.AdapterViewHolder holder = new AdapterViewHolder(view);
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
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.tvName.setText(mData.get(position).getName());
        holder.tvName.setText(mData.get(position).getName());
        String year = String.valueOf(mData.get(position).getSpec().getProperties().get("year_of_production"));
        holder.tvYear.setText(year);
        GeneralShow.assignCat(mData.get(position).getCat_id() , context , holder.tvType);
        //holder.imgPoster.setImageBitmap(mData.get(position).getPoster_image());
        Picasso.get().load( MainActivity.SERVER_ROOT_LINK_URL+"/"+mData.get(position).getPosterImagePath()).fit().into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvYear;
        private TextView tvName;
        private TextView tvType;
        private ImageView imgPoster;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.id_favourites_show_name);
            imgPoster = itemView.findViewById(R.id.id_favourites_show_image);
            tvType = itemView.findViewById(R.id.id_favourites_show_type);
            tvYear = itemView.findViewById(R.id.id_favourites_show_year);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            GeneralShow ret =  mData.get(clickedPosition);
        }
    }
}
