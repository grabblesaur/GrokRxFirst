package com.bbayar.grokrxfirst.tasks;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Create method that takes list of strings and creates an observable of integers,
 * that represents length of strings which contains letter 'r' (or 'R')
 */

public class Task1 {

    private List<String> list;
    private List<Integer> lengthsOfR;

    public Task1(List<String> list) {
        this.list = list;
        lengthsOfR = new ArrayList<>();
    }

    @NonNull
    public Observable<Integer> getLengthOfStringsWithR() {
        return Observable.from(list)
                .map(String::toLowerCase)
                .filter(s -> s.contains("r"))
                .map(String::length);
    }

    public List<String> getList() {
        return list;
    }

    public List<Integer> getLengthsOfR() {
        return lengthsOfR;
    }
}
