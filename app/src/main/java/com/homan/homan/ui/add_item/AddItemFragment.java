package com.homan.homan.ui.add_item;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;
import com.homan.homan.Models.UserModel;
import com.homan.homan.R;
import com.homan.homan.ui.MyAdapter;
import com.homan.homan.ui.cars.CarsFragment;

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
        inputDescription = rootView.findViewById(R.id.input_description);
        inputAmount = rootView.findViewById(R.id.input_amount);
        submitButton = rootView.findViewById(R.id.submit);
        submitButton.setOnClickListener(v -> addNewItem());
        return rootView;
    }
        private void addNewItem() {
            submitButton.setEnabled(false);
            String userId = UserModel.instance.getEmail();
            int houseId = 777;
            Category newItem = new Category(houseId , userId, "Cars");

            pb.setVisibility(View.VISIBLE);
            Model.instance.addItem(newItem, () -> reloadData());

        }

        void reloadData() {
            pb.setVisibility(View.VISIBLE);
            submitButton.setEnabled(false);
            Model.instance.getAllByCategory(data -> {
                categoryList = data;
                for (Category ct : data) {
                    Log.d("TAG", "category type" + " " + ct.getCategoryType());
                }
                pb.setVisibility(View.INVISIBLE);
                submitButton.setEnabled(true);

            }, "Cars");
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(CarsListfrgViewModel.class);
        // TODO: Use the ViewModel
    }
}