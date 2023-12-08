package com.korchagin.breaking.presentation.screens

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.korchagin.breaking.BuildConfig
import com.korchagin.breaking.R
import com.korchagin.breaking.presentation.Screen
import com.korchagin.breaking.presentation.view_model.LogInViewModel
import com.korchagin.breaking.ui.theme.MainGreen
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController, viewModel: LogInViewModel = hiltViewModel()) = Box(
    Modifier
        .fillMaxWidth()
        .fillMaxHeight()
) {

    val scale = remember {
        androidx.compose.animation.core.Animatable(0.0f)
    }
    val stateCurrentEmail = viewModel.currentEmail.collectAsState(initial = null)

    LaunchedEffect(key1 = true) {
        viewModel.checkVerification()
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(800, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        delay(1000)
       // Log.d("ILYA","stateEmail = ${stateCurrentEmail.value}")
        if(stateCurrentEmail.value.isNullOrEmpty()) {
            navController.navigate(Screen.LogInScreen.route) {
                popUpTo(Screen.SplashScreen.route) {
                    inclusive = true
                }
            }
        }
        else{
          //  Log.d("ILYA","email = ${stateCurrentEmail.value}")
            navController.navigate(Screen.ElementsScreen.route + stateCurrentEmail.value) {
            popUpTo(Screen.SplashScreen.route) {
                inclusive = true
            }
        }}
    }

    Image(
        painter = painterResource(id = R.drawable.airflare),
        contentDescription = "",
        alignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
    )

    Text(
        text = "Версия - ${BuildConfig.VERSION_NAME}",
        textAlign = TextAlign.Center,
        color = MainGreen,
        fontSize = 24.sp,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}