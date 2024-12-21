package com.example.fetchtakehomeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fetchtakehomeproject.models.FetchItem
import com.example.fetchtakehomeproject.ui.theme.FetchTakeHomeProjectTheme
import com.example.fetchtakehomeproject.viewModels.MainActivityViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchTakeHomeProjectTheme {
                MainComposable()
            }
        }
    }
}

@Composable
fun MainComposable(
    viewModel: MainActivityViewModel = viewModel()
) {
    viewModel.getFetchItems()

    val itemList by viewModel.itemList.collectAsState()

    LazyColumn (
        modifier = Modifier.padding(horizontal = 5.dp, vertical = 50.dp)
    ) {
        items(itemList) { item ->
            Item(item)
        }
    }
}

@Composable
fun Item(item: FetchItem) {
    Text("id: " + item.id.toString())
    Text("listId: " + item.listId.toString())
    Text("name: " + item.name.toString())
    HorizontalDivider(thickness = 3.dp)
}
