package com.example.deviceconnector.ui.news.architecture

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.deviceconnector.ui.news.NewsModel
import com.example.deviceconnector.util.Constants.DATABASE_NAME

@Database(entities = [NewsModel::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabaseClient(context: Context): NewsDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, NewsDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }

    }

}