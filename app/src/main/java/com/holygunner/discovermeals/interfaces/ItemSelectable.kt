package com.holygunner.discovermeals.interfaces

interface ItemSelectable<T> {
    fun onItemSelected(item: T)
}