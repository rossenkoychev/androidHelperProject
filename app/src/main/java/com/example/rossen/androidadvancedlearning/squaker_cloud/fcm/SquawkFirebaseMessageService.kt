package com.example.rossen.androidadvancedlearning.squaker_cloud.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.AsyncTask
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.squaker_cloud.MainSquakerActivity
import com.example.rossen.androidadvancedlearning.squaker_cloud.provider.SquawkContract
import com.example.rossen.androidadvancedlearning.squaker_cloud.provider.SquawkProvider
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/



/**
 * Listens for squawk FCM messages both in the background and the foreground and responds
 * appropriately
 * depending on type of message
 */
class SquawkFirebaseMessageService : FirebaseMessagingService() {

    public var token: String? = null

    override fun onNewToken(s: String?) {
        super.onNewToken(s)
        token = s
        Log.e("NEW_TOKEN", s)
    }



    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with FCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options\

        // The Squawk server always sends just *data* messages, meaning that onMessageReceived when
        // the app is both in the foreground AND the background

        Log.d(LOG_TAG, "From: " + remoteMessage.getFrom())

        // Check if message contains a data payload.

        val data = remoteMessage.getData()

        if (data.size > 0) {
            Log.d(LOG_TAG, "Message data payload: $data")

            // Send a notification that you got a new message
            sendNotification(data)
            insertSquawk(data)

        }
    }

    /**
     * Inserts a single squawk into the database;
     *
     * @param data Map which has the message data in it
     */
    private fun insertSquawk(data: Map<String, String>) {

        // Database operations should not be done on the main thread
        val insertSquawkTask = object : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                val newMessage = ContentValues()
                newMessage.put(SquawkContract.COLUMN_AUTHOR, data[JSON_KEY_AUTHOR])
                newMessage.put(SquawkContract.COLUMN_MESSAGE, data[JSON_KEY_MESSAGE]!!.trim { it <= ' ' })
                newMessage.put(SquawkContract.COLUMN_DATE, data[JSON_KEY_DATE])
                newMessage.put(SquawkContract.COLUMN_AUTHOR_KEY, data[JSON_KEY_AUTHOR_KEY])
                getContentResolver().insert(SquawkProvider.SquawkMessages.CONTENT_URI, newMessage)
                return null
            }
        }
        insertSquawkTask.execute()
    }

    /**
     * Create and show a simple notification containing the received FCM message
     *
     * @param data Map which has the message data in it
     */
    private fun sendNotification(data: Map<String, String>) {
        val intent = Intent(this, MainSquakerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Create the pending intent to launch the activity
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val author = data[JSON_KEY_AUTHOR]
        var message = data[JSON_KEY_MESSAGE]

        // If the message is longer than the max number of characters we want in our
        // notification, truncate it and add the unicode character for ellipsis
        if (message!!.length > NOTIFICATION_MAX_CHARACTERS) {
            message = message.substring(0, NOTIFICATION_MAX_CHARACTERS) + "\u2026"
        }

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_duck)
                .setContentTitle(String.format(getString(R.string.notification_message), author))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    companion object {

        private val JSON_KEY_AUTHOR = SquawkContract.COLUMN_AUTHOR
        private val JSON_KEY_AUTHOR_KEY = SquawkContract.COLUMN_AUTHOR_KEY
        private val JSON_KEY_MESSAGE = SquawkContract.COLUMN_MESSAGE
        private val JSON_KEY_DATE = SquawkContract.COLUMN_DATE

        private val NOTIFICATION_MAX_CHARACTERS = 30
        private val LOG_TAG = SquawkFirebaseMessageService::class.java.simpleName
    }
}