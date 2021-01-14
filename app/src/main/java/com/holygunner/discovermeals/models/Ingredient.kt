package com.holygunner.discovermeals.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Ingredient (
    @PrimaryKey
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
    Parcelable {
        var assosiatedMeals : RealmList<Meal> =  RealmList()
        var isFetched : Boolean = false

    override fun equals(other: Any?): Boolean {
        if (other is Ingredient) {
            return other.name!! == this.name
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
    }