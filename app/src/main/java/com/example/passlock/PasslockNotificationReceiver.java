package com.example.passlock;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PasslockNotificationReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.example.passlock.SEND_NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!ACTION.equals(intent.getAction())) return;

        NotificationHelper.createNotificationChannel(context);

        // Intent to launch PasswordTestActivity
        Intent launchIntent = PasswordTestActivity.intentFactory(context);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        PendingIntent actionIntent = PendingIntent.getActivity(
                context, 1, launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("PassLock Reminder")
                        .setContentText("Your password safety matters. Test it now!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .addAction(android.R.drawable.ic_input_add, "Test Now", actionIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }

}


