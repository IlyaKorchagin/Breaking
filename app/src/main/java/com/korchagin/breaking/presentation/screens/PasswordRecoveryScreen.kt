package com.korchagin.breaking.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.korchagin.breaking.R
import com.korchagin.breaking.presentation.Screen
import com.korchagin.breaking.presentation.view_model.LogInViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun PasswordRecoveryScreen(
    navController: NavController,
    viewModel: LogInViewModel = hiltViewModel()
) {

    var email by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.passwordRecoveryState.collectAsState(initial = null)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.passwordRecoveryTitle),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = {
                email = it.trim().lowercase(Locale.ROOT)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp), singleLine = true, placeholder = {
                Text(text = "email")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                scope.launch {
                    viewModel.passwordRecovery(email)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(text = stringResource(R.string.passwordRecoveryButton))

        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.value?.isLoading == true) {
                CircularProgressIndicator()
            }
        }

        LaunchedEffect(key1 = state.value) {
          //  Log.d("ILYA", "state.value = ${state.value}")
            scope.launch {
                if(!state.value?.isSuccess.isNullOrEmpty()) {
                    Toast.makeText(
                        context,
                        "Проверьте вашу почту для подтверждения регистрации",
                        Toast.LENGTH_LONG
                    ).show()
                    delay(1100)
                    navController.navigate(Screen.LogInScreen.route)
                }
            }
        }

    }

}
