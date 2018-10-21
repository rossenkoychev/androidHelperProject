package com.example.rossen.androidadvancedlearning.squaker_cloud.following

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.SwitchPreferenceCompat
import android.util.Log
import com.example.rossen.androidadvancedlearning.R
import com.google.firebase.messaging.FirebaseMessaging

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
 * Shows the list of instructors you can follow
 */
class FollowingPreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Add visualizer preferences, defined in the XML file in res->xml->preferences_squawker
        addPreferencesFromResource(R.xml.following_squawker)
    }

    /**
     * Triggered when shared preferences changes. This will be triggered when a person is followed
     * or un-followed
     *
     * @param sharedPreferences SharedPreferences file
     * @param key               The key of the preference which was changed
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

        val preference = findPreference(key)
        if (preference != null && preference is SwitchPreferenceCompat) {
            // Get the current state of the switch preference
            val isOn = sharedPreferences.getBoolean(key, false)
            if (isOn) {
                // The preference key matches the following key for the associated instructor in
                // FCM. For example, the key for Lyla is key_lyla (as seen in
                // following_squawker.xml). The topic for Lyla's messages is /topics/key_lyla

                // Subscribe
                FirebaseMessaging.getInstance().subscribeToTopic(key)
                Log.d(LOG_TAG, "Subscribing to $key")
            } else {
                // Un-subscribe
                FirebaseMessaging.getInstance().unsubscribeFromTopic(key)
                Log.d(LOG_TAG, "Un-subscribing to $key")
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add the shared preference change listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this)
    }

   override fun onDestroy() {
        super.onDestroy()
        // Remove the shared preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this)
    }

    companion object {

        private val LOG_TAG = FollowingPreferenceFragment::class.java.simpleName
    }

}
