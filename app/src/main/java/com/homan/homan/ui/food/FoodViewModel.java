package com.homan.homan.ui.food;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;

import java.util.List;

public class FoodViewModel  extends ViewModel {
    private LiveData<List<Category>> mFoodList;

    public FoodViewModel() {
        Log.d("TAG","FoodViewModel");
        mFoodList = Model.instance.getAll("Food");
    }

    public LiveData<List<Category>> getList(){
        return mFoodList;
    }

    public void refreshCategoryList() {
        mFoodList = Model.instance.getAll("Food");
    }
}