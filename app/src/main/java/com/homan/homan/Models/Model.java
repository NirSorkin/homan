package com.homan.homan.Models;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Model {
    public final static Model instance = new Model();
    public final static UserModel users = UserModel.instance;
    ModelFirebase modelFirebase = new ModelFirebase();
    ModelSql modelSql = new ModelSql();

    private Model(){

    }

    public interface GetAllCategoriesListener{
        void onComplete( List<Category> data);
    }

    public void getAllByCategory(GetAllCategoriesListener listener, String type){
        modelFirebase.getAllByCategory(listener , type);
    }

    public interface  AddItemListener{
        void onComplete();
    }

    public void addItem(Category item, AddItemListener listener){
        modelFirebase.addItem(item , listener);
    }

    public interface  UpdateItemListener extends  AddItemListener{}

    public void updateItem(Category item, UpdateItemListener listener){
        modelFirebase.updateItem(item , listener);
    }
}
