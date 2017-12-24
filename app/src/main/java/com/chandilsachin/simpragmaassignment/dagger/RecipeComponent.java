package com.chandilsachin.simpragmaassignment.dagger;

import com.chandilsachin.simpragmaassignment.network.NetworkRepo;

import dagger.Component;

@Component(modules = {ContextModule.class, RecipeModule.class})
public interface RecipeComponent {

    void inject(NetworkRepo activity);

}
