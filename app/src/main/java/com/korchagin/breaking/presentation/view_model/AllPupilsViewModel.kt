package com.korchagin.breaking.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korchagin.breaking.common.Result
import com.korchagin.breaking.common.Status
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.domain.usecase.GetPupilsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllPupilsViewModel @Inject constructor(
    private val getPupils: GetPupilsUseCase
) : ViewModel() {

    val _pupilList = Channel<Result<List<PupilEntity>>>()
    val pupilList = _pupilList.receiveAsFlow()

    init {
            getAllPupils("rating")
    }

    suspend inline fun <T> Flow<T>.collect(crossinline action: suspend (value: T) -> Unit): Unit =
        collect(object : FlowCollector<T> {
            override suspend fun emit(value: T) = action(value)
        })


    fun getAllPupils(filter: String){
        viewModelScope.launch {
          //  Log.d("ILYA", "filter =  $filter")
            getPupils.invoke(filter).collect{
               // Log.d("ILYA", "get ${it.status}")
                _pupilList.send(Result.loading(null))
                when (it.status) {
                    Status.SUCCESS -> {
                      //  Log.d("ILYA", "Success get data")
                        it.data?.let { items ->
                        //    Log.d("ILYA", "Success get data $items")
                            _pupilList.send(Result.success(items))
                        }
                    }
                    Status.ERROR -> {
                     //   Log.d("ILYA", "Success get error")
                        _pupilList.send( Result.error(
                            "Failed to grab items from Firebase",
                            emptyList<PupilEntity>()
                        ))
                    }
                    Status.LOADING -> {
                      //  Log.d("ILYA", "Loading get")
                        _pupilList.send( Result.loading(null))
                    }
                }
            }
        }
    }
}

