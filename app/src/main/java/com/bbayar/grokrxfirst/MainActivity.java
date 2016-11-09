package com.bbayar.grokrxfirst;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cheese_recyclerview)
    RecyclerView inputRecyclerView;

    @BindView(R.id.result_recyclerview)
    RecyclerView resultRecyclerView;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private List<String> inputList, resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        inputList = new ArrayList<>();
        resultList = new ArrayList<>();

        inputRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        inputRecyclerView.setAdapter(new CheeseAdapter(inputList));

        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(new ResultAdapter(resultList));
    }

    private void initInputList(String taskName) {
        resetLists();
        switch (taskName) {
            case "task1":
                inputList.addAll(Cheeses.randomList(25));
                inputRecyclerView.getAdapter().notifyItemInserted(0);
                inputRecyclerView.getAdapter().notifyItemRangeChanged(0, inputList.size());
                subscribeToTask1(inputList);
                break;

            case "task2":
                String[] temp = {"John", "Sam", "Smith", "Richard", "Alex", "Sam", "Smith", "END"};
                inputList.addAll(Arrays.asList(temp));
                inputRecyclerView.getAdapter().notifyItemInserted(0);
                inputRecyclerView.getAdapter().notifyItemRangeChanged(0, inputList.size());
                subscribeToTask2(inputList);
                break;
        }
    }

    private void resetLists() {
        inputList.clear();
        inputRecyclerView.getAdapter().notifyItemRemoved(0);
        inputRecyclerView.getAdapter().notifyItemRangeRemoved(0, inputList.size());
        resultList.clear();
        resultRecyclerView.getAdapter().notifyItemRemoved(0);
        resultRecyclerView.getAdapter().notifyItemRangeRemoved(0, resultList.size());
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
