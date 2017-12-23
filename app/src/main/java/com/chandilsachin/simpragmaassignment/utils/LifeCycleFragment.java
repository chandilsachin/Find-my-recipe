package com.chandilsachin.simpragmaassignment.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public abstract class LifeCycleFragment extends Fragment {

    /*private LifecycleRegistry registry = new LifecycleRegistry(this);
    @Override
    public LifecycleRegistry getLifecycle() {
        return registry;
    }
*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, v);
        compositeDisposable = new CompositeDisposable();
        init(v, savedInstanceState);
        initLoadViews();
        setUpEvents();
        return v;
    }

    /**
     * Returns layout id for fragment
     * @return
     */
    public abstract int getLayoutId();

    public abstract void init(View v, @Nullable Bundle savedInstanceState);

    public abstract void initLoadViews();

    public void setUpEvents(){}

    public void setUpToolbar(Toolbar toolbar, int stringRes){
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.setTitle(stringRes);
    }

    public void setUpToolbar(Toolbar toolbar, String title){
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.setTitle(title);
    }

    public void setDisplayHomeAsUpEnabled(boolean value){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(value);
    }

    public void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public CompositeDisposable compositeDisposable;

    @Override
    public void onStop() {
        super.onStop();
        if(compositeDisposable != null)
        compositeDisposable.clear();
    }
}
