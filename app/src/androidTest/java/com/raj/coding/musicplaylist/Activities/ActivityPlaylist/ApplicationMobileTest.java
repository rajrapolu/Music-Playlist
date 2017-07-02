package com.raj.coding.musicplaylist.Activities.ActivityPlaylist;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.raj.coding.musicplaylist.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ApplicationMobileTest {

    @Rule
    public ActivityTestRule<PlaylistActivity> mActivityTestRule = new ActivityTestRule<>(PlaylistActivity.class);

    @Test
    public void playlistActivityTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        withParent(withId(R.id.fragment)),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.edit_playlist_name), isDisplayed()));
        textInputEditText.perform(replaceText("Playlist 1"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_add), withText("Add"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab),
                        withParent(withId(R.id.fragment)),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.edit_playlist_name), isDisplayed()));
        textInputEditText2.perform(replaceText("Playlist 2"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_add), withText("Add"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view),
                        withParent(withId(R.id.fragment)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_add_songs), withText("Add Songs"), withContentDescription("Add Songs"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.tool_bar)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.fab),
                        withParent(withId(R.id.fragment)),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.edit_playlist_name), isDisplayed()));
        textInputEditText3.perform(replaceText("Playlist 2"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_add), withText("Add"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button_cancel), withText("Cancel"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recycler_view),
                        withParent(withId(R.id.fragment)),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.tool_bar)))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recycler_view),
                        withParent(withId(R.id.fragment)),
                        isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.action_add_songs), withText("Add Songs"), withContentDescription("Add Songs"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.tool_bar)))),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

    }

}
