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
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getColor
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.korchagin.breaking.R
import com.korchagin.breaking.domain.common.LOCK
import com.korchagin.breaking.domain.common.Result
import com.korchagin.breaking.domain.common.Status
import com.korchagin.breaking.domain.model.ElementEntity
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.helper.setAvatarBorder
import com.korchagin.breaking.helper.setElementColor
import com.korchagin.breaking.helper.setElementImage
import com.korchagin.breaking.helper.setLevel
import com.korchagin.breaking.model.Elements
import com.korchagin.breaking.model.ImageWithText
import com.korchagin.breaking.presentation.Screen
import com.korchagin.breaking.presentation.screens.common.CustomProgressBar
import com.korchagin.breaking.presentation.screens.common.getElementRating
import com.korchagin.breaking.presentation.screens.common.setProgress
import com.korchagin.breaking.presentation.screens.common.shimmerBrush
import com.korchagin.breaking.presentation.view_model.ElementViewModel
import com.korchagin.breaking.presentation.view_model.LogInViewModel
import com.korchagin.breaking.presentation.view_model.MainViewModel
import com.korchagin.breaking.ui.theme.Default
import com.korchagin.breaking.ui.theme.Progress
import kotlinx.coroutines.launch

const val FREEZE = 0
const val POWER = 1
const val OFP = 2
const val STRETCH = 3

@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ElementsScreen(
    navController: NavController,
    email: String,
    sharedViewModel: ElementViewModel,
    viewModel: MainViewModel = hiltViewModel(),
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    selectedTabIndex = sharedViewModel.elementTabPosition
    val state = viewModel.curPupil.collectAsState(initial = null)
    if (state.value != null) state.value!!.data?.let { sharedViewModel.addCurrentPupil(it) }
    //   Log.d("ILYA", "state = $state")
    val stateFreeze = viewModel.freezeList.collectAsState(initial = null)
    val statePower = viewModel.powerMoveList.collectAsState(initial = null)
    val stateOfp = viewModel.ofpList.collectAsState(initial = null)
    val stateStretch = viewModel.stretchList.collectAsState(initial = null)
//    Log.d("ILYA", "email = ${sharedViewModel.curPupil?.email}")
    if (state.value == null) sharedViewModel.curPupil?.let { viewModel.getCurrentPupil(it.email) }
    if (stateFreeze.value == null) viewModel.getFreezeElements()
    if (statePower.value == null) viewModel.getPowerMoveElements()
    if (stateOfp.value == null) viewModel.getOfpElements()
    if (stateStretch.value == null) viewModel.getStretchElements()
    if (state.value != null && stateFreeze.value?.data != null) {
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
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Center
            ) {
                CircularProgressIndicator(modifier = Modifier.wrapContentWidth())
            }
        }

        if (state.value!!.status == Status.SUCCESS) {
            Column {
                //             Log.d("ILYA", "state.value = ${state.value!!.data}")
                state.value!!.data?.let { ProfileSection(it, viewModel, selectedTabIndex) }

                Spacer(modifier = Modifier.height(5.dp))

                val imageWithTexts = listOf(
                    ImageWithText(
                        image = painterResource(id = R.drawable.baby),
                        text = stringResource(R.string.FreezeTitle)
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.airflare),
                        text = stringResource(R.string.PowerMoveTitle)
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.pushups),
                        text = stringResource(R.string.OFPTitle)
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.twin),
                        text = stringResource(R.string.StretchTitle)
                    ),
                )
                AnimatedTabWithHorizontalPager(
                    stateFreeze = stateFreeze,
                    statePower = statePower,
                    stateOfp = stateOfp,
                    stateStretch = stateStretch,
                    state = state,
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    tabs = imageWithTexts,
                    onTabSelected = { selectedTabIndex = it },
                )
            }
        }
    }
}


@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@Composable
fun ProfileSection(curPupil: PupilEntity, viewModel: MainViewModel, selectedTabIndex: Int) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                ImageBorderAnimation(curPupil = curPupil, viewModel = viewModel)
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 5.dp),
                    text = "Изменить аватар",
                    fontFamily = FontFamily.Serif,
                    color = Color.Gray,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textDecoration = TextDecoration.Underline,
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            InfoSection(curPupil = curPupil, selectedTabIndex)
        }
    }
}


@Composable
fun InfoSection(
    curPupil: PupilEntity,
    selectedElementsTab: Int,
    modifier: Modifier = Modifier
) {
    var text = ""
    var rating = 0

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        Text(curPupil.name,
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .graphicsLayer(alpha = 0.8f)
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            Brush.horizontalGradient(
                                listOf(
                                    Default,
                                    Color.Black
                                )
                            ), blendMode = BlendMode.SrcAtop
                        )
                    }
                })
        /*  Text(
              setStatus(curPupil.status),
              style = TextStyle(
                  textAlign = TextAlign.Center,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold
              )
          )*/
        Text(
            setLevel(curPupil.rating.toInt()),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )

        when (selectedElementsTab) {
            FREEZE -> {
                text = "Рейтинг по фризам: "
                rating = curPupil.freeze_rating.toInt()
            }

            POWER -> {
                text = "Рейтинг по PowerMoves: "
                rating = curPupil.powermove_rating.toInt()
            }

            OFP -> {
                text = "Рейтинг по ОФП: "
                rating = curPupil.ofp_rating.toInt()
            }

            STRETCH -> {
                text = "Рейтинг по растяжке: "
                rating = curPupil.strechingRating.toInt()
            }
        }

        Text(
            modifier = Modifier.animateContentSize(
                animationSpec = tween(400, easing = FastOutSlowInEasing),
            ),
            text = text,
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
        CustomProgressBar1(progress = rating.toFloat())
    }
}

