package com.example.Diary

import kotlinx.coroutines.flow.Flow

class DiaryRepository(private val diaryDao: DiaryDao)
{

    suspend fun addDiary(diary: Diary)
    {
        diaryDao.addDiary(diary)
    }

    fun getDiaries():Flow<List<Diary>> =diaryDao.getALlDiaries()


    suspend fun UpdateADiary(diary: Diary)
    {

        diaryDao.updateDiary(diary)
    }

    suspend fun DeleteADiary(diary: Diary)
    {
       diaryDao.deleteDiary(diary)
    }



}