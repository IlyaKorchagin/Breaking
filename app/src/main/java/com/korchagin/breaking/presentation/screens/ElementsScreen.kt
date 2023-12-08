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
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.korchagin.breaking.R
import com.korchagin.breaking.common.Status
import com.korchagin.breaking.domain.common.*
import com.korchagin.breaking.domain.model.ElementEntity
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.helper.*
import com.korchagin.breaking.model.Elements
import com.korchagin.breaking.model.ImageWithText
import com.korchagin.breaking.presentation.Screen
import com.korchagin.breaking.presentation.screens.common.CustomProgressBar
import com.korchagin.breaking.presentation.screens.common.shimmerBrush
import com.korchagin.breaking.presentation.view_model.ElementViewModel
import com.korchagin.breaking.presentation.view_model.MainViewModel
import com.korchagin.breaking.ui.theme.Progress
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ElementsScreen(
    navController: NavController,
    email: String,
    sharedViewModel: ElementViewModel,
    viewModel: MainViewModel = hiltViewModel()
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    selectedTabIndex = sharedViewModel.elementTabPosition
    val state = viewModel.curPupil.collectAsState(initial = null)
    val stateFreeze = viewModel.freezeList.collectAsState(initial = null)
    val statePower = viewModel.powerMoveList.collectAsState(initial = null)
    val stateOfp = viewModel.ofpList.collectAsState(initial = null)
    val stateStretch = viewModel.stretchList.collectAsState(initial = null)
    if (state.value == null) viewModel.getCurrentPupil(email.substringAfter('}'))
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
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(50.dp)
                )
            }
        }

        if (state.value!!.status == Status.SUCCESS) {
            Column {
                state.value!!.data?.let { ProfileSection(it, viewModel) }

                Spacer(modifier = Modifier.height(4.dp))

                Spacer(modifier = Modifier.height(10.dp))

                PostTabView(
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    imageWithTexts = listOf(
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
                ) {
                    sharedViewModel.setPosition(it)
                    selectedTabIndex = it
                }
                when (selectedTabIndex) {
                    0 -> {
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
                                    progress = state.value?.data?.let { it1 ->
                                        setProgress(
                                            elementTitle = it.title,
                                            currentPupil = it1
                                        )
                                    }
                                )
                            )
                        }
                        PostSection(
                            posts = posts,
                            stateElement = stateFreeze.value?.data,
                            navController = navController,
                            modifier = Modifier.fillMaxWidth(),
                            sharedViewModel = sharedViewModel
                        )
                    }


                    1 -> {
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
                                    progress = state.value?.data?.let { it1 ->
                                        setProgress(
                                            elementTitle = it.title,
                                            currentPupil = it1
                                        )
                                    }
                                )
                            )
                        }
                        PostSection(
                            posts = posts,
                            stateElement = statePower.value?.data,
                            navController = navController,
                            modifier = Modifier.fillMaxWidth(),
                            sharedViewModel = sharedViewModel
                        )
                    }

                    2 -> {
                        val posts: MutableList<Elements> =
                            emptyList<Elements>().toMutableList()

                        stateOfp.value?.data?.forEach {
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
                                    progress = state.value?.data?.let { it1 ->
                                        setProgress(
                                            elementTitle = it.title,
                                            currentPupil = it1
                                        )
                                    }
                                )
                            )
                        }
                        PostSection(
                            posts = posts,
                            stateElement = stateOfp.value?.data,
                            navController = navController,
                            modifier = Modifier.fillMaxWidth(),
                            sharedViewModel = sharedViewModel
                        )
                    }

                    3 -> {
                        val posts: MutableList<Elements> =
                            emptyList<Elements>().toMutableList()

                        stateStretch.value?.data?.forEach {
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
                                    progress = state.value?.data?.let { it1 ->
                                        setProgress(
                                            elementTitle = it.title,
                                            currentPupil = it1
                                        )
                                    }
                                )
                            )
                        }
                        PostSection(
                            posts = posts,
                            stateElement = stateStretch.value?.data,
                            navController = navController,
                            modifier = Modifier.fillMaxWidth(),
                            sharedViewModel = sharedViewModel
                        )
                    }
                }
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
fun ProfileSection(curPupil: PupilEntity, viewModel: MainViewModel) {
    val showShimmer = remember { mutableStateOf(true) }
    //  Log.d("ILYA", "ProfileSection run")
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
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            val avatarBorderColor = setAvatarBorder(curPupil)
            Box(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        brush = Brush.horizontalGradient(avatarBorderColor),
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
                    //  .border(2.dp, avatarBorderColor, CircleShape),
                    onSuccess = { showShimmer.value = false },
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            InfoSection(curPupil = curPupil)
        }
    }
    LaunchedEffect(key1 = imageUri) {
        scope.launch {
            //   Log.d("ILYA", "imageUri = $imageUri")
            imageUri?.let {
                Log.d("ILYA", "imageUri = $imageUri")
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)

                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }
            }
            Log.d("ILYA", "bitmap = ${bitmap.value}")
            bitmap.value?.let { viewModel.uploadImage(it) }
        }
    }
}

