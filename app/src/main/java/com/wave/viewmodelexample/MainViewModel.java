package com.wave.viewmodelexample;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    // Create a LiveData with a String
    private MutableLiveData<Integer> clickCountA;

    public MutableLiveData<Integer> getClickCountA() {
        if (clickCountA == null) {
            clickCountA = new MutableLiveData<Integer>();
        }
        return clickCountA;
    }

    // Create a LiveData with a String
    private MutableLiveData<Integer> clickCountB;

    public MutableLiveData<Integer> getClickCountB() {
        if (clickCountB == null) {
            clickCountB = new MutableLiveData<Integer>();
        }
        return clickCountB;
    }
}
