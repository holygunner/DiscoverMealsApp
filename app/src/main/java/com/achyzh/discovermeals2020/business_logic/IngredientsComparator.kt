package com.achyzh.discovermeals2020.business_logic

import com.achyzh.discovermeals2020.models.Ingredient
import java.text.Collator

class IngredientsComparator : Comparator<Ingredient> {
    override fun compare(i1: Ingredient, i2: Ingredient): Int {
        return Collator.getInstance().compare(i1.name, i2.name)
    }
}