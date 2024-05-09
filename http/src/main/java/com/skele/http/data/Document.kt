package com.skele.http.data

import com.google.gson.annotations.SerializedName

data class Document(
    val authors : String,
    val contents : String,
    @SerializedName("datetime") val dateTime : String,
    val price : Int,
    val publisher : String,
    @SerializedName("sale_price") val salePrice : Int,
    val status : String,
    val thumbnail : String,
    val title : String,
    val translators : List<String>,
    val url : String
)