@Composable
fun InfoSection(
    curPupil: PupilEntity,
    modifier: Modifier = Modifier
) {
    // Log.d("ILYA", "InfoSection run")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        Text("${curPupil.name}@mail.ru")
        Text("Номинация - ${curPupil.name}")
        Text("Уровень - ${curPupil.rating}")
        CustomProgressBar1(progress = curPupil.freeze_rating)
    }
}

@Composable
fun CustomProgressBar1(progress: Float) {
    Column(
        modifier = Modifier
            .padding(end = 10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                // on below line we are specifying
                // height for the box
                .height(30.dp)

                // on below line we are specifying
                // background color for box.
                .background(Color.Gray)

                // on below line we are 
                // specifying width for the box.
                .width(300.dp)
        ) {
            Box(
                modifier = Modifier
                    // on below line we are adding clip \
                    // for the modifier with round radius as 15 dp. 
                    .clip(RoundedCornerShape(15.dp))
                    .height(30.dp)
                    .background(
                        Brush.horizontalGradient(
                            // in this color we are specifying a gradient 
                            // with the list of the colors. 
                            listOf(
                                // on below line we are adding two colors. 
                                Color(0xFF0F9D58),
                                Color(0xF055CA4D)
                            )
                        )
                    )
                    // on below line we are specifying width for the inner box
                    .width(300.dp * progress / 100)
            )
            Text(
                text = "$progress %",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }
}

@Composable
fun PostTabView(
    navController: NavController,
    modifier: Modifier = Modifier,
    sharedViewModel: ElementViewModel,
    imageWithTexts: List<ImageWithText>,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    /* var selectedTabIndex by remember {
         mutableStateOf(0)
     }*/
    var selectedTabIndex = sharedViewModel.elementTabPosition
    val inactiveColor = Color(0xFF777777)
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        contentColor = Color.Black,
        modifier = modifier
    ) {
        imageWithTexts.forEachIndexed { index, item ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = Color.Black,
                unselectedContentColor = inactiveColor,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    horizontalAlignment = CenterHorizontally
                ) {
                    RoundImage(
                        image = item.image,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Text(text = item.text)
                }
                /* Icon(
                     painter = item.image,
                     contentDescription = item.text,
                     tint = if(selectedTabIndex == index) Color.Black else inactiveColor,
                     modifier = Modifier
                         .padding(10.dp)
                         .size(50.dp)
                 )*/
            }
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
    sharedViewModel: ElementViewModel
) {
    val showShimmer = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 57.dp)
    ) {
        itemsIndexed(posts) { index, value ->
            var startBackgroundColor = Color.White
            var endBackgroundColor = setElementColor(value.title)
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
                var borderColor = setElementColor(value.title)
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
                                    ?.let { sharedViewModel.addElement(it) }
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
                            width = 300.dp,
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


