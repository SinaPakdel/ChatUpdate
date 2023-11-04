package com.sina.testchat.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sina.testchat.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao


    class Callback @Inject constructor(
        private val database: Provider<AppDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().messageDao()
            applicationScope.launch {
                dao.insertMessage(
                    Message(
                        text = "Salam1"
                    )
                )
                dao.insertMessage(
                    Message(
                        text = "Salam2"
                    )
                )
                dao.insertMessage(
                    Message(
                        text = "Salam3"
                    )
                )
                dao.insertMessage(
                    Message(
                        text = "Salam4"
                    )
                )
            }
        }
    }
}