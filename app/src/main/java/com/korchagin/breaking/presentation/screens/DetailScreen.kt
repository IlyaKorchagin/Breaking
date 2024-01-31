package com.korchagin.breaking.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.korchagin.breaking.domain.model.ElementEntity
import com.korchagin.breaking.helper.setDescriptionImage
import com.korchagin.breaking.helper.setDescriptionText
import com.korchagin.breaking.presentation.Screen
import com.korchagin.breaking.presentation.view_model.ElementViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@SuppressLint("UnrememberedMutableState", "UnusedMaterialScaffoldPaddingParameter")
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "My Toolbar") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Menu")
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary
                )
            },
            content = {
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
        )
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
