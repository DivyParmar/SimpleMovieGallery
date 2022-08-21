package com.myapp.simplegallery.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myapp.simplegallery.interfaces.Dao

@Database(entities = [MoviesTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(this){
                    instance =Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,"SimpleGallery_Db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }
    }
}