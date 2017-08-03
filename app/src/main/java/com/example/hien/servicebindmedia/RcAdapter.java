package com.example.hien.servicebindmedia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by hien on 8/3/17.
 */

public class RcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private IAdapter mAdapter;

    private Context mContext;

    public RcAdapter(IAdapter mAdapter, Context context) {
        this.mAdapter = mAdapter;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewClass viewClass
                = new ViewClass(inflater.inflate(R.layout.item_layout, parent, false), this);

        return viewClass;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ItemMusic itemMusic = mAdapter.getItem(position);

        ViewClass viewClass = (ViewClass) holder;

        viewClass.tvNameSong.setText(itemMusic.getNameSong());
        viewClass.tvNameArtist.setText(itemMusic.getNameArtist());

    }

    @Override
    public int getItemCount() {
        return mAdapter.getSize();
    }

    @Override
    public void onClick(View view) {
        IGetPosition iGetPosition = (IGetPosition) view.getTag();

        int position = iGetPosition.getPosition();

        mAdapter.clickItem(position);

    }

    private class ViewClass extends RecyclerView.ViewHolder{

        private TextView tvNameSong;
        private TextView tvNameArtist;

        public ViewClass(View itemView, View.OnClickListener click) {
            super(itemView);

            tvNameSong = itemView.findViewById(R.id.tv_name_song);
            tvNameArtist = itemView.findViewById(R.id.tv_name_artist);

            IGetPosition getPosition = new IGetPosition() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }
            };

            itemView.setTag(getPosition);
            itemView.setOnClickListener(click);
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.translate_view);
            tvNameArtist.startAnimation(animation);
            tvNameSong.startAnimation(animation);

        }
    }

    public interface IAdapter{
        int getSize();
        ItemMusic getItem(int position);
        void clickItem(int position);
    }
}
