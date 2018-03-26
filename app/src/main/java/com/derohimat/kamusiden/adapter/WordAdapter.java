package com.derohimat.kamusiden.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.derohimat.kamusiden.R;
import com.derohimat.kamusiden.model.WordDao;

import java.util.ArrayList;

/**
 * Created by denirohimat on 27/03/18.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.MahasiswaHolder> {
    private ArrayList<WordDao> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public WordAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MahasiswaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word_row, parent, false);
        return new MahasiswaHolder(view);
    }

    public void addItem(ArrayList<WordDao> mData) {

        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MahasiswaHolder holder, int position) {
        holder.txtWord.setText(mData.get(position).getWord());
        holder.txtMeaning.setText(mData.get(position).getMeaning());

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MahasiswaHolder extends RecyclerView.ViewHolder {
        private TextView txtWord;
        private TextView txtMeaning;

        public MahasiswaHolder(View itemView) {
            super(itemView);

            txtWord = itemView.findViewById(R.id.txt_word);
            txtMeaning = itemView.findViewById(R.id.txt_meaning);
        }
    }
}
