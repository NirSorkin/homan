package com.homan.homan.ui.add_item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.media.Image;
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

import com.google.firebase.firestore.FieldValue;
import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;
import com.homan.homan.Models.ModelFirebase;
import com.homan.homan.Models.UserModel;
import com.homan.homan.R;
import com.homan.homan.ui.MyAdapter;
import com.homan.homan.ui.cars.CarsFragment;
import com.homan.homan.ui.edit_item.EditItemFragmentArgs;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddItemFragment extends Fragment {
    List<Category> categoryList = new LinkedList<>();
    MyAdapter myAdapter;
    ProgressBar pb;
    Button saveButton;
    EditText inputAmount;
    EditText inputDescription;
    ImageView imageAvatar;
    Button inputImage;
    String imageUrl;
    String type;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public static AddItemFragment newInstance() {
        return new AddItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_item, container, false);
        pb = rootView.findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        type = AddItemFragmentArgs.fromBundle(getArguments()).getType();
        inputDescription = rootView.findViewById(R.id.input_description);
        inputAmount = rootView.findViewById(R.id.input_amount);
        saveButton = rootView.findViewById(R.id.save);
        imageAvatar = rootView.findViewById(R.id.imageView2);
        inputImage = rootView.findViewById(R.id.image_uploader);
        inputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editImage();
            }
        });

        saveButton.setOnClickListener(v -> addNewItem(type, rootView));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        Navigation.findNavController(rootView).navigate(R.id.action_carsFragment2_to_addItemFragment2);
        return rootView;
    }

//    private void editImage() {
//        Intent takePictureIntent = new Intent(
//                MediaStore.ACTION_IMAGE_CAPTURE);
////        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
////            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
////        }
//        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE &&
//                resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageAvatar.setImageBitmap(imageBitmap);
//        }
//    }

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

    private void addNewItem(String type, View rootView) {
        Category ct = new Category();
        saveButton.setEnabled(false);
        String Amount = inputAmount.getText().toString().trim();
        String Description = inputDescription.getText().toString().trim();
        String name = UserModel.instance.getEmail() +"-" + Description + "-" + "Image";
        BitmapDrawable drawable = (BitmapDrawable)imageAvatar.getDrawable();
        if(drawable == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Operation Failed");
            builder.setMessage("Saving item failed, must contain an image");
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Navigation.findNavController(rootView).popBackStack();
                }
            });
            builder.show();
        }
        if(drawable != null)
        {
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
                        displaySuccessAlertDialog(rootView);
                    }
                }
            });

        }

    }


    void reloadData(String type) {
        pb.setVisibility(View.VISIBLE);
        saveButton.setEnabled(false);
        Model.instance.getAll( type);
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

    private void displaySuccessAlertDialog(View view) {
        AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
        ad.setCancelable(true);
        ad.setTitle("Success");
        ad.setMessage("Item was created successfully");
        ad.setButton("Close", (dialog, which) -> {
            dialog.dismiss();
            Navigation.findNavController(view).popBackStack();
//            Navigation.findNavController(view).navigate(R.id.action_addItemFragment2_to_carsFragment22);
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