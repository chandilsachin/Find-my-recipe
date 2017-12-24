package com.chandilsachin.simpragmaassignment.fragments.racipeList;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.support.v4.app.Fragment;

import com.chandilsachin.simpragmaassignment.BuildConfig;
import com.chandilsachin.simpragmaassignment.MainActivity;
import com.chandilsachin.simpragmaassignment.R;
import com.chandilsachin.simpragmaassignment.RecipeTestModule;
import com.chandilsachin.simpragmaassignment.dagger.DaggerRecipeComponent;
import com.chandilsachin.simpragmaassignment.dagger.MyApplication;
import com.chandilsachin.simpragmaassignment.dagger.RecipeComponent;
import com.chandilsachin.simpragmaassignment.fragments.recipeDetails.RecipeDetailsFragment;
import com.chandilsachin.simpragmaassignment.network.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RecipeListFragmentTest {

    @ClassRule
    public static InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    List<Recipe> demoRecipeList;
    private MainActivity activity;

    private static void overrideRxJavaPlugins() {
        RxJavaPlugins.setIoSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                scheduler -> Schedulers.trampoline());
    }

    @Before
    public void setUp() throws Exception {
        overrideRxJavaPlugins();

        Service service = (ingredients, page) -> Single.create(it -> {
            RecipePuppy root = new RecipePuppy();
            demoRecipeList = new ArrayList<>();
            demoRecipeList.add(new Recipe("Recipe 1", "link1", "Ingredients1", "thumbnail1"));
            demoRecipeList.add(new Recipe("Recipe 2", "link2", "Ingredients2", "thumbnail2"));
            demoRecipeList.add(new Recipe("Recipe 3", "link3", "Ingredients3", "thumbnail3"));
            demoRecipeList.add(new Recipe("Recipe 4", "link4", "Ingredients4", "thumbnail4"));
            root.setResults(demoRecipeList);
            it.onSuccess(root);
        });
        RecipeTestModule module = new RecipeTestModule(service);
        RecipeComponent component = DaggerRecipeComponent.builder().recipeModule(module).build();
        MyApplication.setComponent(component);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_recipeListFragmentSouldBeLoaded() throws InterruptedException {
        activity = Robolectric.setupActivity(MainActivity.class);
        Fragment recipeListFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        assert (recipeListFragment instanceof RecipeListFragment);
        assertNotNull(recipeListFragment);
        RecipeListFragment recipeListFragment1 = (RecipeListFragment) recipeListFragment;
        assertEquals("Recipes", recipeListFragment1.toolbar.getTitle());
    }

    @Test
    public void test_shouldSearchForRecipesAndLoadDetailsPage() throws InterruptedException {
        activity = Robolectric.setupActivity(MainActivity.class);
        RecipeListFragment recipeListFragment = (RecipeListFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);

        // click on search icon
        shadowActivity.clickMenuItem(R.id.menu_idSearch);
        // search for recipe
        recipeListFragment.searchView.setQuery("potato", true);
        RecipeListAdapter recipeListAdapter = (RecipeListAdapter) recipeListFragment.rvRecipeList.getAdapter();

        // if items are loaded
        assertNotEquals(0, recipeListAdapter.getItemCount());


        String clickingItemTitle = recipeListAdapter.getItem(0).getTitle();
        // click on first item of recipe list
        recipeListFragment.rvRecipeList.getChildAt(0).performClick();
        Fragment recipeDetailsFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        // check if details fragment is loaded.
        assertTrue(recipeDetailsFragment instanceof RecipeDetailsFragment);
        // check if correct title is set.
        assertEquals(clickingItemTitle, activity.getSupportActionBar().getTitle());
    }


}