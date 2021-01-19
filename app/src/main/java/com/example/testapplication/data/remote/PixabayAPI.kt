package com.example.testapplication.data.remote

import com.example.testapplication.BuildConfig
import com.example.testapplication.data.remote.responses.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Created by Victor Hernandez on 1/8/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

interface PixabayAPI {
    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY_PIXA_BAY
        ): ImageResponse?
}