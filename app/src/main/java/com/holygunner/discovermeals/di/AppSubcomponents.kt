package com.holygunner.discovermeals.di

import dagger.Module

@Module(
    subcomponents = [
        MainActivitySubcomponent::class,
        FragmentsSubcomponent::class
    ]
)
class AppSubcomponents