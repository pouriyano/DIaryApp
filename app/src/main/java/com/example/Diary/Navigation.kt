package com.example.Diary

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson

@Composable
fun Navigation(viewModel: DiaryViewModel) {

    val navController = rememberNavController()
    val gson = Gson()

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable("DiaryScreen/{diaryobject}") { backStackEntry ->
            val diaryJson = backStackEntry.arguments?.getString("diaryobject")
            val diaryObject = gson.fromJson(diaryJson, Diary::class.java)
            DiaryScreen(navController = navController, diary = diaryObject, viewModel)
        }
    }

}



