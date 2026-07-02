package com.example.simpledictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    public interface OnWordActionListener {
        void onWordClick(int position);
        void onWordLongClick(int position);
    }

    private final ArrayList<DictionaryEntry> entries;
    private final OnWordActionListener listener;

    public WordAdapter(ArrayList<DictionaryEntry> entries, OnWordActionListener listener) {
        this.entries = entries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        DictionaryEntry entry = entries.get(position);
        holder.wordText.setText(entry.getWord());
        holder.meaningText.setText(entry.getMeaning());

        int backgroundColor = (position % 2 == 0)
                ? R.color.item_background_even
                : R.color.item_background_odd;
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), backgroundColor));

        holder.itemView.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onWordClick(adapterPosition);
            }
        });

        holder.itemView.setOnLongClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onWordLongClick(adapterPosition);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView wordText;
        TextView meaningText;

        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordText = itemView.findViewById(R.id.word_text);
            meaningText = itemView.findViewById(R.id.meaning_text);
        }
    }
}
