package com.example.kebunrayabanua.main.model

import com.google.gson.annotations.SerializedName

data class ResponseDetaiTree(
        @SerializedName("count") var count: String?,
        @SerializedName("data") var data: List<DataDetailTree>?
)