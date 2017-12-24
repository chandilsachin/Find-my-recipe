package com.chandilsachin.simpragmaassignment.fragments.racipeList;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.chandilsachin.simpragmaassignment.RecipeTestModule;
import com.chandilsachin.simpragmaassignment.dagger.DaggerRecipeComponent;
import com.chandilsachin.simpragmaassignment.dagger.MyApplication;
import com.chandilsachin.simpragmaassignment.dagger.RecipeComponent;
import com.chandilsachin.simpragmaassignment.network.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

public class RecipeListViewModelTest {


    @ClassRule
    public static InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    RecipeListViewModel viewModel;
    List<Recipe> demoRecipeList;

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

        viewModel = new RecipeListViewModel();

       // observer = (Observer<List<Recipe>>) Mockito.mock(Observer.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void searchRecipe() throws Exception {
        viewModel.searchRecipe("ingredients", 1);

        List<Recipe> list = viewModel.recipeListLiveData.getValue();
        assertNotEquals(list, null);
        assertEquals(list.size(), 4);
        assertEquals(list.get(3).getTitle(), demoRecipeList.get(3).getTitle());

    }

}