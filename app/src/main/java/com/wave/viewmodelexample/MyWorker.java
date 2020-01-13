package com.wave.viewmodelexample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MyWorker extends Worker {

    //a public static string that will be used as the key
    //for sending and receiving data
    public static final String TASK_DESC = "task_desc";
    Context context;


    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {
        //getting the input data
       // String taskDesc = getInputData().getString(TASK_DESC);
       Map<String, Object> map =  getInputData().getKeyValueMap();
       for (String k: map.keySet())
       {
           Log.d("KEY--", k);
       }
//        context = deserializeFromJson(taskDesc);
//        displayNotification("My Worker", taskDesc);
       // return Result.SUCCESS();
        return null;
    }


    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());

       // ((MainActivity)context).letsee();

       // MainActivity.mainViewModel .getClickCountA().setValue(115);

    }

    // Deserialize to single object.
    public static Context deserializeFromJson(String contextstring) {
        Gson gson = new Gson();
        return gson.fromJson(contextstring, Context.class);
    }
}
