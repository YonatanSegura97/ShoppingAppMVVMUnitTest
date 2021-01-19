package com.example.testapplication.data.remote.responses

// Created by Victor Hernandez on 1/8/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

data class ImageResponse(
    val hits:List<ImageResult>,
    val total: Int,
    val totalHits: Int
)