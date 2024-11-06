package br.com.livrokotlin.listadecompras.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.livrokotlin.listadecompras.data.ItemEntity
import br.com.livrokotlin.listadecompras.data.ItemsDatabase
import br.com.livrokotlin.listadecompras.data.toModel
import br.com.livrokotlin.listadecompras.model.ItemModel
import br.com.livrokotlin.listadecompras.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ItemsViewModel(
    private val database: ItemsDatabase
): ViewModel() {
    val itemsLiveData = MutableLiveData<List<ItemModel>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAll()
        }
    }

    fun addItem(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = ItemEntity(name = name)
            database.itemsDao().insert(entity)
            fetchAll()
        }
    }

    private suspend fun fetchAll() {
        val result = database.itemsDao().getAll().map {
            it.toModel(onRemove = ::removeItem)
        }
        itemsLiveData.postValue(result)
    }

    private fun removeItem(item: ItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = item.toEntity()
            database.itemsDao().delete(entity)
            fetchAll()
        }
    }
}

