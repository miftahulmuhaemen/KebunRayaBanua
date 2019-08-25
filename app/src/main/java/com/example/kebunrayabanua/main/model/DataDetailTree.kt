package com.example.kebunrayabanua.main.model

import com.google.gson.annotations.SerializedName

data class DataDetailTree(
        @SerializedName("itemKode") var itemKode: String?,
        @SerializedName("tanamNama") var tanamNama: String?,
        @SerializedName("tanamNamaLatin") var tanamNamaLatin: String?,
        @SerializedName("famNama") var famNama: String?,
        @SerializedName("tanamDeskripsiTeks") var tanamDeskripsiTeks: String?,
        @SerializedName("tanamDeskripsiHtml") var tanamDeskripsiHtml: String?,
        @SerializedName("tanamStatusKonservasi") var tanamStatusKonservasi: String?,
        @SerializedName("itemInfoTanamTeks") var itemInfoTanamTeks: String?,
        @SerializedName("itemInfoTanamHtml") var itemInfoTanamHtml: String?,
        @SerializedName("itemNoRegister") var itemNoRegister: String?,
        @SerializedName("itemLokasi") var itemLokasi: String?,
        @SerializedName("itemTglTanam") var itemTglTanam: String?,
        @SerializedName("itemAsal") var itemAsal: String?,
        @SerializedName("tanamFoto") var tanamFoto: List<String>?
)