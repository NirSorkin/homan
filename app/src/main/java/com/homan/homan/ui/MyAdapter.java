package com.homan.homan.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.homan.homan.Models.Category;
import com.homan.homan.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    String categoryType;
    Category itemCategories[];
    List<Category> cList = new LinkedList<Category>();
    List<Category> typeArray = new ArrayList<>();
    public MyAdapter(Context ct, List<Category> categoryListList , String type){
        context = ct;
        cList = categoryListList;
        categoryType = type;
        for(int i = 0; i < cList.size(); i++){
            if(cList.get(i).getCategoryType() == categoryType){
                typeArray.add(cList.get(i));
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.description.setText(typeArray.get(position).getUserID());
        holder.categorytext.setText(typeArray.get(position).getCategoryType());
    }

    @Override
    public int getItemCount() {
        return typeArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView description , categorytext;
        //TODO: add images
        ImageView itemImage;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            description = itemView.findViewById(R.id.descriptiontext);
            categorytext = itemView.findViewById(R.id.categorytext);
        }
    }
}
