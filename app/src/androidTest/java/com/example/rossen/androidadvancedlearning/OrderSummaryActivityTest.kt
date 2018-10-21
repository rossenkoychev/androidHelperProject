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

package com.example.rossen.androidadvancedlearning

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Build
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import com.example.rossen.androidadvancedlearning.espresso.OrderSummaryActivity
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OrderSummaryActivityTest {
    private val emailMessage = "I just ordered a delicious tea from TeaTime. Next time you are craving a tea, check " +
            "them out!"

    @get:Rule
    var mActivityRule: IntentsTestRule<OrderSummaryActivity> = IntentsTestRule<OrderSummaryActivity>(OrderSummaryActivity::class
            .java)


    // TODO (3) Finish this method which runs before each test and will stub all external
    // intents so all external intents will be blocked
    @Before
    fun stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    @Before
    fun grantMailPermision() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + getTargetContext().packageName +
                    " android.permission.CALL_PHONE")
        }
    }

    // TODO (4) Finish this method which verifies that the intent sent by clicking the send email
    // button matches the intent sent by the application
    @Test
    fun clickSendEmailButton_SendsEmail() {
        onView(withId(R.id.send_email_button)).perform(click())
        intended(allOf(hasAction(Intent.ACTION_SENDTO), hasExtra(Intent.EXTRA_TEXT, emailMessage)))
    }
}