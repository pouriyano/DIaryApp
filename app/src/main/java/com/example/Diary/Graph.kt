package com.example.Diary

import android.content.Context
import androidx.room.Room

object Graph {


    lateinit var database: DiaryDatabase

    val diaryRepository by lazy {
        DiaryRepository(database.diaryDao())

    }

    fun provider(contex:Context){
        database= Room.databaseBuilder(contex,DiaryDatabase::class.java,"diaries_table") .build()

    }

}