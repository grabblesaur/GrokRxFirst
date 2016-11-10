package com.bbayar.grokrxfirst;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bbayar.grokrxfirst.adapter.InputAdapter;
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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cheese_recyclerview)
    RecyclerView inputRecyclerView;

    @BindView(R.id.result_recyclerview)
    RecyclerView resultRecyclerView;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private List<String> inputList, resultList;
    private InputAdapter inputAdapter;
    private ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        inputList = new ArrayList<>();
        resultList = new ArrayList<>();

        inputAdapter = new InputAdapter(inputList);
        inputRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        inputRecyclerView.setAdapter(inputAdapter);

        resultAdapter = new ResultAdapter(resultList);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(resultAdapter);
    }

    private void initInputList(String taskName) {

        if (!inputList.isEmpty()&& !resultList.isEmpty()) {
            resetData();
        }

        switch (taskName) {
            case "task1":
                inputList.addAll(Cheeses.randomList(25));
                notifyDataWasAdded(inputAdapter, inputList);
                subscribeToTask1(inputList);
                break;

            case "task2":
                String[] temp = {"John", "Sam", "Smith", "Richard", "Alex", "Sam", "Smith", "END"};
                inputList.addAll(Arrays.asList(temp));
                notifyDataWasAdded(inputAdapter, inputList);
                subscribeToTask2(inputList);
                break;
        }
    }

    private void resetData() {
        int inputSize = inputList.size();
        int resultSize = resultList.size();
        if (inputSize > 0 || resultSize > 0) {

            for (int i = 0; i < inputSize; i++) {
                inputList.remove(0);
            }
            inputAdapter.notifyItemRangeRemoved(0, inputSize);

            for (int j = 0; j < resultSize; j++) {
                resultList.remove(0);
            }
            resultAdapter.notifyItemRangeRemoved(0, resultSize);
        }
    }

    public void notifyDataWasAdded(RecyclerView.Adapter adapter, List<String> list) {
        adapter.notifyItemInserted(0);
        adapter.notifyItemRangeChanged(0, list.size());
    }

    public void subscribeToTask1(List<String> list) {
        Task1 task1 = new Task1(list);
        compositeSubscription.add(task1
                .getLengthOfStringsWithR()
                .map(String::valueOf)
                .subscribe(s -> {
                    resultList.add(s);
                }));
        notifyDataWasAdded(resultAdapter, resultList);
    }

    public void subscribeToTask2(List<String> list) {
        Task2 task2 = new Task2(Observable.from(list));
        task2.getUniqueStringsBeforeEnd()
                .subscribe(s -> {
                    resultList.add(s);
                });
        notifyDataWasAdded(resultAdapter, resultList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        int id = item.getItemId();
        String task = "";

        switch (id) {
            case R.id.task1:
                task = "task1";
                break;
            case R.id.task2:
                task = "task2";
                break;
            case R.id.task3:
                task = "task3";
                break;
            case R.id.task4:
                task = "task4";
                break;
        }
        initInputList(task);
        Toast.makeText(this, "doing " + task, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
