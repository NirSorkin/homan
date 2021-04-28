package com.homan.homan.Models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


public class ModelFirebase {

    public interface GetAllListener {
        void onComplete(List<Category> categories);
    }
    public void getAll(Long lastUpdated, GetAllListener listener,String type) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp timestamp = new Timestamp(lastUpdated);
        List<Category> categories = new LinkedList<>();
        db.collection("Users").document(UserModel.instance.getEmail()).collection(type).whereGreaterThanOrEqualTo("lastUpdated", timestamp).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.d("TAG", "***Getting categories from Firebase***");
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Category ct = new Category();
                        ct.fromMap(doc.getData());
                        categories.add(ct);
                        Log.d("TAG", ct.getDesc());
                    }
                    listener.onComplete(categories);
                })
                .addOnFailureListener(e -> listener.onComplete(categories));
    }




/*    public void getAllByCategory(Model.GetAllCategoriesListener listener, String type) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(UserModel.instance.getEmail())
                .whereEqualTo(type, true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        LiveData<List<Category>> data = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Category ct = document.toObject(Category.class);
                                data.add(ct);
                            }
                        }
                        listener.onComplete(data);
                    }
                });
    }*/



    public void addItem(Category item, Model.AddItemListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newCategoryRef =  db.collection("Users").document(UserModel.instance.getEmail()).collection(item.getCategoryType()).document(item.getDesc());
        item.setOwnerId(UserModel.instance.getEmail());
        item.setRemoved(false);
        item.setUserID(item.getDesc());
        // Add a new document with a generated ID
            newCategoryRef
                .set(item.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Item added successfully");
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Error adding item");
                        listener.onComplete();
                    }
                });
    }

    public void updateItem(Category item, Model.UpdateItemListener listener) {
        addItem(item , listener);
    }


    public void deleteItem(Category item, Model.DeleteListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db.collection("Users").document(UserModel.instance.getEmail()).collection(item.getCategoryType()).document(item.getDesc())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Item deleted successfully");
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Error deleting item");
                        listener.onComplete();
                    }
                });
    }

    public void uploadImage(Bitmap imageBmp, String name, Model.UploadImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }
}
