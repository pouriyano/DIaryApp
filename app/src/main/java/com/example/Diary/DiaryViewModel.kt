package com.example.Diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DiaryViewModel(val diaryRepository: DiaryRepository=Graph.diaryRepository) :ViewModel()
{





        lateinit var getAllDiaries: Flow<List<Diary>>
    init {
        viewModelScope.launch {
            getAllDiaries = diaryRepository.getDiaries()
        }
    }
    fun addDiary(diary: Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryRepository.addDiary(diary)
        }
    }
    fun updateDiary(diary: Diary) {


            viewModelScope.launch(Dispatchers.IO) {
                diaryRepository.UpdateADiary(diary)
            }



    }

    fun deleteDiary(diary: Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryRepository.DeleteADiary(diary)
        }
    }

}