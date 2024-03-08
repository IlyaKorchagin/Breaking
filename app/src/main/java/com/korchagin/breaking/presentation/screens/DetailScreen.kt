package com.korchagin.breaking.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.korchagin.breaking.R
import com.korchagin.breaking.domain.model.ElementEntity
import com.korchagin.breaking.helper.setDescriptionImage
import com.korchagin.breaking.helper.setDescriptionText
import com.korchagin.breaking.presentation.view_model.ElementViewModel
import com.korchagin.breaking.presentation.view_model.MainViewModel
import com.korchagin.breaking.ui.theme.OnePosition
import com.korchagin.breaking.ui.theme.Progress
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@SuppressLint("UnrememberedMutableState", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    sharedViewModel: ElementViewModel
) {

    val element = sharedViewModel.element
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = LocalContext.current.getString(R.string.backToHome)) },
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
                //                Log.d("ILYA", "element = $element")
                element?.videoUrl?.let { YoutubeScreen(videoId = it, modifier = Modifier) }
                if (element != null) {
                    Log.d("ILYA", "element rating = ${sharedViewModel.elementRating / 10}")
                    DescriptionSection(
                        element = element,
                        rating = sharedViewModel.elementRating / 10
                    )
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
    rating: Int,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(10) { index ->
            Log.d("ILYA", "index = $index")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        brush = if (rating >= index + 1) Brush.horizontalGradient(
                            listOf(
                                Color.White,
                                Progress
                            ), startX = 350f
                        ) else Brush.horizontalGradient(
                            listOf(Color.White, OnePosition),
                            startX = 350f
                        ), shape = CircleShape
                    )
                    .border(3.dp, Color.Gray, CircleShape),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = setDescriptionImage(index)),
                    contentDescription = "",
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .padding(10.dp)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(5.dp),
                    text = setDescriptionText(index, element),
                    fontSize = 16.sp,
                    letterSpacing = 1.sp
                )
            }
        }


    }
}

/*@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(navController = NavController(LocalContext.current), sharedViewModel = ElementViewModel())
}*/
