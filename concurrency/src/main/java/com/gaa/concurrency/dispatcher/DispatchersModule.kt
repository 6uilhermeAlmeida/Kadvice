package com.gaa.concurrency.dispatcher

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ApplicationComponent::class)
object DispatchersModule {

    @Provides
    fun provideDispatcherProvider() = DispatcherProvider(
        main = Dispatchers.Main,
        io = Dispatchers.IO,
        cpu = Dispatchers.Default
    )
}