package com.example.fetchtakehomeproject.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchtakehomeproject.models.FetchItem
import com.example.fetchtakehomeproject.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {


    private val _itemList = MutableStateFlow<List<FetchItem>>(emptyList())
    val itemList: StateFlow<List<FetchItem>> = _itemList
    private var job: Job? = null

    fun getFetchItems() {
        job = viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.getFetchItems()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = sortItems(response.body()!!)
                    _itemList.emit(items)
                    Log.d("VIEWMODEL", "SUCCESS")
                } else {
                    Log.d("VIEWMODEL", "FAILED")
                }
            }
        }
    }

    private fun sortItems(items: List<FetchItem>): List<FetchItem> {
        val regex = Regex("""\d+$""")
        return items.sortedWith (
            compareBy<FetchItem> { it.listId }
                .thenBy {
                    val result = regex.find(it.name.toString())
                    result?.value?.toIntOrNull() ?: 0
                }
        ).filterNot {
            it.name.isNullOrEmpty()
        }
    }



    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}