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

import java.util.ArrayList;

public class ServerShowAdapter extends RecyclerView.Adapter<ServerShowAdapter.AdapterViewHolder> {

    ArrayList<Server> mData;
    private ServerShowAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int pos );
    }


    public ServerShowAdapter(ListItemClickListener listener , ArrayList<Server> mData){
        this.mData = mData;
        mOnClickListener = listener;
    }


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_server_choose,parent,false);
        final ServerShowAdapter.AdapterViewHolder holder = new ServerShowAdapter.AdapterViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                mOnClickListener.onListItemClick(pos);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
            holder.serverName.setText(mData.get(position).getServerName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView serverName;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            serverName = itemView.findViewById(R.id.id_item_server_choose_textView);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Server ret =  mData.get(clickedPosition);
        }
    }
}
