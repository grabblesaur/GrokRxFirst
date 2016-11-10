package com.bbayar.grokrxfirst.tasks;

import rx.Observable;

/**
 * Sum all elements from observable and return observable
 * with single sum
 * Example:
 * Input stream (1,2,3,4,5)
 * Result stream (15)
 * Find suitable operator for this task.
 */

public class Task3 {

    public static Observable<Integer> sum(Observable<Integer> observable) {
        return Observable.just(1,2,3,4,5);
    }

}
