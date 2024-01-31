package com.korchagin.breaking.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
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
fun SignUpScreen(
    navController: NavController,
    viewModel: LogInViewModel = hiltViewModel()
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.registration),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = stringResource(id = R.string.email)) },
            placeholder = { Text(text = stringResource(id = R.string.yourEmail)) },
            onValueChange = {
                email = it.trim().lowercase(Locale.ROOT)
            }
        )

        OutlinedTextField(
            value = password,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = null) },
            label = { Text(text = stringResource(id = R.string.password)) },
            placeholder = { Text(text = stringResource(id = R.string.yourPassword)) },
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                password = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )


        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = null) },
            label = { Text(text = stringResource(id = R.string.name)) },
            placeholder = { Text(text = stringResource(id = R.string.yourName)) },
            onValueChange = {
                name = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    viewModel.registerUser(email, password, name)
                    Toast.makeText(context, "Проверьте вашу почту для завершения регистрации", Toast.LENGTH_LONG).show()
                    delay(1500)
                    navController.navigate(Screen.LogInScreen.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(text = stringResource(R.string.registrate))

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

        LaunchedEffect(key1 = state.value?.isError) {
            scope.launch {
                if (state.value?.isError?.isNotEmpty() == true) {
                    val error = state.value?.isError
                    Toast.makeText(context, "$error", Toast.LENGTH_LONG)
                }
            }
        }

       /* LaunchedEffect(key1 = state.value?.isVerification) {
            scope.launch {
                if (state.value?.isVerification == false) {
                  Log.d("ILYA","isVerification = ${state.value?.isVerification}")
                    Toast.makeText(context, "Проверьте вашу почту для завершения операции", Toast.LENGTH_LONG).show()
                }
            }
        }*/
    }

}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = NavController(LocalContext.current))
}