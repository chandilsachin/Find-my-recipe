package com.chandilsachin.simpragmaassignment.fragments.racipeList;

import android.support.v4.app.Fragment;

import com.chandilsachin.simpragmaassignment.BuildConfig;
import com.chandilsachin.simpragmaassignment.MainActivity;
import com.chandilsachin.simpragmaassignment.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 25)
public class RecipeListFragmentTest {

    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create().resume().get();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_recipeListFragmentSouldBeLoaded(){
        Fragment recipeListFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        assert recipeListFragment instanceof RecipeListFragment;
        assertNotNull(recipeListFragment);
    }

}