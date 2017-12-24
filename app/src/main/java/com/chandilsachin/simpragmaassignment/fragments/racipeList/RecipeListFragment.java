package com.chandilsachin.simpragmaassignment.fragments.racipeList;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.chandilsachin.simpragmaassignment.R;
import com.chandilsachin.simpragmaassignment.fragments.recipeDetails.RecipeDetailsFragment;
import com.chandilsachin.simpragmaassignment.utils.ConstantMethod;
import com.chandilsachin.simpragmaassignment.utils.LifeCycleFragment;

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
    SearchView searchView;

    // -- Member variables
    private RecipeListAdapter mRecipeListAdapter;
    private RecipeListViewModel mViewModel;
    private GridLayoutManager mGridLayoutManager;
    private boolean searching;
    private boolean searchMode;
    private String searchQuery;
    private boolean fetching;
    private int pageCount = 1;

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
        pageCount = 1;
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

        mGridLayoutManager = new CustomGridLayoutManager(getContext(), 2);
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

            searching = false;
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
        //mViewModel.searchRecipe("", pageCount);
    }

    @Override
    public void setUpEvents() {
        mRecipeListAdapter.setOnItemClickListener((sharedView, recipe) -> {
            ConstantMethod.loadFragment((AppCompatActivity) getActivity(),
                    R.id.fragmentContainer, RecipeDetailsFragment.newInstance(recipe,
                            ViewCompat.getTransitionName(sharedView)), sharedView);
        });

        rvRecipeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //EventBus.getDefault().post(new NewsListScrollEvent(true));
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mGridLayoutManager.getChildCount();
                int loadedItemCount = mGridLayoutManager.getItemCount();
                int firstVisibleItemPosition = mGridLayoutManager.findFirstVisibleItemPosition();
                int itemsLeft = loadedItemCount - (firstVisibleItemPosition + visibleItemCount);
                if (itemsLeft < 1 && !fetching && !searching) {
                    if (searchMode) {
                        if (searchQuery != null) {
                            pageCount++;
                            mViewModel.searchRecipe(searchQuery, pageCount);
                            fetching = true;
                        }
                    }
                } else {
                    fetching = false;
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        searchView = (SearchView) menu.findItem(R.id.menu_idSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searching = true;
                searchMode = true;
                searchQuery = query;
                pageCount = 1;
                mViewModel.searchRecipe(query, pageCount);
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
