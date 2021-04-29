package com.homan.homan.ui;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.homan.homan.Models.Category;
import com.homan.homan.R;
import com.homan.homan.ui.cars.CarsFragmentDirections;
import com.homan.homan.ui.clothing.ClothingFragment;
import com.homan.homan.ui.clothing.ClothingFragmentDirections;
import com.homan.homan.ui.clothing.ClothingViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.MyViewHolder> {

    private final ClothingViewModel clothingViewModel;

    private final LayoutInflater mInflater;

    public ClothingAdapter(Context context, ClothingViewModel viewModel){
        mInflater = LayoutInflater.from(context);
        clothingViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(viewModel.getClass());
    }

    @NonNull
    @Override
    public ClothingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View mItemView = mInflater.inflate(R.layout.list_row, parent, false);
        return new ClothingAdapter.MyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category mCurrent = clothingViewModel.getList().getValue().get(position);
        if (mCurrent.getImage() != null) {
            Picasso.get().load(mCurrent.getImage()).placeholder(R.drawable.ic_menu_gallery).into(holder.itemImage);
        }
        holder.categorytext.setText(mCurrent.getDesc());
        holder.description.setText(mCurrent.getAmount() + "$");
    }


    @Override
    public int getItemCount() {
        if (clothingViewModel.getList() == null) return 0;
        List<Category> categories = clothingViewModel.getList().getValue();
        return categories != null ? categories.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView description , categorytext;
        public final ImageView itemImage;
        final ClothingAdapter mAdapter;

        public MyViewHolder(@NonNull View itemView , ClothingAdapter adapter) {
            super(itemView);
            categorytext = itemView.findViewById(R.id.categorytext);
            description = itemView.findViewById(R.id.descriptiontext);
            itemImage = itemView.findViewById(R.id.itemimage);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Category item = clothingViewModel.getList().getValue().get(getLayoutPosition());

            if (clothingViewModel instanceof ClothingViewModel) {
                Navigation.findNavController(v)
                        .navigate(ClothingFragmentDirections.actionClothingFragmentToEditItemFragment(item));
            }
        }
    }
}

