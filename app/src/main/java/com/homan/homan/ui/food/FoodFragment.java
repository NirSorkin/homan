package com.homan.homan.ui.food;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
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

import com.homan.homan.R;
import com.homan.homan.ui.FoodAdapter;


public class FoodFragment extends Fragment {
    FoodViewModel viewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView foodListRecycler;
    FoodAdapter mAdapter;
    ProgressBar pb;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_food, container, false);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) rootView.getContext()).get(FoodViewModel.class);
        viewModel.getList().observe(getViewLifecycleOwner(), categories -> mAdapter.notifyDataSetChanged());
        pb = rootView.findViewById(R.id.foodlistprogressbar);
        pb.setVisibility(View.INVISIBLE);

        initializeViewElements(rootView);
        initializeRecyclerView(rootView);
        initializeViewHandlers();
        refreshCategoryList();

        Button addBtn = rootView.findViewById(R.id.foodaddbutton);
        addBtn.setOnClickListener(v -> {
            String type = "Food";
            Navigation.findNavController(v)
                    .navigate(FoodFragmentDirections.actionFoodFragmentToAddItemFragment2(type));
        });

        return rootView;
    }

    private void initializeViewElements(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            mSwipeRefreshLayout.setRefreshing(false);
        });
        foodListRecycler = view.findViewById(R.id.foodList);
    }

    private void initializeRecyclerView(View view) {
        mAdapter = new FoodAdapter(getContext(), viewModel);
        foodListRecycler.setAdapter(mAdapter);
        foodListRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void initializeViewHandlers() {
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshCategoryList);
    }

    private void refreshCategoryList() {
        viewModel.refreshCategoryList();
        mSwipeRefreshLayout.setRefreshing(false);
    }



}