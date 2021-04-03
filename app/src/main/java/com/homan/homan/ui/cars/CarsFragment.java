package com.homan.homan.ui.cars;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.LiveData;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class CarsFragment extends Fragment {
    List<Category> c = new LinkedList<Category>();
    RecyclerView carsList;
    MyAdapter myAdapter;
    ProgressBar pb;
    Button addBtn;

    //private CarsListfrgViewModel mViewModel;

  /*  Category itemCategories[] =  {
            new Category(7, "56546" , "Cars"),
            new Category(7, "45645456" , "Cars"),
            new Category(67, "5654456" , "food"),
            new Category(434, "565454546" , "others"),
            new Category(234, "5654645" , "clothing"),
            new Category(5, "5654456" , "houseHold")
    };*/


    public static CarsFragment newInstance() {
        return new CarsFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cars, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.carsList);
        pb = rootView.findViewById(R.id.carslistprogressbar);
        pb.setVisibility(View.INVISIBLE);
        addBtn = rootView.findViewById(R.id.carsaddbutton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myAdapter = new MyAdapter(rootView.getContext(), c , "Cars");
        recyclerView.setAdapter(myAdapter);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        reloadData();
        return rootView;
    }

    private void addNewItem() {
        Random r = new Random();
        int randomId = r.nextInt(111111 - 999999) + 111111;
        Category newItem = new Category(2 , String.valueOf(randomId), "Cars");
        Model.instance.addItem(newItem, new Model.AddItemListener() {
            @Override
            public void onComplete() {
                reloadData();
            }
        });
    }

    void reloadData(){
        pb.setVisibility(View.VISIBLE);
        addBtn.setEnabled(false);
        Model.instance.getAllByCategory(new Model.GetAllCategoriesListener(){

            @Override
            public void onComplete(List<Category> data) {
                c = data;
                for(Category ct : data){
                    Log.d("TAG" , "category type" + ct.getCategoryType());
                }
                pb.setVisibility(View.INVISIBLE);
                addBtn.setEnabled(true);
            }
        } , "Cars");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(CarsListfrgViewModel.class);
        // TODO: Use the ViewModel
    }

}