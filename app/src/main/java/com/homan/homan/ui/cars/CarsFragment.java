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

import java.util.List;


public class CarsFragment extends Fragment {

    CarsFragmentViewModel viewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView carsListRecycler;
    MyAdapter mAdapter;
    ProgressBar pb;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cars, container, false);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) rootView.getContext()).get(CarsFragmentViewModel.class);
        //RecyclerView recyclerView = rootView.findViewById(R.id.carsList);
        pb = rootView.findViewById(R.id.carslistprogressbar);
        pb.setVisibility(View.INVISIBLE);
/*        addBtn = rootView.findViewById(R.id.carsaddbutton);
        addBtn.setOnClickListener( v -> addNewItem());*/
        initializeViewElements(rootView);
        initializeRecyclerView(rootView);
        initializeViewHandlers();
        refreshCategoryList();


        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mAdapter = new MyAdapter(rootView.getContext(),"Cars");
        //carsListRecycler.setAdapter(mAdapter);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //carsListRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        Button insurencesBtn = rootView.findViewById(R.id.carsaddbutton);
        insurencesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_carsFragment2_to_addItemFragment2);
            }
        });
        //reloadData();
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