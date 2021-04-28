package com.homan.homan.ui.insurnces;

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
import com.homan.homan.ui.HouseHoldAdapter;
import com.homan.homan.ui.InsurencesAdapter;
import com.homan.homan.ui.MyAdapter;
import com.homan.homan.ui.food.FoodFragmentDirections;
import com.homan.homan.ui.houseHold.HouseHoldViewModel;

import java.util.LinkedList;
import java.util.List;


public class InsurencesFragment extends Fragment {

    InsurencesViewModel viewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView insurencesListRecycler;
    InsurencesAdapter mAdapter;
    ProgressBar pb;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_insurences, container, false);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) rootView.getContext()).get(InsurencesViewModel.class);
        pb = rootView.findViewById(R.id.insurencelistprogressbar);
        pb.setVisibility(View.INVISIBLE);
        initializeViewElements(rootView);
        initializeRecyclerView(rootView);
        initializeViewHandlers();
        refreshCategoryList();

        Button addBtn = rootView.findViewById(R.id.insurenceaddbutton);
        addBtn.setOnClickListener(v -> {
            String type = "Insurences";
            Navigation.findNavController(v)
                    .navigate(InsurencesFragmentDirections.actionInsurencesFragmentToAddItemFragment2(type));
        });

        viewModel.getList().observe(getViewLifecycleOwner(), categories -> mAdapter.notifyDataSetChanged());
        return rootView;
    }

    private void initializeViewElements(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        insurencesListRecycler = view.findViewById(R.id.insurencesList);
    }

    private void initializeRecyclerView(View view) {
        mAdapter = new InsurencesAdapter(getContext(), viewModel);
        insurencesListRecycler.setAdapter(mAdapter);
        insurencesListRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void initializeViewHandlers() {
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshCategoryList);
    }

    private void refreshCategoryList() {
        viewModel.refreshCategoryList();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}