package com.sina.testchat.di

import android.app.Application
import androidx.room.Room
import com.sina.testchat.MessageRepository
import com.sina.testchat.db.AppDatabase
import com.sina.testchat.db.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @ApplicationScope
    @Singleton
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: AppDatabase.Callback): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "message_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    fun provideNoteDao(appDatabase: AppDatabase): MessageDao = appDatabase.messageDao()

    @Provides
    fun provideRepository(messageDao: MessageDao): MessageRepository = MessageRepository(messageDao)
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope