package com.homan.homan.ui.other;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;

import java.util.List;

public class OtherViewModel extends ViewModel {
    private LiveData<List<Category>> mOtherList;

    public OtherViewModel() {
        Log.d("TAG","OtherViewModel");
        mOtherList = Model.instance.getAll("Other");
    }

    public LiveData<List<Category>> getList(){
        return mOtherList;
    }

    public void refreshCategoryList() {
        /*mOtherList = Model.instance.getAll("Other");*/
    }
}