@Composable
fun CustomProgressBar1(progress: Float) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(key1 = progress) {
        animatedProgress.animateTo(progress, animationSpec = tween(durationMillis = 1500))
    }
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(15.dp)
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .height(30.dp)
                .background(Color(getColor(LocalContext.current, R.color.light_gray)))
                .width(300.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .height(30.dp)
                    .background(
                        Brush.horizontalGradient(listOf(Color.White, Progress))
                    )
                    .fillMaxWidth(animatedProgress.value / 100f)
            )
            Text(
                text = "$progress %",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

    }
}

@ExperimentalFoundationApi
@Composable
fun PostSection(
    posts: List<Elements>,
    stateElement: List<ElementEntity>?,
    navController: NavController,
    modifier: Modifier = Modifier,
    sharedViewModel: ElementViewModel,
    state: State<Result<PupilEntity>?>,
) {
    val showShimmer = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 57.dp)
    ) {
        itemsIndexed(posts) { index, value ->
            val startBackgroundColor = Color.White
            val endBackgroundColor = setElementColor(value.title)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                startBackgroundColor,
                                endBackgroundColor
                            ),
                            startX = 300.0f
                        )
                    )
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val borderColor = setElementColor(value.title)

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(value.icon)
                        .crossfade(true)
                        .build(),
                    contentDescription = "default crossfade example",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = showShimmer.value
                            )
                        )
                        .border(3.dp, borderColor, CircleShape)
                        .clickable {
                            scope.launch {
                                stateElement
                                    ?.get(index)
                                    ?.let {
                                        sharedViewModel.addElement(it)
                                        sharedViewModel.addElementRating(
                                            state.value!!.data!!.getElementRating(
                                                it.title
                                            )
                                        )
                                    }
                            }
                            if (value.icon != LOCK) navController.navigate(Screen.DetailScreen.route)
                        },
                    onSuccess = { showShimmer.value = false },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = value.title,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    value.progress?.let { progress ->
                        CustomProgressBar(
                            Modifier
                                .clip(shape = RoundedCornerShape(5.dp))
                                .height(25.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            Color.White,
                            Brush.horizontalGradient(listOf(Color.White, Progress)),
                            progress.toInt(),
                            true
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = value.block_description,
                        fontSize = 12.sp
                    )
                }

            }

            if (index < posts.size - 1) {
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



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedTabWithHorizontalPager(
    tabs: List<ImageWithText>,
    onTabSelected: (Int) -> Unit,
    stateFreeze: State<Result<List<ElementEntity>>?>,
    statePower: State<Result<List<ElementEntity>>?>,
    stateOfp: State<Result<List<ElementEntity>>?>,
    stateStretch: State<Result<List<ElementEntity>>?>,
    state: State<Result<PupilEntity>?>,
    navController: NavController,
    sharedViewModel: ElementViewModel,
) {
    val pagerState = rememberPagerState(initialPage = sharedViewModel.elementTabPosition) {
        tabs.size
    }
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
    ) {
        tabs.forEachIndexed { index, tab ->
            val selected = index == pagerState.currentPage
            val scale = remember { Animatable(1f) }

            LaunchedEffect(selected) {
                Log.d("ILYA", "LaunchedEffect currentPage = ${pagerState.currentPage}")
                onTabSelected(pagerState.currentPage)
                sharedViewModel.elementTabPosition = pagerState.currentPage
                if (selected) {
                    scale.animateTo(1.9f, animationSpec = spring())
                } else {
                    scale.animateTo(1.2f, animationSpec = spring())
                }
            }
            Tab(
                modifier = Modifier
                    .background(Color(getColor(LocalContext.current, R.color.white))),
                selected = selected,
                onClick = {
                    scope.launch {
                        Log.d("ILYA", "on Click index = $index")
                        sharedViewModel.elementTabPosition = index
                        onTabSelected(index)
                        pagerState.animateScrollToPage(
                            index,
                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                        )
                    }
                }
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                    horizontalAlignment = CenterHorizontally

                ) {
                    RoundImage(
                        image = tab.image,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(vertical = 5.dp)
                            .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
                            .border(
                                width = 1.dp,
                                color = if (selected) Color.Black else Color.LightGray,
                                shape = CircleShape
                            )
                    )
                    Text(
                        text = tab.text,
                        color = if (selected)
                            Color(getColor(LocalContext.current, R.color.black))
                        else Color(getColor(LocalContext.current, R.color.light_gray))
                    )
                }
            }
        }
    }

    HorizontalPager(state = pagerState) { page ->
        AnimatedVisibility(
            visible = pagerState.currentPage == page,
            enter = fadeIn(animationSpec = tween(durationMillis = 1200)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1200))
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (page) {
                    FREEZE -> {
                        val posts: MutableList<Elements> =
                            emptyList<Elements>().toMutableList()

                        stateFreeze.value?.data?.forEach {
                            posts.add(
                                Elements(
                                    icon = state.value?.data?.let { it1 ->
                                        setElementImage(
                                            elementTitle = it.title,
                                            currentPupil = it1,
                                            info = it
                                        )
                                    },
                                    title = it.title,
                                    block_description = it.blockDescription,
                                    progress = state.value?.data?.let { currentPupil ->
                                        currentPupil.setProgress(it.title)
                                    }
                                )
                            )
                        }
                        PostSection(
                            posts = posts,
                            stateElement = stateFreeze.value?.data,
                            navController = navController,
                            modifier = Modifier.fillMaxWidth(),
                            sharedViewModel = sharedViewModel,
                            state = state
                        )
                    }


                    POWER -> {
                        val posts: MutableList<Elements> =
                            emptyList<Elements>().toMutableList()

                        statePower.value?.data?.forEach {
                            posts.add(
                                Elements(
                                    icon = state.value?.data?.let { it1 ->
                                        setElementImage(
                                            elementTitle = it.title,
                                            currentPupil = it1,
                                            info = it
                                        )
                                    },
                                    title = it.title,
                                    block_description = it.blockDescription,
                                    progress = state.value?.data?.let { currentPupil ->
                                        currentPupil.setProgress(it.title)
                                    }
                                )
                            )
                        }
                        PostSection(
                            posts = posts,
                            stateElement = statePower.value?.data,
                            navController = navController,
                            modifier = Modifier.fillMaxWidth(),
                            sharedViewModel = sharedViewModel,
                            state = state
                        )
                    }

                    OFP -> {
                        val posts: MutableList<Elements> =
                            emptyList<Elements>().toMutableList()

                        stateOfp.value?.data?.forEach {
                            posts.add(
                                Elements(
                                    icon = setElementImage(
                                        elementTitle = it.title,
                                        currentPupil = state.value!!.data!!,
                                        info = it
                                    ),
                                    title = it.title,
                                    block_description = it.blockDescription,
                                    progress = state.value?.data?.let { currentPupil ->
                                        currentPupil.setProgress(it.title)
                                    }
                                )
                            )
                        }

                        PostSection(
                            posts = posts,
                            stateElement = stateOfp.value?.data,
                            navController = navController,
                            modifier = Modifier.fillMaxWidth(),
                            sharedViewModel = sharedViewModel,
                            state = state
                        )
                    }

                    STRETCH -> {
                        val posts: MutableList<Elements> =
                            emptyList<Elements>().toMutableList()

                        stateStretch.value?.data?.forEach {
                            posts.add(
                                Elements(
                                    icon = setElementImage(
                                        elementTitle = it.title,
                                        currentPupil = state.value!!.data!!,
                                        info = it
                                    ),
                                    title = it.title,
                                    block_description = it.blockDescription,
                                    progress = state.value?.data?.let { currentPupil ->
                                        currentPupil.setProgress(it.title)
                                    }
                                )
                            )
                        }
                        PostSection(
                            posts = posts,
                            stateElement = stateStretch.value?.data,
                            navController = navController,
                            modifier = Modifier.fillMaxWidth(),
                            sharedViewModel = sharedViewModel,
                            state = state
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun ImageBorderAnimation(curPupil: PupilEntity, viewModel: MainViewModel) {
    val showShimmer = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    val infiniteTransition = rememberInfiniteTransition()
    val rotationValue = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(2000, easing = LinearEasing)
        )
    )
    //Log.d("ILYA", "avatar = ${curPupil.avatar}")
    val avatarBorderColor = setAvatarBorder(curPupil)
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(curPupil.avatar)
            .crossfade(true)
            .build(),
        contentDescription = "default crossfade example",
        modifier = Modifier
            .drawBehind {
                rotate(rotationValue.value) {
                    drawCircle(
                        Brush.horizontalGradient(
                            avatarBorderColor
                        ), style = Stroke(10f)
                    )
                }
            }
            .size(120.dp)
            .clip(CircleShape)
            .background(
                shimmerBrush(
                    targetValue = 1300f,
                    showShimmer = showShimmer.value
                )
            )
            .clickable {
                scope.launch {
                    launcher.launch("image/*")
                }
            },
        onSuccess = { showShimmer.value = false },
        contentScale = ContentScale.Crop
    )
    LaunchedEffect(key1 = imageUri) {
        scope.launch {
            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }
            }
            bitmap.value?.let { viewModel.uploadImage(it, curPupil.email)
            }
        }
    }
}






