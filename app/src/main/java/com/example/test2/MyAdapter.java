package com.example.test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;


import jp.wasabeef.picasso.transformations.CropCircleTransformation;

//Adapter class for Recycler view

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable
{

     private Context context;
     private ArrayList<Model_basicDetails> profile;
     private ArrayList<Model_basicDetails> profileFull;

     MyAdapter(Context context, ArrayList<Model_basicDetails> profile)
     {
         this.context = context;
         this.profile = profile;
         profileFull = new ArrayList<>(profile);
     }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {

        holder.name.setText(profile.get(position).getName());
        holder.usn.setText(profile.get(position).getUsn());
        holder.email.setText(profile.get(position).getEmail());
        holder.phone.setText(profile.get(position).getPhone());
        Picasso.with(context).load(profile.get(position).getmImageUrl())
                .transform(new CropCircleTransformation())
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return profile.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,usn,email,phone;
        ImageView pic;

            MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            usn = itemView.findViewById(R.id.usn);
            email = itemView.findViewById(R.id.email);
            pic = itemView.findViewById(R.id.pic);
            phone = itemView.findViewById(R.id.phone);

        }
    }

    @Override
    public  Filter getFilter()
    {
        return profileFilter;
    }
    private Filter profileFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Model_basicDetails> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length()==0)
            {
                filteredList.addAll(profileFull);
            }
            else
            {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Model_basicDetails item : profileFull )
                {
                    if(item.getName().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults res) {

            profile.clear();
            profile.addAll((ArrayList) res.values);
            notifyDataSetChanged();
        }
    };
}
