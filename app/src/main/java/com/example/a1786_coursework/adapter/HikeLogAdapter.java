package com.example.a1786_coursework.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1786_coursework.R;
import com.example.a1786_coursework.models.HikeLog;

import java.util.List;

public class HikeLogAdapter extends RecyclerView.Adapter<HikeLogAdapter.HikeLogViewHolder> {
    private List<HikeLog> hikeLogs;
    private OnItemClickListener listener;

    public HikeLogAdapter(List<HikeLog> hikeLogs) {
        this.hikeLogs = hikeLogs;
    }

    @NonNull
    @Override
    public HikeLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_card, parent, false);
        return new HikeLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeLogViewHolder holder, int position) {
        HikeLog hikeLog = hikeLogs.get(position);
        holder.hikeName.setText(hikeLog.name_hike);
        holder.hikeLocation.setText(hikeLog.location_hike);
        holder.hikeLength.setText(String.valueOf(hikeLog.length_hike));
        holder.hikeDate.setText(hikeLog.date_hike);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hikeLogs.size();
    }

    public static class HikeLogViewHolder extends RecyclerView.ViewHolder {
        TextView hikeName, hikeLocation, hikeDate, hikeLength;
        Button editButton, deleteButton;
        public HikeLogViewHolder(@NonNull View itemView) {
            super(itemView);
            hikeName = itemView.findViewById(R.id.view_name_hike);
            hikeLocation = itemView.findViewById(R.id.view_location_hike);
            hikeDate = itemView.findViewById(R.id.view_date_hike);
            hikeLength = itemView.findViewById(R.id.view_length_hike);
            editButton = itemView.findViewById(R.id.button_edit);
            deleteButton = itemView.findViewById(R.id.button_delete);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
