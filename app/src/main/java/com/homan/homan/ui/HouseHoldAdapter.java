package com.homan.homan.ui;

import android.content.Context;
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
import com.homan.homan.ui.clothing.ClothingFragmentDirections;
import com.homan.homan.ui.clothing.ClothingViewModel;
import com.homan.homan.ui.houseHold.HouseHoldFragmentDirections;
import com.homan.homan.ui.houseHold.HouseHoldViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HouseHoldAdapter  extends RecyclerView.Adapter<HouseHoldAdapter.MyViewHolder> {

    private final HouseHoldViewModel houseHoldViewModel;

    private final LayoutInflater mInflater;

    public HouseHoldAdapter(Context context, HouseHoldViewModel viewModel){
        mInflater = LayoutInflater.from(context);
        houseHoldViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(viewModel.getClass());
    }

    @NonNull
    @Override
    public HouseHoldAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View mItemView = mInflater.inflate(R.layout.list_row, parent, false);
        return new HouseHoldAdapter.MyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseHoldAdapter.MyViewHolder holder, int position) {
        Category mCurrent = houseHoldViewModel.getList().getValue().get(position);
        if (mCurrent.getImage() != null) {
            Picasso.get().load(mCurrent.getImage()).placeholder(R.drawable.ic_menu_gallery).into(holder.itemImage);
        }
        holder.categorytext.setText(mCurrent.getDesc());
        holder.description.setText(mCurrent.getCategoryType());
    }


    @Override
    public int getItemCount() {
        if (houseHoldViewModel.getList() == null) return 0;
        List<Category> categories = houseHoldViewModel.getList().getValue();
        return categories != null ? categories.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView description , categorytext;
        public final ImageView itemImage;
        final HouseHoldAdapter mAdapter;

        public MyViewHolder(@NonNull View itemView , HouseHoldAdapter adapter) {
            super(itemView);
            categorytext = itemView.findViewById(R.id.categorytext);
            description = itemView.findViewById(R.id.descriptiontext);
            itemImage = itemView.findViewById(R.id.itemimage);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Category item = houseHoldViewModel.getList().getValue().get(getLayoutPosition());

            if (houseHoldViewModel instanceof HouseHoldViewModel) {
                Navigation.findNavController(v)
                        .navigate(HouseHoldFragmentDirections.actionHouseHoldFragmentToEditItemFragment(item));
            }
        }
    }
}

