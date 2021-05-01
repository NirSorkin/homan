package com.homan.homan.ui.clothing;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;

import java.util.List;

public class ClothingViewModel extends ViewModel {
    private LiveData<List<Category>> mClothingList;

    public ClothingViewModel() {
        Log.d("TAG","ClothingViewModel");
        mClothingList = Model.instance.getAll("Clothing");
    }

    public LiveData<List<Category>> getList(){
        return mClothingList;
    }

    public void refreshCategoryList() {
        /*mClothingList = Model.instance.getAll("Clothing");*/
    }
}
