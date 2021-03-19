package com.homan.homan.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.homan.homan.Models.Category;
import com.homan.homan.R;

public class CarsListfrg extends Fragment {

    private CarsListfrgViewModel mViewModel;

    public static CarsListfrg newInstance() {
        return new CarsListfrg();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cars_listfrg_fragment, container, false);
   /*     RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.carsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Category itemCategories[] =  {
                new Category(7, "56546" , "Cars"),
                new Category(67, "5654456" , "Caradsfs"),
                new Category(434, "565454546" , "Cadsfars"),
                new Category(234, "5654645" , "Cadrs"),
                new Category(5, "5654456" , "Cs")
        };
        MyAdapter myAdapter = new MyAdapter(itemCategories);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());*/
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CarsListfrgViewModel.class);
        // TODO: Use the ViewModel
    }

}