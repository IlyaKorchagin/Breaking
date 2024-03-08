package com.korchagin.breaking.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.korchagin.breaking.R
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.helper.setAvatarBorder
import com.korchagin.breaking.presentation.Screen
import com.korchagin.breaking.presentation.screens.common.ExitAlertDialog
import com.korchagin.breaking.presentation.screens.common.shimmerBrush
import com.korchagin.breaking.presentation.view_model.ElementViewModel
import com.korchagin.breaking.presentation.view_model.LogInViewModel
import com.korchagin.breaking.presentation.view_model.MainViewModel
import com.korchagin.breaking.ui.theme.Bronze
import com.korchagin.breaking.ui.theme.Silver
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyAccountScreen(
    navController: NavController,
    sharedViewModel: ElementViewModel,
    viewModel: LogInViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val curPupil = sharedViewModel.curPupil
    val showShimmer = remember { mutableStateOf(true) }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = LocalContext.current.getString(R.string.back)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Search")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Gray, Color.White),
                            startY = 0f,
                            endY = 300f
                        )
                    )
                    .verticalScroll(rememberScrollState())

            ) {

                if (showDialog) {
                    ExitAlertDialog(
                        onConfirmExit = {
                            viewModel.signOut()
                            navController.navigate(Screen.LogInScreen.route) {
                                popUpTo(Screen.LogInScreen.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onDismiss = { showDialog = false }
                    )
                }
                if (curPupil != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 3.dp,
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            Color.Black,
                                            Silver
                                        )
                                    ),
                                    shape = RoundedCornerShape(percent = 50)
                                )
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(curPupil.avatar)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "default crossfade example",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .background(
                                        shimmerBrush(
                                            targetValue = 1300f,
                                            showShimmer = showShimmer.value
                                        )
                                    ),
                                onSuccess = { showShimmer.value = false },
                                contentScale = ContentScale.Crop
                            )
                        }
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center)
                            ) {
                                Text(
                                    text = curPupil.name,
                                    letterSpacing = 0.1.em,
                                    fontFamily = FontFamily.Serif,
                                    style = TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    textDecoration = TextDecoration.Underline,
                                    modifier = Modifier
                                        .graphicsLayer(alpha = 0.99f)
                                        .padding(8.dp)
                                        .drawWithCache {
                                            onDrawWithContent {
                                                drawContent()
                                                drawRect(
                                                    Brush.horizontalGradient(
                                                        listOf(
                                                            Color.Black,
                                                            Bronze
                                                        )
                                                    ), blendMode = BlendMode.SrcAtop
                                                )
                                            }
                                        }
                                )
                            }
                            StyledTextScreen(
                                title = "Возраст: ",
                                description =   if(curPupil.born.isNotEmpty()) calculateAge(curPupil.born).toString()
                                else "не указано"
                            )
                            StyledTextScreen(title = "Дата рождения: ", description = curPupil.born)
                            StyledTextScreen(title = "Страна: ", description = curPupil.country)
                            StyledTextScreen(title = "Город: ", description = curPupil.city)
                        }

                    }

                    YoutubeScreen(
                        videoId = curPupil.video,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                }
            }
        }
    )
}

