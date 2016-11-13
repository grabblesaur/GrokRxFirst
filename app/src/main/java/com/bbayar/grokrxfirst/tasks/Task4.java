package com.bbayar.grokrxfirst.tasks;

import rx.Observable;

/***
 * Method takes boolean stream of single boolean value and
 * two numbers streams
 *
 * If boolean value in stream is true, then you should choose first stream, in
 * other case - second
 *
 * if result stream has any number greater than 99, your observable should
 * finish with error
 *
 * Examples:
 * input boolean stream: (true)
 * first input stream: (5, 9, 12)
 * second input stream: (9, 210, 87)
 * result stream: (5, 9, 12)
 *
 * Input boolean stream: (false)
 * First input stream: (5, 19, 12)
 * Second input stream: (9, 210, 87)
 * Result stream: (9, Exception)
 */
public class Task4 {

    public static Observable<Integer> task4(Observable<Boolean> flagObserver,
                                            Observable<Integer> firstStream,
                                            Observable<Integer> secondStream) {

        return flagObserver
                .flatMap(b -> b ? firstStream : secondStream);
    }

}
