package com.korchagin.breaking.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.korchagin.breaking.R
import com.korchagin.breaking.domain.model.ElementEntity
import com.korchagin.breaking.helper.setDescriptionImage
import com.korchagin.breaking.helper.setDescriptionText
import com.korchagin.breaking.presentation.view_model.ElementViewModel
import com.korchagin.breaking.presentation.view_model.LogInViewModel
import com.korchagin.breaking.presentation.view_model.MainViewModel
import com.korchagin.breaking.presentation.view_model.SharedViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.scopes.ViewModelScoped

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    sharedViewModel: ElementViewModel,
) {
    val element = sharedViewModel.element
   LaunchedEffect(key1 = element){
       Log.d("ILYA", "shared_element = $element")
   }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Log.d("ILYA", "element = $element")
        element?.videoUrl?.let { YoutubeScreen(videoId = it, modifier = Modifier) }
        if (element != null) {
            DescriptionSection(element = element)
        }
    }
}

@Composable
fun YoutubeScreen(
    videoId: String,
    modifier: Modifier
) {
    LocalContext.current
    AndroidView(factory = {
        var view = YouTubePlayerView(it)
        view.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        )
        view
    })
}

@ExperimentalFoundationApi
@Composable
fun DescriptionSection(
    element: ElementEntity,
    modifier: Modifier = Modifier
) {
    val showShimmer = remember { mutableStateOf(true) }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 57.dp)
    ) {
        items(10) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Image(
                    painter = painterResource(id = setDescriptionImage(index)),
                    contentDescription = "",
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .padding(end = 10.dp)
                )
                Text(
                    text = setDescriptionText(index, element),
                    letterSpacing = 1.sp
                )
            }
            if (index < 9) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
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

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(navController = NavController(LocalContext.current), sharedViewModel = ElementViewModel())
}
