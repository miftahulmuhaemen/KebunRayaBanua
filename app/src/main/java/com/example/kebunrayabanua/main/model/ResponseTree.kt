package com.example.kebunrayabanua.main.model

import com.google.gson.annotations.SerializedName

data class ResponseTree<T>(
        @SerializedName("count") var count: String?,
        @SerializedName("data") var data: List<T>?
)