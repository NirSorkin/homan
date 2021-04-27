package com.homan.homan.Models;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.homan.homan.MyApplication;

import java.util.List;

public class Model {
    public final static Model instance = new Model();
    public final static UserModel users = UserModel.instance;
    ModelFirebase modelFirebase = new ModelFirebase();
    ModelSql modelSql = new ModelSql();
    private LiveData<List<Category>> categories;

    private Model(){ }

    public LiveData<List<Category>> getAll(String type) {
        if (categories == null) {
            categories = modelSql.getAll();
        }
        fetchUpdatedDataFromFirebase(null, type);
        return categories;
    }

    public interface UpdateListener {
        void onComplete();
    }

    private void fetchUpdatedDataFromFirebase(UpdateListener listener, String type) {
        SharedPreferences sharedPreferences = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sharedPreferences.getLong("lastUpdated", 0);

        modelFirebase.getAll(lastUpdated, result -> {
            long lastU = 0;
            for (Category ct : result) {
                modelSql.addItem(ct, null);
                if (ct.getLastUpdated() > lastU) {
                    lastU = ct.getLastUpdated();
                }
             /*   if (ct.getRemoved()) {
                    modelSql.delete(ct, null);
                }*/
            }
            sharedPreferences.edit().putLong("lastUpdated", lastU).commit();
            if (listener != null) listener.onComplete();
        },type);
    }





    public interface GetAllCategoriesListener{
        void onComplete(LiveData<List<Category>> data);
    }

/*    public void getAllByCategory(GetAllCategoriesListener listener, String type){
        modelFirebase.getAllByCategory(listener , type);
    }*/

    public interface  AddItemListener{
        void onComplete();
    }

    public void addItem(Category item,  AddItemListener listener){
        modelFirebase.addItem(item , listener);
    }

    public interface  UpdateItemListener extends  AddItemListener{}

    public void updateItem(Category item, UpdateItemListener listener){
        modelFirebase.updateItem(item , listener);
    }

    public interface DeleteListener extends AddItemListener{}
    public void deleteItem(Category ct , DeleteListener listener){
    modelFirebase.deleteItem(ct ,listener);
    }
}
