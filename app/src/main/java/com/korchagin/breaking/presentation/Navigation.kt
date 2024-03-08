package com.korchagin.breaking.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.korchagin.breaking.domain.common.EMAIL_KEY
import com.korchagin.breaking.presentation.screens.BboysDetailScreen
import com.korchagin.breaking.presentation.screens.DetailScreen
import com.korchagin.breaking.presentation.screens.ElementsScreen
import com.korchagin.breaking.presentation.screens.LogInScreen
import com.korchagin.breaking.presentation.screens.PasswordRecoveryScreen
import com.korchagin.breaking.presentation.screens.BboysScreen
import com.korchagin.breaking.presentation.screens.RatingScreen
import com.korchagin.breaking.presentation.screens.SignUpScreen
import com.korchagin.breaking.presentation.screens.SplashScreen
import com.korchagin.breaking.presentation.screens.MyAccountScreen
import com.korchagin.breaking.presentation.screens.UserAccountScreen
import com.korchagin.breaking.presentation.screens.common.AppBarState
import com.korchagin.breaking.presentation.view_model.ElementViewModel


@RequiresApi(Build.VERSION_CODES.O)
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
        composable(route = Screen.BboysScreen.route) {
            BboysScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.BboysDetailScreen.route) {
            BboysDetailScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.MyAccountScreen.route) {
            MyAccountScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.UserAccountScreen.route) {
            UserAccountScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(
            route = Screen.DetailScreen.route) {
                DetailScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(
            route = Screen.ElementsScreen.route,
            arguments = listOf(navArgument(EMAIL_KEY) {
                type = NavType.StringType
            })
        ) { entry ->
            entry.arguments?.getString(EMAIL_KEY)?.let { it1 ->
                //          Log.d("ILYA", "email = $it1")
                ElementsScreen(
                    navController = navController,
                    email = it1,
                    sharedViewModel = sharedViewModel
                )
            }
        }
        composable(
            route = Screen.RatingScreen.route
        ) {
            RatingScreen(navController = navController, sharedViewModel = sharedViewModel, onComposing = { appBarState })
        }
    }
}

