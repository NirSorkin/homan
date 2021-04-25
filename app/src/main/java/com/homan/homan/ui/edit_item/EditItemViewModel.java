package com.homan.homan.ui.edit_item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EditItemViewModel {
    private MutableLiveData<String> mText;

    public EditItemViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
