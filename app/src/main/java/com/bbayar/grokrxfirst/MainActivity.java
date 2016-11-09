package com.bbayar.grokrxfirst;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bbayar.grokrxfirst.adapter.CheeseAdapter;
import com.bbayar.grokrxfirst.adapter.ResultAdapter;
import com.bbayar.grokrxfirst.tasks.Task1;
import com.bbayar.grokrxfirst.tasks.Task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "global";

    @BindView(R.id.cheese_recyclerview)
    RecyclerView cheeseRecyclerView;

    @BindView(R.id.result_recyclerview)
    RecyclerView resultRecyclerView;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private List<String> inputList, resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        inputList = Cheeses.randomList(100);
        inputList = new ArrayList<>();
        resultList = new ArrayList<>();

        //temporary method for task2
        initInputList();

        cheeseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cheeseRecyclerView.setAdapter(new CheeseAdapter(inputList));

        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(new ResultAdapter(resultList));

//        subscribeToTask1(inputList);
        subscribeToTask2(inputList);
    }

    private void initInputList() {
        String[] temp = {"John", "Sam", "Smith", "Richard", "Alex", "Sam", "Smith", "END"};
        inputList = Arrays.asList(temp);
    }

    public void subscribeToTask1(List<String> list) {
        Task1 task1 = new Task1(list);
        compositeSubscription.add(task1
                .getLengthOfStringsWithR()
                .map(String::valueOf)
                .subscribe(s -> {
                    resultList.add(s);
                    resultRecyclerView.getAdapter().notifyItemInserted(0);
                    resultRecyclerView.getAdapter().notifyItemRangeChanged(0, resultList.size());
                }));
    }

    public void subscribeToTask2(List<String> list) {
        Task2 task2 = new Task2(Observable.from(list));
        task2.getUniqueStringsBeforeEnd()
                .subscribe(s -> {
                    resultList.add(s);
                    resultRecyclerView.getAdapter().notifyItemInserted(0);
                    resultRecyclerView.getAdapter().notifyItemRangeChanged(0, resultList.size());
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
