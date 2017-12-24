package com.chandilsachin.simpragmaassignment.dagger;

import android.app.Application;


public class MyApplication extends Application {
    private static RecipeComponent component;

    public static RecipeComponent getComponent() {
        return component;
    }

    public static void setComponent(RecipeComponent component) {
        MyApplication.component = component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponent();
    }

    private void buildComponent() {
        component = DaggerRecipeComponent.builder()
                .contextModule(new ContextModule(this)).build();
    }
}
