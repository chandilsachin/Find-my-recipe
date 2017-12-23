package com.chandilsachin.simpragmaassignment.fragments.racipeList;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chandilsachin.simpragmaassignment.R;
import com.chandilsachin.simpragmaassignment.fragments.recipeDetails.RecipeDetailsFragment;
import com.chandilsachin.simpragmaassignment.utils.ConstantMethod;
import com.chandilsachin.simpragmaassignment.utils.LifeCycleFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class RecipeListFragment extends LifeCycleFragment {

    // -- Views
    @BindView(R.id.rvRecipeList)
    RecyclerView rvRecipeList;
    @BindView(R.id.srlRecipeList)
    SwipeRefreshLayout srlRecipeList;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvSearchMsg)
    TextView tvSearchMsg;

    // -- Member variables
    private RecipeListAdapter mRecipeListAdapter;
    private RecipeListViewModel mViewModel;
    private GridLayoutManager mGridLayoutManager;
    private boolean searching;
    private boolean searchMode;
    private String searchQuery;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    public static RecipeListFragment newInstance() {
        RecipeListFragment fragment = new RecipeListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recipe_list;
    }

    @Override
    public void init(View v, @Nullable Bundle savedInstanceState) {
        setUpToolbar(toolbar, R.string.recipes);
        mViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        mRecipeListAdapter = new RecipeListAdapter(getContext());

        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvRecipeList.setLayoutManager(mGridLayoutManager);
        rvRecipeList.setAdapter(mRecipeListAdapter);

        srlRecipeList.setEnabled(false);
    }

    @Override
    public void initLoadViews() {

        mViewModel.recipeListLiveData.observe(this, list -> {
            if (list != null && !list.isEmpty()) {
                if(searchMode && searching)
                    mRecipeListAdapter.clearItems();
                mRecipeListAdapter.addItems(list);
                searching = false;
            }else if(searching){
                tvSearchMsg.setText(String.format(getString(R.string.emptySearchResultMsg), searchQuery));
                tvSearchMsg.setVisibility(View.VISIBLE);
                rvRecipeList.setVisibility(View.GONE);
                ConstantMethod.showToast(getContext(), "Sorry, No results found. Please try again.");
            }
            srlRecipeList.setRefreshing(false);
            if(mRecipeListAdapter.getItemCount() < 1){
                tvSearchMsg.setVisibility(View.VISIBLE);
                rvRecipeList.setVisibility(View.GONE);
            }else{
                tvSearchMsg.setVisibility(View.GONE);
                rvRecipeList.setVisibility(View.VISIBLE);
            }
        });

        if(mRecipeListAdapter.getItemCount() < 1){
            tvSearchMsg.setVisibility(View.VISIBLE);
            rvRecipeList.setVisibility(View.GONE);
        }

        mViewModel.errorMessageLiveData.observe(this, msgStringRes -> {
            if(msgStringRes != null && msgStringRes != -1){
                showToast(getString(msgStringRes));
            }
            srlRecipeList.setRefreshing(false);
        });
        //mViewModel.searchRecipe("potato", 1);
    }

    @Override
    public void setUpEvents() {
        mRecipeListAdapter.setOnItemClickListener((sharedView, recipe) -> {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(recipe.getHref()));
//            startActivity(intent);
            ConstantMethod.loadFragment((AppCompatActivity) getActivity(),
                    R.id.fragmentContainer, RecipeDetailsFragment.newInstance(recipe,
                            ViewCompat.getTransitionName(sharedView)), sharedView);
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_idSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searching = true;
                searchMode = true;
                searchQuery = query;
//                searchQuery = query;
//                mSongListAdapter.setSongList(new ArrayList<>());
                mViewModel.searchRecipe(query, 1);
                srlRecipeList.setRefreshing(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            if (searchMode) {
                searching = false;
                searchMode = false;
                srlRecipeList.setRefreshing(false);
            }
            return false;
        });


        super.onCreateOptionsMenu(menu, inflater);
    }
}
