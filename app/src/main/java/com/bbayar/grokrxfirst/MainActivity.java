package com.bbayar.grokrxfirst;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bbayar.grokrxfirst.adapter.CheeseAdapter;
import com.bbayar.grokrxfirst.adapter.ResultAdapter;
import com.bbayar.grokrxfirst.tasks.Task1;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "global";

    @BindView(R.id.cheese_recyclerview)
    RecyclerView cheeseRecyclerView;

    @BindView(R.id.result_recyclerview)
    RecyclerView resultRecyclerView;

    private CompositeSubscription compositeSubscription;
    private List<String> cheeses;
    private List<String> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cheeseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cheeseRecyclerView.setAdapter(new CheeseAdapter(cheeses));

        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(new ResultAdapter(results));
    }

    public void subscribeToTask1(List<String> list) {
        Task1 task1 = new Task1(list);
        compositeSubscription.add(task1
                .getLengthOfStringsWithR()
                .subscribe(i -> task1.getLengthsOfR().add(i)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
