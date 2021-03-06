package com.homan.homan.ui.home;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.homan.homan.MainActivity;
import com.homan.homan.Models.Model;
import com.homan.homan.R;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        getActivity().getViewModelStore().clear();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.homeTitle_Text);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ImageButton carsBtn = root.findViewById(R.id.homeCars_btn);
        carsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_carsFragment2);
            }
        });

        ImageButton foodBtn = root.findViewById(R.id.homeFood_btn);
        foodBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_foodFragment);
            }
        });

        ImageButton homeClothing_btn = root.findViewById(R.id.homeClothing_btn);
        homeClothing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_clothingFragment);
            }
        });

        ImageButton houseHold_btn = root.findViewById(R.id.homeHouseHold_btn);
        houseHold_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_houseHoldFragment);
            }
        });

        ImageButton insurencesBtn = root.findViewById(R.id.homeInsurences_btn);
        insurencesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_insurencesFragment);
            }
        });

        ImageButton otherBtn = root.findViewById(R.id.homeOther_btn);
        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_otherFragment);
            }
        });

        if(!Model.users.isLoggedIn()) {
            displayLoginAlertDialog(root);
        }

        return root;
    }

    private void displayLoginAlertDialog(View view) {
        AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
        ad.setCancelable(true);
        ad.setTitle("Alert");
        ad.setMessage("You need to login first");
        ad.setButton("Login", (dialog, which) -> {
            dialog.dismiss();
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_slideshow);
        });
        ad.setOnCancelListener(dialog -> {
            dialog.dismiss();
            Navigation.findNavController(view).popBackStack();
        });
        ad.show();
    }
}