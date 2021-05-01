package com.homan.homan.ui.houseHold;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;

import java.util.List;

public class HouseHoldViewModel extends ViewModel {
    private LiveData<List<Category>> mHouseHoldList;

    public HouseHoldViewModel() {
        Log.d("TAG","ClothingViewModel");
        mHouseHoldList = Model.instance.getAll("HouseHold");
    }

    public LiveData<List<Category>> getList(){
        return mHouseHoldList;
    }

    public void refreshCategoryList() {
   /*     mHouseHoldList = Model.instance.getAll("HouseHold");*/
    }
}