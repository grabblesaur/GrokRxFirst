package com.bbayar.grokrxfirst;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bbayar.grokrxfirst.adapter.CheeseAdapter;
import com.bbayar.grokrxfirst.adapter.ResultAdapter;
import com.bbayar.grokrxfirst.tasks.Task1;

import java.util.ArrayList;
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

    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private List<String> cheeses, results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cheeses = Cheeses.randomList(100);
        results = new ArrayList<>();

        cheeseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cheeseRecyclerView.setAdapter(new CheeseAdapter(cheeses));

        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(new ResultAdapter(results));

        subscribeToTask1(cheeses);
    }

    public void subscribeToTask1(List<String> list) {
        Task1 task1 = new Task1(list);
        compositeSubscription.add(task1
                .getLengthOfStringsWithR()
                .map(String::valueOf)
                .subscribe(s -> {
                    results.add(s);
                    resultRecyclerView.getAdapter().notifyItemInserted(0);
                    resultRecyclerView.getAdapter().notifyItemRangeChanged(0, results.size());
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
