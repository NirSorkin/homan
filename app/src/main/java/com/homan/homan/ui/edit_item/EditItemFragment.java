package com.homan.homan.ui.edit_item;

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

public class EditItemFragment extends Fragment {

    List<Category> categoryList = new LinkedList<>();
    MyAdapter myAdapter;
    ProgressBar pb;
    Button saveButton;
    Button deleteButton;
    EditText inputAmount;
    EditText inputDescription;
    Category item;

    public static EditItemFragment newInstance() {
        return new EditItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_item, container, false);
        pb = rootView.findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        item = EditItemFragmentArgs.fromBundle(getArguments()).getItemID();
        Category oldItem = EditItemFragmentArgs.fromBundle(getArguments()).getItemID();
        inputDescription = rootView.findViewById(R.id.input_description);
        inputAmount = rootView.findViewById(R.id.input_amount);
        inputDescription.setText(item.getDesc());
        inputAmount.setText(item.getAmount());
        saveButton = rootView.findViewById(R.id.save);
        saveButton.setOnClickListener(v -> EditItem(item, rootView));
        deleteButton = rootView.findViewById(R.id.delete);
        deleteButton.setOnClickListener(v -> DeleteItem(item, rootView));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        Navigation.findNavController(rootView).navigate(R.id.action_carsFragment2_to_addItemFragment2);
        return rootView;
    }
    private void EditItem(Category item, View rootView) {
        Category ct = item;
        Model.instance.deleteItem(ct, () -> reloadData(ct.getCategoryType()));
        saveButton.setEnabled(false);
        deleteButton.setEnabled(false);
        String userId = UserModel.instance.getEmail();
        ct.setAmount(inputAmount.getText().toString());
        ct.setDesc(inputDescription.getText().toString());
        ct.setUserID(userId);
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String CurrentDate = mDay + "." + mMonth + "." + mYear;
        ct.setDate(CurrentDate);
        Model.instance.updateItem(ct, () -> reloadData(ct.getCategoryType()));
        saveButton.setEnabled(false);
        deleteButton.setEnabled(false);
        displaySuccessEditAlertDialog(rootView);
//            reloadData("Cars");
    }

    private void DeleteItem(Category item, View rootView) {
        Category ct = item;
        saveButton.setEnabled(false);
        deleteButton.setEnabled(false);
        Model.instance.deleteItem(ct, () -> reloadData(ct.getCategoryType()));
        saveButton.setEnabled(false);
        deleteButton.setEnabled(false);
        displaySuccessDeleteAlertDialog(rootView);
    }

    void reloadData(String type) {
        pb.setVisibility(View.VISIBLE);
        saveButton.setEnabled(false);
        Model.instance.getAll( "Cars");
    }

    private void displaySuccessEditAlertDialog(View view) {
        AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
        ad.setCancelable(true);
        ad.setTitle("Success");
        ad.setMessage("Item was updated successfully");
        ad.setButton("Close", (dialog, which) -> {
            dialog.dismiss();
            Navigation.findNavController(view).navigate(R.id.action_editItemFragment_to_carsFragment2);
        });
        ad.setOnCancelListener(dialog -> {
            dialog.dismiss();
            Navigation.findNavController(view).popBackStack();
        });
        ad.show();
    }
        private void displaySuccessDeleteAlertDialog(View view) {
            AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
            ad.setCancelable(true);
            ad.setTitle("Success");
            ad.setMessage("Item was deleted successfully");
            ad.setButton("Close", (dialog, which) -> {
                dialog.dismiss();
                Navigation.findNavController(view).navigate(R.id.action_editItemFragment_to_carsFragment2);
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
