package com.chandilsachin.simpragmaassignment.fragments.racipeList;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.chandilsachin.simpragmaassignment.R;
import com.chandilsachin.simpragmaassignment.network.NetworkRepo;
import com.chandilsachin.simpragmaassignment.utils.ConstantMethod;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class RecipeListViewModel extends ViewModel {

    public MutableLiveData<List<Recipe>> recipeListLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> errorMessageLiveData = new MutableLiveData<>();

    private NetworkRepo networkRepo;

    public RecipeListViewModel() {
        networkRepo = new NetworkRepo();
    }

    public void searchRecipe(String ingredients, int page) {
        networkRepo.fetchSongs(ingredients, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    recipeListLiveData.setValue(list);
                }, e -> {
                    if(ConstantMethod.isInternetError(e))
                        errorMessageLiveData.setValue(R.string.checkInternetConnection);
                });
    }
}
