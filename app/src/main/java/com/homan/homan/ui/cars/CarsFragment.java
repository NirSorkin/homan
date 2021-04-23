package com.homan.homan.ui.cars;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;
import com.homan.homan.Models.ModelSql;
import com.homan.homan.R;
import com.homan.homan.ui.MyAdapter;

import java.util.LinkedList;
import java.util.List;


public class CarsFragment extends Fragment {
    List<Category> categoryList = new LinkedList<>();
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

        RecyclerView recyclerView = rootView.findViewById(R.id.carsList);
        pb = rootView.findViewById(R.id.carslistprogressbar);
        pb.setVisibility(View.INVISIBLE);
        addBtn = rootView.findViewById(R.id.carsaddbutton);
        addBtn.setOnClickListener(v -> addNewItem());

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myAdapter = new MyAdapter(rootView.getContext(), categoryList, "Cars");
        recyclerView.setAdapter(myAdapter);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        Button insurencesBtn = rootView.findViewById(R.id.carsaddbutton);
        insurencesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_carsFragment2_to_addItemFragment2);
            }
        });

        reloadData();
        return rootView;
    }

    private void addNewItem() {
        addBtn.setEnabled(false);
        Category newItem = new Category();
        pb.setVisibility(View.VISIBLE);
        Model.instance.addItem(newItem, () -> reloadData());
    }

    void reloadData(){
        pb.setVisibility(View.VISIBLE);
        addBtn.setEnabled(false);
        Model.instance.getAllByCategory(data -> {
            categoryList = data;
            for(Category ct : data){
                Log.d("TAG" , "category type" +" " + ct.getCategoryType());
            }
            pb.setVisibility(View.INVISIBLE);
            addBtn.setEnabled(true);
            myAdapter.notifyDataSetChanged();
        }, "Cars");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(CarsListfrgViewModel.class);
        // TODO: Use the ViewModel
    }

}