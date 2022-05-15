package com.example.notes;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class customAdapter extends RecyclerView.Adapter<customAdapter.ViewHolder> {

    private List<notesEn> notesData = new ArrayList<>();
    private OnItemClickListener listener;


    public void setNotesData(List<notesEn> notesData){

        this.notesData.clear();
        this.notesData = notesData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public customAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View notesRow = layoutInflater.inflate(R.layout.notes_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(notesRow);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull customAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final notesEn data = notesData.get(position);
        holder.notesTitle.setText(data.getNotesTitle());
        holder.notesText.setText(data.getNotesText());
    }

    @Override
    public int getItemCount() {
        return notesData.size();
    }

    public int getPosition(){ return getPosition(); }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView notesTitle;
        public TextView notesText;
        public RelativeLayout container;
        public ImageView delete;

        public ViewHolder(View view){
            super(view);
            this.notesTitle = view.findViewById(R.id.notesTitle);
            this.notesText = view.findViewById(R.id.notesText);
            this.container = view.findViewById(R.id.notes_row);
            this.delete = view.findViewById(R.id.delete);
            boolean undo = false;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    MainActivity.deleteNote(notesData.get(position));
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(notesData.get(position));
                    }
                }
            });

        }

        }

    public interface OnItemClickListener{

        void onItemClick(notesEn note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;
    }

}



