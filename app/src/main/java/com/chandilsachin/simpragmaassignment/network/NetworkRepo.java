package com.chandilsachin.simpragmaassignment.network;

import com.chandilsachin.simpragmaassignment.dagger.MyApplication;
import com.chandilsachin.simpragmaassignment.fragments.racipeList.Recipe;
import com.chandilsachin.simpragmaassignment.fragments.racipeList.RecipePuppy;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class NetworkRepo {

    @Inject
    Service service;

    public NetworkRepo(){
        MyApplication.getComponent().inject(this);
    }

    public Single<List<Recipe>> fetchRecipes(String ingredients, int page) {
        return service.getRecipe(ingredients, page)
                .map(RecipePuppy::getResults)
                .subscribeOn(Schedulers.io());
    }
}
