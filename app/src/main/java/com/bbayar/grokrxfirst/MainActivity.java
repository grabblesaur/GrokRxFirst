package com.bbayar.grokrxfirst;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.bbayar.grokrxfirst.tasks.Task1;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "global";

    @BindView(R.id.tv1)
    TextView textView;

    private Subscription task1Subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<String> cheeses = Cheeses.randomList(5);
        for (String c : cheeses) {
            Log.i(TAG, "cheese: " + c);
        }
    }

    // TODO: реализовать подписку на задачи, а также их корректную обработку.
    public void subscribeToTask1(List<String> list) {
        Task1 task1 = new Task1(list);
        task1Subscription = task1.getLengthOfStringsWithR()
                .subscribe(i -> task1.getLengthsOfR().add(i));
    }

    @Override
    protected void onPause() {
        super.onPause();
        /**
         * TODO: реализовать список подписок, от которых нужно отписываться с проверкой условия
         */
        task1Subscription.unsubscribe();
    }
}
