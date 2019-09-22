package com.example.kebunrayabanua.main.model

import com.google.gson.annotations.SerializedName

data class ResponseHeaderHighlight(
    @SerializedName("galeri") var galeri: List<String>?,
    @SerializedName("event") var event: List<DataEvent>?
)