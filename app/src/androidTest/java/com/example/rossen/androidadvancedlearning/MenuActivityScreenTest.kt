package com.example.rossen.androidadvancedlearning

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.rossen.androidadvancedlearning.espresso.MenuActivity
import com.example.rossen.androidadvancedlearning.espresso.OrderActivity
import org.hamcrest.Matchers.anything
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * TODO: Add file description
 *
 * @author rosen.koychev
 */
@RunWith(AndroidJUnit4::class)

class MenuActivityScreenTest {

    val TEA_NAME = "Green Tea"

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MenuActivity> = ActivityTestRule<MenuActivity>(MenuActivity::class
            .java)

    @Test
    fun clickAdapterPositionAndOpenActivity() {
        onData(anything()).inAdapterView(withId(R.id.tea_grid_view)).atPosition(1).perform(click())

        onView((withId(R.id.tea_name_text_view))).check(matches(withText(TEA_NAME)))
    }
}