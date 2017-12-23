package com.chandilsachin.simpragmaassignment.network;

import com.chandilsachin.simpragmaassignment.fragments.racipeList.Recipe;
import com.chandilsachin.simpragmaassignment.fragments.racipeList.RecipePuppy;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {

    @GET("api")
    Single<RecipePuppy> getRecipy(@Query("i") String ingredients,
                                        @Query("p") int page);
}
