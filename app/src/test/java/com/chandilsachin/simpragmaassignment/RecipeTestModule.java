package com.chandilsachin.simpragmaassignment;

import com.chandilsachin.simpragmaassignment.dagger.RecipeModule;
import com.chandilsachin.simpragmaassignment.network.Service;


public class RecipeTestModule extends RecipeModule {

    private Service service;

    public RecipeTestModule(Service service) {
        this.service = service;
    }

    @Override
    public Service getRecipeService() {
        return service;
    }
}
