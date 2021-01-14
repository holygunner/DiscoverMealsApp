package com.holygunner.discovermeals.business_logic

import com.holygunner.discovermeals.models.Ingredient
import java.text.Collator

class IngredientsComparator : Comparator<Ingredient> {
    override fun compare(i1: Ingredient, i2: Ingredient): Int {
        return Collator.getInstance().compare(i1.name, i2.name)
    }
}