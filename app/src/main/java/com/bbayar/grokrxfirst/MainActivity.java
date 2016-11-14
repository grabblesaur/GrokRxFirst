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
import com.bbayar.grokrxfirst.tasks.Task3;
import com.bbayar.grokrxfirst.tasks.Task4;
import com.bbayar.grokrxfirst.tasks.Task5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

            case "task3":
                // need to init inputList with random integers
                Random r = new Random(47);
                int size = r.nextInt(10);
                for (int i = 0; i < size; i++) {
                    inputList.add(String.valueOf(r.nextInt(10)));
                }
                notifyDataWasAdded(inputAdapter, inputList);
                subscribeToTask3(inputList);
                break;

            case "task4":
                String[] tempArray = {"false", "1", "2", "41", "12", "509", "21"};
                inputList.addAll(Arrays.asList(tempArray));
                notifyDataWasAdded(inputAdapter, inputList);
                subscribeToTask4(inputList);
                break;

            case "task5":
                Integer[] task5First = {100, 17, 63};
                Integer[] task5Second = {15, 89, 27};

                for (int i = 0; i < (task5First.length + task5Second.length); i++) {
                    if (i < task5First.length) {
                        inputList.add(String.valueOf(task5First[i]));
                    } else {
                        inputList.add(String.valueOf(task5Second[i]));
                    }
                }

                notifyDataWasAdded(inputAdapter, inputList);
                subscribeToTask5(task5First, task5Second);
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

    public void subscribeToTask3(List<String> list) {
        List<Integer> listInteger = new ArrayList<>(list.size());
        for (String l : list) {
            listInteger.add(Integer.valueOf(l));
        }
        Task3.sum(Observable.from(listInteger))
                .subscribe(i -> {
                    resultList.add(String.valueOf(i));
                });
        notifyDataWasAdded(resultAdapter, resultList);
    }

    public void subscribeToTask4(List<String> list) {
        Boolean flag = Boolean.valueOf(list.get(0));
        Integer[] first = {Integer.valueOf(list.get(1)), Integer.valueOf(list.get(2)), Integer.valueOf(list.get(3))};
        Integer[] second = {Integer.valueOf(list.get(4)), Integer.valueOf(list.get(5)), Integer.valueOf(list.get(6))};
        Task4.task4(
                Observable.just(flag),
                Observable.from(first),
                Observable.from(second))
                .subscribe(
                        i -> {
                            resultList.add(String.valueOf(i));
                        },
                        throwable -> {
                            resultList.add("Exception");
                        });
        notifyDataWasAdded(resultAdapter, resultList);
    }

    private void subscribeToTask5(Integer[] first, Integer[] second) {
        Task5.gcdsObservable(Observable.from(first), Observable.from(second))
                .subscribe(integer -> resultList.add(String.valueOf(integer)));
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
