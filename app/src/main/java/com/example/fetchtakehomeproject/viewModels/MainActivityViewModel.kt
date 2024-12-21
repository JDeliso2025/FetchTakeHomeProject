package com.example.fetchtakehomeproject.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fetchtakehomeproject.models.FetchItem
import com.example.fetchtakehomeproject.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel: ViewModel() {

    val errorMessage = MutableLiveData<String>()

    val itemList = MutableLiveData<List<FetchItem>>()
    var job: Job? = null

    fun getFetchItems() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.getFetchItems()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    itemList.postValue(response.body())
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}