package com.homan.homan.ui.houseHold;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.homan.homan.ui.MyAdapter;

import java.util.LinkedList;
import java.util.List;


public class HouseHoldFragment extends Fragment {
    List<Category> hHoldList = new LinkedList<Category>();
    RecyclerView carsList;
    MyAdapter myAdapter;
    ProgressBar pb;
    //private CarsListfrgViewModel mViewModel;
    Category itemCategories[] =  {
            new Category(7, "56546" , "Cars"),
            new Category(7, "45645456" , "Cars"),
            new Category(67, "5654456" , "food"),
            new Category(434, "565454546" , "others"),
            new Category(234, "5654645" , "clothing"),
            new Category(5, "5654456" , "houseHold")
    };
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static HouseHoldFragment newInstance() {
        return new HouseHoldFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_house_hold, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.houseList);
        pb = rootView.findViewById(R.id.houseHoldlistprogressbar);
        pb.setVisibility(View.INVISIBLE);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myAdapter = new MyAdapter(rootView.getContext(),hHoldList , "houseHold");
        recyclerView.setAdapter(myAdapter);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        Button insurencesBtn = rootView.findViewById(R.id.houseHoldaddbutton);
        insurencesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_houseHoldFragment_to_addItemFragment2);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(CarsListfrgViewModel.class);
        // TODO: Use the ViewModel
    }

}