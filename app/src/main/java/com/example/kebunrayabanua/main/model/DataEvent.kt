package com.example.kebunrayabanua.main.model

import com.google.gson.annotations.SerializedName

data class DataEvent(
        @SerializedName("eventKode") var eventKode: String?,
        @SerializedName("eventNama") var eventNama: String?,
        @SerializedName("eventDeskripsi") var eventDeskripsi: String?,
        @SerializedName("eventMulai") var eventMulai: String?,
        @SerializedName("eventSelesai") var eventSelesai: String?,
        @SerializedName("eventPoster") var eventPoster: List<String>?
)