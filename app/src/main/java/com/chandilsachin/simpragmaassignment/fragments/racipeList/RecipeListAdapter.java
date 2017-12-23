package com.chandilsachin.simpragmaassignment.fragments.racipeList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chandilsachin.simpragmaassignment.R;
import com.chandilsachin.simpragmaassignment.utils.ActionCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private List<Recipe> list;
    private LayoutInflater layoutInflater;
    private ActionCallback<Recipe> onItemClickListener;

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
        holder.itemView.setOnClickListener(v -> {
            if(onItemClickListener != null)
                onItemClickListener.call(recipe);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<Recipe> newItems) {
        list.addAll(newItems);
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
    }

    public void setOnItemClickListener(ActionCallback<Recipe> onItemClickListener) {
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

        public void bindView(Recipe recipe){
            Glide.with(ivRecipeImage.getContext()).load(recipe.getThumbnail()).into(ivRecipeImage);
            tvRecipeTitle.setText(recipe.getTitle().trim());
        }
    }
}
