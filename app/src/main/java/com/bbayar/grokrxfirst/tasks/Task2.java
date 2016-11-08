package com.bbayar.grokrxfirst.tasks;

import rx.Observable;

/**
 * TODO:
 * <p>
 * Method takes observable of strings as a parameter
 * <p>
 * Supply all elements until you meet word END in the stream (END word should be excluded)
 * After that remove all repeated values from the stream
 */

public class Task2 {

    private Observable<String> observable;

    public Task2(Observable<String> observable) {
        this.observable = observable;
    }

    public Observable<String> getUniqueStringsBeforeEnd(Observable<String> o) {
        return o.takeUntil(s -> s.equals("END"))
                .distinct();
    }
}
