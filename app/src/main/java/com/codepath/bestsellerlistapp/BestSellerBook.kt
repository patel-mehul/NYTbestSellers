package com.codepath.bestsellerlistapp

import com.google.gson.annotations.SerializedName

data class BestSellerBook(
    @SerializedName("rank") val rank: Int,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("book_image") val bookImageUrl: String,
    @SerializedName("amazon_product_url") val amazonUrl: String,
    @SerializedName("description") val description: String // ✅ Fix: Add this field
)
