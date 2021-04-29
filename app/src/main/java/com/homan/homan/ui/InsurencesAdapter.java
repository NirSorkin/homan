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
import com.homan.homan.ui.insurnces.InsurencesFragmentDirections;
import com.homan.homan.ui.insurnces.InsurencesViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InsurencesAdapter extends RecyclerView.Adapter<InsurencesAdapter.MyViewHolder> {

    private final InsurencesViewModel insurencesViewModel;

    private final LayoutInflater mInflater;

    public InsurencesAdapter(Context context, InsurencesViewModel viewModel){
        mInflater = LayoutInflater.from(context);
        insurencesViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(viewModel.getClass());
    }

    @NonNull
    @Override
    public InsurencesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View mItemView = mInflater.inflate(R.layout.list_row, parent, false);
        return new InsurencesAdapter.MyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull InsurencesAdapter.MyViewHolder holder, int position) {
        Category mCurrent = insurencesViewModel.getList().getValue().get(position);
        if (mCurrent.getImage() != null) {
            Picasso.get().load(mCurrent.getImage()).placeholder(R.drawable.ic_menu_gallery).into(holder.itemImage);
        }
        holder.categorytext.setText(mCurrent.getDesc());
        holder.description.setText(mCurrent.getAmount() + "$");
    }


    @Override
    public int getItemCount() {
        if (insurencesViewModel.getList() == null) return 0;
        List<Category> categories = insurencesViewModel.getList().getValue();
        return categories != null ? categories.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView description , categorytext;
        public final ImageView itemImage;
        final InsurencesAdapter mAdapter;

        public MyViewHolder(@NonNull View itemView , InsurencesAdapter adapter) {
            super(itemView);
            categorytext = itemView.findViewById(R.id.categorytext);
            description = itemView.findViewById(R.id.descriptiontext);
            itemImage = itemView.findViewById(R.id.itemimage);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Category item = insurencesViewModel.getList().getValue().get(getLayoutPosition());

            if (insurencesViewModel instanceof InsurencesViewModel) {
                Navigation.findNavController(v)
                        .navigate(InsurencesFragmentDirections.actionInsurencesFragmentToEditItemFragment(item));
            }
        }
    }
}

