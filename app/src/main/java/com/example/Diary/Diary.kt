package com.example.Diary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diaries_table")
data class Diary(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "red")
    var red: Int,
    @ColumnInfo(name = "green")
    var green: Int,
    @ColumnInfo(name = "blue")
    var blue: Int,
    @ColumnInfo(name = "alpha")
    var alpha: Int,

)


