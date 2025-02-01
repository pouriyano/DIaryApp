package com.example.Diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiaryViewModel(val diaryRepository: DiaryRepository=Graph.diaryRepository) :ViewModel()
{
    private val _getAllDiaries = MutableStateFlow<List<Diary>>(emptyList())
    val getAllDiaries: StateFlow<List<Diary>> get() = _getAllDiaries

    init {
        viewModelScope.launch {
            diaryRepository.getDiaries().collect {
                _getAllDiaries.value = it
            }
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