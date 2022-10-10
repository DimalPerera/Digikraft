package com.digikraftapp.bikestation.application

import android.content.Context
import com.digikraftapp.bikestation.repository.StationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


    /**
    * According to android best practices, context can't pass directly to the Repository.
    * Here we used Hilt for dependency injection for the Application context
    */

    @Singleton
    @Provides
    fun provideJsonDatabase(@ApplicationContext appContext: Context): StationRepository {
        return StationRepository(context = appContext)
    }

}
