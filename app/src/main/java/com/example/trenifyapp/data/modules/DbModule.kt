package com.example.trenifyapp.data.modules

import android.app.Application
import androidx.room.Room
import com.example.trenifyapp.data.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideAppDb(app: Application) : AppDb {
        return Room.databaseBuilder(
            app,
            AppDb::class.java,
            "trenify.db"
        ).createFromAsset("database/trenify.db").build()
    }
}