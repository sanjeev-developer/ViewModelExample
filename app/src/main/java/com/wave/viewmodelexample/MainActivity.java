package com.wave.viewmodelexample;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static MainViewModel mainViewModel;
    SampleView samplemodel;
    TextView tvScoreA, tvScoreB;
    Button btnPlayerA, btnPlayerB;
    int countaa, countbb = 0;
    PeriodicWorkRequest periodicWorkRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        /**
         * initialized ViewModel
         */

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        samplemodel = ViewModelProviders.of(this).get(SampleView.class);

       // Data data = new Data.Builder().putString(MyWorker.TASK_DESC, "Task execute").build();
        Map<String, Object> map = new HashMap<>();
        map.put("s", mainViewModel);
        Data data = new Data.Builder().putAll(map).build();
        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();
        final OneTimeWorkRequest workRequest1 = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();
        final OneTimeWorkRequest workRequest2 = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();
        final OneTimeWorkRequest workRequest3 = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();

        periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 1, TimeUnit.MILLISECONDS).setInputData(data).build();

        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        tvScoreA.append(workInfo.getState().name() + "\n");
                    }
                });

        // Create the observer which updates the UI.
        final Observer<Integer> countaob = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newcounta) {
                // Update the UI, in this case, a TextView.
                tvScoreA.setText(""+newcounta);
                countaa = newcounta;
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mainViewModel.getClickCountA().observe(this, countaob);

        // Create the observer which updates the UI.
        final Observer<Integer> countbob = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newcountb) {
                // Update the UI, in this case, a TextView.
                tvScoreB.setText(""+newcountb);
                countbb = newcountb;
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mainViewModel.getClickCountB().observe(this, countbob);

    }


    private void initView() {
        // initialized all views here
        tvScoreA = findViewById(R.id.tvScorePlayerA);
        tvScoreB = findViewById(R.id.tvScorePlayerB);
        btnPlayerA = findViewById(R.id.btnPlayerA);
        btnPlayerB = findViewById(R.id.btnPlayerB);
        btnPlayerA.setOnClickListener(this);
        btnPlayerB.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlayerA:

                countaa++;
                mainViewModel.getClickCountA().setValue(countaa);
               // WorkManager.getInstance().enqueueUniquePeriodicWork("send_reminder_periodic", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);


                break;
            case R.id.btnPlayerB:

                countbb++;
                mainViewModel.getClickCountB().setValue(countbb);
                break;

            default:
        }
    }

    // Serialize a single object.
    public static String serializeToJson(Context context) {
        Gson gson = new Gson();
        return gson.toJson(context);
    }


}