package hu.urban.david.sprintformtest.ui.fragment.adapter

data class BoardGameItem(
    val id: String,
    val name: String,
    val img: String,
    var itemState: ItemState
) {
    fun isFavorite(): Boolean {
        return itemState == ItemState.Favorite
    }

    fun isNotFavorite(): Boolean {
        return itemState == ItemState.NotFavorite
    }
}

sealed class ItemState {
    object Favorite : ItemState()
    object NotFavorite : ItemState()
    object Loading : ItemState()
}
