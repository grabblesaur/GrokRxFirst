package com.bbayar.grokrxfirst.tasks;

import rx.Observable;
import rx.observables.MathObservable;

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
        return MathObservable.sumInteger(observable);
    }

}
