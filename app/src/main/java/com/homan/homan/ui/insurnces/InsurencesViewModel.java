package com.homan.homan.ui.insurnces;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homan.homan.Models.Category;
import com.homan.homan.Models.Model;

import java.util.List;

public class InsurencesViewModel  extends ViewModel {
    private LiveData<List<Category>> mInsurencesList;

    public InsurencesViewModel() {
        Log.d("TAG","ClothingViewModel");
        mInsurencesList = Model.instance.getAll("Insurences");
    }

    public LiveData<List<Category>> getList(){
        return mInsurencesList;
    }

    public void refreshCategoryList() {
        mInsurencesList = Model.instance.getAll("Insurences");
    }
}
