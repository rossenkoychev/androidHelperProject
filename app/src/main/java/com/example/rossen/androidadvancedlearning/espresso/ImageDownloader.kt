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

package com.example.rossen.androidadvancedlearning.espresso

import android.content.Context
import android.os.Handler
import android.widget.Toast
import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.espresso.model.Tea
import java.util.*

/**
 * Takes a String and returns it after a while via a callback.
 *
 *
 * This executes a long-running operation on a different thread that results in problems with
 * Espresso if an [IdlingResource] is not implemented and registered.
 */
internal object ImageDownloader {

    private val DELAY_MILLIS = 3000

    // Create an ArrayList of mTeas
    val mTeas = ArrayList<Tea>()

    internal interface DelayerCallback {
        fun onDone(teas: ArrayList<Tea>)
    }

    /**
     * This method is meant to simulate downloading a large image file which has a loading time
     * delay. This could be similar to downloading an image from the internet.
     * For simplicity, in this hypothetical situation, we've provided the image in
     * [/order_activity_tea_image.jpg][drawable].
     * We simulate a delay time of [.DELAY_MILLIS] and once the time
     * is up we return the image back to the calling activity via a [DelayerCallback].
     * @param callback used to notify the caller asynchronously
     */
    fun downloadImage(context: Context, callback: DelayerCallback?,
                      idlingResource: SimpleIdlingResource?) {

        /**
         * The IdlingResource is null in production as set by the @Nullable annotation which means
         * the value is allowed to be null.
         *
         * If the idle state is true, Espresso can perform the next action.
         * If the idle state is false, Espresso will wait until it is true before
         * performing the next action.
         */
        idlingResource?.setIdleState(false)

        // Display a toast to let the user know the images are downloading
        val text = context.getString(R.string.loading_msg)
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(context, text, duration)
        toast.show()

        // Fill ArrayList with Tea objects
        mTeas.add(Tea(context.getString(R.string.black_tea_name), R.drawable.black_tea))
        mTeas.add(Tea(context.getString(R.string.green_tea_name), R.drawable.green_tea))
        mTeas.add(Tea(context.getString(R.string.white_tea_name), R.drawable.white_tea))
        mTeas.add(Tea(context.getString(R.string.oolong_tea_name), R.drawable.oolong_tea))
        mTeas.add(Tea(context.getString(R.string.honey_lemon_tea_name), R.drawable.honey_lemon_tea))
        mTeas.add(Tea(context.getString(R.string.chamomile_tea_name), R.drawable.chamomile_tea))


        /**
         * [postDelayed] allows the [Runnable] to be run after the specified amount of
         * time set in DELAY_MILLIS elapses. An object that implements the Runnable interface
         * creates a thread. When this thread starts, the object's run method is called.
         *
         * After the time elapses, if there is a callback we return the image resource ID and
         * set the idle state to true.
         */
        val handler = Handler()
        handler.postDelayed({
            if (callback != null) {
                callback.onDone(mTeas)
                idlingResource?.setIdleState(true)
            }
        }, DELAY_MILLIS.toLong())
    }
}