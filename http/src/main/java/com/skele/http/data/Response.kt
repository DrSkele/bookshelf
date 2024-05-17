package com.skele.http.data

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("meta") val metaData: MetaData,
    @SerializedName("documents") val documents : List<Document>
)
