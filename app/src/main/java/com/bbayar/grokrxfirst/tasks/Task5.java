package com.bbayar.grokrxfirst.tasks;

import java.math.BigInteger;

import rx.Observable;

public class Task5 {
    /**
     * TODO : you have two streams of integers of equal length
     * <p>
     * Return one stream of the same length with GCDs
     * of the original streams values
     * <p>
     * Example:
     * Stream 1 (100, 17, 63)
     * Stream 2 (15, 89, 27)
     * Result stream (5, 1, 9)
     * <p>
     * You can use {@link java.math.BigInteger#gcd(BigInteger)}
     */
    public static Observable<Integer> gcdsObservable(Observable<Integer> first,
                                                     Observable<Integer> second) {
        return Observable.zip(first, second,
                (i1, i2) -> {
                    BigInteger result = BigInteger.valueOf(i1).gcd(BigInteger.valueOf(i2));
                    return Integer.valueOf(String.valueOf(result));
                });
    }
}
