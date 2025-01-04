package com.example.Diary

import android.app.Application

class DiaryApp:Application()
{
    override fun onCreate() {
        super.onCreate()
        Graph.provider(this)

    }
}