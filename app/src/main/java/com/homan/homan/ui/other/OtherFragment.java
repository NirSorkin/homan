package com.homan.homan.ui.other;

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

public class OtherFragment extends Fragment {
    List<Category> otherList = new LinkedList<Category>();
    RecyclerView carsList;
    MyAdapter myAdapter;
    ProgressBar pb;
    //private CarsListfrgViewModel mViewModel;

    private SwipeRefreshLayout mSwipeRefreshLayout;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_other, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.otherList);
        pb = rootView.findViewById(R.id.otherlistprogressbar);
        pb.setVisibility(View.INVISIBLE);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //myAdapter = new MyAdapter(rootView.getContext(), "others");
        recyclerView.setAdapter(myAdapter);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        Button insurencesBtn = rootView.findViewById(R.id.otheraddbutton);
        insurencesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_otherFragment_to_addItemFragment2);
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