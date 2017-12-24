package com.chandilsachin.simpragmaassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chandilsachin.simpragmaassignment.fragments.racipeList.Recipe;
import com.chandilsachin.simpragmaassignment.fragments.racipeList.RecipeListFragment;
import com.chandilsachin.simpragmaassignment.utils.ConstantMethod;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeListFragment fragment = RecipeListFragment.newInstance();
        ConstantMethod.loadFragment(this, R.id.fragmentContainer, fragment);
    }

    @Override
    public void onBackPressed() {
     if (getSupportFragmentManager().getBackStackEntryCount() < 2) {
            finish();
        } else
            super.onBackPressed();
    }
}
