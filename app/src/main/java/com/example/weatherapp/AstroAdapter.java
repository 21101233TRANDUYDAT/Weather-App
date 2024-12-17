package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AstroAdapter extends RecyclerView.Adapter<AstroAdapter.AstroViewHolder> {
    private List<WeatherResponse.Hour> hours = new ArrayList<>();

    private Context context;

    public void updateData(List<WeatherResponse.Hour> astros) {
        this.hours = astros;
        notifyDataSetChanged();
    }

    public AstroAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override

    public AstroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.astro_item, parent, false);
        return new AstroViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull AstroViewHolder holder, int position) {
        holder.onBind(context, hours.get(position));
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public static class AstroViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView temp;

        public AstroViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.astro_icon);
            name = itemView.findViewById(R.id.astro_time);
            temp = itemView.findViewById(R.id.astro_tem);
        }

        public void onBind(Context context, WeatherResponse.Hour astro) {
            Glide.with(context).load(astro.getCondition().getIcon().replace("//", "https://")).into(icon);
            name.setText(TimeConversion.convertToReadableTime(astro.getTime()));
            temp.setText(astro.getTemp_c() + "°");
        }
    }
}
