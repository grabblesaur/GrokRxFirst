package com.bbayar.grokrxfirst.tasks;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * Create method that takes list of strings and creates an observable of integers,
 * that represents length of strings which contains letter 'r' (or 'R')
 */

public class Task1 {

    private List<String> list;

    public Task1(List<String> list) {
        this.list = list;
    }

    @NonNull
    public Observable<Integer> getLengthOfStringsWithR() {
        return Observable.from(list)
                .map(String::toLowerCase)
                .map(s -> {
                    if (s.contains("r")) {
                        return s.length();
                    } else {
                        return null;
                    }
                });
    }

    public List<String> getList() {
        return list;
    }
}
