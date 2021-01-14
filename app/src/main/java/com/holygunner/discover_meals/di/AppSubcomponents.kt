package com.holygunner.discover_meals.di

import dagger.Module

@Module(
    subcomponents = [
        MainActivitySubcomponent::class,
        FragmentsSubcomponent::class
    ]
)
class AppSubcomponents