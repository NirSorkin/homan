package com.homan.homan.ui.edit_item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class EditItemFragment extends Fragment {

    List<Category> categoryList = new LinkedList<>();
    MyAdapter myAdapter;
    ProgressBar pb;
    Button saveButton;
    Button deleteButton;
    EditText inputAmount;
    EditText inputDescription;
    Category item;
    ImageView imageAvatar;
    Button inputImage;
    String imageUrl;
    static final int REQUEST_IMAGE_CAPTURE = 1;

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
        imageAvatar = rootView.findViewById(R.id.imageView2);
        inputDescription.setText(item.getDesc());
        inputAmount.setText(item.getAmount());
//        Picasso.get().load(mProduct.getImage()).into(mProductImageView);
        Picasso.get().load(item.getImage()).into(imageAvatar);
        saveButton = rootView.findViewById(R.id.save);
        inputImage = rootView.findViewById(R.id.image_uploader);
        inputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editImage();
            }
        });
        saveButton.setOnClickListener(v -> EditItem(item, rootView));
        deleteButton = rootView.findViewById(R.id.delete);
        deleteButton.setOnClickListener(v -> DeleteItem(item, rootView));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        Navigation.findNavController(rootView).navigate(R.id.action_carsFragment2_to_addItemFragment2);
        return rootView;
    }

    private void editImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageAvatar.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageAvatar.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    private void EditItem(Category item, View rootView) {
        Category ct1 = item;
        String type = item.getCategoryType();
        Model.instance.deleteItem(ct1, () -> reloadData(ct1.getCategoryType()));
        Category ct = new Category();
        saveButton.setEnabled(false);
        String Amount = inputAmount.getText().toString().trim();
        String Description = inputDescription.getText().toString().trim();
//            Random r = new Random();
//            int num = r.nextInt(100000);
//            String stNum = ""+num;
//            ct.setHouseID(num);
        String name = UserModel.instance.getEmail() +"-" + Description + "-" + "Image";
        BitmapDrawable drawable = (BitmapDrawable)imageAvatar.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Model.instance.uploadImage(bitmap, name, new Model.UploadImageListener() {
            @Override
            public void onComplete(String url) {
                if(url == null){
                    displayFailedError();
                }else{
                    ct.setImage(url);
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

                    String userId = UserModel.instance.getEmail();
                    ct.setAmount(inputAmount.getText().toString());
                    ct.setDesc(inputDescription.getText().toString());
                    ct.setUserID(userId);
//            pb.setVisibility(View.VISIBLE);
                    ct.setCategoryType(type);
                    Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    String CurrentDate = mDay + "." + mMonth + "." + mYear;
                    ct.setDate(CurrentDate);

//            ModelFirebase.instance.addItem(ct, () -> reloadData(ct.getCategoryType()));
                    Model.instance.addItem(ct, () -> reloadData(ct.getCategoryType()));
                    saveButton.setEnabled(false);
                    displaySuccessEditAlertDialog(rootView);
                }
            }
        });
    }


//    private void EditItem(Category item, View rootView) {
//        Category ct = item;
//        Model.instance.deleteItem(ct, () -> reloadData(ct.getCategoryType()));
//        saveButton.setEnabled(false);
//        deleteButton.setEnabled(false);
//        String userId = UserModel.instance.getEmail();
//        ct.setAmount(inputAmount.getText().toString());
//        ct.setDesc(inputDescription.getText().toString());
//        ct.setUserID(userId);
//        Calendar c = Calendar.getInstance();
//        int mYear = c.get(Calendar.YEAR);
//        int mMonth = c.get(Calendar.MONTH);
//        int mDay = c.get(Calendar.DAY_OF_MONTH);
//        String CurrentDate = mDay + "." + mMonth + "." + mYear;
//        ct.setDate(CurrentDate);
//        Model.instance.updateItem(ct, () -> reloadData(ct.getCategoryType()));
//        saveButton.setEnabled(false);
//        deleteButton.setEnabled(false);
//        displaySuccessEditAlertDialog(rootView);
////            reloadData("Cars");
//    }



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

    private void displayFailedError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Operation Failed");
        builder.setMessage("Saving item failed, please try again later");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void displaySuccessEditAlertDialog(View view) {
        AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
        ad.setCancelable(true);
        ad.setTitle("Success");
        ad.setMessage("Item was updated successfully");
        ad.setButton("Close", (dialog, which) -> {
            dialog.dismiss();
            Navigation.findNavController(view).popBackStack();
//            Navigation.findNavController(view).navigate(R.id.action_editItemFragment_to_carsFragment2);
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
                Navigation.findNavController(view).popBackStack();
//                Navigation.findNavController(view).navigate(R.id.action_editItemFragment_to_carsFragment2);
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
