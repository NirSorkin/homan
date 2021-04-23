package com.homan.homan.Models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class ModelFirebase {
    public void getAllByCategory(Model.GetAllCategoriesListener listener, String type) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(UserModel.instance.getEmail())
                .whereEqualTo(type, true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Category> data = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Category ct = document.toObject(Category.class);
                                data.add(ct);
                            }
                        }
                        listener.onComplete(data);
                    }
                });
    }



    public void addItem(Category item, Model.AddItemListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
// Add a new document with a generated ID
        db.collection(UserModel.instance.getEmail()).document(item.getCategoryType()).collection(item.getDesc()).document(item.getDate())
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
