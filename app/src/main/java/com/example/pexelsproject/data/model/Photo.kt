package com.example.pexelsproject.data.model

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographerUrl: String,
    val photographerId: Int,
    val avgColor: String,
    val src: PhotoFeatures,
    val liked: Boolean,
    val alt: String
)