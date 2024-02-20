package com.korchagin.breaking.presentation.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.korchagin.breaking.domain.common.LOCK
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.helper.setAvatarBorder
import com.korchagin.breaking.presentation.Screen
import com.korchagin.breaking.presentation.screens.common.shimmerBrush
import com.korchagin.breaking.presentation.view_model.ElementViewModel
import com.korchagin.breaking.presentation.view_model.MainViewModel
import com.korchagin.breaking.ui.theme.Silver
import com.korchagin.breaking.ui.theme.StartAvatar
import kotlinx.coroutines.launch

@Composable
fun BboysScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    sharedViewModel: ElementViewModel,
) {
    val showShimmer = remember { mutableStateOf(true) }
    val state = viewModel.bboysList.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    if (state.value == null) viewModel.getBboys()
    val infiniteTransition = rememberInfiniteTransition()
    val rotationValue = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing)
        )
    )
    Log.d("ILYA", "BboysScreen run curPupil = ${sharedViewModel.curPupil}")
    if (state.value != null && sharedViewModel.curPupil != null) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 52.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Silver),
                        startY = 0f,
                        endY = 1600f
                    )
                ),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            state.value?.data?.let {
                itemsIndexed(it) { index, bboy ->
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                brush = Brush.horizontalGradient(listOf(Color.Black, Silver)),
                                shape = RoundedCornerShape(percent = 50)
                            )
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(if (sharedViewModel.curPupil?.rating?.toInt()!! >= bboy.rating.toInt()) bboy.avatar else LOCK)
                                .crossfade(true)
                                .build(),
                            contentDescription = bboy.name,
                            modifier = Modifier
                                .drawBehind {
                                    rotate(rotationValue.value) {
                                        drawCircle(
                                            Brush.horizontalGradient(
                                                listOf(StartAvatar, Silver)
                                            ), style = Stroke(10f)
                                        )
                                    }
                                }
                                .size(130.dp)
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(
                                    shimmerBrush(
                                        targetValue = 1300f,
                                        showShimmer = showShimmer.value
                                    )
                                )
                                .clickable {
                                    scope.launch {
                                        sharedViewModel.addBboy(bboy)
                                    }
                                    if (sharedViewModel.curPupil?.rating?.toInt()!! >= bboy.rating.toInt()) navController.navigate(
                                        Screen.BboysDetailScreen.route
                                    )
                                },
                            onSuccess = { showShimmer.value = false },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}


