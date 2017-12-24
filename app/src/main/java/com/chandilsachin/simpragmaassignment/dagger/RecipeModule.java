package com.chandilsachin.simpragmaassignment.dagger;

import com.chandilsachin.simpragmaassignment.network.RetrofitNetworkClient;
import com.chandilsachin.simpragmaassignment.network.Service;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipeModule {


    @Provides
    public Service getRecipeService() {
        return RetrofitNetworkClient.getService();
    }

}
