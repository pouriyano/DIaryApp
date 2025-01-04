package com.example.Diary

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import com.google.gson.Gson
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.Dairy.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSection(searchText: MutableState<String>) {
    SearchBar(
        shape = RoundedCornerShape(24.dp),
        query = searchText.value,
        onQueryChange = { searchText.value = it },
        placeholder = {
            Text(
                text = "Search Diaries",
                fontSize = 14.sp,
                color = Color.Gray
            )
        },
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search",
                tint = Color.Gray
            )
        },
        trailingIcon = {
            if (searchText.value.isNotEmpty()) {
                IconButton(onClick = { searchText.value = "" }) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "",
                        tint = Color.Gray
                    )
                }
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color.LightGray.copy(alpha = 0.2f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(86.dp)
            .padding(16.dp)
    ){}
}

@Composable
fun DiaryGridSection(
    diaries: List<Diary>,
    navController: NavController,
    viewModel: DiaryViewModel,
    gson: Gson
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 14.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(diaries) { diary ->
            DiaryItem(diary, navController, viewModel, gson)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiaryItem(
    diary: Diary,
    navController: NavController,
    viewModel: DiaryViewModel,
    gson: Gson
) {
    val showDeleteOption = remember { mutableStateOf(false) }
    val boxHeight = calculateBoxHeight(diary.content.length)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .heightIn(min = 30.dp, max = 300.dp)
            .height(boxHeight)
            .border(
                shape = RoundedCornerShape(24.dp),
                width = 1.dp,
                color = Color.Gray
            )
            .background(
                Color(
                    diary.red,
                    diary.green,
                    diary.blue,
                    diary.alpha
                ), shape = RoundedCornerShape(24.dp)
            )
            .combinedClickable(
                onClick = {
                   navController.navigate("DiaryScreen/${gson.toJson(diary)}")



                },
                onLongClick = {
                    showDeleteOption.value = true
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = diary.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                fontSize = 14.sp
            )
            if (showDeleteOption.value) {
                DeleteMenu(showDeleteOption, onDelete = {
                    viewModel.deleteDiary(diary)
                    showDeleteOption.value = false
                })
            }
            Text(
                text = diary.content,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Left,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun DeleteMenu(showDeleteOption: MutableState<Boolean>, onDelete: () -> Unit) {
    DropdownMenu(
        expanded = showDeleteOption.value,
        onDismissRequest = { showDeleteOption.value = false },
        offset = DpOffset(50.dp, -110.dp)
    ) {
        Text(
            text = "Delete",
            modifier = Modifier
                .padding(4.dp)
                .clickable { onDelete() }
        )
    }
}
@Composable
fun DeleteMenu2(showDeleteOption: MutableState<Boolean>, onDelete: () -> Unit) {
    DropdownMenu(
        expanded = showDeleteOption.value,
        onDismissRequest = { showDeleteOption.value = false },
       offset = DpOffset(-25.dp,0.dp)
    ) {
        Text(
            text = "Delete",
            modifier = Modifier
                .padding(4.dp)
                .clickable { onDelete() }
        )
    }
}

@Composable
fun AddDiaryButton(onAddDiary: () -> Unit) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(shape = CircleShape, color = Color(250, 246, 105, 255))
            .clickable { onAddDiary() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.myplus),
            contentDescription = "",
            modifier = Modifier.size(30.dp),
            tint = Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryTopAppBar(navController: NavController, onDelete: () -> Unit) {
    val deletexpanded = remember { mutableStateOf(false) }

    TopAppBar(modifier = Modifier,
        title = {},
        actions = {
            IconButton(onClick = { deletexpanded.value = true }) {
                DeleteMenu2(deletexpanded, onDelete)
                Icon(
                    painter = painterResource(id = R.drawable.baseline_menu_24),
                    contentDescription = ""
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {

                    navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = ""
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.Transparent,
            actionIconContentColor = Color.Black,
            navigationIconContentColor = Color.Black
        )
    )

}
@Composable
fun DiaryContentSection(
    title: String,
    titleChange: (String) -> Unit,
    content: String,
    contentChange: (String) -> Unit,
    selectedColor: MutableState<Color>
) {

    val colors = listOf(
        Color(246, 247, 250, 255),
        Color(176, 242, 255, 255),
        Color(243, 176, 255, 255),
        Color(178, 255, 176, 255),
        Color(255, 252, 176, 255)
    )


    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()

    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { if (it.length <= 19) titleChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            textStyle = TextStyle(
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            placeholder = {
                Text(
                    text = "Title",
                    textAlign = TextAlign.Left,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Thin
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.Gray
            )
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.Gray,
            thickness = 1.dp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = content,
                onValueChange = contentChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    textAlign = TextAlign.Left,
                    fontSize = 18.sp,
                    lineHeight = 24.sp
                ),
                placeholder = {
                    Text(
                        text = "Content",
                        textAlign = TextAlign.Left,
                        fontSize = 14.sp
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.Gray
                )
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.Gray,
            thickness = 1.dp
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            , contentAlignment = Alignment.BottomEnd)
        {
            ColorPickerSection(selectedColor =selectedColor , colors =colors )

        }







    }
}

@Composable
fun calculateBoxHeight(contentLength: Int): Dp {

    return when {
        contentLength < 30 -> 70.dp
        contentLength < 50 -> 90.dp
        contentLength < 75 -> 115.dp
        contentLength < 100 -> 140.dp
        contentLength < 125 -> 170.dp
        contentLength < 150 -> 190.dp
        contentLength < 175 -> 140.dp
        contentLength < 200 -> 250.dp
        contentLength < 225 -> 265.dp
        else -> 300.dp
    }
}

@Composable
fun ColorPickerSection(selectedColor: MutableState<Color>, colors: List<Color>) {
    val isMenuExpanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .systemBarsPadding()
            .size(42.dp)
            .background(selectedColor.value, shape = CircleShape)
            .border(shape = CircleShape, color = Color.Gray, width = 1.dp)

            .clickable(role = Role.DropdownList) { isMenuExpanded.value = true }
    ) {
        DropdownMenu(
            expanded = isMenuExpanded.value,
            onDismissRequest = { isMenuExpanded.value = false },
            modifier = Modifier
                .background(color = Color.White)
                .width(50.dp)
                .height(224.dp),
            offset = DpOffset(-5.dp, 0.dp)
        ) {
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .border(shape = CircleShape, color = Color.Gray, width = 1.dp)
                        .background(color, shape = CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            selectedColor.value = color
                            isMenuExpanded.value = false
                        }
                )
                Spacer(modifier = Modifier.size(4.dp))
            }
        }
    }
}


@Composable
fun MainScreen(viewModel: DiaryViewModel, navController: NavController) {
    val diaries = viewModel.getAllDiaries.collectAsState(initial = listOf())
    val booltoadd = remember { mutableStateOf(false) }
    val gson = Gson()
    val searchText = remember { mutableStateOf("") }
    val filteredDiaries = diaries.value.filter { diary ->
        diary.title.contains(searchText.value, ignoreCase = true) ||
                diary.content.contains(searchText.value, ignoreCase = true)
    }

    LaunchedEffect(diaries.value) {
        if (booltoadd.value && diaries.value.isNotEmpty()) {
            val newDiary = diaries.value.last()
            navController.navigate("DiaryScreen/${gson.toJson(newDiary)}")
            booltoadd.value = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        SearchBarSection(searchText)
        Spacer(modifier = Modifier.size(22.dp))

        if (diaries.value.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "You don't have any Diary", fontWeight = FontWeight.Thin)
            }
        } else {
            DiaryGridSection(filteredDiaries, navController, viewModel, gson)
        }
    }

    Row(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .padding(23.dp),
        horizontalArrangement = Arrangement.Absolute.Right,
        verticalAlignment = Alignment.Bottom
    ) {
        AddDiaryButton {
            val newDiary = Diary(
                id = -0,
                title = "",
                content = "",
                red = 246,
                green = 247,
                blue = 250,
                alpha = 255
            )
            viewModel.addDiary(newDiary)
            booltoadd.value = true
        }
    }
}


@Composable
fun DiaryScreen(navController: NavController, diary: Diary, viewModel: DiaryViewModel) {
    val titleView = remember { mutableStateOf(diary.title) }
    val contentView = remember { mutableStateOf(diary.content) }


    val selectedColor = remember {
        mutableStateOf(
            Color(
                red = diary.red / 255f,
                green = diary.green / 255f,
                blue = diary.blue / 255f,
                alpha = diary.alpha / 255f
            )
        )
    }
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .background(selectedColor.value)
            .padding(16.dp)

    ) {
        DiaryTopAppBar(navController = navController, onDelete = {
            navController.popBackStack()
            viewModel.deleteDiary(diary)
        })




        DiaryContentSection(
            title = titleView.value,
            titleChange = { titleView.value = it },
            content = contentView.value,
            contentChange = { contentView.value = it },selectedColor = selectedColor

        )

    }

    val updatedDiary = diary.copy(
        title = titleView.value,
        content = contentView.value,
        red = (selectedColor.value.red * 255).toInt(),
        green = (selectedColor.value.green * 255).toInt(),
        blue = (selectedColor.value.blue * 255).toInt(),
        alpha = (selectedColor.value.alpha * 255).toInt()
    )
    viewModel.updateDiary(updatedDiary)
}