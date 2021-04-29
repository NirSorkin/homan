package com.homan.homan.ui.cars;

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
import com.homan.homan.ui.MyAdapter;
import com.homan.homan.ui.food.FoodViewModel;

import java.io.Serializable;
import java.util.List;


public class CarsFragment extends Fragment {

    CarsFragmentViewModel viewModel;
    FoodViewModel foodViewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView carsListRecycler;
    MyAdapter mAdapter;
    ProgressBar pb;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cars, container, false);
        foodViewModel = new ViewModelProvider((ViewModelStoreOwner) rootView.getContext()).get(FoodViewModel.class);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) rootView.getContext()).get(CarsFragmentViewModel.class);
        pb = rootView.findViewById(R.id.carslistprogressbar);
        pb.setVisibility(View.INVISIBLE);
        initializeViewElements(rootView);
        initializeRecyclerView(rootView);
        initializeViewHandlers();
        refreshCategoryList();

        Button addBtn = rootView.findViewById(R.id.carsaddbutton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "Cars";
//                Navigation.findNavController(v).navigate(R.id.action_carsFragment2_to_addItemFragment2("Cars"));
                Navigation.findNavController(v)
                        .navigate(CarsFragmentDirections.actionCarsFragment2ToAddItemFragment2(type));
            }
        });

        viewModel.getList().observe(getViewLifecycleOwner(), categories -> mAdapter.notifyDataSetChanged());
        return rootView;
    }

    private void initializeViewElements(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        carsListRecycler = view.findViewById(R.id.carsList);
    }

    private void initializeRecyclerView(View view) {
        mAdapter = new MyAdapter(getContext(), viewModel);
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