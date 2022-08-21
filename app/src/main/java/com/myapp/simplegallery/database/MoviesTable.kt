package com.myapp.simplegallery.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.nio.channels.FileLock

@Entity(tableName = "moviesTable")
data class MoviesTable(
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "desc") var desc: String,
    @ColumnInfo(name = "imageUrl") var imageUrl : String,
    @ColumnInfo(name = "name") var name : String,
) {
    @PrimaryKey(autoGenerate = true) var movieId: Int=0
}