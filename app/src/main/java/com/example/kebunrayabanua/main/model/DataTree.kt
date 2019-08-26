package com.example.kebunrayabanua.main.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataTree(
        @SerializedName("itemKode") var itemKode: String?,
        @SerializedName("tanamNama") var tanamNama: String?,
        @SerializedName("tanamNamaLatin") var tanamNamaLatin: String?,
        @SerializedName("famNama") var famNama: String?,
        @SerializedName("itemLokasi") var itemLokasi: String?,
        @SerializedName("itemAsal") var itemAsal: String?,
        @SerializedName("itemTglPenanaman") var itemTglPenanaman: String?,
        @SerializedName("tanamFoto") var tanamFoto: String?
) : Parcelable