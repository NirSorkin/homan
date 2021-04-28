package com.homan.homan.ui.add_item;

import android.app.AlertDialog;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;
import com.homan.homan.Models.ModelFirebase;
import com.homan.homan.Models.UserModel;
import com.homan.homan.R;
import com.homan.homan.ui.MyAdapter;
import com.homan.homan.ui.cars.CarsFragment;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class AddItemFragment extends Fragment {
    List<Category> categoryList = new LinkedList<>();
    MyAdapter myAdapter;
    ProgressBar pb;
    Button saveButton;
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
        saveButton = rootView.findViewById(R.id.save);
        saveButton.setOnClickListener(v -> addNewItem("Cars", rootView));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        Navigation.findNavController(rootView).navigate(R.id.action_carsFragment2_to_addItemFragment2);
        return rootView;
    }
        private void addNewItem(String type, View rootView) {
            Category ct = new Category();
            saveButton.setEnabled(false);
            String Amount = inputAmount.getText().toString().trim();
            String Description = inputDescription.getText().toString().trim();
            if (Description.isEmpty()){
                inputDescription.setError("Description is Required.");
                saveButton.setEnabled(true);
                if (Amount.isEmpty()){
                    inputAmount.setError("Amount is Required.");
                    saveButton.setEnabled(true);
                    return;
                }
                return;
            }

            if (Amount.isEmpty()){
                inputAmount.setError("Amount is Required.");
                saveButton.setEnabled(true);
                if (Description.isEmpty()) {
                    inputDescription.setError("Description is Required.");
                    saveButton.setEnabled(true);
                    return;
                }
                return;
            }

            ct.setAmount(inputAmount.getText().toString());
            ct.setDesc(inputDescription.getText().toString());
            ct.setCategoryType(type);
            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            String CurrentDate = mDay + "." + mMonth + "." + mYear;
            ct.setDate(CurrentDate);

            Model.instance.addItem(ct, () -> reloadData(ct.getOwnerId()));

            saveButton.setEnabled(false);
            displaySuccessAlertDialog(rootView);

        }

    void reloadData(String type) {
        pb.setVisibility(View.VISIBLE);
        saveButton.setEnabled(false);
        Model.instance.getAll( "Cars");
    }

    private void displaySuccessAlertDialog(View view) {
        AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
        ad.setCancelable(true);
        ad.setTitle("Success");
        ad.setMessage("Item was created successfully");
        ad.setButton("Close", (dialog, which) -> {
            dialog.dismiss();
            Navigation.findNavController(view).navigate(R.id.action_addItemFragment2_to_carsFragment22);
        });
        ad.setOnCancelListener(dialog -> {
            dialog.dismiss();
            Navigation.findNavController(view).popBackStack();
        });
        ad.show();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(CarsListfrgViewModel.class);
        // TODO: Use the ViewModel
    }
}