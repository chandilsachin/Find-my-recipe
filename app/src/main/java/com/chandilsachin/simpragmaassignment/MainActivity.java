package com.chandilsachin.simpragmaassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chandilsachin.simpragmaassignment.fragments.racipeList.RecipeListFragment;
import com.chandilsachin.simpragmaassignment.utils.ConstantMethod;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstantMethod.loadFragment(this, R.id.fragmentContainer, RecipeListFragment.newInstance());
    }

    @Override
    public void onBackPressed() {
     if (getSupportFragmentManager().getBackStackEntryCount() < 2) {
            finish();
        } else
            super.onBackPressed();
    }
}
