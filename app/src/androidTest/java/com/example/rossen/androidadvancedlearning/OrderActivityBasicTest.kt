package com.example.rossen.androidadvancedlearning

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.rossen.androidadvancedlearning.espresso.OrderActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * TODO: Add file description
 *
 * @author rosen.koychev
 */
@RunWith(AndroidJUnit4::class)
class OrderActivityBasicTest {

    @get:Rule var mActivityTestRule: ActivityTestRule<OrderActivity> = ActivityTestRule<OrderActivity>(OrderActivity::class
            .java)

    @Test
    fun clickIncrementButtonChangesQuantityAndCost(){
        onView((withId(R.id.increment_button))).perform(click())

        onView(withId(R.id.quantity_text_view)).check(matches(withText("1")))
        onView(withId(R.id.cost_text_view)).check(matches(withText("$5.00")))
    }

    @Test
    fun clickDecrementButtonChangesQuantityAndCost(){
        onView(withId(R.id.cost_text_view)).check(matches(withText("$0.00")))

        onView((withId(R.id.decrement_button))).perform(click())
        onView(withId(R.id.quantity_text_view)).check(matches(withText("0")))
        onView(withId(R.id.cost_text_view)).check(matches(withText("$0.00")))
    }

    @Test
    fun checkInitialQuantity(){
        onView(withId(R.id.cost_text_view)).check(matches(withText("$0.00")))
    }
}