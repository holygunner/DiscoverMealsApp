package com.holygunner.discover_meals.business_logic

import com.holygunner.discover_meals.models.Ingredient
import java.text.Collator

class IngredientsComparator : Comparator<Ingredient> {
    override fun compare(i1: Ingredient, i2: Ingredient): Int {
        return Collator.getInstance().compare(i1.name, i2.name)
    }
}