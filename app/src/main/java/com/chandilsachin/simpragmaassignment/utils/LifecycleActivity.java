package com.chandilsachin.simpragmaassignment.utils;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LifecycleActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private LifecycleRegistry registry = new LifecycleRegistry(this);
    @Override
    public LifecycleRegistry getLifecycle() {
        return registry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
