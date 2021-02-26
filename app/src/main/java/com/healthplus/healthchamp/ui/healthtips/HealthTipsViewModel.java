package com.healthplus.healthchamp.ui.healthtips;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HealthTipsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HealthTipsViewModel() {
        mText = new MutableLiveData<>();
       // mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}