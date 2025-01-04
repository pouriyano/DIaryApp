package com.example.Diary


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Diary::class], version = 1, exportSchema = false)
abstract class DiaryDatabase : RoomDatabase() {


    abstract fun diaryDao(): DiaryDao


}
