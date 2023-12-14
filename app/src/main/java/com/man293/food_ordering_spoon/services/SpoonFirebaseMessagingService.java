package com.man293.food_ordering_spoon.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.views.activities.OrderDetailAdminActivity;

import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class SpoonFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        String title = message.getNotification().getTitle();
        String text = message.getNotification().getBody();
        Map<String, String> data = message.getData();
        Log.d("MSG_DATA", new Gson().toJson(data));
        if(data.size() > 0) {
            final String ORDER_ID = data.get("orderId");
                sendNotification(title, text, ORDER_ID);
        }
    }

    private void sendNotification(String title , String messageBody, String orderId) {
        Intent intent = new Intent(this, OrderDetailAdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("ORDER_ID", orderId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = "NEW_ORDER_CHANEL_ID";
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.audio);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher_v2)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "NEW_ORDER_CHANEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}