package com.example.hatirlatmaappson;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private List<Not> notList;

    private OnNoteListener mOnNoteListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView notName,notIcerik,notTarih,notSaat,notDurumu;


        OnNoteListener onNoteListener;

        public MyViewHolder(View v, OnNoteListener onNoteListener) {
            super(v);

            this.onNoteListener = onNoteListener;
            //Düzenlenecek layoutta
            notName = v.findViewById(R.id.notName);
            notIcerik = v.findViewById(R.id.notIcerik);
            notTarih = v.findViewById(R.id.notTarih);
            notSaat=v.findViewById(R.id.notSaat);
            notDurumu = v.findViewById(R.id.durum);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomAdapter(List<Not> notList, OnNoteListener onNoteListener) {
        this.notList = notList;
        this.mOnNoteListener = onNoteListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item_layout, parent, false);

        return new MyViewHolder(itemView,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Not not = notList.get(position);
        holder. notName.setText("Not Başlık:"+not.getNotBaslik());
        holder.notIcerik.setText("Açıklama:"+not.getNotAciklama());
        holder.notTarih.setText("Tarih:"+not.getNotTarih());
        holder.notSaat.setText("Saat:"+not.getNotSaat());
        holder.notDurumu.setText(not.getNotDurumu());


        if (not.getNotDurumu().equals("1")){
            holder.notDurumu.setText("Görev Tamamlandı");
            holder.notDurumu.setTextColor(Color.parseColor("#008000"));
        }
        else{
            holder.notDurumu.setText("Görev Tamamlanmadı");
            holder.notDurumu.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public int getItemCount() {
        return notList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}