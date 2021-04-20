package com.homan.homan.Models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class ModelFirebase {
    public void getAllByCategory(Model.GetAllCategoriesListener listener, String type) {
        List<Category> data = new LinkedList<Category>();
        listener.onComplete(data);
    }

    public void addItem(Category item, Model.AddItemListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

/*        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);*/

// Add a new document with a generated ID
        db.collection("Items").document(item.getUserID())
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
}
