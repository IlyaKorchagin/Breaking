package com.korchagin.breaking.presentation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.korchagin.breaking.domain.common.EMAIL_KEY
import com.korchagin.breaking.presentation.screens.*
import com.korchagin.breaking.presentation.screens.common.AppBarState
import com.korchagin.breaking.presentation.screens.main_screen.MainScreen
import com.korchagin.breaking.presentation.view_model.ElementViewModel
import com.korchagin.breaking.presentation.view_model.SharedViewModel


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController, appBarState: AppBarState) {
    val sharedViewModel: ElementViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {

        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.PasswordRecoveryScreen.route) {
            PasswordRecoveryScreen(navController = navController)
        }
        composable(route = Screen.LogInScreen.route) {
            LogInScreen(navController = navController)
        }
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = Screen.DetailScreen.route
        ) {
            DetailScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.ElementsScreen.route,
            arguments = listOf(navArgument(EMAIL_KEY) {
                type = NavType.StringType
            })
        ) { entry ->
            entry.arguments?.getString("$EMAIL_KEY")?.let { it1 ->
                ElementsScreen(navController = navController, email = it1, sharedViewModel = sharedViewModel)
            }
        }
        composable(
            route = Screen.RatingScreen.route
        ) {
            RatingScreen(navController = navController, onComposing = {appBarState})
        }
    }
}

