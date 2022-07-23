package com.youssef.task.business.entities

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("background_image") val image: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("ratings_count") val ratingsCount: Int,
    @SerializedName("released") val released: String,
)