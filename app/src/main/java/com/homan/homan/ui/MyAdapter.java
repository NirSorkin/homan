package com.homan.homan.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;
import com.homan.homan.R;
import com.homan.homan.ui.cars.CarsFragmentDirections;
import com.homan.homan.ui.cars.CarsFragmentViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final CarsFragmentViewModel mViewModel;

    private final LayoutInflater mInflater;

    public MyAdapter(Context context, CarsFragmentViewModel viewModel){
        mInflater = LayoutInflater.from(context);
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(viewModel.getClass());
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View mItemView = mInflater.inflate(R.layout.list_row, parent, false);
        return new MyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category mCurrent = mViewModel.getList().getValue().get(position);
        if (mCurrent.getImage() != null) {
            Picasso.get().load(mCurrent.getImage()).placeholder(R.drawable.ic_menu_gallery).into(holder.itemImage);
        }
        holder.categorytext.setText(mCurrent.getDesc());
        holder.description.setText(mCurrent.getCategoryType());
    }

    @Override
    public int getItemCount() {
        if (mViewModel.getList() == null) return 0;
        List<Category> categories = mViewModel.getList().getValue();
        return categories != null ? categories.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView description , categorytext;
        public final ImageView itemImage;
        final MyAdapter mAdapter;

        public MyViewHolder(@NonNull View itemView , MyAdapter adapter) {
            super(itemView);
            categorytext = itemView.findViewById(R.id.categorytext);
            description = itemView.findViewById(R.id.descriptiontext);
            itemImage = itemView.findViewById(R.id.itemimage);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String itemId = mViewModel.getList().getValue().get(getLayoutPosition()).getDesc();
            if (mViewModel instanceof CarsFragmentViewModel) {
                Navigation.findNavController(v)
                        .navigate(CarsFragmentDirections.actionCarsFragment2ToAddItemFragment2());
            }
        }
    }
}
