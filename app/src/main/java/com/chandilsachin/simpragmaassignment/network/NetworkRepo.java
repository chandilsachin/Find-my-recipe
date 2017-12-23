package com.chandilsachin.simpragmaassignment.network;

import com.chandilsachin.simpragmaassignment.fragments.racipeList.Recipe;
import com.chandilsachin.simpragmaassignment.fragments.racipeList.RecipePuppy;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class NetworkRepo {

    public Single<List<Recipe>> fetchSongs(String ingredients, int page) {
        return RetrofitNetworkClient.getService().getRecipy(ingredients, page)
                .map(RecipePuppy::getResults)
                .subscribeOn(Schedulers.io());
    }
}
