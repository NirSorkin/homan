package com.homan.homan.ui.houseHold;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.homan.homan.Models.Category;
import com.homan.homan.R;
import com.homan.homan.ui.ClothingAdapter;
import com.homan.homan.ui.HouseHoldAdapter;
import com.homan.homan.ui.MyAdapter;
import com.homan.homan.ui.clothing.ClothingFragmentDirections;
import com.homan.homan.ui.clothing.ClothingViewModel;
import com.homan.homan.ui.food.FoodFragmentDirections;

import java.util.LinkedList;
import java.util.List;


public class HouseHoldFragment extends Fragment {
    HouseHoldViewModel viewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView houseListRecycler;
    HouseHoldAdapter mAdapter;
    ProgressBar pb;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_house_hold, container, false);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) rootView.getContext()).get(HouseHoldViewModel.class);
        pb = rootView.findViewById(R.id.houseHoldlistprogressbar);
        pb.setVisibility(View.INVISIBLE);
        initializeViewElements(rootView);
        initializeRecyclerView(rootView);
        initializeViewHandlers();
        refreshCategoryList();

        Button addBtn = rootView.findViewById(R.id.houseHoldaddbutton);
        addBtn.setOnClickListener(v -> {
            String type = "HouseHold";
            Navigation.findNavController(v)
                    .navigate(HouseHoldFragmentDirections.actionHouseHoldFragmentToAddItemFragment2(type));
        });

        viewModel.getList().observe(getViewLifecycleOwner(), categories -> mAdapter.notifyDataSetChanged());
        return rootView;
    }

    private void initializeViewElements(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        houseListRecycler = view.findViewById(R.id.houseList);
    }

    private void initializeRecyclerView(View view) {
        mAdapter = new HouseHoldAdapter(getContext(), viewModel);
        houseListRecycler.setAdapter(mAdapter);
        houseListRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void initializeViewHandlers() {
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshCategoryList);
    }

    private void refreshCategoryList() {
        viewModel.refreshCategoryList();
        mSwipeRefreshLayout.setRefreshing(false);
    }

}