package com.homan.homan.ui.add_item;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;
import com.homan.homan.Models.UserModel;
import com.homan.homan.R;
import com.homan.homan.ui.MyAdapter;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class AddItemFragment extends Fragment {
    List<Category> categoryList = new LinkedList<>();
    MyAdapter myAdapter;
    ProgressBar pb;
    Button submitButton;
    EditText inputAmount;
    EditText inputDescription;


    public static AddItemFragment newInstance() {
        return new AddItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_item, container, false);
        pb = rootView.findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        inputDescription = rootView.findViewById(R.id.input_description);
        inputAmount = rootView.findViewById(R.id.input_amount);
        submitButton = rootView.findViewById(R.id.submit);
        submitButton.setOnClickListener(v -> addNewItem("Cars"));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return rootView;
    }
        private void addNewItem(String type) {
            Category ct = new Category();
            submitButton.setEnabled(false);
            String userId = UserModel.instance.getEmail();
            ct.setAmount(inputAmount.getText().toString());
            ct.setDesc(inputDescription.getText().toString());
            ct.setUserID(userId);
            pb.setVisibility(View.VISIBLE);
            ct.setCategoryType(type);
            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            String CurrentDate = mDay + "." + mMonth + "." + mYear;
            ct.setDate(CurrentDate);
            Model.instance.addItem(ct,  () -> reloadData(ct.getCategoryType()));

        }

        void reloadData(String type) {
            pb.setVisibility(View.VISIBLE);
            submitButton.setEnabled(false);
            Model.instance.getAll( "Cars");
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(CarsListfrgViewModel.class);
        // TODO: Use the ViewModel
    }
}