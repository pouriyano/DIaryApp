package com.example.Diary

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
abstract class DiaryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend abstract fun addDiary(diary: Diary)

    @Query("Select * from diaries_table")
    abstract fun getALlDiaries(): Flow<List<Diary>>

    @Update
    abstract suspend fun updateDiary(diary: Diary)

    @Delete
    abstract suspend fun deleteDiary(diary: Diary)

    @Query("Select * from diaries_table where id = :id")
    abstract  fun getADiaryById(id: Long): Flow<Diary>

}