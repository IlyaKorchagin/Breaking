package com.korchagin.breaking.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.korchagin.breaking.R
import com.korchagin.breaking.common.Result
import com.korchagin.breaking.common.Status
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.helper.setPositionBackgroundColor
import com.korchagin.breaking.helper.setPositionColor
import com.korchagin.breaking.helper.setPositionFontSize
import com.korchagin.breaking.helper.setPositionStar
import com.korchagin.breaking.presentation.Screen
import com.korchagin.breaking.presentation.screens.common.AppBarState
import com.korchagin.breaking.presentation.screens.common.CustomProgressBar
import com.korchagin.breaking.presentation.screens.common.shimmerBrush
import com.korchagin.breaking.presentation.view_model.AllPupilsViewModel
import com.korchagin.breaking.ui.theme.Progress

@Composable
fun RatingScreen(
    onComposing: (AppBarState) -> Unit,
    navController: NavController,
    viewModel: AllPupilsViewModel = hiltViewModel()
) {
    val checkedState = remember { mutableStateOf(true) }

    val state = viewModel.pupilList.collectAsState(initial = null)

    val elementsArray = stringArrayResource(R.array.elementsBDList)

    var selectedItem by remember {
        mutableStateOf(elementsArray[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    if (state.value == null) viewModel.getAllPupils("rating")

    if (state.value != null) {
        // Log.d("ILYA", "status = ${state.value!!.status}")
        if (state.value!!.status == Status.SUCCESS) {
            state.value!!.data?.let { pupilsList ->
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "My Toolbar") },
                                navigationIcon = {
                                    IconButton(onClick = { /* Handle navigation icon click */ }) {
                                        Icon(Icons.Default.Home, contentDescription = "Menu")
                                    }
                                },
                                actions = {
                                    IconButton(onClick = { checkedState.value = !checkedState.value }) {
                                        Icon(Icons.Default.Settings, contentDescription = "Search")
                                    }
                                },
                                backgroundColor = MaterialTheme.colors.primary
                            )
                        },
                        content = {
                            if (checkedState.value) NewRatingTable(
                                ratingList = pupilsList, navController = navController
                            )
                            else {
                                RatingTable(
                                    state = state.value!!,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = Color.Black)
                                )
                            }
                        }
                    )

                }
            }

        }
        if (state.value!!.status == Status.ERROR) {
            state.value!!.message?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
        }
        if (state.value!!.status == Status.LOADING) {
            CircularProgressIndicator(modifier = Modifier.wrapContentWidth())
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            CircularProgressIndicator(modifier = Modifier.wrapContentWidth())
        }
    }
    LaunchedEffect(key1 = true) {
        onComposing(AppBarState(title = "Rating Table", actions = {
            IconButton(onClick = { checkedState.value = !checkedState.value }) {
                Icon(
                    imageVector = Icons.Default.Favorite, contentDescription = null
                )
            }
        }))
    }
}

@Composable
fun NewRatingTable(
    ratingList: List<PupilEntity>, navController: NavController, modifier: Modifier = Modifier
) {
    val showShimmer = remember { mutableStateOf(true) }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 57.dp)
    ) {
        itemsIndexed(ratingList) { index, value ->
            var startBackgroundColor = Color.White
            var endBackgroundColor = setPositionBackgroundColor(index)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                startBackgroundColor, endBackgroundColor
                            ), startX = 300.0f
                        )
                    )
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(value.avatar)
                        .crossfade(true).build(),
                    contentDescription = "default crossfade example",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            shimmerBrush(
                                targetValue = 1300f, showShimmer = showShimmer.value
                            )
                        )
                        .border(3.dp, Color.Gray, CircleShape)
                        .clickable { navController.navigate(Screen.DetailScreen.route) },
                    onSuccess = { showShimmer.value = false },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = value.name, letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    value.rating?.let { progress ->
                        CustomProgressBar(
                            Modifier
                                .clip(shape = RoundedCornerShape(5.dp))
                                .height(25.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            width = 300.dp,
                            Color.White,
                            Brush.horizontalGradient(listOf(Color.White, Progress)),
                            progress.toInt(),
                            true
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        Arrangement.Start,
                        Alignment.CenterVertically
                    ) {
                        val positionImage = setPositionStar(index)
                        val positionSize = setPositionFontSize(index)
                        Image(
                            painter = painterResource(id = positionImage), contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "${index + 1} - место", fontSize = positionSize.sp
                        )
                    }
                }
            }

            if (index < ratingList.size - 1) {
                Column(
                    modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End
                ) {
                    Divider(
                        modifier = Modifier.fillMaxWidth(0.78f),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
fun RatingTable(
    state: Result<List<PupilEntity>>, modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .background(color = Color.Black)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier.width(80.dp), contentAlignment = Alignment.Center
            ) {
                Text(text = "Позиция", fontSize = 14.sp, color = Color.White)
            }

            Box(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth(0.7f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Фамилия и имя", fontSize = 14.sp, color = Color.White)
            }

            Box(
                modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center
            ) {
                Text(text = "Рейтинг", fontSize = 14.sp, color = Color.White)
            }
        }
        LazyColumn(
            modifier = modifier
        ) {
            state.data?.let {
                itemsIndexed(it) { index, item ->
                    Row(
                        modifier = Modifier
                            .background(color = Color.Black)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val color = setPositionColor(index)
                        val fontSize = setPositionFontSize(index)

                        Box(
                            modifier = Modifier.width(80.dp), contentAlignment = Alignment.Center
                        ) {
                            Text(text = "${index + 1}", fontSize = fontSize.sp, color = color)
                        }

                        Box(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .fillMaxWidth(0.7f), contentAlignment = Alignment.Center
                        ) {
                            Text(text = "${item.name}", fontSize = fontSize.sp, color = color)
                        }

                        Box(
                            modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center
                        ) {
                            Text(text = "${item.rating}", fontSize = fontSize.sp, color = color)
                        }

                    }
                }
            }
        }

    }

}

