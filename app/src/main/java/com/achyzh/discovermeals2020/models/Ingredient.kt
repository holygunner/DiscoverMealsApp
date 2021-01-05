package com.achyzh.discovermeals2020.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Ingredient (
    @SerializedName("strIngredient")
    var name: String? = "",
    var category: String? = null,
    var isFill: Boolean = false,
    var measure: String? = null,
    @SerializedName("idIngredient")
    var id: String? = null,
    @SerializedName("strDescription")
    var description: String? = null,
    @SerializedName("strType")
    var type: String? = null)
    :
    RealmObject(),
    Parcelable