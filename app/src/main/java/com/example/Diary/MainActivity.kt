package com.example.Diary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.Diary.ui.theme.RoomdataTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val viewModel: DiaryViewModel = viewModel()

            RoomdataTheme {
                Navigation(viewModel)


            }
        }
    }
}


