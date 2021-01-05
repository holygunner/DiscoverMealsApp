package com.achyzh.discovermeals2020.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Meal (
    @PrimaryKey
    @SerializedName("idMeal")
    var id: Int,
    @SerializedName("strMeal")
    var name: String = "",
    @SerializedName("strCategory")
    var category: String? = null,
    @SerializedName("strArea")
    var area: String? = null,
    @SerializedName("strInstructions")
    var instruction: String? = null,
    @SerializedName("strMealThumb")
    var urlImage: String? = null,
    @SerializedName("strTags")
    var tags: String? = null,
    @SerializedName("strYoutube")
    var youtubeUrl: String? = null,
    var strIngredient1: String? = null,
    var strIngredient2: String? = null,
    var strIngredient3: String? = null,
    var strIngredient4: String? = null,
    var strIngredient5: String? = null,
    var strIngredient6: String? = null,
    var strIngredient7: String? = null,
    var strIngredient8: String? = null,
    var strIngredient9: String? = null,
    var strIngredient10: String? = null,
    var strIngredient11: String? = null,
    var strIngredient12: String? = null,
    var strIngredient13: String? = null,
    var strIngredient14: String? = null,
    var strIngredient15: String? = null,
    var strIngredient16: String? = null,
    var strIngredient17: String? = null,
    var strIngredient18: String? = null,
    var strIngredient19: String? = null,
    var strIngredient20: String? = null,
    var strMeasure1: String? = null,
    var strMeasure2: String? = null,
    var strMeasure3: String? = null,
    var strMeasure4: String? = null,
    var strMeasure5: String? = null,
    var strMeasure6: String? = null,
    var strMeasure7: String? = null,
    var strMeasure8: String? = null,
    var strMeasure9: String? = null,
    var strMeasure10: String? = null,
    var strMeasure11: String? = null,
    var strMeasure12: String? = null,
    var strMeasure13: String? = null,
    var strMeasure14: String? = null,
    var strMeasure15: String? = null,
    var strMeasure16: String? = null,
    var strMeasure17: String? = null,
    var strMeasure18: String? = null,
    var strMeasure19: String? = null,
    var strMeasure20: String? = null,
    )
    :
    RealmObject(),
    Parcelable
{
    constructor() : this(id = 0)

    var isFav = false

    @Ignore
    private var _ingredientsList: MutableList<Ingredient> = mutableListOf()
    val ingredientsList get() = _ingredientsList

    var ingredientsRealmList: RealmList<Ingredient> = RealmList()

    private var mIsFavourite = false

    fun addIngredient(ingredient: Ingredient) {
        if (_ingredientsList == null)
            _ingredientsList = mutableListOf()
        _ingredientsList.add(ingredient)
    }
}