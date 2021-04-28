package com.homan.homan.ui.clothing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;
import com.homan.homan.R;
import com.homan.homan.ui.ClothingAdapter;
import com.homan.homan.ui.MyAdapter;
import com.homan.homan.ui.cars.CarsFragmentDirections;

import java.io.Serializable;
import java.util.List;


public class ClothingFragment extends Fragment {

    ClothingViewModel viewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView carsListRecycler;
    ClothingAdapter mAdapter;
    ProgressBar pb;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_clothing, container, false);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) rootView.getContext()).get(ClothingViewModel.class);
        pb = rootView.findViewById(R.id.clothinglistprogressbar);
        pb.setVisibility(View.INVISIBLE);
        initializeViewElements(rootView);
        initializeRecyclerView(rootView);
        initializeViewHandlers();
        refreshCategoryList();

        Button addBtn = rootView.findViewById(R.id.clothingaddbutton);
        addBtn.setOnClickListener(v -> {
            String type = "Clothing";
            Navigation.findNavController(v)
                    .navigate(ClothingFragmentDirections.actionClothingFragmentToAddItemFragment2(type));
        });

        viewModel.getList().observe(getViewLifecycleOwner(), categories -> mAdapter.notifyDataSetChanged());
        return rootView;
    }

    private void initializeViewElements(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        carsListRecycler = view.findViewById(R.id.clothingList);
    }

    private void initializeRecyclerView(View view) {
        mAdapter = new ClothingAdapter(getContext(), viewModel);
        carsListRecycler.setAdapter(mAdapter);
        carsListRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void initializeViewHandlers() {
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshCategoryList);
    }

    private void refreshCategoryList() {
        viewModel.refreshCategoryList();
        mSwipeRefreshLayout.setRefreshing(false);
    }

}