package com.homan.homan.Models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
                        //Log.d("TAG", ct.getDesc());
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
        // Add a new document with a generated ID
        db.collection("Users").document(UserModel.instance.getEmail()).collection(item.getCategoryType()).document(item.getDesc())
                .set(item)
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


    public void deleteItem(Category ct, Model.DeleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(ct.getCategoryType()).document(ct.getUserID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete();
            }
        });
    }
}
