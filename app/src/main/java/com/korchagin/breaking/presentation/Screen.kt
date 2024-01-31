package com.korchagin.breaking.presentation

import com.korchagin.breaking.domain.common.EMAIL_KEY

sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object DetailScreen: Screen("detail_screen")
    object ElementsScreen: Screen("elements_screen/{$EMAIL_KEY}")
    object RatingScreen: Screen("rating_screen")
    object LogInScreen: Screen("log_in_screen")
    object SignUpScreen: Screen("sign_up_screen")
    object SplashScreen: Screen("splash_screen")
    object BboysScreen: Screen("bboys_screen")
    object BboysDetailScreen: Screen("bboys_detailscreen")
    object PasswordRecoveryScreen: Screen("password_recovery_screen")

}
