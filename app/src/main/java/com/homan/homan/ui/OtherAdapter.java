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
import com.homan.homan.ui.houseHold.HouseHoldViewModel;
import com.homan.homan.ui.other.OtherFragmentDirections;
import com.homan.homan.ui.other.OtherViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.MyViewHolder> {

    private final OtherViewModel otherViewModel;

    private final LayoutInflater mInflater;

    public OtherAdapter(Context context, OtherViewModel viewModel){
        mInflater = LayoutInflater.from(context);
        otherViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(viewModel.getClass());
    }

    @NonNull
    @Override
    public OtherAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View mItemView = mInflater.inflate(R.layout.list_row, parent, false);
        return new OtherAdapter.MyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherAdapter.MyViewHolder holder, int position) {
        Category mCurrent = otherViewModel.getList().getValue().get(position);
        if (mCurrent.getImage() != null) {
            Picasso.get().load(mCurrent.getImage()).placeholder(R.drawable.ic_menu_gallery).into(holder.itemImage);
        }
        holder.categorytext.setText(mCurrent.getDesc());
        holder.description.setText(mCurrent.getCategoryType());
    }


    @Override
    public int getItemCount() {
        if (otherViewModel.getList() == null) return 0;
        List<Category> categories = otherViewModel.getList().getValue();
        return categories != null ? categories.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView description , categorytext;
        public final ImageView itemImage;
        final OtherAdapter mAdapter;

        public MyViewHolder(@NonNull View itemView , OtherAdapter adapter) {
            super(itemView);
            categorytext = itemView.findViewById(R.id.categorytext);
            description = itemView.findViewById(R.id.descriptiontext);
            itemImage = itemView.findViewById(R.id.itemimage);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Category item = otherViewModel.getList().getValue().get(getLayoutPosition());

            if (otherViewModel instanceof OtherViewModel) {
                Navigation.findNavController(v)
                        .navigate(OtherFragmentDirections.actionOtherFragmentToAddItemFragment2("HouseHold"));
            }
        }
    }
}

