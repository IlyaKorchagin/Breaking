package com.korchagin.breaking.presentation.screens.main_screen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.korchagin.breaking.presentation.view_model.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Button(modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
            .background(color = Color.Red),
            onClick = {
                launcher.launch("image/*")
            })
        {
            Text(text = "Upload Image to FireStore")
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
        //    bitmap.value?.let { mediaViewModel.uploadImage(it) }
            bitmap.value?.let { mainViewModel.uploadImage(it) }
        }
    }

    /*LaunchedEffect(key1 = stateMedia.value?.isSuccess) {
        Log.d("ILYA", "stateMedia = ${stateMedia.value?.isSuccess}")
        scope.launch {
            if (stateCurrentPupil.value != null) {
                Log.d("ILYA", "user = ${stateCurrentPupil.value?.data}")
                stateCurrentPupil.value?.data?.avatar = stateMedia.value?.isSuccess
                val hashMap: HashMap<String?, Any?> = HashMap<String?, Any?>()
                hashMap["avatar"] = stateMedia.value?.isSuccess
                stateCurrentPupil.value!!.data?.id?.toInt()
                    ?.let { mainViewModel.updateAvatar(it, hashMap) }
            }
        }
    }*/


}

