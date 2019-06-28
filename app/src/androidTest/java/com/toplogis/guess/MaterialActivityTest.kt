package com.toplogis.guess

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MaterialActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MaterialActivity>(MaterialActivity::class.java)

    @Test
    fun guessWrong (){
        val secret = activityTestRule.activity.secretNumber.secret
        val resources = activityTestRule.activity.resources

        for (n in 1..10) {
            if (n != secret) {
                onView(withId(R.id.ed_number)).perform(clearText())
                onView(withId(R.id.ed_number)).perform(typeText(n.toString()))
                onView(withId(R.id.bt_ok)).perform(click())

                val msg = if (n < secret) resources.getString(R.string.bigger)
                else resources.getString(R.string.smaller)

                onView(withText(msg)).check(matches(isDisplayed()))
                onView(withText(resources.getString(R.string.ok))).perform(click())
            } else {
                onView(withId(R.id.ed_number)).perform(clearText())
                onView(withId(R.id.ed_number)).perform(typeText(n.toString()))
                onView(withId(R.id.bt_ok)).perform(click())

                val count = activityTestRule.activity.secretNumber.count
                val msg = if (count < 3) resources.getString(R.string.excellent, secret)
                else resources.getString(R.string.you_got_it)

                onView(withText(msg)).check(matches(isDisplayed()))
                onView(withText(resources.getString(R.string.ok))).perform(click())
            }
        }

    }
    @Test
    fun reset (){
        val count = activityTestRule.activity.secretNumber.count
        val resources = activityTestRule.activity.resources

        if(count > 0 ) {
            onView(withId(R.id.fab_reset)).perform(click())
            onView(withText(resources.getString(R.string.ok))).perform(click())
            check(count == 0)

        }
    }
}