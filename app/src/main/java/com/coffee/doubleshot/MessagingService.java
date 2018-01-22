package com.coffee.doubleshot;

import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

/**
 * Created by rubenbaghajyan on 10/3/17.
 */

public class MessagingService extends FirebaseMessagingService {
	private static final String TAG = "FCM Service";

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		// TODO: Handle FCM messages here.
		// If the application is in the foreground handle both data and notification messages here.
		// Also if you intend on generating your own notifications as a result of a received FCM
		// message, here is where that should be initiated.
		Log.d(TAG, "From: " + remoteMessage.getFrom());
		Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

		setBadge(getApplicationContext(), 10);

	}

	public static void setBadge(Context context, int count) {
		String launcherClassName = "com.coffee.doubleshot.MainActivity";
		Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
		intent.putExtra("badge_count", count);
		intent.putExtra("badge_count_package_name", context.getPackageName());
		intent.putExtra("badge_count_class_name", launcherClassName);
		context.sendBroadcast(intent);
	}

}