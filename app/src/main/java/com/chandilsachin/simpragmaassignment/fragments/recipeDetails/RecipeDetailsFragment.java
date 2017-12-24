package com.chandilsachin.simpragmaassignment.fragments.recipeDetails;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.transition.TransitionInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chandilsachin.simpragmaassignment.R;
import com.chandilsachin.simpragmaassignment.fragments.racipeList.Recipe;
import com.chandilsachin.simpragmaassignment.utils.ConstantMethod;
import com.chandilsachin.simpragmaassignment.utils.LifeCycleFragment;

import butterknife.BindView;

public class RecipeDetailsFragment extends LifeCycleFragment {
    private static final String ARG_RECIPE = "ARG_RECIPE";
    private static final String ARG_TRANSITION_NAME = "ARG_TRANSITION_NAME";

    // -- Views
    @BindView(R.id.main_backdrop)
    ImageView ivRecipeImage;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.webviewDetailsPage)
    WebView webviewDetailsPage;
    @BindView(R.id.pbWebViewProgress)
    ProgressBar pbWebViewProgress;

    // -- Member variables
    private Recipe mRecipe;
    private String mTransitionName;


    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailsFragment newInstance(Recipe recipe, String transitionName) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        args.putString(ARG_TRANSITION_NAME, transitionName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE);
            mTransitionName = getArguments().getString(ARG_TRANSITION_NAME);
        }

        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
//        setSharedElementReturnTransition(null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recipe_details;
    }

    @Override
    public void init(View v, @Nullable Bundle savedInstanceState) {
        ViewCompat.setTransitionName(ivRecipeImage, mTransitionName);
        setUpToolbar(toolbar, Html.fromHtml(mRecipe.getTitle()).toString());
        setupWebView();
    }

    @Override
    public void initLoadViews() {

        Glide.with(getContext()).load(mRecipe.getThumbnail()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                ivRecipeImage.setImageDrawable(resource);
                startPostponedEnterTransition();
            }
        });

        if(ConstantMethod.isInternetAvailable(getContext()))
            webviewDetailsPage.loadUrl(mRecipe.getHref());
        else
            ConstantMethod.showToast(getContext(), getString(R.string.checkInternetConnection));

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        webviewDetailsPage.getSettings().setJavaScriptEnabled(true);
        webviewDetailsPage.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pbWebViewProgress.setVisibility(View.VISIBLE);
                pbWebViewProgress.setProgress(newProgress);
            }
        });

        webviewDetailsPage.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pbWebViewProgress.setVisibility(View.GONE);

                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        webviewDetailsPage.destroy();

    }
}
