package com.example.pexelsproject.data.source.api.model

import java.io.Serializable

data class PhotoSrcListResponse(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
) : Serializable