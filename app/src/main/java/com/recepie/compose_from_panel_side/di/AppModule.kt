package com.recepie.compose_from_panel_side.di

import android.content.Context
import com.recepie.compose_from_panel_side.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApplication(@ApplicationContext application: Context): BaseApplication{
        return application as BaseApplication
    }
}