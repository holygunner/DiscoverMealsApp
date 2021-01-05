package com.achyzh.discovermeals2020.di

import dagger.Module

@Module(
    subcomponents = [
        MainActivitySubcomponent::class,
        FragmentsSubcomponent::class
    ]
)
class AppSubcomponents