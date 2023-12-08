package com.korchagin.breaking.presentation.view_model

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.korchagin.breaking.common.Resource
import com.korchagin.breaking.common.Result
import com.korchagin.breaking.common.Status
import com.korchagin.breaking.domain.common.EMAIL_KEY
import com.korchagin.breaking.domain.common.Preferences
import com.korchagin.breaking.domain.model.ElementEntity
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.domain.usecase.*
import com.korchagin.breaking.presentation.model.MediaState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.set


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentPupilUseCase: GetCurrentPupilUseCase,
    private val updateAvatarUseCase: UpdateAvatarUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val getFreezeElementsUseCase: GetFreezeElementsUseCase,
    private val getPowerMoveElementsUseCase: GetPowerMoveElementsUseCase,
    private val getOfpElementsUseCase: GetOfpElementsUseCase,
    private val getStretchElementsUseCase: GetStretchElementsUseCase,
    private val preferences: Preferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val _curPupil = Channel<Result<PupilEntity>>()
    val curPupil = _curPupil.receiveAsFlow()

    private val _mediaState = Channel<MediaState>()
    val mediaState = _mediaState.receiveAsFlow()

    val _freezeList = Channel<Result<List<ElementEntity>>>()
    val freezeList = _freezeList.receiveAsFlow()

    val _powerMoveList = Channel<Result<List<ElementEntity>>>()
    val powerMoveList = _powerMoveList
        .receiveAsFlow()

    val _ofpList = Channel<Result<List<ElementEntity>>>()
    val ofpList = _ofpList.receiveAsFlow()

    val _stretchList = Channel<Result<List<ElementEntity>>>()
    val stretchList = _stretchList
        .receiveAsFlow()

    init {
        savedStateHandle.get<String>(EMAIL_KEY)?.let { email ->
            getCurrentPupil(email.substringAfter('}'))
            getFreezeElements()
            getPowerMoveElements()
            getOfpElements()
            getStretchElements()
        }
    }

    fun getFreezeElements() {
        viewModelScope.launch {
            getFreezeElementsUseCase.invoke().collect {
              //    Log.d("ILYA", "get freeze - ${it.status} | ${it.data}")
                _freezeList.send(Result.loading(null))
                when (it.status) {
                    Status.SUCCESS -> {
                 //       Log.d("ILYA", "Success get data")
                        it.data?.let { items ->
                       //     Log.d("ILYA", "Success get data $items")
                            _freezeList.send(Result.success(items))
                        }
                    }
                    Status.ERROR -> {
                    //    Log.d("ILYA", "Success get error")
                        _freezeList.send(
                            Result.error(
                                "Failed to grab items from Firebase",
                                null
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun getPowerMoveElements() {
        viewModelScope.launch {
            getPowerMoveElementsUseCase.invoke().collect {
                    Log.d("ILYA", "get power - ${it.status} | ${it.data}")
                _powerMoveList.send(Result.loading(null))
                when (it.status) {
                    Status.SUCCESS -> {
                               Log.d("ILYA", "Success get data")
                        it.data?.let { items ->
                                 Log.d("ILYA", "Success get data $items")
                            _powerMoveList.send(Result.success(items))
                        }
                    }
                    Status.ERROR -> {
                        //    Log.d("ILYA", "Success get error")
                        _powerMoveList.send(
                            Result.error(
                                "Failed to grab items from Firebase",
                                null
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun getOfpElements() {
        viewModelScope.launch {
            getOfpElementsUseCase.invoke().collect {
                Log.d("ILYA", "get power - ${it.status} | ${it.data}")
                _ofpList.send(Result.loading(null))
                when (it.status) {
                    Status.SUCCESS -> {
                        Log.d("ILYA", "Success get data")
                        it.data?.let { items ->
                            Log.d("ILYA", "Success get data $items")
                            _ofpList.send(Result.success(items))
                        }
                    }
                    Status.ERROR -> {
                        //    Log.d("ILYA", "Success get error")
                        _ofpList.send(
                            Result.error(
                                "Failed to grab items from Firebase",
                                null
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun getStretchElements() {
        viewModelScope.launch {
            getStretchElementsUseCase.invoke().collect {
                Log.d("ILYA", "get power - ${it.status} | ${it.data}")
                _stretchList.send(Result.loading(null))
                when (it.status) {
                    Status.SUCCESS -> {
                        Log.d("ILYA", "Success get data")
                        it.data?.let { items ->
                            Log.d("ILYA", "Success get data $items")
                            _stretchList.send(Result.success(items))
                        }
                    }
                    Status.ERROR -> {
                        //    Log.d("ILYA", "Success get error")
                        _stretchList.send(
                            Result.error(
                                "Failed to grab items from Firebase",
                                null
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun getCurrentPupil(email: String) {
        viewModelScope.launch {
            getCurrentPupilUseCase.invoke(email).collect {
                //  Log.d("ILYA", "get ${it.status}")
                _curPupil.send(Result.loading(null))
                when (it.status) {
                    Status.SUCCESS -> {
                    //    Log.d("ILYA", "Success get data")
                        it.data?.let { items ->
                         //   Log.d("ILYA", "Success get data $items")
                            _curPupil.send(Result.success(items))
                            items.id?.let { it1 -> preferences.storeCurrentPupilId(it1) }
                        }
                    }
                    Status.ERROR -> {
                     //   Log.d("ILYA", "Success get error")
                        _curPupil.send(
                            Result.error(
                                "Failed to grab items from Firebase",
                                null
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun updateAvatar(userId: String, userAvatar: HashMap<String?, Any?>) {
        viewModelScope.launch {
            updateAvatarUseCase.invoke(userId = userId, userAvatar = userAvatar).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                    //    Log.d("ILYA", "Success update data")
                        /*  it.data?.let { items ->
                              Log.d("ILYA", "Success get data $items")
                              _curPupil.send(Result.success(items))
                          }*/
                    }
                    Status.ERROR -> {
                 //       Log.d("ILYA", "update error")
                        /*_curPupil.send( Result.error(
                            "Failed to grab items from Firebase",
                            PupilEntity()
                        )
                        )*/
                    }
                    else -> {}
                }
            }
        }
    }

    fun uploadImage(bitmap: Bitmap) = viewModelScope.launch {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        Log.d("ILYA", "uploadImage data = $data")
        uploadImageUseCase.invoke(data).collect { result ->
            Log.d("ILYA", "uploadImage result = ${result.data}")
            when (result) {
                is Resource.Success -> {
                    Log.d("ILYA", "Success - ${result.data}")
                    _mediaState.send(MediaState(isSuccess = result.data))
                    val hashMap: HashMap<String?, Any?> = HashMap<String?, Any?>()
                    hashMap["avatar"] = result.data
                    val id = preferences.getCurrentPupilId()
                    Log.d("ILYA", "Success currentPupilId - $id")
                    updateAvatar(id, hashMap)
                }
                is Resource.Loading -> {
                    _mediaState.send(MediaState(isLoading = true))
                }
                is Resource.Error -> {
                    _mediaState.send(MediaState(isError = result.message))
                }
            }
        }
    }


}
