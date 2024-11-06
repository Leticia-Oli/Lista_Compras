package br.com.livrokotlin.listadecompras.model

import br.com.livrokotlin.listadecompras.data.ItemEntity

data class ItemModel(
    val id: Long = 0,
    val name: String,
    val onRemove:(ItemModel) -> Unit
)
fun ItemModel.toEntity(): ItemEntity {
    return ItemEntity(
        id = this.id,
        name = this.name
    )
}
