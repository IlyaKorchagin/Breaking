package com.korchagin.breaking.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.korchagin.breaking.R
import com.korchagin.breaking.presentation.screens.common.shimmerBrush
import com.korchagin.breaking.presentation.view_model.ElementViewModel
import com.korchagin.breaking.ui.theme.Bronze
import com.korchagin.breaking.ui.theme.Silver
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BboysDetailScreen(
    navController: NavController,
    sharedViewModel: ElementViewModel,
) {
    val bboy = sharedViewModel.bboy
    val showShimmer = remember { mutableStateOf(true) }
    LaunchedEffect(key1 = bboy) {
        Log.d("ILYA", "shared_element = $bboy")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = LocalContext.current.getString(R.string.back)) },
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
                Log.d("ILYA", "element = $bboy")
                if (bboy != null) {
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
                                    brush = Brush.horizontalGradient(listOf(Color.Black, Silver)),
                                    shape = RoundedCornerShape(percent = 50)
                                )
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(bboy.avatar)
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
                                    text = bboy.name,
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
                                description = calculateAge(bboy.born).toString()
                            )
                            StyledTextScreen(title = "Дата рождения: ", description = bboy.born)
                            StyledTextScreen(title = "Страна: ", description = bboy.country)
                        }

                    }

                    YoutubeScreen(
                        videoId = bboy.video,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    ExpandableTextField(
                        shortDescription = bboy.shortdescription,
                        fullDescription = bboy.description
                    )

                }
            }
        }
    )
}

@Composable
fun ExpandableTextField(shortDescription: String, fullDescription: String) {
    var expanded by remember { mutableStateOf(false) }

    val text = if (expanded) {
        fullDescription
    } else {
        shortDescription
    }

    val title = if (expanded) {
        "Полное описание."
    } else {
        "Краткое описание."
    }

    Text(
        text = title,
        style = MaterialTheme.typography.h6,
        fontFamily = FontFamily.Serif,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        textAlign = TextAlign.Center
    )

    ClickableText(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                2.dp,
                brush = Brush.horizontalGradient(listOf(Color.Black, Silver)),
                shape = RoundedCornerShape(4.dp)
            )
            .border(2.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
            .padding(8.dp),
        text = AnnotatedString(text),
        onClick = {
            expanded = !expanded
        },
        style = TextStyle(
            color = Color.Black,
            fontSize = if (expanded) 14.sp else 18.sp

        )
    )
}

@Composable
fun StyledTextScreen(title: String, description: String) {
    val styledText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray, fontSize = 11.sp)) {
            append(title)
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
            append(description)
        }
    }

    Text(
        text = styledText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        textAlign = TextAlign.Center
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateAge(dateOfBirthString: String): Int {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
    val dateOfBirth = LocalDate.parse(dateOfBirthString, formatter)
    val currentDate = LocalDate.now()
    val period = java.time.Period.between(dateOfBirth, currentDate)
    return period.years
}
