package com.example.co_19tracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.co_19tracker.api.CounterData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    private Context context;
    private List<CounterData> list;

    public CountryAdapter(Context context, List<CounterData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.countryitem,parent,false);

        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        CounterData data=list.get(position);
        holder.countercases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
        holder.countryname.setText(data.getCountry());
        holder.sno.setText(String.valueOf(position+1));

        Map<String,String >img=data.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.counterflag);

        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context,MainActivity.class);
            intent.putExtra("country",data.getCountry());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        private TextView sno,countryname,countercases;
        private ImageView counterflag;
        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            sno=itemView.findViewById(R.id.sno);
            countryname=itemView.findViewById(R.id.countryName);
            countercases=itemView.findViewById(R.id.countryCases);
            counterflag=itemView.findViewById(R.id.countryImage);
        }
    }

    public void filterList(List<CounterData> filterList){
        list=filterList;
        notifyDataSetChanged();
    }

}
