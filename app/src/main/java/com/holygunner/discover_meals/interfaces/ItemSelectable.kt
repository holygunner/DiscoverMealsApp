package com.holygunner.discover_meals.interfaces

interface ItemSelectable<T> {
    fun onItemSelected(item: T)
}