package com.ahmadshahwaiz.beatexplorer

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.ahmadshahwaiz.beatexplorer.presenter.ui.MapsActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MapsActivityTest: BaseUiTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MapsActivity::class.java)

    @Test
    fun appShouldBeInExplorationMode_WhenItStarts() {
        sleep(twoSeconds)
        onView(allOf(instanceOf(TextView::class.java), withParent(withResourceName("action_bar"))))
            .check(matches(withText(mActivityTestRule.activity.getString(R.string.exploration_mode))))
        sleep(twoSeconds)
    }

    @Test
    fun appShouldBeInNavigationMode_WhenVenueIsClicked() {
        sleep(tenSeconds)
        onView(withId(R.id.venueList))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        sleep(twoSeconds)
        onView(allOf(instanceOf(TextView::class.java), withParent(withResourceName("action_bar"))))
            .check(matches(withText(mActivityTestRule.activity.getString(R.string.navigation_mode))))
        sleep(twoSeconds)
    }

    @Test
    fun appShouldBeInExplorationMode_WhenUsePressBackFromNavigationModel() {
        sleep(tenSeconds)
        onView(withId(R.id.venueList))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        sleep(twoSeconds)
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(allOf(instanceOf(TextView::class.java), withParent(withResourceName("action_bar"))))
            .check(matches(withText(mActivityTestRule.activity.getString(R.string.exploration_mode))))
        sleep(twoSeconds)
    }
}