package com.example.test2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;


import jp.wasabeef.picasso.transformations.CropCircleTransformation;

//Adapter class for Recycler view

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable
{

    private ProgressBar progressBar;
    private Context context;
    private ArrayList<Model_basicDetails> profile;
    private ArrayList<Model_basicDetails> profileFull;
    private OnNoteListener mOnNoteListener;


    MyAdapter(Context context, ArrayList<Model_basicDetails> profile, OnNoteListener onNoteListener )
    {
        this.context = context;
        this.profile = profile;
        this.mOnNoteListener = onNoteListener;
        profileFull = new ArrayList<>(profile);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        return new MyViewHolder(v,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        try
        {

            holder.name.setText(profile.get(position).getName());
            holder.usn.setText(profile.get(position).getUsn());
            holder.email.setText(profile.get(position).getEmail());
            holder.phone.setText(profile.get(position).getPhone());
            holder.id.getId();
            Picasso.with(context).load(profile.get(position).getmImageUrl())
                    .transform(new CropCircleTransformation())
                    .into(holder.pic);
            holder.itemView.findViewById(R.id.viewProgress).setVisibility(ProgressBar.GONE);

        }
        catch (Exception e)
        {
            //
        }

    }

    @Override
    public int getItemCount() {
        return profile.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name,usn,email,phone, id;//check
        ImageView pic;
        OnNoteListener onNoteListener;

        MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener)
        {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            usn = itemView.findViewById(R.id.usn);
            email = itemView.findViewById(R.id.email);
            pic = itemView.findViewById(R.id.pic);
            phone = itemView.findViewById(R.id.phone);
            id = itemView.findViewById(R.id.id);
            progressBar = itemView.findViewById(R.id.viewProgress);

            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view)
        {

            onNoteListener.onNoteClick(getAdapterPosition());

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

    public interface OnNoteListener
    {
        void onNoteClick(int position);

    }
}