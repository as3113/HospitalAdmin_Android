package com.example.hospitalapp3;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class NotificationService extends Service {

    private static final long TOAST_INTERVAL = 30000; // 30 seconds
    private Handler toastHandler;
    private boolean isServiceRunning;

    @Override
    public void onCreate() {
        super.onCreate();
        toastHandler = new Handler();
        isServiceRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        toastHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isServiceRunning) {
                    // Display a toast message
                    Toast.makeText(NotificationService.this, ":) remember to breath (:", Toast.LENGTH_SHORT).show();

                    // Schedule the next toast
                    toastHandler.postDelayed(this, TOAST_INTERVAL);
                }
            }
        }, TOAST_INTERVAL);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceRunning = false;
        toastHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}




