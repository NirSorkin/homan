package com.homan.homan.ui.cars;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;

import java.util.List;

public  class CarsFragmentViewModel extends ViewModel {
    private LiveData<List<Category>> mCarsList;

    public CarsFragmentViewModel() {
        Log.d("TAG","StudentListViewModel");
        mCarsList = Model.instance.getAll("Cars");
    }

    public LiveData<List<Category>> getList(){
        return mCarsList;
    }

    public void refreshCategoryList() {
        mCarsList = Model.instance.getAll("Cars");
    }
}
