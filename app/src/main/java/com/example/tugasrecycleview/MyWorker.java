package com.example.tugasrecycleview;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public MyWorker(@NonNull Context context, @NonNull
            WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        displayNotification("Annyeong!!", "Pesanan Album Anda Segera Kami Proses");
        return Result.success();
    }

    public void displayNotification(String task, String desc) {
        NotificationManager manager =
            (NotificationManager)

                    getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("Annyeong!!", "Pesanan Album Anda Segera Kami Proses", NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel); NotificationCompat.Builder builder = new
                NotificationCompat.Builder(getApplicationContext(),
                "Notifiksai!!")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.drawable.fs)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

                .setCategory(NotificationCompat.CATEGORY_MESSAGE); manager.notify(1, builder.build());
    }
}

