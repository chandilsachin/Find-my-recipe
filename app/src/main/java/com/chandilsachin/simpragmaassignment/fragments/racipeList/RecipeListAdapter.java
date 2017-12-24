package com.chandilsachin.simpragmaassignment.fragments.racipeList;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chandilsachin.simpragmaassignment.R;
import com.chandilsachin.simpragmaassignment.utils.Action2Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private List<Recipe> list;
    private LayoutInflater layoutInflater;
    private Action2Callback<ImageView, Recipe> onItemClickListener;
    private int previousItemPosition = -1;

    public RecipeListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.recipe_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = list.get(position);
        holder.bindView(recipe);
        ViewCompat.setTransitionName(holder.ivRecipeImage, recipe.getTitle() + position);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.call(holder.ivRecipeImage, recipe);
        });

        if(previousItemPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.item_animation_fall_down);
            holder.itemView.startAnimation(animation);
        }
        previousItemPosition = position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Recipe getItem(int position){
        return list.get(position);
    }

    public void addItems(List<Recipe> newItems) {
        List<Recipe> oldList = new ArrayList<>(this.list);
        list.addAll(newItems);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MyDiffCallback(oldList, list));
        result.dispatchUpdatesTo(this);

    }

    public void clearItems() {
        list.clear();
        addItems(list);
    }

    public void setOnItemClickListener(Action2Callback<ImageView, Recipe> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivRecipeImage)
        ImageView ivRecipeImage;
        @BindView(R.id.tvRecipeTitle)
        TextView tvRecipeTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Recipe recipe) {
            Glide.with(ivRecipeImage.getContext()).load(recipe.getThumbnail()).into(ivRecipeImage);
            tvRecipeTitle.setText(Html.fromHtml(recipe.getTitle().trim()));
        }
    }

    class MyDiffCallback extends DiffUtil.Callback {

        List<Recipe> oldList;
        List<Recipe> newList;

        public MyDiffCallback(List<Recipe> oldList, List<Recipe> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getTitle().equals(newList.get(newItemPosition).getTitle());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getTitle().equals(newList.get(newItemPosition).getTitle());
        }
    }
}
