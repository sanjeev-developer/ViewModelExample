package com.wave.viewmodelexample;

import androidx.lifecycle.ViewModel;

public class SampleView extends ViewModel {

  int counta, countb=0;


    public int getCounta() {
        return counta;
    }

    public void setCounta() {

        counta++;
    }

    public int getCountb() {
        return countb;
    }

    public void setCountb() {
        countb++;
    }
}
